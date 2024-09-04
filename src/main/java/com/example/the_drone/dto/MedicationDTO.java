package com.example.the_drone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDTO {
    private int id;
    private String name;
    private int weight;
    private String code;
    private String image;
}
