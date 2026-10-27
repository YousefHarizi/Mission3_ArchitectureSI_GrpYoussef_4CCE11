package tn.esprit.elharizi.repository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.elharizi.domain.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}
