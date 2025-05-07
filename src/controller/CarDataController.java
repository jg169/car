package controller;

import java.io.*;
import java.util.*;
import model.Car;

public class CarDataController {
    private static CarDataController instance;
    private List<Car> cars;
    private static final String CAR_FILE = "cars.dat";

    // Singleton
    private CarDataController() {
        cars = new ArrayList<>();
        loadCars();
    }

    // Singleton getInstance method
    public static CarDataController getInstance() {
        if (instance == null) {
            instance = new CarDataController();
        }
        return instance;
    }

    // Load cars from file
    private void loadCars() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAR_FILE))) {
            cars = (List<Car>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Creating new cars file.");
            saveCars();
        }
    }

    // Save cars to file
    private void saveCars() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAR_FILE))) {
            oos.writeObject(cars);
        } catch (IOException e) {
            System.out.println("Error saving cars: " + e.getMessage());
        }
    }

    // Get all cars
    public List<Car> getAllCars() {
        return cars;
    }

    // Get car by ID
    public Car getCarById(String carId) {
        for (Car car : cars) {
            if (car.getCarId().equals(carId)) {
                return car;
            }
        }
        return null;
    }

    // Add car (this should be done on the admin side i just made what it could look like)
    public boolean addCar(Car car) {
        if (getCarById(car.getCarId()) != null) {
            return false;
        }
        cars.add(car);
        saveCars();
        return true;
    }

    // Update car (same as above)
    public boolean updateCar(Car car) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getCarId().equals(car.getCarId())) {
                cars.set(i, car);
                saveCars();
                return true;
            }
        }
        return false;
    }

    // Delete car (same as above)
    public boolean deleteCar(String carId) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getCarId().equals(carId)) {
                cars.remove(i);
                saveCars();
                return true;
            }
        }
        return false;
    }

    // Allows searching for cars based off of a particular filter, i.e. make and model
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
    public boolean checkAvailability(String carId, Date startDate, Date endDate) {
        Car car = getCarById(carId);
        return car != null && car.getAvailability().equals("Available");
    }
}