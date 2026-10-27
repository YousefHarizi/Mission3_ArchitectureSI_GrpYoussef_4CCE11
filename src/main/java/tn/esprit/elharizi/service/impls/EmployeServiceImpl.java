package tn.esprit.elharizi.service.impls;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.elharizi.domain.Employe;
import tn.esprit.elharizi.domain.Equipement;
import tn.esprit.elharizi.exception.ResourceNotFoundException;
import tn.esprit.elharizi.repository.IEmployeRepository;
import tn.esprit.elharizi.service.IEmployeService;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe employe) {

        if (employe.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel employé ne doit pas avoir d'identifiant"
            );
        }

        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'employé est obligatoire"
            );
        }

        return employeRepository.save(employe);
    }


    public Equipement create(Equipement equipement) {
        return null;
    }

    @Override
    public Employe findById(Long id) {

        return employeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employe", id)
                );
    }

    @Override
    public List<Employe> findAll() {

        return employeRepository.findAll();
    }


    public Equipement update(Long id, Equipement equipement) {
        return null;
    }


    public Employe update(Long id, Employe employe) {

        Employe existant = findById(id);

        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'employé est obligatoire"
            );
        }

        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());

        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {

        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }

        employeRepository.deleteById(id);
    }
}

