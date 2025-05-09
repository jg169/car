import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// run from here for this part
public class CarManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeGUI homeGUI = new HomeGUI();
            homeGUI.setVisible(true);
        });
    }
}



class HomeGUI extends JFrame {
    //attributes,
    private JPanel contentPane;
    private JButton adminButton;
    private JButton homeButton;

    //constructor
    public HomeGUI() {
        setTitle("Car Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel buttonPanel = new JPanel();
        adminButton = new JButton("Admin");
        homeButton = new JButton("Home");

        buttonPanel.add(adminButton);
        buttonPanel.add(homeButton);

        JLabel welcomeLabel = new JLabel("Welcome to Car Rental Management System", JLabel.CENTER);

        contentPane.add(welcomeLabel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);


        adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAdminContactGUI();
			}
		});

        homeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Went back to home screen");
        });
    }

    public void showAdminContactGUI()
    {
        AdminGUI adminGUI = new AdminGUI();
        adminGUI.setVisible(true);
        this.dispose();
    }
}


class AdminGUI extends JFrame {
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
            HomeGUI homeGUI = new HomeGUI();
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

//car class version
class Car {
    private String make;
    private String model;
    private String price;
    private String type;
    private String availability;
    private String licenseNum;
    private String contactName;
    private String name;
    private String phone;

    public Car(String make, String model, String price, String type, String availability, String licenseNum) {
        this.make = make;
        this.model = model;
        this.price = price;
        this.type = type;
        this.availability = availability;
        this.licenseNum = licenseNum;
    }

    // Getters and setters
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public String getLicenseNum() { return licenseNum; }
    public void setLicenseNum(String licenseNum) { this.licenseNum = licenseNum; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}


//model booking request, need to integrate later with other.
class BookingRequest {
    private String customerName;
    private String carLicenseNum;
    private String startDate;
    private String endDate;
    private boolean pending;

    public BookingRequest(String customerName, String carLicenseNum, String startDate, String endDate) {
        this.customerName = customerName;
        this.carLicenseNum = carLicenseNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pending = true;
    }

    public String getCustomerName() { return customerName; }
    public String getCarLicenseNum() { return carLicenseNum; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public boolean isPending() { return pending; }
    public void accept() { this.pending = false; }
    public void reject() { this.pending = false; }

    @Override
    public String toString() {
        return customerName + " - " + carLicenseNum + " - " + startDate + " to " + endDate;
    }
}

// CarDataFileController, is Singleton pattern
class CarDataFileController {
    private static CarDataFileController instance;
    private ArrayList<Car> cars;
    private ArrayList<BookingRequest> bookingRequests;

    // Private constructor for singleton
    private CarDataFileController() {
        cars = new ArrayList<>();
        bookingRequests = new ArrayList<>();

        // manual sample data, DELETE LATER
        cars.add(new Car("Toyota", "Camry", "25000", "Sedan", "Available", "ABC123"));
        cars.add(new Car("Honda", "Civic", "22000", "Sedan", "Available", "XYZ789"));
        cars.add(new Car("Ford", "Mustang", "35000", "Sports", "Available", "DEF456"));

        bookingRequests.add(new BookingRequest("John Doe", "ABC123", "05/10/2025", "05/15/2025"));
        bookingRequests.add(new BookingRequest("Jane Smith", "XYZ789", "05/12/2025", "05/14/2025"));
        bookingRequests.add(new BookingRequest("Bob Johnson", "DEF456", "05/20/2025", "05/25/2025"));
    }

    // get singleton instance
    public static CarDataFileController getInstance() {
        if (instance == null) {
            instance = new CarDataFileController();
        }
        return instance;
    }

    // Method to get all cars
    public ArrayList<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    //implementation of get cars by filter (from other side)
    /*
    public List<Car> getCarsByFilter(Map<String, Object> filter) {
        List<Car> filteredCars = new ArrayList<>();
        for (Car car : cars) {
            boolean match = true;
            // make filter
            if (filter.containsKey("make") && !car.getMake().toLowerCase()
                    .contains(((String) filter.get("make")).toLowerCase())) {
                match = false;
            }
            // type filter
            if (filter.containsKey("type") && !car.getType().equals(filter.get("type"))) {
                match = false;
            }
            // price range filter
            if (filter.containsKey("maxPrice") && car.getPricePerDay() > (double) filter.get("maxPrice")) {
                match = false;
            }
            // availability filter
            if (filter.containsKey("availability") &&
                    !car.getAvailability().equals(filter.get("availability"))) {
                match = false;
            }
            if (match) {
                filteredCars.add(car);
            }
        }
        return filteredCars;
    }

    */


