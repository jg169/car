package model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Booking {
    private int bookingId;
    private String licensePlate;
    private Date startDate;
    private Date endDate;
    // Would be either "Pending", "Approved", "Rejected", "Completed", "Cancelled"
    private String status;
    private double totalCost;

    // Constructor
    public Booking(int bookingId, String licensePlate, 
                  Date startDate, Date endDate, String status, double totalCost) {
        this.bookingId = bookingId;
        this.licensePlate = licensePlate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.totalCost = totalCost;
    }

    // Getters and setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

}