package com.resolve.geoService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GeoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoServiceApplication.class, args);
	}

}
