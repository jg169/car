package Adminstuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import model.Car;
import model.Booking;
import controller.RenterDataController;

public class ViewRequestsGUI extends JFrame {
    private JPanel contentPane;
    private JList<Booking> bookingsList;
    private DefaultListModel<Booking> listModel;
    private JButton btnAcceptRequestBooking;
    private JButton btnRejectRequestBooking;
    private JButton btnCancel;
    private AdminDataController controller;

    public ViewRequestsGUI() {
        setTitle("View Booking Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 450, 300);
        setLocationRelativeTo(null);

        controller = AdminDataController.getInstance();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        listModel = new DefaultListModel<>();
        java.util.List<Booking> pendingRequests = AdminDataController.getInstance().getPendingBookingRequests();
        for (Booking request : pendingRequests) {
            listModel.addElement(request);
        }

        bookingsList = new JList<Booking>(listModel);
        bookingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Booking request = (Booking) value;
                return super.getListCellRendererComponent(list, request.toString(), index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scrollPane = new JScrollPane(bookingsList);

        JPanel buttonPanel = new JPanel();
        btnAcceptRequestBooking = new JButton("Accept Booking");
        btnRejectRequestBooking = new JButton("Reject Booking");
        btnCancel = new JButton("Close");

        buttonPanel.add(btnAcceptRequestBooking);
        buttonPanel.add(btnRejectRequestBooking);
        buttonPanel.add(btnCancel);

        JLabel titleLabel = new JLabel("Booking Requests", JLabel.CENTER);

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnAcceptRequestBooking.addActionListener(e -> acceptRequestBooking());
        btnRejectRequestBooking.addActionListener(e -> rejectRequestBooking());
        btnCancel.addActionListener(e -> dispose());
    }

    private void acceptRequestBooking() {
        int selectedIndex = bookingsList.getSelectedIndex();
        if (selectedIndex == -1) {
            System.out.println("problem Nothing selected");
            return;
        }

        boolean success = controller.acceptBookingRequest(selectedIndex);
        if (success) {
            refreshBookingsList();
        }
    }

    private void rejectRequestBooking() {
        int selectedIndex = bookingsList.getSelectedIndex();
        if (selectedIndex == -1) {
            System.out.println("problem Nothing selected");
            return;
        }

        boolean success = controller.rejectBookingRequest(selectedIndex);
        if (success) {
            refreshBookingsList();
        }
    }

    private void refreshBookingsList() {
        listModel.clear();
        java.util.List<Booking> pendingRequests = AdminDataController.getInstance().getPendingBookingRequests();
        for (Booking request : pendingRequests) {
            listModel.addElement(request);
        }
    }
}
