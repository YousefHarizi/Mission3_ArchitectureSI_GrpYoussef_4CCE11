package tn.esprit.elharizi.service.impls;
import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.elharizi.domain.Reservation;
import tn.esprit.elharizi.exception.ResourceNotFoundException;
import tn.esprit.elharizi.repository.IReservationRepository;
import tn.esprit.elharizi.service.IReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository reservationRepository;

    @Override
    public Reservation create(Reservation reservation) {

        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant"
            );
        }

        verifierDates(reservation.getDateDebut(), reservation.getDateFin());

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {

        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id)
                );
    }

    @Override
    public List<Reservation> findAll() {

        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {

        Reservation existante = findById(id);

        verifierDates(reservation.getDateDebut(), reservation.getDateFin());

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {

        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }

    private void verifierDates(LocalDate dateDebut, LocalDate dateFin) {

        if (dateDebut != null
                && dateFin != null
                && dateFin.isBefore(dateDebut)) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }
}
