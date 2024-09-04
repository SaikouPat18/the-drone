package com.example.the_drone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.the_drone.models.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Integer> {
}