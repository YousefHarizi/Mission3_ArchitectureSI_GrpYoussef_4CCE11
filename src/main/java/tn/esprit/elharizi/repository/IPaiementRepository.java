package tn.esprit.elharizi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.elharizi.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
