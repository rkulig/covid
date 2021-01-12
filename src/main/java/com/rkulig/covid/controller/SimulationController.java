package com.rkulig.covid.controller;

import com.rkulig.covid.model.Simulation;
import com.rkulig.covid.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/simulations")
public class SimulationController {

    private SimulationRepository simulationRepository;

    @Autowired
    public SimulationController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Simulation>> allSimulations() {
        List<Simulation> allSimulations = simulationRepository.findAll();
        return ResponseEntity.ok(allSimulations);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Simulation> getSimulationById(@PathVariable Long id) {
        return simulationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveSimulation(@RequestBody Simulation simulation) {
        Simulation save = simulationRepository.save(simulation);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getI())
                .toUri();
        return ResponseEntity.created(location).body(save);
    }

}
