package com.example.the_drone.dto;

import java.util.List;

import com.example.the_drone.enums.DroneModel;
import com.example.the_drone.enums.DroneState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneDTO {
    private int id;
    private String serialNumber;
    private DroneModel model;
    private int batteryCapacity;
    private DroneState state;
    private List<MedicationDTO> medications; 
}
