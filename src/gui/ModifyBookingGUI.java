package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import model.Car;
import model.Booking;
import controller.CarDataController;
import controller.BookingDataController;

public class ModifyBookingGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JPanel bookingPanel;
    private JComboBox<Integer> startDayCombo, startMonthCombo, startYearCombo;
    private JComboBox<Integer> endDayCombo, endMonthCombo, endYearCombo;
    private JLabel updatedCostLabel;
    private JButton saveChangesBtn;
    private JButton cancelBtn;
    private int currentBookingId;
    private Booking currentBooking;
    private Car currentCar;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public ModifyBookingGUI() {
        setTitle("Modify Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel(new BorderLayout(10, 10));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Modify Booking", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        centerPane.add(titleLabel, BorderLayout.NORTH);
        setupBookingPanel();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        saveChangesBtn = new JButton("Save Changes");
        cancelBtn = new JButton("Cancel");
        saveChangesBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        buttonPanel.add(saveChangesBtn);
        buttonPanel.add(cancelBtn);
        centerPane.add(bookingPanel, BorderLayout.CENTER);
        centerPane.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPane);
    }
    
    private void setupBookingPanel() {
        bookingPanel = new JPanel(new GridLayout(0, 2, 10, 20));
        bookingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bookingPanel.add(new JLabel("Booking ID:"));
        JLabel bookingIdLabel = new JLabel();
        bookingIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingPanel.add(bookingIdLabel);
        bookingPanel.add(new JLabel("Car:"));
        JLabel carLabel = new JLabel();
        carLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingPanel.add(carLabel);
        bookingPanel.add(new JLabel("Current Status:"));
        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingPanel.add(statusLabel);
        bookingPanel.add(new JLabel("Price per Day:"));
        JLabel priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingPanel.add(priceLabel);
        setupDatePanels();
        bookingPanel.add(new JLabel("Updated Total Cost:"));
        updatedCostLabel = new JLabel();
        updatedCostLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bookingPanel.add(updatedCostLabel);
    }
    
    private void setupDatePanels() {
        startDayCombo = new JComboBox<>(getDaysArray());
        startMonthCombo = new JComboBox<>(getMonthsArray());
        startYearCombo = new JComboBox<>(getYearsArray());
        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDatePanel.add(new JLabel("Day:"));
        startDatePanel.add(startDayCombo);
        startDatePanel.add(new JLabel("Month:"));
        startDatePanel.add(startMonthCombo);
        startDatePanel.add(new JLabel("Year:"));
        startDatePanel.add(startYearCombo);
        startDayCombo.addActionListener(this);
        startMonthCombo.addActionListener(this);
        startYearCombo.addActionListener(this);
        bookingPanel.add(new JLabel("Start Date:"));
        bookingPanel.add(startDatePanel);
        endDayCombo = new JComboBox<>(getDaysArray());
        endMonthCombo = new JComboBox<>(getMonthsArray());
        endYearCombo = new JComboBox<>(getYearsArray());
        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        endDatePanel.add(new JLabel("Day:"));
        endDatePanel.add(endDayCombo);
        endDatePanel.add(new JLabel("Month:"));
        endDatePanel.add(endMonthCombo);
        endDatePanel.add(new JLabel("Year:"));
        endDatePanel.add(endYearCombo);
        endDayCombo.addActionListener(this);
        endMonthCombo.addActionListener(this);
        endYearCombo.addActionListener(this);
        bookingPanel.add(new JLabel("End Date:"));
        bookingPanel.add(endDatePanel);
    }
    
    // Helper methods to create arrays for combo boxes for dates
    private Integer[] getDaysArray() {
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        return days;
    }
    
    private Integer[] getMonthsArray() {
        Integer[] months = new Integer[12];
        for (int i = 0; i < 12; i++) {
            months[i] = i + 1;
        }
        return months;
    }
    
    private Integer[] getYearsArray() {
        Integer[] years = new Integer[5];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 5; i++) {
            years[i] = currentYear + i;
        }
        return years;
    }
    
    public void loadBookingDetails(int bookingId) {
        currentBooking = BookingDataController.getInstance().getBookingById(bookingId);
        if (currentBooking != null) {
            // Use BookingDataController instead of directly using CarDataController
            currentCar = BookingDataController.getInstance().getCarByLicensePlate(currentBooking.getLicensePlate());
            if (currentCar != null) {
                Component[] components = bookingPanel.getComponents();
                ((JLabel)components[1]).setText(String.valueOf(currentBooking.getBookingId()));
                ((JLabel)components[3]).setText(currentCar.getMake() + " " + currentCar.getModel());
                ((JLabel)components[5]).setText(currentBooking.getStatus());
                ((JLabel)components[7]).setText("$" + currentCar.getPricePerDay());
                // Set date values
                Calendar startCal = Calendar.getInstance();
                startCal.setTime(currentBooking.getStartDate());
                startDayCombo.setSelectedItem(startCal.get(Calendar.DAY_OF_MONTH));
                startMonthCombo.setSelectedItem(startCal.get(Calendar.MONTH) + 1);
                startYearCombo.setSelectedItem(startCal.get(Calendar.YEAR));
                Calendar endCal = Calendar.getInstance();
                endCal.setTime(currentBooking.getEndDate());
                endDayCombo.setSelectedItem(endCal.get(Calendar.DAY_OF_MONTH));
                endMonthCombo.setSelectedItem(endCal.get(Calendar.MONTH) + 1);
                endYearCombo.setSelectedItem(endCal.get(Calendar.YEAR));
                calculateUpdatedCost();
                // Disable editing for cancelled bookings
                boolean canModify = !currentBooking.getStatus().equals("Cancelled");
                startDayCombo.setEnabled(canModify);
                startMonthCombo.setEnabled(canModify);
                startYearCombo.setEnabled(canModify);
                endDayCombo.setEnabled(canModify);
                endMonthCombo.setEnabled(canModify);
                endYearCombo.setEnabled(canModify);
                saveChangesBtn.setEnabled(canModify);
                if (!canModify) {
                    JOptionPane.showMessageDialog(this, 
                        "This booking has been cancelled and cannot be modified.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    
    private Date getStartDate() throws ParseException {
        int day = (Integer) startDayCombo.getSelectedItem();
        int month = (Integer) startMonthCombo.getSelectedItem();
        int year = (Integer) startYearCombo.getSelectedItem();
        return dateFormat.parse(year + "-" + month + "-" + day);
    }
    
    private Date getEndDate() throws ParseException {
        int day = (Integer) endDayCombo.getSelectedItem();
        int month = (Integer) endMonthCombo.getSelectedItem();
        int year = (Integer) endYearCombo.getSelectedItem();
        return dateFormat.parse(year + "-" + month + "-" + day);
    }
    
    private void calculateUpdatedCost() {
        if (currentCar == null || currentBooking == null) {
            return;
        }
        try {
            Date startDate = getStartDate();
            Date endDate = getEndDate();
            if (startDate != null && endDate != null) {
                long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
                int days = (int) (diffInMillies / (1000 * 60 * 60 * 24)) + 1;
                // Calculate cost
                double totalCost = days * currentCar.getPricePerDay();
                updatedCostLabel.setText("$" + totalCost);
                if (totalCost != currentBooking.getTotalCost()) {
                    updatedCostLabel.setText("$" + totalCost + 
                        (totalCost > currentBooking.getTotalCost() ? 
                        " (+" : " (") + 
                        "$" + Math.abs(totalCost - currentBooking.getTotalCost()) + ")");
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error in date format: " + e.getMessage());
        }
    }
    
    public void updateBooking() {
        if (currentCar == null || currentBooking == null) {
            return;
        }
        try {
            Date startDate = getStartDate();
            Date endDate = getEndDate();
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(this, "End date must be after start date.");
                return;
            }
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            int days = (int) (diffInMillies / (1000 * 60 * 60 * 24)) + 1;
            double totalCost = days * currentCar.getPricePerDay();
            // Create an updated booking object
            Booking updatedBooking = new Booking(
                currentBooking.getBookingId(),
                currentBooking.getLicensePlate(),
                startDate,
                endDate,
                currentBooking.getStatus(),
                totalCost
            );
            boolean success = BookingDataController.getInstance().updateBooking(
                currentBooking.getBookingId(), updatedBooking);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking updated successfully!");
                MyBookingsGUI myBookingsGUI = new MyBookingsGUI();
                myBookingsGUI.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update booking. The car may not be available for the selected dates.",
                    "Update Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error in date format: " + e.getMessage());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startDayCombo || e.getSource() == startMonthCombo || 
            e.getSource() == startYearCombo || e.getSource() == endDayCombo || 
            e.getSource() == endMonthCombo || e.getSource() == endYearCombo) {
            calculateUpdatedCost();
        } else if (e.getSource() == saveChangesBtn) {
            updateBooking();
        } else if (e.getSource() == cancelBtn) {
            // Go back to My Bookings
            MyBookingsGUI myBookingsGUI = new MyBookingsGUI();
            myBookingsGUI.setVisible(true);
            this.setVisible(false);
        }
    }
    
    public void displayGUI(int bookingId) {
        currentBookingId = bookingId;
        loadBookingDetails(bookingId);
        this.setVisible(true);
    }
}