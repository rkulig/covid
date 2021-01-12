package com.rkulig.covid.repository;

import com.rkulig.covid.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationRepository extends JpaRepository<Simulation,Long> {
}
