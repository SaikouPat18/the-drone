package com.example.the_drone.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.the_drone.dto.DroneDTO;
import com.example.the_drone.dto.MedicationDTO;
import com.example.the_drone.enums.DroneState;
import com.example.the_drone.exceptions.ResourceNotFoundException;
import com.example.the_drone.models.Drone;
import com.example.the_drone.models.Medication;
import com.example.the_drone.repositories.DroneRepository;
import com.example.the_drone.repositories.MedicationRepository;
import com.example.the_drone.services.DroneService;

@Service
public class DroneServiceImpl implements DroneService {
    
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    
    public DroneServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public DroneDTO registerDrone(Drone drone) {
        if (drone == null) {
            throw new IllegalArgumentException("Drone cannot be null");
        }
        Drone savedDrone = droneRepository.save(drone);
        return convertToDTO(savedDrone);
    }

    @Override
    public DroneDTO loadDrone(int droneId, Medication medication) {
        if (medication == null) {
            throw new IllegalArgumentException("Medication cannot be null");
        }

        Drone drone = droneRepository.findById(droneId)
            .orElseThrow(() -> new ResourceNotFoundException("Drone not found"));

        if (drone.getBatteryCapacity() < 25) {
            throw new IllegalStateException("Drone battery is too low for loading.");
        }

        List<Medication> medications = drone.getMedications();
        if (medications == null) {
            medications = new ArrayList<>();
        }

        int totalWeight = medications.stream()
            .mapToInt(Medication::getWeight).sum() + medication.getWeight();

        if (totalWeight > drone.getModel().getWeightLimit()) {
            throw new IllegalStateException("Drone will be overloaded.");
        }

        medication.setDrone(drone);
        medicationRepository.save(medication);

        medications.add(medication);
        drone.setMedications(medications); 
        drone.setState(DroneState.LOADING);
        Drone savedDrone = droneRepository.save(drone);
        return convertToDTO(savedDrone);
    }

    @Override
    public List<MedicationDTO> getLoadedMedications(int droneId) {
        Drone drone = droneRepository.findById(droneId)
            .orElseThrow(() -> new ResourceNotFoundException("Drone not found"));

        List<Medication> medications = drone.getMedications();
        if (medications == null) {
            medications = new ArrayList<>();
        }

        return medications.stream()
            .map(medication -> new MedicationDTO(
                medication.getId(),
                medication.getName(),
                medication.getWeight(),
                medication.getCode(),
                medication.getImage()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<DroneDTO> getAvailableDrones() {
        List<Drone> drones = droneRepository.findByStateAndBatteryCapacityGreaterThan(DroneState.IDLE, 25);
        return drones.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DroneDTO> getDrones() {
        List<Drone> drones = droneRepository.findAll();
        return drones.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int getDroneBatteryLevel(int droneId) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new ResourceNotFoundException("Drone not found"));
        return drone.getBatteryCapacity();
    }

    @Scheduled(fixedRate = 10000) 
    @Override
    public void updateDroneStates() {
        List<Drone> drones = droneRepository.findAll();
        for (Drone drone : drones) {
            List<Medication> medications = drone.getMedications();
            if (medications == null) {
                medications = new ArrayList<>();
            }

            switch (drone.getState()) {
                case IDLE -> {
                    // Stay in idle if not manually loaded
                }

                case LOADING -> {
                    drone.setState(DroneState.LOADED);
                    droneRepository.save(drone);
                }

                case LOADED -> {
                    drone.setState(DroneState.DELIVERING);
                    droneRepository.save(drone);
                }

                case DELIVERING -> {
                    drone.setState(DroneState.DELIVERED);
                    droneRepository.save(drone);
                }

                case DELIVERED -> {
                    int currentBattery = drone.getBatteryCapacity();
                    int newBattery = Math.max(currentBattery - 10, 0);
                    drone.setBatteryCapacity(newBattery);

                    if (!medications.isEmpty()) {
                        medications.forEach(medication -> medication.setDrone(null));
                        medicationRepository.saveAll(medications);
                        drone.getMedications().clear();
                    }

                    drone.setState(DroneState.RETURNING);
                    droneRepository.save(drone);
                }

                case RETURNING -> {
                    drone.setState(DroneState.IDLE);
                    droneRepository.save(drone);
                }

                default -> {
                    throw new IllegalStateException("Unexpected state: " + drone.getState());
                }
            }
        }
    }

    public DroneDTO convertToDTO(Drone drone) {
   
        List<Medication> medications = drone.getMedications();
        if (medications == null) {
            medications = new ArrayList<>();
        }

        List<MedicationDTO> medicationDTOs = medications.stream()
            .map(medication -> new MedicationDTO(
                medication.getId(),
                medication.getName(),
                medication.getWeight(),
                medication.getCode(),
                medication.getImage()
            ))
            .collect(Collectors.toList());

        return new DroneDTO(
            drone.getId(),
            drone.getSerialNumber(),
            drone.getModel(),
            drone.getBatteryCapacity(),
            drone.getState(),
            medicationDTOs
        );
    }
}
