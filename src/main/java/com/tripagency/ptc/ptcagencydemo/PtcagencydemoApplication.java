package com.tripagency.ptc.ptcagencydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PtcagencydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtcagencydemoApplication.class, args);
	}

}
