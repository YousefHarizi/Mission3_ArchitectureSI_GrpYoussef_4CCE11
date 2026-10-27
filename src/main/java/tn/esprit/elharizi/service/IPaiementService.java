package tn.esprit.elharizi.service;
import tn.esprit.elharizi.domain.Paiement;

import java.util.List;
public interface IPaiementService {
    Paiement findById(Long id);

    List<Paiement> findAll();
}
