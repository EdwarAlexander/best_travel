package com.debugeando.test.best_travel;

import com.debugeando.test.best_travel.domain.repositories.mongo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BestTravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}
}
