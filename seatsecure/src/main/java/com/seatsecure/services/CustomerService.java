package com.seatsecure.services;

import java.util.List;

import com.seatsecure.entities.Customer;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(Long id);
    Customer addCustomer(Customer c);
    Customer updateCustomer(Long id, Customer newCustomerInfo);
    Customer deleteCustomerById(Long id);
}