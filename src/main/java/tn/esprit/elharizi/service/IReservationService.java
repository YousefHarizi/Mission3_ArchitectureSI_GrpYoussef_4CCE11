package tn.esprit.elharizi.service;
import tn.esprit.elharizi.domain.Reservation;

import java.util.List;
public interface IReservationService {
    Reservation create(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);
}
