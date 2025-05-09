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

public class AddNewCarGUI extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldMake;
    private JTextField textFieldModel;
    private JTextField textFieldPrice;
    private JTextField textFieldType;
    private JTextField textFieldAvailability;
    private JTextField textFieldLicenseNum;
    private JButton btnAddToInventory;
    private JButton btnCancel;
    private CarDataFileController controller;

    public AddNewCarGUI() {
        setTitle("Add New Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 450, 300);
        setLocationRelativeTo(null);

        controller = CarDataFileController.getInstance();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Make:"));
        textFieldMake = new JTextField();
        formPanel.add(textFieldMake);

        formPanel.add(new JLabel("Model:"));
        textFieldModel = new JTextField();
        formPanel.add(textFieldModel);

        formPanel.add(new JLabel("Price:"));
        textFieldPrice = new JTextField();
        formPanel.add(textFieldPrice);

        formPanel.add(new JLabel("Type:"));
        textFieldType = new JTextField();
        formPanel.add(textFieldType);

        formPanel.add(new JLabel("Availability:"));
        textFieldAvailability = new JTextField();
        formPanel.add(textFieldAvailability);

        formPanel.add(new JLabel("License Number:"));
        textFieldLicenseNum = new JTextField();
        formPanel.add(textFieldLicenseNum);

        JPanel buttonPanel = new JPanel();
        btnAddToInventory = new JButton("Add to Inventory");
        btnCancel = new JButton("Cancel");

        buttonPanel.add(btnAddToInventory);
        buttonPanel.add(btnCancel);

        JLabel titleLabel = new JLabel("Add New Car", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnAddToInventory.addActionListener(e -> saveCarInfo());
        btnCancel.addActionListener(e -> dispose());
    }

    private void saveCarInfo() {
        String make = textFieldMake.getText();
        String model = textFieldModel.getText();
        String price = textFieldPrice.getText();
        String type = textFieldType.getText();
        String availability = textFieldAvailability.getText();
        String licenseNum = textFieldLicenseNum.getText();

        if (make.isEmpty() || model.isEmpty() || price.isEmpty() ||
            type.isEmpty() || availability.isEmpty() || licenseNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        //make sure to go back and change PricePerDay in things


        Car car = new Car( make, model, type,
        Double.parseDouble(price),  availability, licenseNum);

        //Car car = new Car(make, model, price, type, availability, licenseNum);

        boolean success = controller.addCar(car);

        if(success){
            System.out.println("car added");
        }
    }
}
