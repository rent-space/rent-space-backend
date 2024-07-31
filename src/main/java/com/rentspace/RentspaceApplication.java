package com.rentspace;

import com.rentspace.initializer.FirebaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RentspaceApplication {

	public static void main(String[] args) {
		FirebaseInitializer.initialize();
		SpringApplication.run(RentspaceApplication.class, args);
	}
}
