package tn.esprit.elharizi.service.location.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Component;
import tn.esprit.elharizi.domain.ModePaiement;
import tn.esprit.elharizi.domain.Paiement;

/**
 * Gestion des paiements et remboursements par carte bancaire.
 * Supporte le paiement et le remboursement (LSP respecte).
 */
@Component
public class PaiementCarte implements IModePaiementRemboursable {

    @Override
    public ModePaiement getMode() {
        return ModePaiement.CARTE;
    }

    @Override
    public Paiement payer(BigDecimal montant) {
        System.out.println("[CARTE] Debit de la carte bancaire...");
        // 1% de frais de transaction
        BigDecimal montantAvecFrais = montant.multiply(BigDecimal.valueOf(1.01));

        return Paiement.builder()
                .datePaiement(LocalDate.now())
                .modePaiement(ModePaiement.CARTE)
                .montant(montantAvecFrais)
                .build();
    }

    @Override
    public void rembourser(Paiement paiement) {
        System.out.println("[CARTE] Recredit du montant " + paiement.getMontant() + " TND sur la carte bancaire.");
    }
}
