package thequeuers.seatsecure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import thequeuers.seatsecure.repositories.AdminRepository;
import thequeuers.seatsecure.repositories.CustomerRepository;
import thequeuers.seatsecure.customer.*;
import thequeuers.seatsecure.entities.Admin;
import thequeuers.seatsecure.entities.Customer;

@SpringBootApplication
public class SeatsecureApplication {

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(SeatsecureApplication.class, args);

        // JPA repository init
        CustomerRepository customerRepo = ctx.getBean(CustomerRepository.class);
        System.out.println(customerRepo.save(new Customer("Colin","colin123","colin123@gmail.com","Colin Lee",90304003,"M")).getUsername());
        System.out.println(customerRepo.save(new Customer("Carla","carla456","carla123@gmail.com","Carla Kal",90304723,"F")).getUsername());
    
        AdminRepository adminRepo = ctx.getBean(AdminRepository.class);
        System.out.println(adminRepo.save(new Admin("Arnold","arnold123","arnold123@gmail.com","Arnold Schwartz",80303545,"M")).getUsername());
        System.out.println(adminRepo.save(new Admin("Angelia","angelia456","angelia123@gmail.com","Angelia Alison",81924614,"F")).getUsername());
        
    }
    
}
