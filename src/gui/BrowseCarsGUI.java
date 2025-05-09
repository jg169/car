package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.CarDataController;
import model.Car;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BrowseCarsGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JTable carListTable;
    private JButton bookCarBtn;
    private JButton backBtn;
    private DefaultTableModel tableModel;
    private List<Car> carList;

    public BrowseCarsGUI() {
        setTitle("Browse Cars");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel();
        centerPane.setLayout(new BorderLayout(10, 10));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Available Cars", JLabel.CENTER);
        centerPane.add(titleLabel, BorderLayout.NORTH);
        // Create table model with columns
        String[] columns = {"License Plate", "Make", "Model", "Type", "Price per Day", "Availability"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        carListTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carListTable);
        carListTable.setFillsViewportHeight(true);
        carListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bookCarBtn = new JButton("Book Selected Car");
        backBtn = new JButton("Back");
        bookCarBtn.addActionListener(this);
        backBtn.addActionListener(this);
        buttonPanel.add(bookCarBtn);
        buttonPanel.add(backBtn);
        centerPane.add(scrollPane, BorderLayout.CENTER);
        centerPane.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPane);
        loadAllCars();
    }

    public void loadAllCars() {
        // Clear existing data
        tableModel.setRowCount(0);
        carList = CarDataController.getInstance().getAllCars();
        for (Car car : carList) {
            Object[] row = {
                car.getLicensePlate(),
                car.getMake(),
                car.getModel(),
                car.getType(),
                car.getPricePerDay(),
                car.getAvailability()
            };
            tableModel.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookCarBtn) {
            int selectedRow = carListTable.getSelectedRow();
            if (selectedRow != -1) {
                String licensePlate = (String) tableModel.getValueAt(selectedRow, 0);
                BookCarGUI bookCarGUI = new BookCarGUI();
                bookCarGUI.displayGUI(licensePlate);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a car first");
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