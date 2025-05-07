package model;

import java.util.HashMap;
import java.util.Map;

public class Car {
    private String carId;
    private String make;
    private String model;
    private String type;
    private String availability;
    private double pricePerDay;
    private String licensePlate;

    public Car(String carId, String make, String model, String type, 
               double pricePerDay, String availability, String licensePlate) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.type = type;
        this.pricePerDay = pricePerDay;
        this.availability = availability;
        this.licensePlate = licensePlate;
    }

    // Getters and setters
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    // Method to get car details as a map
    public Map<String, Object> getDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("carId", carId);
        details.put("make", make);
        details.put("model", model);
        details.put("type", type);
        details.put("pricePerDay", pricePerDay);
        details.put("availability", availability);
        details.put("licensePlate", licensePlate);
        return details;
    }

    @Override
    public String toString() {
        return make + " " + model;
    }
}