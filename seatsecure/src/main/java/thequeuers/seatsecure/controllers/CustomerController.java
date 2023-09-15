package thequeuers.seatsecure.controllers;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import thequeuers.seatsecure.entities.Customer;
import thequeuers.seatsecure.exceptions.CustomerNotFoundException;
import thequeuers.seatsecure.services.CustomerService;

@RestController
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService cs){
        this.customerService = cs;
    }

    /**
     * List all customers in the system
     * @return list of all customers
     */
    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return customerService.listCustomers();
    }

    /**
     * Search for book with the given id
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @return book with the given id
     */
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);

        // Need to handle "book not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if(customer == null) throw new CustomerNotFoundException(id);
        return customerService.getCustomerById(id);

    }
    /**
     * Add a new book with POST request to "/books"
     * Note the use of @RequestBod
     * @return list of all books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    /**
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @param newBookInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer newBookInfo){
        Customer customer = customerService.updateCustomer(id, newBookInfo);
        if(customer == null) throw new CustomerNotFoundException(id);
        
        return customer;
    }

    /**
     * Remove a book with the DELETE request to "/books/{id}"
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     */
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        try{
            customerService.deleteCustomerById(id);
         }catch(EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException(id);
         }
    }
}