package tn.esprit.elharizi;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tn.esprit.elharizi.domain.ModePaiement;
import tn.esprit.elharizi.domain.Paiement;
import tn.esprit.elharizi.repository.IContratRepository;
import tn.esprit.elharizi.repository.IReservationRepository;
import tn.esprit.elharizi.service.location.payment.*;

import static org.junit.jupiter.api.Assertions.*;

class Mission3LspTest {

    // =========================================================================
    // 1. DEMONSTRATION DU PROBLEME DU STAGIAIRE (Violation de LSP)
    // =========================================================================

    // Interface mal concue du stagiaire (comme donnee dans le sujet de Mission 3)
    interface IModePaiementStagiaire {
        Paiement payer(double montant);
        void rembourser(Paiement paiement); // Force tous les modes a implementer ceci
    }

    class PaiementEspecesStagiaire implements IModePaiementStagiaire {
        @Override
        public Paiement payer(double montant) {
            return new Paiement();
        }

        @Override
        public void rembourser(Paiement paiement) {
            // Le stagiaire jette une exception a l'execution car les especes ne sont pas remboursables !
            throw new UnsupportedOperationException("impossible");
        }
    }

    @Test
    @DisplayName("Preuve de la violation de LSP dans la proposition du stagiaire")
    void testViolationLSPStagiaire() {
        IModePaiementStagiaire mode = new PaiementEspecesStagiaire();
        Paiement paiement = new Paiement();

        // Le code appelant s'attend legitimement a pouvoir appeler rembourser(...) sans planter
        assertThrows(UnsupportedOperationException.class, () -> {
            mode.rembourser(paiement);
        }, "La proposition du stagiaire viole le principe de Liskov en jetant UnsupportedOperationException a l'execution");
    }

    // =========================================================================
    // 2. DEMONSTRATION DU CODE CORRIGE (Respect strict de LSP & ISP)
    // =========================================================================

    @Test
    @DisplayName("Respect de LSP : Carte et Virement supportent le remboursement sans erreur")
    void testCarteEtVirementRemboursementValide() {
        PaiementCarte carte = new PaiementCarte();
        PaiementVirement virement = new PaiementVirement();

        // Verifications polymorphiques
        assertTrue(carte instanceof IRemboursable);
        assertTrue(virement instanceof IRemboursable);

        Paiement pCarte = carte.payer(BigDecimal.valueOf(150.0));
        assertNotNull(pCarte);
        assertEquals(ModePaiement.CARTE, pCarte.getModePaiement());
        assertDoesNotThrow(() -> carte.rembourser(pCarte));

        Paiement pVirement = virement.payer(BigDecimal.valueOf(200.0));
        assertNotNull(pVirement);
        assertEquals(ModePaiement.VIREMENT, pVirement.getModePaiement());
        assertDoesNotThrow(() -> virement.rembourser(pVirement));
    }

    @Test
    @DisplayName("Respect de LSP : Especes n'implemente pas IRemboursable et ne jette pas d'exception")
    void testEspecesConformeLSP() {
        PaiementEspeces especes = new PaiementEspeces();

        // Verification du contrat : especes n'est PAS un sous-type de IRemboursable !
        assertFalse(especes instanceof IRemboursable,
                "PaiementEspeces ne doit pas implementer IRemboursable (principe ISP/LSP)");

        Paiement p = especes.payer(BigDecimal.valueOf(100.0));
        assertNotNull(p);
        assertEquals(ModePaiement.ESPECES, p.getModePaiement());

        // Test avec le service de gestion de remboursement
        ModePaiementRegistry registry = new ModePaiementRegistry(List.of(
                new PaiementCarte(),
                new PaiementVirement(),
                new PaiementEspeces()
        ));

        IReservationRepository mockReservationRepo = Mockito.mock(IReservationRepository.class);
        IContratRepository mockContratRepo = Mockito.mock(IContratRepository.class);
        RemboursementServiceImpl service = new RemboursementServiceImpl(registry, mockReservationRepo, mockContratRepo);

        // Appel du remboursement : AUCUNE EXCEPTION levée, retour métier propre
        RemboursementResult result = service.traiterRemboursement(p);
        assertNotNull(result);
        assertFalse(result.isSucces());
        assertEquals(RemboursementResult.Statut.NON_REMBOURSABLE_ESPECES, result.getStatut());
        assertTrue(result.getMessage().contains("Remboursement impossible en especes"));
    }
}
