package tn.esprit.elharizi.service.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tn.esprit.elharizi.domain.Vehicule;
import tn.esprit.elharizi.repository.IVehiculeRepository;
import tn.esprit.elharizi.service.IVehiculeServices;

import java.util.List;
@RequiredArgsConstructor
@Service

public class VehiculeServiceImpl implements IVehiculeServices {

    private final IVehiculeRepository vehiculeRepository;


    @Override
    public Vehicule create(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        return null;
    }

    @Override
    public void deleteById(Vehicule vehicule) {
        vehiculeRepository.delete(vehicule);

    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public List<Vehicule> findAll() {
        return (List<Vehicule>) vehiculeRepository.findAll();
    }
}
