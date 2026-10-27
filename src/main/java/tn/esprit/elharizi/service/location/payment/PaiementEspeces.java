package tn.esprit.elharizi.service.location.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Component;
import tn.esprit.elharizi.domain.ModePaiement;
import tn.esprit.elharizi.domain.Paiement;

/**
 * Gestion des paiements en especes au comptoir de l'agence.
 * 
 * POINT CLE ARCHITECTURAL (SOLID / LSP) :
 * Les especes ne peuvent pas faire l'objet d'un remboursement automatique ou a distance.
 * Dans l'ancienne conception du stagiaire, cette classe implementait IModePaiement avec :
 *   public void rembourser(Paiement p) { throw new UnsupportedOperationException("impossible"); }
 * Cela constituait une violation majeure du Principe de Substitution de Liskov (LSP).
 * 
 * Dans notre solution, PaiementEspeces n'implemente QUE IModePaiement.
 * Elle n'implemente pas IRemboursable, eliminant ainsi definitivement l'exception inattendue.
 */
@Component
public class PaiementEspeces implements IModePaiement {

    @Override
    public ModePaiement getMode() {
        return ModePaiement.ESPECES;
    }

    @Override
    public Paiement payer(BigDecimal montant) {
        System.out.println("[ESPECES] Encaissement en especes effectue a l'agence.");

        return Paiement.builder()
                .datePaiement(LocalDate.now())
                .modePaiement(ModePaiement.ESPECES)
                .montant(montant)
                .build();
    }
}
