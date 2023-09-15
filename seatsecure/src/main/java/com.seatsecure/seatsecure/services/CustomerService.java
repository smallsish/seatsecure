package com.seatsecure.seatsecure.services;

import java.util.List;

import com.seatsecure.seatsecure.entities.Customer;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(Long id);
    Customer addCustomer(Customer c);
    Customer updateCustomer(Long id, Customer newCustomerInfo);
    Customer deleteCustomerById(Long id);
}