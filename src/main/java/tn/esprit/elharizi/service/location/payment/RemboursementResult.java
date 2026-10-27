package tn.esprit.elharizi.service.location.payment;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemboursementResult {

    public enum Statut {
        EFFECTUE,
        NON_REMBOURSABLE_ESPECES,
        AUCUN_PAIEMENT_A_REMBOURSER
    }

    private Statut statut;
    private BigDecimal montantRembourse;
    private String message;
    private boolean succes;
}
