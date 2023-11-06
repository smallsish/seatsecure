package com.seatsecure.backend;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.security.auth.AuthenticationService;
import com.seatsecure.backend.security.auth.RegisterRequest;
import com.seatsecure.backend.services.CatService;
import com.seatsecure.backend.services.EventService;
import com.seatsecure.backend.services.RunService;
import com.seatsecure.backend.services.SeatService;
import com.seatsecure.backend.services.TicketService;
import com.seatsecure.backend.services.UserService;
import com.seatsecure.backend.services.VenueService;

@SpringBootApplication
public class SeatsecureBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeatsecureBackendApplication.class, args);
	}

	@Bean
	@Autowired
	public CommandLineRunner addMocks(AuthenticationService as, EventService es,
	VenueService vs, CatService cs, RunService rs, SeatService ss,
	TicketService ts, UserService us) {
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


			// Save users (and print token)
			System.out.println("Admin token:" + as.register(admin, true).getToken());
			System.out.println("User token:" + as.register(cust, false).getToken());
			User a = us.getUserByUsername(admin.getUsername());
			User c = us.getUserByUsername(cust.getUsername());

			// Save venue
			venue1 = vs.addVenue(venue1);
			venue2 = vs.addVenue(venue2);

			// Add new seats to venue
			ss.addNewSeatsToVenue(venue1.getId(), 10);
			ss.addNewSeatsToVenue(venue2.getId(), 10);

			// Save events
			event1 = es.addEvent(event1);
			event2 = es.addEvent(event2);

			// Update events with venue
			es.setVenueForEvent(event1.getId(), venue1.getId());
			es.setVenueForEvent(event2.getId(), venue2.getId());

			// Add new cats to events
			event1_cat1 = cs.addNewCatToEvent(event1.getId(), event1_cat1);
			event1_cat2 = cs.addNewCatToEvent(event1.getId(), event1_cat2);
			event2_cat1 = cs.addNewCatToEvent(event2.getId(), event2_cat1);
			event2_cat2 = cs.addNewCatToEvent(event2.getId(), event2_cat2);

			// Assign cats to seats in event 1's venue
			ss.assignCatToSeats(event1_cat1.getId(), (long) 1, (long) 5);
			ss.assignCatToSeats(event1_cat2.getId(), (long) 6, (long) 10);

			// Assign cats to seats in event 2's venue
			ss.assignCatToSeats(event2_cat1.getId(), (long) 11, (long) 15);
			ss.assignCatToSeats(event2_cat2.getId(), (long) 16, (long) 20);

			// Add new runs to events
			event1_run1 = rs.addNewRunToEvent(event1.getId(), event1_run1);
			event2_run1 = rs.addNewRunToEvent(event2.getId(), event2_run1);

			// Add new tickets to seats
			ts.addNewTicketsToSeats((long) 1, (long) 10);

			// Assign tickets to run
			ts.assignTicketsToRun(event1_run1.getId(), (long) 1, (long) 5);
			ts.assignTicketsToRun(event2_run1.getId(), (long) 6, (long) 10);

			// Assign ticket to user
			ts.assignTicketToUser(c.getId(), (long) 2);
			ts.assignTicketToUser(c.getId(), (long) 3);	
			
		};

	}
}
