package tn.esprit.elharizi.service.impls;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.elharizi.domain.Client;
import tn.esprit.elharizi.exception.ResourceNotFoundException;
import tn.esprit.elharizi.repository.IClientRepository;
import tn.esprit.elharizi.service.IClientService;

@Service
@RequiredArgsConstructor

public class ClientServiceImpl implements IClientService {
    private final IClientRepository clientRepository;

    @Override
    public Client create(Client client) {

        if (client.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau client ne doit pas avoir d'identifiant"
            );
        }

        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom du client est obligatoire"
            );
        }

        if (client.getEmail() == null || client.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "L'email du client est obligatoire"
            );
        }

        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {

        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", id)
                );
    }

    @Override
    public List<Client> findAll() {

        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client client) {

        Client existant = findById(id);

        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom du client est obligatoire"
            );
        }

        if (client.getEmail() == null || client.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "L'email du client est obligatoire"
            );
        }

        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        existant.setDateInscription(client.getDateInscription());

        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {

        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }

        clientRepository.deleteById(id);
    }
}
