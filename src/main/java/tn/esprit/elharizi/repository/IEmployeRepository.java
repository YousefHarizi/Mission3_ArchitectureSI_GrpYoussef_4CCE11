package tn.esprit.elharizi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.elharizi.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}
