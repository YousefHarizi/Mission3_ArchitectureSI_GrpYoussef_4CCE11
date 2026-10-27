package tn.esprit.elharizi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.elharizi.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
