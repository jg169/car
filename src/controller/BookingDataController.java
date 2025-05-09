package controller;

import java.io.*;
import Adminstuff.*;
import java.util.*;
import model.Booking;
import model.Car;
import Adminstuff.*;

public class BookingDataController {
    private static BookingDataController instance;
    private List<Booking> bookings;
    private static final String BOOKING_FILE = "bookings.dat";
    private int nextBookingId = 1;

    private BookingDataController() {
        bookings = new ArrayList<>();
        loadBookings();
    }

    public static BookingDataController getInstance() {
        if (instance == null) {
            instance = new BookingDataController();
        }
        return instance;
    }

    private void loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKING_FILE))) {
            bookings = (List<Booking>) ois.readObject();
            // Find the highest booking ID for sorting
            for (Booking booking : bookings) {
                if (booking.getBookingId() >= nextBookingId) {
                    nextBookingId = booking.getBookingId() + 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Creating new bookings file.");
            saveBookings();
        }
    }

    private void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKING_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookings;
    }

    // Get booking by booking ID
    public Booking getBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    // Create new booking
    public boolean createBooking(Booking booking) {
        booking.setBookingId(nextBookingId++);
        if (!CarDataController.getInstance().checkAvailability(
                booking.getLicensePlate(), booking.getStartDate(), booking.getEndDate())) {
            return false;
        }
        bookings.add(booking);
        saveBookings();
        return true;
    }

    // Update existing booking
    public boolean updateBooking(int bookingId, Booking updatedBooking) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == bookingId) {
                updatedBooking.setBookingId(bookingId);
                // Check if the car is available
                if (!CarDataController.getInstance().checkAvailability(
                        updatedBooking.getLicensePlate(),
                        updatedBooking.getStartDate(),
                        updatedBooking.getEndDate())) {
                    return false;
                }
                bookings.set(i, updatedBooking);
                saveBookings();
                return true;
            }
        }
        return false;
    }

    public boolean cancelBooking(int bookingId) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == bookingId) {
                Booking booking = bookings.get(i);
                booking.setStatus("Cancelled");
                bookings.set(i, booking);
                saveBookings();
                return true;
            }
        }
        return false;
    }
    public List<Car> getAllCars(){
    	return Adminstuff.CarDataFileController.getInstance().getAllCars();
    }
    
    // Allows searching for cars based off of a particular filter like type, model, etc.
    public List<Car> getCarsByFilter(java.util.Map<String, Object> filter) {
        return Adminstuff.CarDataFileController.getInstance().getCarsByFilter(filter);
    }

}