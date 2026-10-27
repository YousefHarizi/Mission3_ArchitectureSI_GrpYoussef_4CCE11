package tn.esprit.elharizi.service.location.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Component;
import tn.esprit.elharizi.domain.ModePaiement;
import tn.esprit.elharizi.domain.Paiement;

/**
 * Gestion des paiements et remboursements par virement bancaire.
 * Supporte le paiement et le remboursement (LSP respecte).
 */
@Component
public class PaiementVirement implements IModePaiementRemboursable {

    @Override
    public ModePaiement getMode() {
        return ModePaiement.VIREMENT;
    }

    @Override
    public Paiement payer(BigDecimal montant) {
        System.out.println("[VIREMENT] Enregistrement en attente de reception du virement bancaire...");

        return Paiement.builder()
                .datePaiement(LocalDate.now())
                .modePaiement(ModePaiement.VIREMENT)
                .montant(montant)
                .build();
    }

    @Override
    public void rembourser(Paiement paiement) {
        System.out.println("[VIREMENT] Emission de l'ordre de virement de remboursement pour un montant de "
                + paiement.getMontant() + " TND.");
    }
}
