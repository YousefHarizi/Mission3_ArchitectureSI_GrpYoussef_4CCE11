package tn.esprit.elharizi.service.location.payment;

import tn.esprit.elharizi.domain.Paiement;

/**
 * Interface specifique de capacite pour les modes de paiement qui supportent
 * le remboursement automatique (ex: Carte, Virement).
 * 
 * En isolant cette capacite dans une interface separee (Principe ISP),
 * les classes qui ne supportent pas le remboursement (comme PaiementEspeces)
 * ne sont pas forcees de l'implementer avec une UnsupportedOperationException,
 * ce qui garantit le respect strict du Principe de Substitution de Liskov (LSP).
 */
public interface IRemboursable {

    /**
     * Effectue le remboursement d'un paiement prealablement encaisse.
     *
     * @param paiement Le paiement a rembourser
     */
    void rembourser(Paiement paiement);
}
