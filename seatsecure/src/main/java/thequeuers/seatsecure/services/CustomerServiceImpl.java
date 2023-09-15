package thequeuers.seatsecure.services;

import java.util.List;
import org.springframework.stereotype.Service;

import thequeuers.seatsecure.entities.Customer;
import thequeuers.seatsecure.repositories.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {
   
    private CustomerRepository customerRepo;
    

    public CustomerServiceImpl(CustomerRepository customerRepo){
        this.customerRepo = customerRepo;
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepo.findAll();
    }

    
    @Override
    public Customer getCustomerById(Long customerId){
        // Using Java Optional, as "findById" of Spring JPA returns an Optional object
        // Optional forces developers to explicitly handle the case of non-existent values
        // Here is an implementation using lambda expression to extract the value from Optional<Book>
        return customerRepo.findById(customerId).map(c -> {
            return c;
        }).orElse(null);
    }
    
    @Override
    public Customer addCustomer(Customer c) {
        return customerRepo.save(c);
    }
    
    @Override
    public Customer updateCustomer(Long id, Customer newCustomerInfo){
        return customerRepo.findById(id).map(c -> {c.setPassword(newCustomerInfo.getPassword());
            return customerRepo.save(c);
        }).orElse(null);
    }

    /**
     * Remove a customer with the given id
     * Spring Data JPA does not return a value for delete operation
     */
    @Override
    public Customer deleteCustomerById(Long customerId){
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            return null;
        }
        customerRepo.deleteById(customerId);
        return customer;
    }
}