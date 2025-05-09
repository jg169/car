package Adminstuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import model.Car;
import model.Booking;
import controller.BookingDataController;

//model booking request, need to integrate later with other.
public class BookingRequest {
    private String customerName;
    private String carLicenseNum;
    private String startDate;
    private String endDate;
    private boolean pending;

    public BookingRequest(String customerName, String carLicenseNum, String startDate, String endDate) {
        this.customerName = customerName;
        this.carLicenseNum = carLicenseNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pending = true;
    }

    public String getCustomerName() { return customerName; }
    public String getCarLicenseNum() { return carLicenseNum; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public boolean isPending() { return pending; }
    public void accept() { this.pending = false; }
    public void reject() { this.pending = false; }

    @Override
    public String toString() {
        return customerName + " - " + carLicenseNum + " - " + startDate + " to " + endDate;
    }
}