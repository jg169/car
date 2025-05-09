package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Adminstuff.*;

public class HomeGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JButton adminBtn;
    private JButton renterBtn;

    public HomeGUI() {
        setTitle("Car Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel();
        centerPane.setLayout(new BorderLayout());
        JLabel headerLabel = new JLabel("Welcome to Car Rental System", JLabel.CENTER);
        centerPane.add(headerLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 20));
        adminBtn = new JButton("Admin Login");
        renterBtn = new JButton("Renter Login");
        adminBtn.addActionListener(this);
        renterBtn.addActionListener(this);
        buttonPanel.add(adminBtn);
        buttonPanel.add(renterBtn);
        JPanel centerSubPanel = new JPanel();
        centerSubPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerSubPanel.add(buttonPanel);
        centerPane.add(centerSubPanel, BorderLayout.CENTER);
        add(centerPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminBtn) {
            showAdminContent();
        } else if (e.getSource() == renterBtn) {
            showRenterGUI();
        }
    }

    public void showAdminContent() {
        AdminGUI adminGUI = new AdminGUI();
        adminGUI.setVisible(true);
        this.setVisible(false);
    }

    public void showRenterGUI() {
        RenterGUI renterGUI = new RenterGUI();
        renterGUI.setVisible(true);
        this.setVisible(false);
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeGUI home = new HomeGUI();
            home.setVisible(true);
        });
    }
}