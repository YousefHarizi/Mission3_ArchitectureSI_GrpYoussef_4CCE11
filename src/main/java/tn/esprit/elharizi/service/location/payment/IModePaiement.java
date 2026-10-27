package tn.esprit.elharizi.service.location.payment;

import java.math.BigDecimal;
import tn.esprit.elharizi.domain.ModePaiement;
import tn.esprit.elharizi.domain.Paiement;

/**
 * Contrat de base pour tous les modes de paiement.
 * Tout mode de paiement DOIT pouvoir encaisser un paiement.
 * 
 * Respecte le principe de responsabilite unique (SRP) et le principe de 
 * substitution de Liskov (LSP) en ne declarant que les capacites universelles.
 */
public interface IModePaiement {

    /**
     * Identifie le type de mode de paiement gere par cette implementation.
     */
    ModePaiement getMode();

    /**
     * Enregistre un reglement pour le montant donne.
     *
     * @param montant Montant a payer
     * @return Entite Paiement initialisee
     */
    Paiement payer(BigDecimal montant);
}
