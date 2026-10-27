package tn.esprit.elharizi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.elharizi.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}
