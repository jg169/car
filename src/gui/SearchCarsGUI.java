package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.RenterDataController;
//import controller.CarDataController;
import model.Car;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCarsGUI extends JFrame implements ActionListener {
    private JPanel centerPane;
    private JTextField makeField;
    private JSlider priceRangeSlider;
    private JComboBox<String> typeComboBox;
    private JButton searchBtn;
    private JButton resetBtn;
    private JButton backBtn;
    private JButton bookCarBtn;
    private JButton viewDetailsBtn;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    public SearchCarsGUI() {
        setTitle("Search Cars");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        centerPane = new JPanel(new BorderLayout(10, 10));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Search Cars", JLabel.CENTER);
        centerPane.add(titleLabel, BorderLayout.NORTH);
        setupSearchPanel();
        setupResultsTable();
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        viewDetailsBtn = new JButton("View Details");
        bookCarBtn = new JButton("Book Selected Car");
        backBtn = new JButton("Back");
        viewDetailsBtn.addActionListener(this);
        bookCarBtn.addActionListener(this);
        backBtn.addActionListener(this);
        bottomPanel.add(viewDetailsBtn);
        bottomPanel.add(bookCarBtn);
        bottomPanel.add(backBtn);
        centerPane.add(bottomPanel, BorderLayout.SOUTH);
        add(centerPane);
    }

    private void setupSearchPanel() {
        JPanel searchPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Make field
        JLabel makeLabel = new JLabel("Make:");
        makeField = new JTextField(20);
        searchPanel.add(makeLabel);
        searchPanel.add(makeField);
        // Price slider
        JLabel priceLabel = new JLabel("Max Price per Day:");
        priceRangeSlider = new JSlider(JSlider.HORIZONTAL, 50, 500, 200);
        priceRangeSlider.setMajorTickSpacing(50);
        priceRangeSlider.setPaintTicks(true);
        priceRangeSlider.setPaintLabels(true);
        searchPanel.add(priceLabel);
        searchPanel.add(priceRangeSlider);
        // Car type dropdown menu
        JLabel typeLabel = new JLabel("Car Type:");
        String[] carTypes = {"Any", "Sedan", "SUV", "Hatchback", "Convertible", "Truck"};
        typeComboBox = new JComboBox<>(carTypes);
        searchPanel.add(typeLabel);
        searchPanel.add(typeComboBox);
        searchBtn = new JButton("Search");
        resetBtn = new JButton("Reset Filters");
        JPanel buttonSubPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        buttonSubPanel.add(searchBtn);
        buttonSubPanel.add(resetBtn);
        searchPanel.add(new JLabel(""));
        searchPanel.add(buttonSubPanel);
        JPanel searchPanelContainer = new JPanel(new BorderLayout());
        searchPanelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanelContainer.add(searchPanel, BorderLayout.NORTH);
        centerPane.add(searchPanelContainer, BorderLayout.NORTH);
    }

    private void setupResultsTable() {
        String[] columns = {"License Plate", "Make", "Model", "Type", "Price per Day", "Availability"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        resultsTable.setFillsViewportHeight(true);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPane.add(scrollPane, BorderLayout.CENTER);
    }

    public void searchCars(String make, int maxPrice, String type) {
        tableModel.setRowCount(0);
        Map<String, Object> filter = new HashMap<>();
        if (!make.isEmpty()) {
            filter.put("make", make);
        }
        filter.put("maxPrice", (double) maxPrice);
        if (!type.equals("Any")) {
            filter.put("type", type);
        }
        List<Car> filteredCars = RenterDataController.getInstance().getCarsByFilter(filter);
        for (Car car : filteredCars) {
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

    public void resetFilters() {
        makeField.setText("");
        priceRangeSlider.setValue(200);
        typeComboBox.setSelectedIndex(0);
        tableModel.setRowCount(0); // Clear the results table
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String make = makeField.getText().trim();
            int maxPrice = priceRangeSlider.getValue();
            String type = (String) typeComboBox.getSelectedItem();
            searchCars(make, maxPrice, type);
        } else if (e.getSource() == resetBtn) {
            resetFilters();
        } else if (e.getSource() == viewDetailsBtn) {
            int selectedRow = resultsTable.getSelectedRow();
            if (selectedRow != -1) {
                String licensePlate = (String) tableModel.getValueAt(selectedRow, 0);
                CarDetailsGUI detailsGUI = new CarDetailsGUI();
                detailsGUI.displayGUI(licensePlate);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a car first");
            }
        } else if (e.getSource() == bookCarBtn) {
            int selectedRow = resultsTable.getSelectedRow();
            if (selectedRow != -1) {
                String licensePlate = (String) tableModel.getValueAt(selectedRow, 0);
                BookCarGUI bookCarGUI = new BookCarGUI();
                bookCarGUI.displayGUI(licensePlate);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Select a car first");
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