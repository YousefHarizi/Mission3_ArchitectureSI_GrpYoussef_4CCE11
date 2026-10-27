package tn.esprit.elharizi.service;

import tn.esprit.elharizi.domain.Client;

import java.util.List;

public interface IClientService {
    Client create(Client client);

    Client findById(Long id);

    List<Client> findAll();

    Client update(Long id, Client client);

    void deleteById(Long id);
}

