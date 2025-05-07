package model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Booking {
    private int bookingId;
    private String carId;
    private String userId;
    private Date startDate;
    private Date endDate;
    // Would be either "Pending", "Approved", "Rejected", "Completed", "Cancelled"
    private String status;
    private double totalCost;

    // Constructor
    public Booking(int bookingId, String carId, String userId, 
                  Date startDate, Date endDate, String status, double totalCost) {
        this.bookingId = bookingId;
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.totalCost = totalCost;
    }

    // Getters and setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    // Calculate duration in days
    public int calculateDuration() {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}