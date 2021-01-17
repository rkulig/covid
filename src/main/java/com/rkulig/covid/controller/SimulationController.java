package com.rkulig.covid.controller;

import com.rkulig.covid.model.DayOfSimulation;
import com.rkulig.covid.model.Simulation;
import com.rkulig.covid.repository.DayOfSimulationRepository;
import com.rkulig.covid.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
public class SimulationController {

    private SimulationRepository simulationRepository;
    private DayOfSimulationRepository dayOfSimulationRepository;

    @Autowired
    public SimulationController(SimulationRepository simulationRepository, DayOfSimulationRepository dayOfSimulationRepository) {
        this.simulationRepository = simulationRepository;
        this.dayOfSimulationRepository = dayOfSimulationRepository;
    }


    @GetMapping("api/simulations")
    public List<Simulation> getAll() {
        return simulationRepository.findAll();
    }

    @GetMapping("/api/simulations/{id}")
    public ResponseEntity<Simulation> getById(@PathVariable Long id) {
        Simulation simulation = simulationRepository.findById(id).get();
        if (simulation != null) {
            return ResponseEntity.ok(simulation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/simulations")
    public ResponseEntity<?> saveSimulation(@RequestBody Simulation simulation) {
        if (simulation.getId() == null) {
            simulation.setDaysOfSimulation(new ArrayList<>());
            Simulation save = simulationRepository.save(simulation);
       //     System.out.println(simulation.toString());
            List<DayOfSimulation> days = makeSimulation(simulation);

            for (int i = 0; i < days.size(); i++) {

                DayOfSimulation day = days.get(i);
                simulation.getDaysOfSimulation().add(day);
                dayOfSimulationRepository.save(day);
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(save.getI())
                    .toUri();
            return ResponseEntity.created(location).body(save);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private List<DayOfSimulation> makeSimulation(Simulation simulation) {
        Map<Integer, DayOfSimulation> daysOfSimulation = new HashMap<>();
        long p = simulation.getP(); // wielkość populacji
        long i = simulation.getI(); // początkowa liczba zarażonych
        double r = simulation.getR(); // wskaźnik ile osób zaraża jedna chora osoba
        double m = simulation.getM(); // wskaźnik ile osób spośród zakażonych umiera
        int ti = simulation.getTi(); // ilość dni, która mija od momentu zakażenia do wyzdrowienia
        int tm = simulation.getTm(); // ilość dni która mija od zarażenia do śmierci
        int ts = simulation.getTs(); // ilość dni dla której ma być przeprowadzona symulacja

        long pi = i; // liczba osób zarażonych od początku pandemii
        if (pi>p){
            pi=p;
        }
        long pv = p - pi; // liczba osób zdrowych, podatnych na infekcje
        long pm = 0; // liczba osób zmarłych od początku pandemii
        long pr = 0; // liczba osób które wyzdrowiały i nabyły odporność od początku pandemii
        long pia = i; // liczba wszystkich zakazonych (aktywnie zarażających) danego dnia
        long pid = i; // liczba nowych zakazonych danego dnia (przyrost)
        long pmd = 0; // liczba zmarlych danego dnia (przyrost)
        long prd = 0; // liczba osob ktore wyzdrowialy danego dnia(przyrost)
        int day = 0; // dzień symulacji
int l =01;
        System.out.println(l);
     //   System.out.println("p:" + p + " i:" + i + " r:" + r + " m:" + m + " ti:" + ti + " tm:" + tm + " ts:" + ts);
        DayOfSimulation day0 = new DayOfSimulation(day, pi, pv, pm, pr, pia, pid, pmd, prd);
        day0.setSimulation(simulation);
        daysOfSimulation.put(day, day0);
     //   System.out.println("dzien zero");
      //  System.out.println(daysOfSimulation.get(0));

        for (int currentDay = 1; currentDay < ts; currentDay++) {
            if (currentDay >= tm) { // zmarli dane dnia
                pmd = Math.round(m * (daysOfSimulation.get(currentDay - tm).getPid()));
            }
            if (currentDay >= ti) { //ozdrowiali danego dnia
                prd = Math.round((1 - m) * (daysOfSimulation.get(currentDay - ti).getPid()));
            }
            pid = (long) (pia * r); //liczba osob u ktorych sie uaktywnil te dnia wirus
            if (p==0){
                pid=0;
            } else if (pi+pid>p){
                pid=p-pi;
            }
            pia = pia + pid - pmd - prd;
            if (pia <= 0) {
                pia = 0;
            }
            pr = pr + prd;
            pm = pm + pmd;
            pi = pi + pid;
            pv = p - pi;
            if (pv<=0){
                pv=0;
            }
            day++;
            DayOfSimulation day1 = new DayOfSimulation(day, pi, pv, pm, pr, pia, pid, pmd, prd);
            day1.setSimulation(simulation);
            daysOfSimulation.put(day, day1);
        //    System.out.println(daysOfSimulation.get(day));
        }

        List<DayOfSimulation> days = new ArrayList<>();
        for (int j = 0; j < daysOfSimulation.size(); j++) {
            days.add(daysOfSimulation.get(j));
        }

        return days;
    }


}
