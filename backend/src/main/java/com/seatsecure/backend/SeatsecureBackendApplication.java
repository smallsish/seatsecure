package com.seatsecure.backend;

import java.sql.Date;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.seatsecure.backend.entities.Venue;
import com.seatsecure.backend.entities.enums.Gender;
import com.seatsecure.backend.entities.enums.Role;
import com.seatsecure.backend.entities.Category;
import com.seatsecure.backend.entities.Event;
import com.seatsecure.backend.entities.Run;
import com.seatsecure.backend.entities.Seat;
import com.seatsecure.backend.entities.Ticket;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.security.auth.RegisterRequest;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.VenueService;

@SpringBootApplication
public class SeatsecureBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeatsecureBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner addMocks(AuthenticationService as, EventService es, VenueService vs) {
		return args -> {
			// Create mock user / admin
			RegisterRequest admin = RegisterRequest.builder().firstName("admin").lastName("Hi").email("admin@email.com")
					.gender(Gender.MALE)
					.username("admin").phoneNumber(12345678).password("abcdef").role(Role.ADMIN).build();

			RegisterRequest cust = RegisterRequest.builder().firstName("user").lastName("Hi").email("user@email.com")
					.gender(Gender.FEMALE)
					.username("cust").phoneNumber(12345678).password("abcdefgh").role(Role.USER).build();

			// Create new venues
			Venue venue1 = Venue.builder().name("National stadium").address("Address of national stadium")
					.events(new ArrayList<Event>()).seats(new ArrayList<Seat>()).build();

			Venue venue2 = Venue.builder().name("Indoor stadium").address("Address of indoor stadium")
					.events(new ArrayList<Event>()).seats(new ArrayList<Seat>()).build();

			// Create new events
			Event event1 = Event.builder().name("First event").startDate(new Date(System.currentTimeMillis()))
					.endDate(new Date(System.currentTimeMillis())).venue(null).cats(new ArrayList<Category>())
					.runs(new ArrayList<Run>()).build();

			Event event2 = Event.builder().name("Second event").startDate(new Date(System.currentTimeMillis()))
					.endDate(new Date(System.currentTimeMillis())).venue(null).cats(new ArrayList<Category>())
					.runs(new ArrayList<Run>()).build();

			// Create new cats
			Category event1_cat1 = Category.builder().name("Event1_Cat1").description("Category 1 of event 1").price(10)
					.event(null)
					.seats(new ArrayList<Seat>()).build();

			Category event1_cat2 = Category.builder().name("Event1_Cat2").description("Category 2 of event 1").price(11)
					.event(null)
					.seats(new ArrayList<Seat>()).build();

			Category event2_cat1 = Category.builder().name("Event2_Cat1").description("Category 2 of event 1").price(12)
					.event(null)
					.seats(new ArrayList<Seat>()).build();

			Category event2_cat2 = Category.builder().name("Event2_Cat2").description("Category 2 of event 2").price(13)
					.event(null)
					.seats(new ArrayList<Seat>()).build();

			// Create new runs
			Run event1_run1 = Run.builder().name("Event1_Run1").description("Run 1 of event 1")
					.startDate(new Date(System.currentTimeMillis())).endDate(new Date(System.currentTimeMillis()))
					.event(null).tickets(new ArrayList<Ticket>()).build();

			Run event2_run1 = Run.builder().name("Event2_Run1").description("Run 1 of event 2")
					.startDate(new Date(System.currentTimeMillis())).endDate(new Date(System.currentTimeMillis()))
					.event(null).tickets(new ArrayList<Ticket>()).build();

			// // Create tickets
			// Ticket ticket1 = Ticket.builder().seat(null).user(null).run(null).build();
			// Ticket ticket2 = Ticket.builder().seat(null).user(null).run(null).build();
			// Ticket ticket3 = Ticket.builder().seat(null).user(null).run(null).build();

			// Save users (and print token)
			System.out.println("Admin token:" + as.register(admin, true).getToken());
			System.out.println("User token:" + as.register(cust, false).getToken());

			// Save venue
			vs.addVenue(venue1);
			vs.addVenue(venue2);

			// Save events
			es.addEvent(event1);
			es.addEvent(event2);

			// Update events with venue
			es.setVenueForEvent(event1.getId(), venue1.getId());
			es.setVenueForEvent(event2.getId(), venue2.getId());

			// Update events with cats
			es.addNewCatToEvent(event1.getId(), event1_cat1);
			es.addNewCatToEvent(event1.getId(), event1_cat2);
			es.addNewCatToEvent(event2.getId(), event2_cat1);
			es.addNewCatToEvent(event2.getId(), event2_cat2);
			
			// Update events with runs
			es.addNewRunToEvent(event1.getId(), event1_run1);
			es.addNewRunToEvent(event2.getId(), event2_run1);

			// Add new seats to venue
			vs.addSeatsToVenue(venue1.getId(), 10);
			vs.addSeatsToVenue(venue2.getId(), 10);

			// // Assign cats to seats in event 1
			// es.assignCatsToSeats(event1.getId(), 0, 4, event1_cat1);
			// es.assignCatsToSeats(event1.getId(), 5, 9, event1_cat2);

			// // Assign cats to seats in event 2
			// es.assignCatsToSeats(event2.getId(), 0, 4, event1_cat1);
			// es.assignCatsToSeats(event2.getId(), 5, 9, event1_cat2);

		};

	}
}
