package tn.esprit.elharizi.service;

import tn.esprit.elharizi.domain.Vehicule;

import java.util.List;

public interface IVehiculeServices {
    Vehicule create(Vehicule vehicule);
    Vehicule update(Vehicule vehicule);
    void deleteById(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
}
