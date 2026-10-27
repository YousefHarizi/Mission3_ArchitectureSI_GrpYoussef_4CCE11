package tn.esprit.elharizi.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.elharizi.domain.Paiement;
import tn.esprit.elharizi.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementRestController {
    private final IPaiementService paiementService;

    @GetMapping
    public List<Paiement> findAll() { return paiementService.findAll(); }

    @GetMapping("/{id}")
    public Paiement findById(@PathVariable Long id) { return paiementService.findById(id); }
}
