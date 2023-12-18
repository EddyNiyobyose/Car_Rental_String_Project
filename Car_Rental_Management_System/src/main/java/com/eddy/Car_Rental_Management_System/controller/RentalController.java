package com.eddy.Car_Rental_Management_System.controller;

import com.eddy.Car_Rental_Management_System.model.Car;
import com.eddy.Car_Rental_Management_System.model.Customer;
import com.eddy.Car_Rental_Management_System.model.Rental;
import com.eddy.Car_Rental_Management_System.service.CarService;
import com.eddy.Car_Rental_Management_System.service.CustomerService1;
import com.eddy.Car_Rental_Management_System.service.CustomerService1;
import com.eddy.Car_Rental_Management_System.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RentalController {

    private final RentalService rentalService;
    private final CarService carService;
    private final CustomerService1 customerService1;

    @Autowired
    public RentalController(RentalService rentalService, CarService carService, CustomerService1 customerService1) {
        this.rentalService = rentalService;
        this.carService = carService;
        this.customerService1 = customerService1;
    }



    @GetMapping("/rental-form")
    public String showRentalForm(Model model) {
        model.addAttribute("rental", new Rental());
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("customers", customerService1.getCustomers());
        return "rental-form";
    }

    @GetMapping("/rental")
    public String showRentalForm() {
        // Add logic if needed
        return "rental-form"; // Thymeleaf template name
    }

    @PostMapping("/rent")
    public String rentCar(@ModelAttribute Rental rental) {
        // Retrieve or create the Car entity
        Car car = carService.getOrCreateCar(rental.getCar().getPlateNumber());

        // Set the Car entity in the Rental object
        rental.setCar(car);

        // Save rental information to the database
        rentalService.saveRental(rental);

        // Redirect to a success page or any other appropriate page
        return "redirect:/view-rentals";
    }

    @GetMapping("/view-rentals")
    public String viewUsers(Model model) {
        List<Rental> rentals = rentalService.getAllRentals();
        model.addAttribute("rentals", rentals);
        return "rental";
    }

    @GetMapping("/rental/{rentalId}/delete")
    public String deleteRental(@PathVariable("rentalId")Integer Id) {
        rentalService.deleteRental(Id);
        return "redirect:/view-rentals";
    }
}
