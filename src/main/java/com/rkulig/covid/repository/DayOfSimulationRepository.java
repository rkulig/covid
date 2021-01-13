package com.rkulig.covid.repository;

import com.rkulig.covid.model.DayOfSimulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOfSimulationRepository extends JpaRepository<DayOfSimulation,Long> {
}
