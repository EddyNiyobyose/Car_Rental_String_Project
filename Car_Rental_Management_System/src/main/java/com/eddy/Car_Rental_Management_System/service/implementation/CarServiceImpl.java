package com.eddy.Car_Rental_Management_System.service.implementation;

import com.eddy.Car_Rental_Management_System.model.Available;
import com.eddy.Car_Rental_Management_System.model.Car;
import com.eddy.Car_Rental_Management_System.model.Customer;
import com.eddy.Car_Rental_Management_System.repository.CarRepository;
import com.eddy.Car_Rental_Management_System.service.CarService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;


    @Override
    public Car registerCar(String plateNumber, String model, String year, String image,Available available, Customer customer) {
        Car car = new Car();
        car.setPlateNumber(plateNumber);
        car.setModel(model);
        car.setYear(year);
        car.setImage(image);  // Set the image URL instead of raw bytes

        car.setAvailable(available);
        car.setCustomer(customer);

        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Car car) {
        // Implement update logic, e.g., save to the database
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Integer Id) {
        // Implement delete logic, e.g., delete from the database
        carRepository.deleteById(Id);
    }

    @Override
    public List<Car> carList() {
        // Implement logic to get the list of cars from the database
        return carRepository.findAll();
    }

    @Override
    public Car findCarByPlateNumber(int plateNumber) {
            Optional<Car> optionalCar = carRepository.findById(plateNumber);
            return optionalCar.orElse(null);
    }

//    @Override
//    public Car findCarByPlateNumber(Car car) {
//        return carRepository.findById(car.getPlateNumber()).get();
//    }

    @Override
    public List<Car> getAllCars() {
        // Implement logic to get all cars from the database
        return carRepository.findAll();
    }

    @Override
    public void bookCar(Integer carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            car.setAvailable(Available.Booked);
            carRepository.save(car);
        }

    }

    @Override
    public void deleteCarsByUserId(Integer id) {
        carRepository.deleteById(id);

    }

    @Override
    public Car getOrCreateCar(String plateNumber) {
        Car existingCar = carRepository.findByPlateNumber(plateNumber);
        return existingCar != null ? existingCar : createNewCar(plateNumber);
    }
    private Car createNewCar(String plateNumber) {
        Car newCar = new Car();
        newCar.setPlateNumber(plateNumber);
        // Set other properties if needed
        carRepository.save(newCar);
        return newCar;
    }
}
