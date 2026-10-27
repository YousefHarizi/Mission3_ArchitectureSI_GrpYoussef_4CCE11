package tn.esprit.elharizi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.elharizi.domain.ModePaiement;
import tn.esprit.elharizi.domain.Paiement;
import tn.esprit.elharizi.service.location.payment.IModePaiement;
import tn.esprit.elharizi.service.location.payment.IRemboursementService;
import tn.esprit.elharizi.service.location.payment.ModePaiementRegistry;
import tn.esprit.elharizi.service.location.payment.RemboursementResult;

/**
 * Controleur REST dedie a la Mission 3 (Remboursement & Annulation).
 * Permet de tester facilement via Postman.
 */
@RestController
@RequestMapping("/remboursements")
@RequiredArgsConstructor
@Tag(name = "Mission 3 - Remboursements", description = "API de gestion des remboursements et annulations (LSP)")
public class RemboursementRestController {

    private final IRemboursementService remboursementService;
    private final ModePaiementRegistry modePaiementRegistry;

    @Operation(summary = "Annuler et rembourser une reservation par son ID")
    @PostMapping("/reservations/{id}/annuler")
    public ResponseEntity<RemboursementResult> annulerEtRembourser(
            @Parameter(description = "Identifiant de la reservation") @PathVariable Long id) {
        RemboursementResult result = remboursementService.annulerEtRembourserReservation(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Tester en direct le remboursement selon le mode (CARTE, VIREMENT, ESPECES) pour Postman")
    @PostMapping("/simuler")
    public ResponseEntity<RemboursementResult> simulerRemboursement(
            @Parameter(description = "Mode de paiement : CARTE, VIREMENT ou ESPECES") 
            @RequestParam(defaultValue = "CARTE") ModePaiement mode,
            @Parameter(description = "Montant en TND") 
            @RequestParam(defaultValue = "150.0") BigDecimal montant) {

        IModePaiement handler = modePaiementRegistry.getModePaiement(mode);
        Paiement paiement = handler.payer(montant);

        // Appel du service de remboursement (conforme au LSP)
        RemboursementResult result = remboursementService.traiterRemboursement(paiement);
        return ResponseEntity.ok(result);
    }
}
