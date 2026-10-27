package tn.esprit.elharizi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.elharizi.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

}
