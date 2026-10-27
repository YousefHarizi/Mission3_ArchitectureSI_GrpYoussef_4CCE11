package tn.esprit.elharizi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.elharizi.domain.Agence;

public interface IAgenceRepository  extends JpaRepository<Agence,Long> {
}
