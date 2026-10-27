package tn.esprit.elharizi.service.impls;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.elharizi.domain.Paiement;
import tn.esprit.elharizi.exception.ResourceNotFoundException;
import tn.esprit.elharizi.repository.IPaiementRepository;
import tn.esprit.elharizi.service.IPaiementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {

        return paiementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement", id)
                );
    }

    @Override
    public List<Paiement> findAll() {

        return paiementRepository.findAll();
    }
}
