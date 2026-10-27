package tn.esprit.elharizi.web.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import tn.esprit.elharizi.domain.Agence;
import tn.esprit.elharizi.service.impls.AgenceServiceImpl;

@RequiredArgsConstructor
@RequestMapping("agence")
@RestController
public class AgenceRestController {
    private final AgenceServiceImpl agenceServices;

    @PostMapping("add")
    public Agence add(@RequestBody Agence agence) {
        return agenceServices.create(agence);
    }
    @GetMapping("get/{idAgence}")
    public Agence get(@PathVariable Long idAgence){
        return agenceServices.findById(idAgence);
    }

}
