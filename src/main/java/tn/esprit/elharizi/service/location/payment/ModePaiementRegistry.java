package tn.esprit.elharizi.service.location.payment;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import tn.esprit.elharizi.domain.ModePaiement;

/**
 * Registre centralisant les strategies de paiement disponibles.
 * Utilise l'injection par constructeur Spring (conforme Atelier 4 / S6813).
 */
@Component
public class ModePaiementRegistry {

    private final Map<ModePaiement, IModePaiement> registry = new EnumMap<>(ModePaiement.class);

    public ModePaiementRegistry(List<IModePaiement> modes) {
        for (IModePaiement mode : modes) {
            if (mode.getMode() != null) {
                registry.put(mode.getMode(), mode);
            }
        }
    }

    /**
     * Recupere la strategie de paiement associee au mode demande.
     */
    public IModePaiement getModePaiement(ModePaiement mode) {
        IModePaiement modePaiement = registry.get(mode);
        if (modePaiement == null) {
            throw new IllegalArgumentException("Mode de paiement non supporte : " + mode);
        }
        return modePaiement;
    }

    /**
     * Verifie si un mode de paiement supporte le remboursement sans provoquer d'exception.
     */
    public boolean estRemboursable(ModePaiement mode) {
        IModePaiement p = registry.get(mode);
        return p instanceof IRemboursable;
    }

    /**
     * Recupere la capacite de remboursement si disponible.
     */
    public Optional<IRemboursable> getRemboursable(ModePaiement mode) {
        IModePaiement p = registry.get(mode);
        if (p instanceof IRemboursable remboursable) {
            return Optional.of(remboursable);
        }
        return Optional.empty();
    }
}