    public boolean addCar(Car car) {
        for (Car c : cars) {
            if (c.getLicenseNum().equals(car.getLicenseNum())) {
                return false; // Car with this license already exists
            }
        }
        cars.add(car);
        return true;
    }


    public Car findCarByLicenseNum(String licenseNum) {
        for (Car car : cars) {
            if (car.getLicenseNum().equals(licenseNum)) {
                return car;
            }
        }
        return null;
    }


    public boolean updateCar(Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getLicenseNum().equals(updatedCar.getLicenseNum())) {
                cars.set(i, updatedCar);
                return true;
            }
        }
        return false;
    }


    public boolean deleteCar(String licenseNum) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getLicenseNum().equals(licenseNum)) {
                cars.remove(i);
                return true;
            }
        }
        return false;
    }


    public ArrayList<BookingRequest> getPendingBookingRequests() {
        ArrayList<BookingRequest> pendingRequests = new ArrayList<>();
        for (BookingRequest request : bookingRequests) {
            if (request.isPending()) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }


    public boolean acceptBookingRequest(int index) {
        ArrayList<BookingRequest> pendingRequests = getPendingBookingRequests();
        if (index >= 0 && index < pendingRequests.size()) {
            pendingRequests.get(index).accept();
            return true;
        }
        return false;
    }


    public boolean rejectBookingRequest(int index) {
        ArrayList<BookingRequest> pendingRequests = getPendingBookingRequests();
        if (index >= 0 && index < pendingRequests.size()) {
            pendingRequests.get(index).reject();
            return true;
        }
        return false;
    }
}


class AddNewCarGUI extends JFrame {
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

        Car car = new Car(make, model, price, type, availability, licenseNum);

        boolean success = controller.addCar(car);

        if(success){
            System.out.println("car added");
        }
    }
}

class ModifyCarGUI extends JFrame {
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
    private CarDataFileController controller;

    public ModifyCarGUI() {
        setTitle("Modify Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        controller = CarDataFileController.getInstance();

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
        textFieldPrice.setText(car.getPrice());
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

        Car car = new Car(make, model, price, type, availability, licenseNum);

        //
        boolean success = controller.updateCar(car);
        if (success) {
            System.out.println("modified");
        } else {
            System.out.println("not modified");

        }
    }
}

class DeleteCarGUI extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldLicenseNum;
    private JButton btnDeleteCar;
    private JButton btnCancel;
    private CarDataFileController controller;

    public DeleteCarGUI() {
        setTitle("Delete Car");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        controller = CarDataFileController.getInstance();

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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnDeleteCar.addActionListener(e -> deleteCarByLicenseNum());
        btnCancel.addActionListener(e -> dispose());
    }

    private void deleteCarByLicenseNum() {
        String licenseNum = textFieldLicenseNum.getText();
        if (licenseNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a license number");
            return;
        }

        boolean success = controller.deleteCar(licenseNum);
        if (success) {
            JOptionPane.showMessageDialog(this, "Car deleted successfully");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Car not found or could not be deleted");
        }
    }
}

// ViewRequestsGUI class
class ViewRequestsGUI extends JFrame {
    private JPanel contentPane;
    private JList<BookingRequest> bookingsList;
    private DefaultListModel<BookingRequest> listModel;
    private JButton btnAcceptRequestBooking;
    private JButton btnRejectRequestBooking;
    private JButton btnCancel;
    private CarDataFileController controller;

    public ViewRequestsGUI() {
        setTitle("View Booking Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        controller = CarDataFileController.getInstance();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        listModel = new DefaultListModel<>();
        ArrayList<BookingRequest> pendingRequests = controller.getPendingBookingRequests();
        for (BookingRequest request : pendingRequests) {
            listModel.addElement(request);
        }

        bookingsList = new JList<>(listModel);
        bookingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                BookingRequest request = (BookingRequest) value;
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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

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
            JOptionPane.showMessageDialog(this, "Please select a booking request");
            return;
        }

        boolean success = controller.acceptBookingRequest(selectedIndex);
        if (success) {
            JOptionPane.showMessageDialog(this, "Booking accepted");
            refreshBookingsList();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to accept booking");
        }
    }

    private void rejectRequestBooking() {
        int selectedIndex = bookingsList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking request");
            return;
        }

        boolean success = controller.rejectBookingRequest(selectedIndex);
        if (success) {
            JOptionPane.showMessageDialog(this, "Booking rejected");
            refreshBookingsList();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to reject booking");
        }
    }

    private void refreshBookingsList() {
        listModel.clear();
        ArrayList<BookingRequest> pendingRequests = controller.getPendingBookingRequests();
        for (BookingRequest request : pendingRequests) {
            listModel.addElement(request);
        }
    }
}