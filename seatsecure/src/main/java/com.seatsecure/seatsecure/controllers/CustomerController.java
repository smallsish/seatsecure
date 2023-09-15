package com.seatsecure.seatsecure.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.seatsecure.entities.Customer;
import com.seatsecure.seatsecure.exceptions.CustomerNotFoundException;
import com.seatsecure.seatsecure.services.CustomerService;

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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return customerService.listCustomers();
    }

    /**
     * Search for customer with the given id
     * If there is no customer with the given "id", throw a CustomerNotFoundException
     * @param id
     * @return Customer with the given id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@Pattern(regexp = "[0-9]+") @PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);

        if(customer == null) throw new CustomerNotFoundException(id);
        return customerService.getCustomerById(id);

    }
    /**
     * Add a new customer with POST request to "/customers"
     * @param customer
     * @return The new customer that was added
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customers")
    public Customer addCustomer(@Valid @RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    /**
     * Update a customer with new info
     * If there is no customer with the given "id", throw a CustomerNotFoundException
     * @param id
     * @param newCustomerInfo
     * @return the updated customer
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer newCustomerInfo){
        Customer customer = customerService.updateCustomer(id, newCustomerInfo);
        if(customer == null) throw new CustomerNotFoundException(id);
        
        return customer;
    }

    /**
     * Remove a customer with the DELETE request to "/customers/{id}"
     * If there is no customer with the given "id", throw a CustomerNotFoundException
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){

        Customer customer = customerService.deleteCustomerById(id);
        if(customer == null) throw new CustomerNotFoundException(id);

    }
}