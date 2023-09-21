package com.seatsecure.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.seatsecure.backend.entities.Role;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.security.auth.RegisterRequest;


@SpringBootApplication
public class SeatsecureBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeatsecureBackendApplication.class, args);
	}
	//@Bean
	// public CommandLineRunner commandLineRunner(
	// 	AuthenticationService service
	// ){
	// 	return args -> {
	// 		var admin = RegisterRequest.builder().firstName("admin").lastName("Hi").email("admin@email.com").gender("m").phoneNumber(12345678).password("abcdef").role(Role.ADMIN).build();
	// 		System.out.println("Admin token:" + service.register(admin).getToken());

	// 		var cust = RegisterRequest.builder().firstName("user").lastName("Hi").email("user@email.com").gender("m").phoneNumber(12345678).password("abcdefgh").role(Role.USER).build();
	// 		System.out.println("User token:" + service.register(cust).getToken());
	// 	};
	// }
}
