package com.nivea_be.nivea_ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NiveaAdApplication {

	public static void main(String[] args) {
		SpringApplication.run(NiveaAdApplication.class, args);
	}

}
