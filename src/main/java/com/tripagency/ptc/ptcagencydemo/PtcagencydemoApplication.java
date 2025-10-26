package com.tripagency.ptc.ptcagencydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories
@EnableJpaAuditing
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
// @EnableSpringDataWebSupport(pageSerializationMode =
// EnableSpringDataWebSupport.PageSerializationMode.DIRECT )
public class PtcagencydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtcagencydemoApplication.class, args);
	}
}
