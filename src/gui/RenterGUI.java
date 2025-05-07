package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenterGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JButton browseCarsBtn;
    private JButton searchCarsBtn;
    private JButton myBookingsBtn;
    private JButton backBtn;

    public RenterGUI() {
        setTitle("Car Rental System - Renter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel();
        centerPane.setLayout(new BorderLayout());
        JLabel headerLabel = new JLabel("Renter Dashboard", JLabel.CENTER);
        centerPane.add(headerLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 20));
        browseCarsBtn = new JButton("Browse All Cars");
        searchCarsBtn = new JButton("Search Cars");
        myBookingsBtn = new JButton("My Bookings");
        backBtn = new JButton("Back to Home");
        browseCarsBtn.addActionListener(this);
        searchCarsBtn.addActionListener(this);
        myBookingsBtn.addActionListener(this);
        backBtn.addActionListener(this);
        buttonPanel.add(browseCarsBtn);
        buttonPanel.add(searchCarsBtn);
        buttonPanel.add(myBookingsBtn);
        buttonPanel.add(backBtn);
        JPanel centerSubPanel = new JPanel();
        centerSubPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerSubPanel.add(buttonPanel);
        centerPane.add(centerSubPanel, BorderLayout.CENTER);
        add(centerPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseCarsBtn) {
            BrowseCarsGUI browseCarsGUI = new BrowseCarsGUI();
            browseCarsGUI.setVisible(true);
            this.setVisible(false);
        } else if (e.getSource() == searchCarsBtn) {
            SearchCarsGUI searchCarsGUI = new SearchCarsGUI();
            searchCarsGUI.setVisible(true);
            this.setVisible(false);
        } else if (e.getSource() == myBookingsBtn) {
            MyBookingsGUI myBookingsGUI = new MyBookingsGUI();
            myBookingsGUI.setVisible(true);
            this.setVisible(false);
        } else if (e.getSource() == backBtn) {
            HomeGUI homeGUI = new HomeGUI();
            homeGUI.setVisible(true);
            this.setVisible(false);
        }
    }

    public void displayGUI() {
        this.setVisible(true);
    }
}