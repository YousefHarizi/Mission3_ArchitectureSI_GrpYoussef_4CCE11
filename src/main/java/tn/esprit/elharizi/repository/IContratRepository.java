package tn.esprit.elharizi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.elharizi.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
}
