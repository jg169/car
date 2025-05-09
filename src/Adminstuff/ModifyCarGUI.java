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

public class ModifyCarGUI extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldMake;
    private JTextField textFieldModel;
    private JTextField textFieldPrice;
    private JTextField textFieldType;
    private JTextField textFieldAvailability;
    private JTextField textFieldLicenseNum;
    private JButton btnSearchByLicenseNum;
    private JButton btnSaveModification;
    private JButton btnClose;
    private AdminDataController controller;

    public ModifyCarGUI() {
        setTitle("Modify Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 450, 300);
        setLocationRelativeTo(null);

        controller = AdminDataController.getInstance();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchPanel.add(new JLabel("License Number:"));
        textFieldLicenseNum = new JTextField(15);
        searchPanel.add(textFieldLicenseNum);
        btnSearchByLicenseNum = new JButton("Search");
        searchPanel.add(btnSearchByLicenseNum);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
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

        JPanel buttonPanel = new JPanel();
        btnSaveModification = new JButton("Save Modification");
        btnClose = new JButton("Close");

        buttonPanel.add(btnSaveModification);
        buttonPanel.add(btnClose);

        JLabel titleLabel = new JLabel("Modify Car", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(searchPanel, BorderLayout.PAGE_START);
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);


        btnSearchByLicenseNum.addActionListener(e -> searchCar());
        btnSaveModification.addActionListener(e -> modifyCarInfo());
        btnClose.addActionListener(e -> dispose());
    }


    private void searchCar() {
        String licenseNum = textFieldLicenseNum.getText();
        if (licenseNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a license number");
            return;
        }

        //need if car not found thing, message dialog
        Car car = controller.findCarByLicenseNum(licenseNum);
        if (car == null) {
            JOptionPane.showMessageDialog(this, "Car not found");
            return;
        }

        textFieldMake.setText(car.getMake());
        textFieldModel.setText(car.getModel());
        textFieldPrice.setText(Double.toString(car.getPricePerDay()));
        textFieldType.setText(car.getType());
        textFieldAvailability.setText(car.getAvailability());

    }

    private void modifyCarInfo() {
        String licenseNum = textFieldLicenseNum.getText();
        String make = textFieldMake.getText();
        String model = textFieldModel.getText();
        String price = textFieldPrice.getText();
        String type = textFieldType.getText();
        String availability = textFieldAvailability.getText();

        //invalid not all filled out, doesn't do anything
        if (make.isEmpty() || model.isEmpty() || price.isEmpty() ||
            type.isEmpty() || availability.isEmpty()) {
            return;
        }



        Car car = new Car( make, model, type,
        Double.parseDouble(price),  availability, licenseNum);

        //
        boolean success = controller.updateCar(car);
        if (success) {
            System.out.println("modified");
        } else {
            System.out.println("not modified");

        }
    }
}
