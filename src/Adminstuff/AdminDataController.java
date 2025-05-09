package Adminstuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import model.Car;
import model.Booking;
import controller.RenterDataController;

// AdminDataController, is Singleton pattern
// Modify the AdminDataController class to work with the new Car model

public class AdminDataController {
    private static AdminDataController instance;
    private ArrayList<Car> cars;


    // Private constructor for singleton
    private AdminDataController() {
        cars = new ArrayList<>();


        // Some pre-made cars
        // Using adapted constructor to match your new Car model


        cars.add(new Car( "Toyota", "Camry", "Sedan", 200.0, "Available", "ABC123"));
        cars.add(new Car( "Honda", "Civic", "Sedan", 220.0, "Available", "XYZ789"));
        cars.add(new Car( "Ford", "Mustang", "Sports", 350.0, "Available", "DEF456"));

    }


    // Method to convert Car to the new model format when needed
    public java.util.Map<String, Object> getCarDetails(Car car) {
        java.util.Map<String, Object> details = new java.util.HashMap<>();
        details.put("carId", car.getLicensePlate()); // Use licenseNum as carId
        details.put("make", car.getMake());
        details.put("model", car.getModel());
        details.put("type", car.getType());
        details.put("pricePerDay", car.getPricePerDay());
        details.put("availability", car.getAvailability());
        details.put("licensePlate", car.getLicensePlate());
        return details;
    }

    // get singleton instance
    public static AdminDataController getInstance() {
        if (instance == null) {
            instance = new AdminDataController();
        }
        return instance;
    }

    // Method to get all cars
    public ArrayList<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    // Allows searching for cars based off of a particular filter, i.e. make and model
    public java.util.List<Car> getCarsByFilter(java.util.Map<String, Object> filter) {
        java.util.List<Car> filteredCars = new java.util.ArrayList<>();
        for (Car car : cars) {
            boolean match = true;
            // make filter
            if (filter.containsKey("make") && !car.getMake().toLowerCase()
                    .contains(((String) filter.get("make")).toLowerCase())) {
                match = false;
            }
            // type filter
            if (filter.containsKey("type") && !car.getType().equals(filter.get("type"))) {
                match = false;
            }
            // price range filter
            if (filter.containsKey("maxPrice")) {
                double carPrice;
                try {
                    carPrice = car.getPricePerDay();
                } catch (NumberFormatException e) {
                    carPrice = 0.0;
                }
                if (carPrice > (double) filter.get("maxPrice")) {
                    match = false;
                }
            }
            // availability filter
            if (filter.containsKey("availability") &&
                    !car.getAvailability().equals(filter.get("availability"))) {
                match = false;
            }
            if (match) {
                filteredCars.add(car);
            }
        }
        return filteredCars;
    }

    // Checks car availability for specific dates
    public boolean checkAvailability(String carId, java.util.Date startDate, java.util.Date endDate) {
        Car car = findCarByLicenseNum(carId);
        return car != null && car.getAvailability().equals("Available");
    }

    /*
    public java.util.List<Booking> getBookingsForCar(String licensePlate) {
        List<Booking> allBookings = RenterDataController.getInstance().getAllBookings();
        List<Booking> carBookings = new ArrayList<>();
       for (Booking booking : allBookings) {
        if (booking.getLicensePlate().equals(licensePlate)) {
        carBookings.add(booking);
        }
        }
        return carBookings;
    }

    */


    public boolean addCar(Car car) {
        for (Car c : cars) {
            if (c.getLicensePlate().equals(car.getLicensePlate())) {
                return false; // Car with this license already exists
            }
        }
        cars.add(car);
        return true;
    }


    public Car findCarByLicenseNum(String licenseNum) {
        for (Car car : cars) {
            if (car.getLicensePlate().equals(licenseNum)) {
                return car;
            }
        }
        return null;
    }

    public boolean updateCar(Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getLicensePlate().equals(updatedCar.getLicensePlate())) {
                cars.set(i, updatedCar);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCar(String licenseNum) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getLicensePlate().equals(licenseNum)) {
                cars.remove(i);
                return true;
            }
        }
        return false;
    }

    public java.util.List <Booking> getPendingBookingRequests() {

        return RenterDataController.getInstance().getAllBookings();
    }

    public boolean acceptBookingRequest(int index) {
        java.util.List<Booking> pendingRequests = getPendingBookingRequests();
        if (index >= 0 && index < pendingRequests.size()) {
            pendingRequests.get(index).setStatus("Accepted");
            return true;
        }
        return false;
    }

    public boolean rejectBookingRequest(int index) {
        java.util.List<Booking> pendingRequests = getPendingBookingRequests();
        if (index >= 0 && index < pendingRequests.size()) {
            pendingRequests.get(index).setStatus("Rejected");

            return true;
        }
        return false;
    }
}