package com.example.the_drone.services;

import java.util.List;

import com.example.the_drone.dto.DroneDTO;
import com.example.the_drone.dto.MedicationDTO;
import com.example.the_drone.models.Drone;
import com.example.the_drone.models.Medication;

public interface DroneService {
	DroneDTO registerDrone(Drone drone);
	DroneDTO loadDrone(int droneId, Medication medication);
	List<MedicationDTO> getLoadedMedications(int droneId);
	List<DroneDTO> getAvailableDrones();
	List<DroneDTO> getDrones();
	int getDroneBatteryLevel(int droneId);
	void updateDroneStates();
}
