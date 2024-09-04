package com.example.the_drone.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.the_drone.enums.DroneState;
import com.example.the_drone.models.Drone;

public interface DroneRepository extends JpaRepository<Drone, Integer> {
    List<Drone> findByStateAndBatteryCapacityGreaterThan(DroneState state, int batteryCapacity);
}