package tn.esprit.elharizi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.elharizi.domain.Contrat;
import tn.esprit.elharizi.domain.ModePaiement;
import tn.esprit.elharizi.domain.Paiement;
import tn.esprit.elharizi.domain.Reservation;
import tn.esprit.elharizi.repository.IContratRepository;
import tn.esprit.elharizi.repository.IReservationRepository;
import tn.esprit.elharizi.service.location.legacy.SmsGateway;
import tn.esprit.elharizi.service.location.legacy.SmtpClient;

/**
 * Confirmation des reservations.
 * Ecrit en urgence par le stagiaire. NE PAS MODIFIER : ca marche.
 *
 * Fichier fourni pour l'Atelier 6. Adaptez uniquement les noms des
 * getters/setters si votre modele (Atelier 2) utilise d'autres noms
 * (ex. getIdReservation() au lieu de getId()).
 */
@Service
public class LocationManager {
    @Autowired private IReservationRepository reservationRepository;
    @Autowired private IContratRepository contratRepository;

    public Contrat confirmerReservation(Long reservationId, String modePaiement,
                                        String codePromo, String canal) {
        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) throw new RuntimeException("Reservation introuvable");

        // ---------- 1. disponibilite ----------
        for (Reservation x : reservationRepository.findAll()) {
            if (x.getVehicule().getIdVehicule().equals(r.getVehicule().getIdVehicule())
                    && !x.getIdReservation().equals(r.getIdReservation())
                    && !(r.getDateFin().isBefore(x.getDateDebut())
                    || r.getDateDebut().isAfter(x.getDateFin()))) {
                throw new RuntimeException("Vehicule indisponible");
            }
        }

        // ---------- 2. tarif de base ----------
        long j = ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()) + 1;
        double tarifJournalier = r.getVehicule().getTarifJournal() != null
                ? r.getVehicule().getTarifJournal().doubleValue()
                : 0.0;
        double m = j * tarifJournalier;

        // ---------- 3. remises ----------
        String t = r.getClient().getType() != null ? r.getClient().getType() : "PARTICULIER";
        if (t.equals("PARTICULIER")) {
            if (j >= 7) m = m * 0.90;
        } else if (t.equals("ENTREPRISE")) {
            m = m * 0.85;
        } else if (t.equals("VIP")) {
            m = m * 0.80;
        }
        if (codePromo != null && codePromo.equals("BIENVENUE")) {
            m = m - 20;
        }

        // ---------- 4. paiement ----------
        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        if (modePaiement.equalsIgnoreCase("CARTE")) {
            System.out.println("Debit de la carte bancaire...");
            m = m * 1.01; // 1 % de frais
            p.setModePaiement(ModePaiement.CARTE);
        } else if (modePaiement.equalsIgnoreCase("VIREMENT")) {
            System.out.println("Attente du virement...");
            p.setModePaiement(ModePaiement.VIREMENT);
        } else if (modePaiement.equalsIgnoreCase("ESPECES")) {
            System.out.println("Encaissement en especes a l'agence");
            p.setModePaiement(ModePaiement.ESPECES);
        } else {
            throw new RuntimeException("Mode de paiement inconnu : " + modePaiement);
        }
        p.setMontant(BigDecimal.valueOf(m));

        // ---------- 5. contrat ----------
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(BigDecimal.valueOf(m));
        c.setValide(true);
        c.getPaiements().add(p);
        p.setContrat(c);
        Contrat res = contratRepository.save(c);

        // ---------- 6. notification ----------
        if (canal.equalsIgnoreCase("EMAIL")) {
            SmtpClient smtp = new SmtpClient("smtp.autoloc.tn", 25);
            smtp.send(r.getClient().getEmail(), "Contrat " + res.getIdContrat() + " confirme");
        } else if (canal.equalsIgnoreCase("SMS")) {
            SmsGateway sms = new SmsGateway("https://sms.autoloc.tn");
            sms.send(r.getClient().getTelephone(), "Contrat " + res.getIdContrat() + " confirme");
        }

        return res;
    }
}
