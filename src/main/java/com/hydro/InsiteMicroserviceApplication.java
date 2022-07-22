package com.hydro;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hydro.insite_jwt_microservice.utility.ActiveProfile;

@SpringBootApplication
@EnableScheduling
public class InsiteMicroserviceApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		ActiveProfile activeProfile = new ActiveProfile();
		activeProfile.setPropertyFile();

		SpringApplication.run(InsiteMicroserviceApplication.class, args);
	}
}
