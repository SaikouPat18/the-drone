package com.example.the_drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TheDroneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheDroneApplication.class, args);
	}

}
