package tn.esprit.elharizi.service.location.payment;

import tn.esprit.elharizi.domain.Paiement;

public interface IRemboursementService {

    /**
     * Traite le remboursement d'un paiement en appliquant le bon comportement
     * sans jamais lever d'UnsupportedOperationException inattendue.
     *
     * @param paiement Le paiement a traiter
     * @return Le resultat detaille de l'operation
     */
    RemboursementResult traiterRemboursement(Paiement paiement);

    /**
     * Traite l'annulation et le remboursement d'une reservation identifiee par son ID.
     *
     * @param reservationId Identifiant de la reservation
     * @return Le resultat de l'operation
     */
    RemboursementResult annulerEtRembourserReservation(Long reservationId);
}
