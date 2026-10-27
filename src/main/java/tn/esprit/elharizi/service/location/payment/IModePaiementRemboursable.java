package tn.esprit.elharizi.service.location.payment;

/**
 * Interface combinant paiement et remboursement pour les modes
 * qui supportent les deux operations.
 */
public interface IModePaiementRemboursable extends IModePaiement, IRemboursable {
}
