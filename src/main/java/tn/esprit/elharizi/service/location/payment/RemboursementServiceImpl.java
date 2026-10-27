package tn.esprit.elharizi.service.location.payment;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.elharizi.domain.Contrat;
import tn.esprit.elharizi.domain.Paiement;
import tn.esprit.elharizi.domain.Reservation;
import tn.esprit.elharizi.domain.StatutReservation;
import tn.esprit.elharizi.exception.ResourceNotFoundException;
import tn.esprit.elharizi.repository.IContratRepository;
import tn.esprit.elharizi.repository.IReservationRepository;

/**
 * Implementation du service de remboursement respectant les principes SOLID :
 * - LSP : Aucun sous-type de mode de paiement ne leve d'exception inattendue.
 * - ISP : Separation nette entre IModePaiement (payer) et IRemboursable (rembourser).
 * - OCP : De nouveaux modes de paiement (ex: Wallet, Crypto) peuvent etre ajoutes sans modifier ce code.
 * - DIP & Injection par constructeur conforme aux regles SonarQube (S6813).
 */
@Service
@RequiredArgsConstructor
public class RemboursementServiceImpl implements IRemboursementService {

    private final ModePaiementRegistry modePaiementRegistry;
    private final IReservationRepository reservationRepository;
    private final IContratRepository contratRepository;

    @Override
    public RemboursementResult traiterRemboursement(Paiement paiement) {
        if (paiement == null) {
            return RemboursementResult.builder()
                    .succes(false)
                    .statut(RemboursementResult.Statut.AUCUN_PAIEMENT_A_REMBOURSER)
                    .montantRembourse(BigDecimal.ZERO)
                    .message("Aucun paiement a rembourser.")
                    .build();
        }

        Optional<IRemboursable> remboursableOpt = modePaiementRegistry.getRemboursable(paiement.getModePaiement());

        if (remboursableOpt.isPresent()) {
            // Remboursement automatique valide (Carte, Virement, etc.)
            remboursableOpt.get().rembourser(paiement);

            return RemboursementResult.builder()
                    .succes(true)
                    .statut(RemboursementResult.Statut.EFFECTUE)
                    .montantRembourse(paiement.getMontant())
                    .message("Remboursement de " + paiement.getMontant() + " TND effectue avec succes via "
                            + paiement.getModePaiement() + ".")
                    .build();
        } else {
            // Cas des ESPECES : impossible de rembourser automatiquement par le systeme
            // Gestion metier propre SANS declencher de RuntimeException ou UnsupportedOperationException
            System.out.println("[AUDIT GESTION] Paiement en especes ID " + paiement.getIdPaiement()
                    + " : remboursement cash automatique non autorise. Emission d'un bon d'achat client.");

            return RemboursementResult.builder()
                    .succes(false)
                    .statut(RemboursementResult.Statut.NON_REMBOURSABLE_ESPECES)
                    .montantRembourse(BigDecimal.ZERO)
                    .message("Remboursement impossible en especes : le cash ne peut pas etre credite a distance. "
                            + "Un avoir a ete genere ou veuillez vous presenter a l'agence.")
                    .build();
        }
    }

    @Override
    @Transactional
    public RemboursementResult annulerEtRembourserReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", reservationId));

        // Mise a jour du statut de la reservation
        reservation.setStatut(StatutReservation.ANNULEE);
        reservationRepository.save(reservation);

        // Verification du contrat et des paiements lies
        Contrat contrat = reservation.getContrat();
        if (contrat == null || contrat.getPaiements() == null || contrat.getPaiements().isEmpty()) {
            return RemboursementResult.builder()
                    .succes(true)
                    .statut(RemboursementResult.Statut.AUCUN_PAIEMENT_A_REMBOURSER)
                    .montantRembourse(BigDecimal.ZERO)
                    .message("Reservation " + reservationId + " annulee. Aucun paiement effectue prealablement.")
                    .build();
        }

        // On invalide le contrat
        contrat.setValide(false);
        contratRepository.save(contrat);

        // Remboursement du dernier paiement effectue
        Paiement dernierPaiement = contrat.getPaiements().get(contrat.getPaiements().size() - 1);
        return traiterRemboursement(dernierPaiement);
    }
}
