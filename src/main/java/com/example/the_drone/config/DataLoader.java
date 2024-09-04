package com.example.the_drone.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.example.the_drone.models.Drone;
import com.example.the_drone.repositories.DroneRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class DataLoader {

    @Autowired
    private DroneRepository droneRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            Resource resource = new ClassPathResource("default-drones.json");
            try {
                List<Drone> drones = mapper.readValue(resource.getInputStream(), new TypeReference<List<Drone>>() {});
                droneRepository.saveAll(drones);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
