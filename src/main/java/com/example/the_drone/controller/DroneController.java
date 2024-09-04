package com.example.the_drone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.the_drone.dto.DroneDTO;
import com.example.the_drone.dto.MedicationDTO;
import com.example.the_drone.models.Drone;
import com.example.the_drone.models.Medication;
import com.example.the_drone.services.DroneService;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping
    public ResponseEntity<DroneDTO> registerDrone(@RequestBody DroneDTO droneDTO) {
        DroneDTO createdDrone = droneService.registerDrone(convertToEntity(droneDTO));
        return new ResponseEntity<>(createdDrone, HttpStatus.CREATED);
    }

    @PostMapping("/{droneId}/load")
    public ResponseEntity<DroneDTO> loadDrone(@PathVariable int droneId, @RequestBody MedicationDTO medicationDTO) {
        DroneDTO updatedDrone = droneService.loadDrone(droneId, convertToEntity(medicationDTO));
        return new ResponseEntity<>(updatedDrone, HttpStatus.OK);
    }

    @GetMapping("/{droneId}/medications")
    public ResponseEntity<List<MedicationDTO>> getLoadedMedications(@PathVariable int droneId) {
        List<MedicationDTO> medications = droneService.getLoadedMedications(droneId);
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DroneDTO>> getAvailableDrones() {
        List<DroneDTO> availableDrones = droneService.getAvailableDrones();
        return new ResponseEntity<>(availableDrones, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DroneDTO>> getAll() {
        List<DroneDTO> allDrones = droneService.getDrones();
        return new ResponseEntity<>(allDrones, HttpStatus.OK);
    }

    @GetMapping("/{droneId}/battery")
    public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable int droneId) {
        int batteryLevel = droneService.getDroneBatteryLevel(droneId);
        return new ResponseEntity<>(batteryLevel, HttpStatus.OK);
    }

    private Drone convertToEntity(DroneDTO droneDTO) {
        // Convert DroneDTO to Drone entity
        return Drone.builder()
                .id(droneDTO.getId())
                .serialNumber(droneDTO.getSerialNumber())
                .model(droneDTO.getModel())
                .batteryCapacity(droneDTO.getBatteryCapacity())
                .state(droneDTO.getState())
                .build();
    }

    private Medication convertToEntity(MedicationDTO medicationDTO) {
        // Convert MedicationDTO to Medication entity
        return Medication.builder()
                .id(medicationDTO.getId())
                .name(medicationDTO.getName())
                .weight(medicationDTO.getWeight())
                .code(medicationDTO.getCode())
                .image(medicationDTO.getImage())
                .build();
    }
}
