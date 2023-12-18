package com.eddy.Car_Rental_Management_System.service;

import com.eddy.Car_Rental_Management_System.model.Car;
import com.eddy.Car_Rental_Management_System.model.Customer;

import java.util.List;

public interface CustomerService1 {
    Customer registerCustomer(Customer customer);
    Customer authenticate(Customer customer);
    Customer updateCustomer(Customer customer);
    
    List<Customer> customerList();

    Customer findCustomerById(Integer customerId);
    // Customer findCustomerById(Integer customer);
    List<Customer> getCustomers();

    List<Customer> getAllCustomers();

    void deleteCustomer(Integer Id);
}
