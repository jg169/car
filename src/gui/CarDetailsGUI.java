package gui;

import javax.swing.*;

import controller.RenterDataController;
import model.Car;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CarDetailsGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JPanel specificationPanel;
    private JButton backBtn;
    private JButton bookCarBtn;
    private String currentLicensePlate;
    private JLabel imageLabel;

    public CarDetailsGUI() {
        setTitle("Car Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel();
        centerPane.setLayout(new BorderLayout(10, 10));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Car Details", JLabel.CENTER);
        centerPane.add(titleLabel, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        specificationPanel = new JPanel();
        specificationPanel.setLayout(new BoxLayout(specificationPanel, BoxLayout.Y_AXIS));
        specificationPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        contentPanel.add(imagePanel, BorderLayout.WEST);
        contentPanel.add(specificationPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bookCarBtn = new JButton("Book This Car");
        backBtn = new JButton("Back");
        bookCarBtn.addActionListener(this);
        backBtn.addActionListener(this);
        buttonPanel.add(bookCarBtn);
        buttonPanel.add(backBtn);
        centerPane.add(scrollPane, BorderLayout.CENTER);
        centerPane.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPane);
    }

    public void loadCarDetails(String licensePlate) {
        currentLicensePlate = licensePlate;
        Car car = RenterDataController.getInstance().getCarByLicensePlate(licensePlate);
        if (car != null) {
            setTitle("Car Details - " + car.getMake() + " " + car.getModel());
            specificationPanel.removeAll();
            JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            addDetail(detailsPanel, "License Plate:", car.getLicensePlate());
            addDetail(detailsPanel, "Make:", car.getMake());
            addDetail(detailsPanel, "Model:", car.getModel());
            addDetail(detailsPanel, "Type:", car.getType());
            addDetail(detailsPanel, "Price Per Day:", "$" + car.getPricePerDay());
            addDetail(detailsPanel, "Availability:", car.getAvailability());
            specificationPanel.add(detailsPanel);
            loadCarImage(car.getImageFile());
            specificationPanel.revalidate();
            specificationPanel.repaint();
        }
    }
    
    private void loadCarImage(String imageFileName) {
        if (imageFileName == null || imageFileName.trim().isEmpty()) {
            imageLabel.setIcon(null);
            imageLabel.setText("No image available");
            return;
        }
        String imagePath = "images/" + imageFileName;
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image image = originalIcon.getImage();
            Image resizedImage = image.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            imageLabel.setIcon(resizedIcon);
            imageLabel.setText("");
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("Image not found: " + imageFileName);
        }
    }

    private void addDetail(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        JLabel valueComponent = new JLabel(value);
        panel.add(labelComponent);
        panel.add(valueComponent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookCarBtn) {
            BookCarGUI bookCarGUI = new BookCarGUI();
            bookCarGUI.displayGUI(currentLicensePlate);
            this.setVisible(false);
        } else if (e.getSource() == backBtn) {
            this.setVisible(false);
            SearchCarsGUI searchCarsGUI = new SearchCarsGUI();
            searchCarsGUI.setVisible(true);
        }
    }

    public void displayGUI(String licensePlate) {
        loadCarDetails(licensePlate);
        this.setVisible(true);
    }
}