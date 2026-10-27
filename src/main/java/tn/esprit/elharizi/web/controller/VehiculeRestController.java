package tn.esprit.elharizi.web.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.elharizi.domain.Vehicule;
import tn.esprit.elharizi.service.impls.VehiculeServiceImpl;

@RequiredArgsConstructor
@RequestMapping("vehicules")
@RestController

public class VehiculeRestController {

    private final VehiculeServiceImpl vehiculeServices;

    @PostMapping("add")
    public Vehicule add(@RequestBody Vehicule vehicule) {
        return vehiculeServices.create(vehicule);
    }
    @GetMapping("get/{idVehicule}")
    public Vehicule get(@PathVariable Long idVehicule){
        return vehiculeServices.findById(idVehicule);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) { return vehiculeServices.create(vehicule); }

}
