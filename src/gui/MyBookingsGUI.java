package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.BookingDataController;
import controller.CarDataController;
import model.Booking;
import model.Car;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyBookingsGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JTable bookingsTable;
    private JButton viewDetailsBtn;
    private JButton modifyBookingBtn;
    private JButton cancelBookingBtn;
    private JButton backBtn;
    private DefaultTableModel tableModel;
    private List<Booking> bookingList;
    
    public MyBookingsGUI() {
        setTitle("My Bookings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel();
        centerPane.setLayout(new BorderLayout(10, 10));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("My Bookings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        centerPane.add(titleLabel, BorderLayout.NORTH);
        // Create table
        String[] columns = {"Booking ID", "Car", "Start Date", "End Date", "Total Cost", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        bookingsTable.setFillsViewportHeight(true);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        viewDetailsBtn = new JButton("View Details");
        modifyBookingBtn = new JButton("Modify Booking");
        cancelBookingBtn = new JButton("Cancel Booking");
        backBtn = new JButton("Back");
        viewDetailsBtn.addActionListener(this);
        modifyBookingBtn.addActionListener(this);
        cancelBookingBtn.addActionListener(this);
        backBtn.addActionListener(this);
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(modifyBookingBtn);
        buttonPanel.add(cancelBookingBtn);
        buttonPanel.add(backBtn);
        centerPane.add(scrollPane, BorderLayout.CENTER);
        centerPane.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPane);
        // Dummy user ID
        loadBookings("123");
    }
    
    public void loadBookings(String userId) {
        tableModel.setRowCount(0);
        bookingList = BookingDataController.getInstance().getBookingsByUser(userId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Booking booking : bookingList) {
            Car car = CarDataController.getInstance().getCarById(booking.getCarId());
            String carName = "Unknown";
            if (car != null) {
                carName = car.getMake() + " " + car.getModel();
            }
            Object[] row = {
                booking.getBookingId(),
                carName,
                dateFormat.format(booking.getStartDate()),
                dateFormat.format(booking.getEndDate()),
                "$" + booking.getTotalCost(),
                booking.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    public void cancelBooking(int bookingId) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel this booking?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = BookingDataController.getInstance().cancelBooking(bookingId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                loadBookings("123");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking. Please try again.");
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = bookingsTable.getSelectedRow();
        if (e.getSource() == viewDetailsBtn) {
            if (selectedRow != -1) {
                JOptionPane.showMessageDialog(this, "Booking details would be shown here");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a booking first");
            }
        } else if (e.getSource() == modifyBookingBtn) {
            if (selectedRow != -1) {
                int bookingId = (int) tableModel.getValueAt(selectedRow, 0);
                ModifyBookingGUI modifyGUI = new ModifyBookingGUI();
                modifyGUI.displayGUI(bookingId);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a booking first");
            }
        } else if (e.getSource() == cancelBookingBtn) {
            if (selectedRow != -1) {
                int bookingId = (int) tableModel.getValueAt(selectedRow, 0);
                cancelBooking(bookingId);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a booking first");
            }
        } else if (e.getSource() == backBtn) {
            RenterGUI renterGUI = new RenterGUI();
            renterGUI.setVisible(true);
            this.setVisible(false);
        }
    }
    
    public void displayGUI() {
        this.setVisible(true);
    }
}
