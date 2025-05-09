package Adminstuff;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminGUI extends JFrame {
    private JPanel contentPane;
    private JButton addNewCarButton;
    private JButton modifyCarButton;
    private JButton deleteCarButton;
    private JButton viewRequestsButton;
    private JButton backButton;

    public AdminGUI() {
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 450, 300);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        addNewCarButton = new JButton("Add New Car");
        modifyCarButton = new JButton("Modify Car");
        deleteCarButton = new JButton("Delete Car");
        viewRequestsButton = new JButton("View Requests");

        buttonPanel.add(addNewCarButton);
        buttonPanel.add(modifyCarButton);
        buttonPanel.add(deleteCarButton);
        buttonPanel.add(viewRequestsButton);

        JLabel titleLabel = new JLabel("Admin Control Panel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        backButton = new JButton("Back to Home");
        JPanel southPanel = new JPanel();
        southPanel.add(backButton);

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        /* example from class
        btnCmInch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convert_cm_inch();
			}

        */



        addNewCarButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
				showAddNewCar();
			}
        });

        modifyCarButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
				showModifyCar();
			}
        });

        deleteCarButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
				showDeleteCar();
			}
        });

        viewRequestsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
				showViewRequests();
			}
        });


        backButton.addActionListener(e -> {
            gui.HomeGUI homeGUI = new gui.HomeGUI();
            homeGUI.setVisible(true);
            this.dispose();
        });
    }
    public void showAddNewCar(){
        AddNewCarGUI addNewCarGUI = new AddNewCarGUI();
        addNewCarGUI.setVisible(true);
    }

    public void showModifyCar(){
        ModifyCarGUI modifyCarGUI = new ModifyCarGUI();
            modifyCarGUI.setVisible(true);
    }

    public void showDeleteCar(){
        DeleteCarGUI deleteCarGUI = new DeleteCarGUI();
            deleteCarGUI.setVisible(true);
    }

    public void showViewRequests(){
        ViewRequestsGUI viewRequestsGUI = new ViewRequestsGUI();
            viewRequestsGUI.setVisible(true);
    }

}