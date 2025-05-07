package controller;

import java.io.*;
import java.util.*;

import model.Booking;

public class BookingDataController {
    private static BookingDataController instance;
    private List<Booking> bookings;
    private static final String BOOKING_FILE = "bookings.dat";
    private int nextBookingId = 1;

    // singleton pattern
    private BookingDataController() {
        bookings = new ArrayList<>();
        loadBookings();
    }

    // Singleton getInstance method
    public static BookingDataController getInstance() {
        if (instance == null) {
            instance = new BookingDataController();
        }
        return instance;
    }

    // Load the bookings from file
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

    // Save bookings
    private void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKING_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    // bookings based off of user ID
    public List<Booking> getBookingsByUser(String userId) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUserId().equals(userId)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
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
                booking.getCarId(), booking.getStartDate(), booking.getEndDate())) {
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
                        updatedBooking.getCarId(), 
                        updatedBooking.getStartDate(), 
                        updatedBooking.getEndDate())) {
                    return false;
                }
                bookings.set(i, updatedBooking);
                saveBookings();
                return true;
            }
        }
        // if booking not found
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
}