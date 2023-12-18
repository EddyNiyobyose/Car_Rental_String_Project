package com.eddy.Car_Rental_Management_System.service.implementation;

import com.eddy.Car_Rental_Management_System.model.Rental;
import com.eddy.Car_Rental_Management_System.repository.RentalRepository;
import com.eddy.Car_Rental_Management_System.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Add the @Service annotation to mark it as a Spring service
public class RentalServiceImpl implements RentalService {
    @Autowired

    private final RentalRepository rentalRepository;

    @Autowired // This annotation is not necessary here since you're using constructor injection
    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public List<Rental> getAllRentals() {
        // Implement the logic to get all rentals from the repository
        return rentalRepository.findAll();
    }

    @Override
    public Rental getRentalById(int id) {
        // Implement the logic to get a rental by ID from the repository
        return rentalRepository.findById(id).orElse(null);
    }

    @Override
    public Rental rentCar(Rental rental) {
        // Implement the logic to save a new rental in the repository
        return rentalRepository.save(rental);
    }

    @Override
    public Rental returnCar(Rental rental) {
        // Implement the logic to update the rental (e.g., set return date) in the repository
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(int id) {
        // Implement the logic to delete a rental by ID from the repository
        rentalRepository.deleteById(id);
    }

    @Override
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }
}
