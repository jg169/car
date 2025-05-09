package Adminstuff;

import javax.swing.*;
import java.awt.*;

public class DeleteCarGUI extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldLicenseNum;
    private JButton btnDeleteCar;
    private JButton btnCancel;
    private AdminDataController controller;

    public DeleteCarGUI() {
        setTitle("Delete Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 450, 300);

        setLocationRelativeTo(null);

        controller = AdminDataController.getInstance();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel licenseLabel = new JLabel("Enter License Number:");
        textFieldLicenseNum = new JTextField();

        formPanel.add(licenseLabel, BorderLayout.NORTH);
        formPanel.add(textFieldLicenseNum, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnDeleteCar = new JButton("Delete Car");
        btnCancel = new JButton("Cancel");

        buttonPanel.add(btnDeleteCar);
        buttonPanel.add(btnCancel);

        JLabel titleLabel = new JLabel("Delete Car", JLabel.CENTER);

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnDeleteCar.addActionListener(e -> deleteCarByLicenseNum());
        btnCancel.addActionListener(e -> dispose());
    }

    private void deleteCarByLicenseNum() {
        String licenseNum = textFieldLicenseNum.getText();

        boolean success = controller.deleteCar(licenseNum);
        if (success) {
            System.out.println("deleted successfully");
        } else {
            System.out.println("not deleted successfully");
        }
    }
}
