package com.example.the_drone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.the_drone.dto.DroneDTO;
import com.example.the_drone.dto.MedicationDTO;
import com.example.the_drone.enums.DroneModel;
import com.example.the_drone.enums.DroneState;
import com.example.the_drone.models.Drone;
import com.example.the_drone.models.Medication;
import com.example.the_drone.repositories.DroneRepository;
import com.example.the_drone.repositories.MedicationRepository;
import com.example.the_drone.services.impl.DroneServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DroneServiceImplTest {

    @InjectMocks
    private DroneServiceImpl droneService;

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Test
    public void testRegisterDrone() {
        Drone drone = new Drone();
        drone.setId(1);
        drone.setSerialNumber("DRONE001");
        drone.setModel(DroneModel.LIGHTWEIGHT);
        drone.setBatteryCapacity(100);
        drone.setState(DroneState.IDLE);

        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        DroneDTO result = droneService.registerDrone(drone);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    public void testLoadDrone() {
        Drone drone = new Drone();
        drone.setId(1);
        drone.setSerialNumber("DRONE001");
        drone.setModel(DroneModel.LIGHTWEIGHT);
        drone.setBatteryCapacity(100);
        drone.setState(DroneState.IDLE);
        drone.setMedications(new ArrayList<>());

        Medication medication = new Medication();
        medication.setId(1);
        medication.setWeight(100);

        when(droneRepository.findById(1)).thenReturn(Optional.of(drone));
        when(medicationRepository.save(any(Medication.class))).thenReturn(medication);
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        DroneDTO result = droneService.loadDrone(1, medication);

        assertNotNull(result);
        assertEquals(DroneState.LOADING, result.getState());
        assertEquals(1, result.getMedications().size());
    }

    @Test
    public void testLoadDroneBatteryLow() {
        Drone drone = new Drone();
        drone.setId(1);
        drone.setBatteryCapacity(10);
        drone.setState(DroneState.IDLE);

        Medication medication = new Medication();
        medication.setWeight(100);

        when(droneRepository.findById(1)).thenReturn(Optional.of(drone));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            droneService.loadDrone(1, medication);
        });

        assertEquals("Drone battery is too low for loading.", exception.getMessage());
    }

    @Test
    public void testGetLoadedMedications() {
        Drone drone = new Drone();
        drone.setId(1);
        Medication medication = new Medication();
        medication.setId(1);
        medication.setName("Aspirin");
        medication.setWeight(200);
        medication.setCode("MED_001");
        medication.setImage("http://example.com/images/aspirin.png");

        List<Medication> medications = Arrays.asList(medication);
        drone.setMedications(medications);

        when(droneRepository.findById(1)).thenReturn(Optional.of(drone));

        List<MedicationDTO> result = droneService.getLoadedMedications(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Aspirin", result.get(0).getName());
    }

    @Test
    public void testGetAvailableDrones() {
        Drone drone = new Drone();
        drone.setId(1);
        drone.setState(DroneState.IDLE);
        drone.setBatteryCapacity(100);

        List<Drone> drones = Arrays.asList(drone);

        when(droneRepository.findByStateAndBatteryCapacityGreaterThan(DroneState.IDLE, 25)).thenReturn(drones);

        List<DroneDTO> result = droneService.getAvailableDrones();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    public void testGetDroneBatteryLevel() {
        Drone drone = new Drone();
        drone.setId(1);
        drone.setBatteryCapacity(80);

        when(droneRepository.findById(1)).thenReturn(Optional.of(drone));

        int result = droneService.getDroneBatteryLevel(1);

        assertEquals(80, result);
    }

}
