package controller;

import java.io.*;
import java.util.*;
import model.Car;
import model.Booking;

public class CarDataController {
    private static CarDataController instance;
    private List<Car> cars;
    private static final String CAR_FILE = "cars.dat";

    // singleton constructor
    private CarDataController() {
        cars = new ArrayList<>();
        loadCars();
    }

    public static CarDataController getInstance() {
        if (instance == null) {
            instance = new CarDataController();
        }
        return instance;
    }

    private void loadCars() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAR_FILE))) {
            cars = (List<Car>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Creating new cars file.");
            saveCars();
        }
    }

    private void saveCars() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAR_FILE))) {
            oos.writeObject(cars);
        } catch (IOException e) {
            System.out.println("Error saving cars: " + e.getMessage());
        }
    }

    public List<Car> getAllCars() {
        return cars;
    }

    public Car getCarByLicensePlate(String licensePlate) {
        for (Car car : cars) {
            if (car.getLicensePlate().equals(licensePlate)) {
                return car;
            }
        }
        return null;
    }


    // Allows searching for cars based off of a particular filter like type, model, etc.
    public List<Car> getCarsByFilter(Map<String, Object> filter) {
        List<Car> filteredCars = new ArrayList<>();
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
            if (filter.containsKey("maxPrice") && car.getPricePerDay() > (double) filter.get("maxPrice")) {
                match = false;
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
    public boolean checkAvailability(String licensePlate, Date startDate, Date endDate) {
        Car car = getCarByLicensePlate(licensePlate);
        return car != null && car.getAvailability().equals("Available");
    }

    public List<Booking> getBookingsForCar(String licensePlate) {
        List<Booking> allBookings = RenterDataController.getInstance().getAllBookings();
        List<Booking> carBookings = new ArrayList<>();
        for (Booking booking : allBookings) {
            if (booking.getLicensePlate().equals(licensePlate)) {
                carBookings.add(booking);
            }
        }
        return carBookings;
    }

}