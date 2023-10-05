package com.seatsecure.backend;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.enums.Gender;
import com.seatsecure.backend.entities.enums.Role;
import com.seatsecure.backend.repositories.EventRepository;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.security.auth.RegisterRequest;

@SpringBootApplication
public class SeatsecureBackendApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(SeatsecureBackendApplication.class, args);

		Event event1 = Event.builder().name("First event").startDate(new Date(System.currentTimeMillis()))
				.endDate(new Date(System.currentTimeMillis())).build();
		
		Event event2 = Event.builder().name("Second event").startDate(new Date(System.currentTimeMillis()))
				.endDate(new Date(System.currentTimeMillis())).build();
		
		Venue venue = Venue.builder().name("National stadium").address("Address of national stadium")
				.eventsList(new ArrayList<>(Arrays.asList(event1))).build();

		event1.setVenue(venue);
		event2.setVenue(venue);
		venue.getEventsList().add(event2);
		EventRepository eventRepo = ac.getBean(EventRepository.class);
		eventRepo.save(event1);
		eventRepo.save(event2); // Automatically inserts new venue into venueRepo

	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service) {
		return args -> {
			var admin = RegisterRequest.builder().firstName("admin").lastName("Hi").email("admin@email.com").gender(Gender.MALE)
					.username("admin").phoneNumber(12345678).password("abcdef").role(Role.ADMIN).build();
			System.out.println("Admin token:" + service.register(admin, true).getToken());

			var cust = RegisterRequest.builder().firstName("user").lastName("Hi").email("user@email.com").gender(Gender.FEMALE)
					.username("cust").phoneNumber(12345678).password("abcdefgh").role(Role.USER).build();
			System.out.println("User token:" + service.register(cust, false).getToken());

		};
	}
}
