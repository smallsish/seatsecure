package thequeuers.seatsecure;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import thequeuers.seatsecure.user.*;

@SpringBootApplication
public class SeatsecureApplication {

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(SeatsecureApplication.class, args);

        // JPA repository init
        UserRepository jpaRepo = ctx.getBean(UserRepository.class);
        System.out.println(jpaRepo.save(new User("Jim","jim123","jim123@gmail.com","jim lee",90304003,"M")).getUsername());
        System.out.println(jpaRepo.save(new User("Jill","jill123","jill123@gmail.com","jill lee",90304723,"F")).getUsername());
    }
    
}
