package com.eddy.Car_Rental_Management_System.controller;

import com.eddy.Car_Rental_Management_System.model.Car;
import com.eddy.Car_Rental_Management_System.model.Customer;
import com.eddy.Car_Rental_Management_System.model.Rental;
import com.eddy.Car_Rental_Management_System.model.Type;
import com.eddy.Car_Rental_Management_System.service.CarService;
import com.eddy.Car_Rental_Management_System.service.CustomerService1;
import com.eddy.Car_Rental_Management_System.service.RentalService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService1 customerService1;
    @Autowired
    private CarService carService;
    @Autowired
    private RentalService rentalService;

    @GetMapping("/index")
    public String welcomepage(Model model) {
        return "index-page";
    }
    @GetMapping("/contact")
    public String contactuspage(Model model) {
        return "contact-page";
    }

    @GetMapping("/about")
    public String Aboutuspage(Model model) {
        return "about-page";
    }

    @GetMapping("/signup")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new Customer());
        return "signup-page";
    }

    @GetMapping("/customer-dashboard")
    public String getCustomerDashboard(Model model) {
        // Retrieve information about all registered cars and pass it to the model

        List<Car> cars = carService.getAllCars();
        // Add the list to the model
        model.addAttribute("cars", cars);

        // Add any other necessary logic or attributes for the customer dashboard
        model.addAttribute("userlogin", "Customer"); // Example: Set the username for the customer

        return "customer-dashboard";
    }


    @GetMapping("/search")
    public String searchUsers(@RequestParam(name = "customerId", required = false) Integer customerId, Model model) {
        if (customerId != null) {
            Customer foundCustomer = customerService1.findCustomerById(customerId);
            if (foundCustomer != null) {
                // Display only the searched customer
                model.addAttribute("customers", List.of(foundCustomer));
            } else {
                // Handle the case where the customer with the specified ID is not found
                model.addAttribute("customers", List.of());
            }
        } else {
            // Handle the case where no customer ID is provided
            List<Customer> customers = customerService1.getAllCustomers();
            model.addAttribute("customers", customers);
        }
        return "users";
    }
    @PostMapping("/search-car")
    public String searchCarByPlateNumber(@RequestParam(name = "plateNumber") String plateNumber, Model model) {
        if (plateNumber != null && !plateNumber.isEmpty()) {
            Car foundCar = carService.findCarByPlateNumber(Integer.parseInt(plateNumber));
            if (foundCar != null) {
                // Display only the searched car
                model.addAttribute("cars", List.of(foundCar));
            } else {
                // Handle the case where the car with the specified plate number is not found
                model.addAttribute("cars", List.of());
            }
        } else {
            // Handle the case where no plate number is provided
            List<Car> registeredCars = carService.getAllCars();
            model.addAttribute("cars", registeredCars);
        }

        return "customer-dashboard";
    }

    @GetMapping("/user/{customerId}/delete")
    public String deleteCustomer(@PathVariable("customerId") Integer Id) {
        // Delete associated cars first
        carService.deleteCarsByUserId(Id);

        // Now, delete the user
        customerService1.deleteCustomer(Id);

        return "redirect:/view-users";
    }

    @GetMapping("/view-users")
    public String viewUsers(Model model) {
        List<Customer> customers = customerService1.getAllCustomers();
        model.addAttribute("customers", customers);
        return "users";
    }
//    @GetMapping("/user/{customerId}/delete")
//    public String deleteCustomer(@PathVariable("customerId")Integer Id) {
//        customerService1.deleteCustomer(Id);
//        return "redirect:/view-users";
//    }



    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new Customer());
        return "login-page";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute Customer customer) {
        System.out.println("Register request: " + customer);

        if (customer == null || customer.getUsername() == null || customer.getPassword() == null || customer.getEmail() == null || customer.getType() == null) {
            // Handle invalid input or missing fields
            return "error_page1"; // Or redirect to an error page
        }

        // Set the correct enum value for customer type
        customer.setType(Type.Customer);
        customer.setType(Type.Admin);

        Customer registeredCustomer = customerService1.registerCustomer(customer);
        return registeredCustomer == null ? "error_page" : "redirect:/signup"; // Redirect to the signup page
    }


    @PostMapping("/login")
    public String login(@ModelAttribute Customer customer, Model model) {
        System.out.println("Login request: " + customer);

        if (customer == null || customer.getUsername() == null || customer.getPassword() == null) {
            // Handle invalid input or missing fields
            return "error_page1";
        }

        Customer authenticated = customerService1.authenticate(customer);

        if (authenticated != null) {
            System.out.println("Authenticated user type: " + authenticated.getType());

            model.addAttribute("userlogin", authenticated.getUsername());

            if (Type.Admin.equals(authenticated.getType())) {
                return "admin-dashboard";  // Redirect to the admin dashboard
            } else if (Type.Customer.equals(authenticated.getType())) {
                List<Car> cars = carService.getAllCars();
                // Add the list to the model
                model.addAttribute("cars", cars);
                return "customer-dashboard";
                 // Redirect to the customer dashboard
            } else {
                return "error_page";  // Handle unknown user type
            }
        } else {
            return "error_page";
        }
    }



    @GetMapping("/admin-dashboard")
    public String getAdminDashboard(Model model) {
        // You can add any necessary logic or attributes for the admin dashboard
        model.addAttribute("userlogin", "Admin"); // Example: Set the username for the admin
        return "admin-dashboard";
    }

}