package Adminstuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import model.Car;
import model.Booking;
import controller.BookingDataController;

// CarDataFileController, is Singleton pattern
// Modify the CarDataFileController class to work with the new Car model

public class CarDataFileController {
    private static CarDataFileController instance;
    private ArrayList<Car> cars;
    private ArrayList<BookingRequest> bookingRequests;

    // Private constructor for singleton
    private CarDataFileController() {
        cars = new ArrayList<>();
        bookingRequests = new ArrayList<>();

        // manual sample data, DELETE LATER
        // Using adapted constructor to match your new Car model


        cars.add(new Car( "Toyota", "Camry", "Sedan", 25000.0, "Available", "ABC123"));
        cars.add(new Car( "Honda", "Civic", "Sedan", 22000.0, "Available", "XYZ789"));
        cars.add(new Car( "Ford", "Mustang", "Sports", 35000.0, "Available", "DEF456"));

        bookingRequests.add(new BookingRequest("John Doe", "ABC123", "05/10/2025", "05/15/2025"));
        bookingRequests.add(new BookingRequest("Jane Smith", "XYZ789", "05/12/2025", "05/14/2025"));
        bookingRequests.add(new BookingRequest("Bob Johnson", "DEF456", "05/20/2025", "05/25/2025"));
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
    public static CarDataFileController getInstance() {
        if (instance == null) {
            instance = new CarDataFileController();
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
        List<Booking> allBookings = BookingDataController.getInstance().getAllBookings();
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

    // Overloaded method to add a car using the new Car model format
    public boolean addCar(String carId, String make, String model, String type,
                         double pricePerDay, String availability, String licensePlate) {
        // Create a car with the current format

        Car car = new Car( make, model, type,
        pricePerDay,  availability, licensePlate);
        return addCar(car);
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

        return BookingDataController.getInstance().getAllBookings();
    }

    public boolean acceptBookingRequest(int index) {
        java.util.List<Booking> pendingRequests = getPendingBookingRequests();
        if (index >= 0 && index < pendingRequests.size()) {
            //pendingRequests.get(index).accept();
            return true;
        }
        return false;
    }

    public boolean rejectBookingRequest(int index) {
        java.util.List<Booking> pendingRequests = getPendingBookingRequests();
        if (index >= 0 && index < pendingRequests.size()) {
            //pendingRequests.get(index).reject();
            return true;
        }
        return false;
    }
}