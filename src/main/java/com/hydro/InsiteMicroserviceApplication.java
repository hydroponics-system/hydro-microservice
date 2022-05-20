package com.hydro;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class InsiteMicroserviceApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		ActiveProfile activeProfile = new ActiveProfile();
		activeProfile.setPropertyFile();

		SpringApplication.run(InsiteMicroserviceApplication.class, args);
	}
}
