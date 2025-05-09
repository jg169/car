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
import controller.CarDataController;
import controller.BookingDataController;
import model.Booking;

public class BookCarGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JPanel bookingPanel;
    private JComboBox<Integer> startDayCombo, startMonthCombo, startYearCombo;
    private JComboBox<Integer> endDayCombo, endMonthCombo, endYearCombo;
    private JLabel totalCostLabel;
    private JButton confirmBookingBtn;
    private JButton cancelBtn;
    private String currentLicensePlate;
    private Car currentCar;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BookCarGUI() {
        setTitle("Book a Car");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel(new BorderLayout(10, 10));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Book a Car", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        centerPane.add(titleLabel, BorderLayout.NORTH);
        setupBookingPanel();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        confirmBookingBtn = new JButton("Confirm Booking");
        cancelBtn = new JButton("Cancel");
        confirmBookingBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        buttonPanel.add(confirmBookingBtn);
        buttonPanel.add(cancelBtn);
        centerPane.add(bookingPanel, BorderLayout.CENTER);
        centerPane.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPane);
    }
    
    private void setupBookingPanel() {
        bookingPanel = new JPanel(new GridLayout(0, 2, 10, 20));
        bookingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Car info
        bookingPanel.add(new JLabel("Car:"));
        JLabel carLabel = new JLabel();
        carLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingPanel.add(carLabel);
        // Price info
        bookingPanel.add(new JLabel("Price per Day:"));
        JLabel priceLabel = new JLabel();
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bookingPanel.add(priceLabel);
        // Date
        setupDatePanels();
        // Total cost
        bookingPanel.add(new JLabel("Total Cost:"));
        totalCostLabel = new JLabel();
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bookingPanel.add(totalCostLabel);
    }
    
    private void setupDatePanels() {
        JPanel startDatePanel = createDatePanel(startDayCombo, startMonthCombo, startYearCombo);
        bookingPanel.add(new JLabel("Start Date:"));
        bookingPanel.add(startDatePanel);
        JPanel endDatePanel = createDatePanel(endDayCombo, endMonthCombo, endYearCombo);
        bookingPanel.add(new JLabel("End Date:"));
        bookingPanel.add(endDatePanel);
        // Set default dates to today and tomorrow
        Calendar cal = Calendar.getInstance();
        startDayCombo.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
        startMonthCombo.setSelectedItem(cal.get(Calendar.MONTH) + 1);
        startYearCombo.setSelectedItem(cal.get(Calendar.YEAR));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        endDayCombo.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
        endMonthCombo.setSelectedItem(cal.get(Calendar.MONTH) + 1);
        endYearCombo.setSelectedItem(cal.get(Calendar.YEAR));
    }
    
    private JPanel createDatePanel(JComboBox<Integer> dayCombo, JComboBox<Integer> monthCombo, JComboBox<Integer> yearCombo) {
        dayCombo = new JComboBox<>(getDaysArray());
        monthCombo = new JComboBox<>(getMonthsArray());
        yearCombo = new JComboBox<>(getYearsArray());
        // Add action listeners
        dayCombo.addActionListener(this);
        monthCombo.addActionListener(this);
        yearCombo.addActionListener(this);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Day:"));
        datePanel.add(dayCombo);
        datePanel.add(new JLabel("Month:"));
        datePanel.add(monthCombo);
        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearCombo);
        return datePanel;
    }
    
    // Helper methods just to create arrays for combo boxes for dates
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
    
    public void displayGUI(String licensePlate) {
        currentLicensePlate = licensePlate;
        currentCar = CarDataController.getInstance().getCarByLicensePlate(licensePlate);
        if (currentCar != null) {
            setTitle("Book a Car - " + currentCar.getMake() + " " + currentCar.getModel());
            Component[] components = bookingPanel.getComponents();
            ((JLabel)components[1]).setText(currentCar.getMake() + " " + currentCar.getModel());
            ((JLabel)components[3]).setText("$" + currentCar.getPricePerDay());
            calculateCost();
        }
        this.setVisible(true);
    }
    
    public double calculateCost() {
        if (currentCar == null) {
            return 0.0;
        }
        try {
            Date startDate = getStartDate();
            Date endDate = getEndDate();
            if (startDate != null && endDate != null) {
                long diffInMilliS = Math.abs(endDate.getTime() - startDate.getTime());
                int days = (int) (diffInMilliS / (1000 * 60 * 60 * 24)) + 1;
                // Calculate the total cost
                double totalCost = days * currentCar.getPricePerDay();
                totalCostLabel.setText("$" + totalCost);
                return totalCost;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error in date format: " + e.getMessage());
        }
        return 0.0;
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
    
    public void submitBooking() {
        if (currentCar == null) {
            return;
        }
        try {
            Date startDate = getStartDate();
            Date endDate = getEndDate();
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(this, "End date must be after start date.");
                return;
            }
            double totalCost = calculateCost();
            // Create booking object
            Booking booking = new Booking(
                0,
                currentLicensePlate,
                startDate,
                endDate,
                "Pending",
                totalCost
            );
            boolean success = BookingDataController.getInstance().createBooking(booking);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking created successfully!");
                MyBookingsGUI myBookingsGUI = new MyBookingsGUI();
                myBookingsGUI.setVisible(true);
                this.setVisible(false);
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
            calculateCost();
        } else if (e.getSource() == confirmBookingBtn) {
            submitBooking();
        } else if (e.getSource() == cancelBtn) {
            BrowseCarsGUI browseCarsGUI = new BrowseCarsGUI();
            browseCarsGUI.setVisible(true);
            this.setVisible(false);
        }
    }
}