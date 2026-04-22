package ui;

import dao.ParkingDAO;
import dao.BookingDAO;
import dao.UserDAO;
import models.ParkingLocation;
import models.Booking;
import models.User;
import utils.ValidationUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

/**
 * Admin Panel Class
 * This is the interface for admins to manage the parking system
 * Admins can add parking locations, update slots, and view all bookings
 */
public class AdminPanel extends JFrame {
    
    private User currentUser;
    private ParkingDAO parkingDAO;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    private JTabbedPane tabbedPane;
    private JTable parkingTable;
    private JTable bookingTable;
    private JTable userTable;
    
    /**
     * Constructor - Initialize admin panel
     */
    public AdminPanel(User currentUser) {
        this.currentUser = currentUser;
        this.parkingDAO = new ParkingDAO();
        this.bookingDAO = new BookingDAO();
        this.userDAO = new UserDAO();
        
        setTitle("Smart Parking System - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add parking locations management tab
        JPanel parkingPanel = createParkingManagementPanel();
        tabbedPane.addTab("Manage Parking Locations", parkingPanel);
        
        // Add all bookings tab
        JPanel bookingPanel = createAllBookingsPanel();
        tabbedPane.addTab("View All Bookings", bookingPanel);
        
        // Add user management tab
        JPanel userPanel = createUserManagementPanel();
        tabbedPane.addTab("Manage Users", userPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create footer panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Create header panel
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(1100, 70));
        
        JLabel welcomeLabel = new JLabel("Admin Panel - Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutButton.addActionListener(e -> handleLogout());
        
        panel.add(welcomeLabel, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Create parking management panel
     */
    private JPanel createParkingManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        // Create table for parking locations
        parkingTable = new JTable();
        parkingTable.setFont(new Font("Arial", Font.PLAIN, 12));
        parkingTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(parkingTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshParkingTable());
        
        JButton addButton = new JButton("ADD NEW PARKING");
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> openAddParkingDialog());
        
        JButton updateSlotsButton = new JButton("UPDATE SLOTS");
        updateSlotsButton.setBackground(new Color(100, 100, 100));
        updateSlotsButton.setForeground(Color.WHITE);
        updateSlotsButton.setFocusPainted(false);
        updateSlotsButton.addActionListener(e -> openUpdateSlotsDialog());
        
        JButton editButton = new JButton("EDIT DETAILS");
        editButton.setBackground(new Color(200, 150, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.addActionListener(e -> openEditParkingDialog());
        
        JButton searchButton = new JButton("SEARCH BY CITY");
        searchButton.setBackground(new Color(70, 100, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> openSearchByCity());
        
        JButton deleteButton = new JButton("DELETE PARKING");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> handleDeleteParking());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateSlotsButton);
        buttonPanel.add(editButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load initial data
        refreshParkingTable();
        
        return panel;
    }
    
    /**
     * Create all bookings panel
     */
    private JPanel createAllBookingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        // Create table for bookings
        bookingTable = new JTable();
        bookingTable.setFont(new Font("Arial", Font.PLAIN, 11));
        bookingTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshBookingTable());
        
        JButton searchButton = new JButton("FILTER BY PARKING");
        searchButton.setBackground(new Color(100, 180, 100));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> openFilterByParkingDialog());
        
        JButton viewDetailsButton = new JButton("VIEW DETAILS");
        viewDetailsButton.setBackground(new Color(100, 100, 100));
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.addActionListener(e -> viewBookingDetails());
        
        JButton completeButton = new JButton("MARK COMPLETED");
        completeButton.setBackground(new Color(34, 139, 34));
        completeButton.setForeground(Color.WHITE);
        completeButton.setFocusPainted(false);
        completeButton.addActionListener(e -> handleCompleteBooking());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(completeButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load initial data
        refreshBookingTable();
        
        return panel;
    }
    
    /**
     * Refresh parking locations table
     */
    private void refreshParkingTable() {
        List<ParkingLocation> parkings = parkingDAO.getAllParkingLocations();
        
        String[] columns = {"ID", "Parking Name", "Location", "Address", "Total Slots", "Available Slots", "Price/Hour", "City"};
        Object[][] data = new Object[parkings.size()][8];
        
        for (int i = 0; i < parkings.size(); i++) {
            ParkingLocation p = parkings.get(i);
            data[i][0] = p.getParkingId();
            data[i][1] = p.getParkingName();
            data[i][2] = p.getLocation();
            data[i][3] = p.getAddress();
            data[i][4] = p.getTotalSlots();
            data[i][5] = p.getAvailableSlots();
            data[i][6] = "₹ " + p.getPricePerHour();
            data[i][7] = p.getCity();
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        parkingTable.setModel(model);
    }
    
    /**
     * Refresh bookings table
     */
    private void refreshBookingTable() {
        List<Booking> bookings = bookingDAO.getAllBookings();
        
        String[] columns = {"Booking ID", "User", "Parking", "Vehicle", "Date", "Time", "Duration", "Price", "Status"};
        Object[][] data = new Object[bookings.size()][9];
        
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            data[i][0] = b.getBookingId();
            data[i][1] = b.getUserName();
            data[i][2] = b.getParkingName();
            data[i][3] = b.getVehicleNumber();
            data[i][4] = b.getBookingDate();
            data[i][5] = b.getBookingTime();
            data[i][6] = b.getDuration() + " hrs";
            data[i][7] = "₹ " + b.getTotalPrice();
            data[i][8] = b.getStatus();
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bookingTable.setModel(model);
    }
    
    /**
     * Open dialog to add new parking location
     */
    private void openAddParkingDialog() {
        JDialog dialog = new JDialog(this, "Add New Parking Location", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Input fields
        JTextField parkingNameField = new JTextField(20);
        JTextField locationField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField totalSlotsField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField cityField = new JTextField(20);
        JTextField zipField = new JTextField(20);
        
        int row = 0;
        
        // Add labels and fields
        addLabelAndField(panel, "Parking Name:", parkingNameField, gbc, row++);
        addLabelAndField(panel, "Location:", locationField, gbc, row++);
        addLabelAndField(panel, "Address:", addressField, gbc, row++);
        addLabelAndField(panel, "Total Slots:", totalSlotsField, gbc, row++);
        addLabelAndField(panel, "Price/Hour:", priceField, gbc, row++);
        addLabelAndField(panel, "City:", cityField, gbc, row++);
        addLabelAndField(panel, "Zip Code:", zipField, gbc, row++);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("ADD");
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            if (addNewParking(parkingNameField, locationField, addressField, totalSlotsField, priceField, cityField, zipField)) {
                dialog.dispose();
            }
        });
        
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    /**
     * Add label and field to dialog
     */
    private void addLabelAndField(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int row) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(jLabel, gbc);
        
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(field, gbc);
    }
    
    /**
     * Add new parking location
     */
    private boolean addNewParking(JTextField parkingNameField, JTextField locationField, JTextField addressField,
                                   JTextField totalSlotsField, JTextField priceField, JTextField cityField, JTextField zipField) {
        try {
            String parkingName = parkingNameField.getText().trim();
            String location = locationField.getText().trim();
            String address = addressField.getText().trim();
            int totalSlots = Integer.parseInt(totalSlotsField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            String city = cityField.getText().trim();
            String zip = zipField.getText().trim();
            
            // Validation
            if (!ValidationUtils.isNotEmpty(parkingName)) {
                JOptionPane.showMessageDialog(this, "Parking name cannot be empty!");
                return false;
            }
            if (!ValidationUtils.isPositiveNumber(totalSlots)) {
                JOptionPane.showMessageDialog(this, "Total slots must be positive!");
                return false;
            }
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be positive!");
                return false;
            }
            
            ParkingLocation parking = new ParkingLocation(parkingName, location, address, totalSlots, price, city, zip);
            
            if (parkingDAO.addParkingLocation(parking)) {
                JOptionPane.showMessageDialog(this, "Parking location added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshParkingTable();
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add parking location!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for slots and price!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Open dialog to update available slots
     */
    private void openUpdateSlotsDialog() {
        int selectedRow = parkingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a parking location!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int parkingId = (int) parkingTable.getValueAt(selectedRow, 0);
        String parkingName = (String) parkingTable.getValueAt(selectedRow, 1);
        int currentSlots = (int) parkingTable.getValueAt(selectedRow, 5);
        
        String newSlots = JOptionPane.showInputDialog(this, 
                "Current available slots: " + currentSlots + "\n\nEnter new available slots:",
                currentSlots);
        
        if (newSlots != null) {
            try {
                int slots = Integer.parseInt(newSlots);
                ParkingLocation parking = parkingDAO.getParkingLocationById(parkingId);
                if (parking != null) {
                    parking.setAvailableSlots(slots);
                    if (parkingDAO.updateParkingLocation(parking)) {
                        JOptionPane.showMessageDialog(this, "Slots updated successfully!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshParkingTable();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Handle delete parking location
     */
    private void handleDeleteParking() {
        int selectedRow = parkingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a parking location to delete!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int parkingId = (int) parkingTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this parking location?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (parkingDAO.deleteParkingLocation(parkingId)) {
                JOptionPane.showMessageDialog(this, "Parking location deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshParkingTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete parking location!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * View booking details
     */
    private void viewBookingDetails() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to view details!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int bookingId = (int) bookingTable.getValueAt(selectedRow, 0);
        Booking booking = bookingDAO.getBookingById(bookingId);
        
        if (booking != null) {
            String details = "Booking ID: " + booking.getBookingId() + "\n" +
                           "User: " + booking.getUserName() + "\n" +
                           "Parking: " + booking.getParkingName() + "\n" +
                           "Vehicle: " + booking.getVehicleNumber() + " (" + booking.getVehicleType() + ")\n" +
                           "Date: " + booking.getBookingDate() + "\n" +
                           "Time: " + booking.getBookingTime() + "\n" +
                           "Duration: " + booking.getDuration() + " hours\n" +
                           "Total Price: ₹ " + booking.getTotalPrice() + "\n" +
                           "Status: " + booking.getStatus();
            
            JOptionPane.showMessageDialog(this, details, "Booking Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Handle complete booking
     */
    private void handleCompleteBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to mark as completed!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int bookingId = (int) bookingTable.getValueAt(selectedRow, 0);
        String status = (String) bookingTable.getValueAt(selectedRow, 8);
        
        if (!status.equals("Active")) {
            JOptionPane.showMessageDialog(this, "Only active bookings can be marked as completed!",
                    "Invalid Status", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        String checkOutTime = stf.format(new Date());
        
        if (bookingDAO.updateBookingStatus(bookingId, "Completed", checkOutTime)) {
            JOptionPane.showMessageDialog(this, "Booking marked as completed!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshBookingTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update booking!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Create footer panel
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(1100, 40));
        
        JLabel footerLabel = new JLabel("© 2024 Smart Parking System. All rights reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panel.add(footerLabel);
        
        return panel;
    }
    
    /**
     * Handle logout
     */
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?",
                "Confirm Logout", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        }
    }
    
    /**
     * Open dialog to edit parking location details
     */
    private void openEditParkingDialog() {
        int selectedRow = parkingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a parking location to edit!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int parkingId = (int) parkingTable.getValueAt(selectedRow, 0);
        ParkingLocation parking = parkingDAO.getParkingLocationById(parkingId);
        
        if (parking != null) {
            JDialog editDialog = new JDialog(this, "Edit Parking Location", true);
            editDialog.setSize(500, 400);
            editDialog.setLocationRelativeTo(this);
            
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            int row = 0;
            
            // Parking Name
            JLabel nameLabel = new JLabel("Parking Name:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(nameLabel, gbc);
            JTextField nameField = new JTextField(parking.getParkingName(), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(nameField, gbc);
            row++;
            
            // Address
            JLabel addressLabel = new JLabel("Address:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(addressLabel, gbc);
            JTextField addressField = new JTextField(parking.getAddress(), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(addressField, gbc);
            row++;
            
            // Total Slots
            JLabel totalSlotsLabel = new JLabel("Total Slots:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(totalSlotsLabel, gbc);
            JTextField totalSlotsField = new JTextField(String.valueOf(parking.getTotalSlots()), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(totalSlotsField, gbc);
            row++;
            
            // Price Per Hour
            JLabel priceLabel = new JLabel("Price Per Hour:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(priceLabel, gbc);
            JTextField priceField = new JTextField(String.valueOf(parking.getPricePerHour()), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(priceField, gbc);
            row++;
            
            // City
            JLabel cityLabel = new JLabel("City:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(cityLabel, gbc);
            JTextField cityField = new JTextField(parking.getCity(), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(cityField, gbc);
            row++;
            
            // Zip Code
            JLabel zipLabel = new JLabel("Zip Code:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(zipLabel, gbc);
            JTextField zipField = new JTextField(parking.getZipCode(), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(zipField, gbc);
            row++;
            
            // Buttons
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("SAVE");
            saveButton.setBackground(new Color(34, 139, 34));
            saveButton.setForeground(Color.WHITE);
            saveButton.addActionListener(e -> {
                try {
                    parking.setParkingName(nameField.getText().trim());
                    parking.setAddress(addressField.getText().trim());
                    parking.setTotalSlots(Integer.parseInt(totalSlotsField.getText().trim()));
                    parking.setPricePerHour(Double.parseDouble(priceField.getText().trim()));
                    parking.setCity(cityField.getText().trim());
                    parking.setZipCode(zipField.getText().trim());
                    
                    if (parkingDAO.updateParkingLocation(parking)) {
                        JOptionPane.showMessageDialog(editDialog, "Parking updated successfully!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshParkingTable();
                        editDialog.dispose();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editDialog, "Please enter valid values!",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            JButton cancelButton = new JButton("CANCEL");
            cancelButton.setBackground(Color.RED);
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(e -> editDialog.dispose());
            
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            
            gbc.gridx = 0; gbc.gridy = row;
            gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);
            
            JScrollPane scrollPane = new JScrollPane(panel);
            editDialog.add(scrollPane);
            editDialog.setVisible(true);
        }
    }
    
    /**
     * Search parking locations by city
     */
    private void openSearchByCity() {
        String city = JOptionPane.showInputDialog(this, "Enter city name to search:", "");
        
        if (city != null && !city.trim().isEmpty()) {
            List<ParkingLocation> results = parkingDAO.searchParkingByCity(city);
            
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No parking locations found in " + city,
                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String[] columns = {"ID", "Parking Name", "Location", "Address", "Total Slots", "Available Slots", "Price/Hour", "City"};
            Object[][] data = new Object[results.size()][8];
            
            for (int i = 0; i < results.size(); i++) {
                ParkingLocation p = results.get(i);
                data[i][0] = p.getParkingId();
                data[i][1] = p.getParkingName();
                data[i][2] = p.getLocation();
                data[i][3] = p.getAddress();
                data[i][4] = p.getTotalSlots();
                data[i][5] = p.getAvailableSlots();
                data[i][6] = "₹ " + p.getPricePerHour();
                data[i][7] = p.getCity();
            }
            
            DefaultTableModel model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            parkingTable.setModel(model);
            JOptionPane.showMessageDialog(this, "Found " + results.size() + " parking location(s) in " + city,
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Filter bookings by parking location
     */
    private void openFilterByParkingDialog() {
        List<ParkingLocation> parkings = parkingDAO.getAllParkingLocations();
        
        if (parkings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No parking locations available!",
                    "No Data", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] parkingNames = new String[parkings.size()];
        for (int i = 0; i < parkings.size(); i++) {
            parkingNames[i] = parkings.get(i).getParkingName() + " (ID: " + parkings.get(i).getParkingId() + ")";
        }
        
        String selected = (String) JOptionPane.showInputDialog(this,
                "Select parking location to filter bookings:",
                "Filter Bookings by Parking",
                JOptionPane.PLAIN_MESSAGE,
                null,
                parkingNames,
                parkingNames[0]);
        
        if (selected != null) {
            int parkingId = parkings.get(java.util.Arrays.asList(parkingNames).indexOf(selected)).getParkingId();
            List<Booking> filteredBookings = bookingDAO.getActiveBookingsByParking(parkingId);
            
            String[] columns = {"Booking ID", "User", "Parking", "Vehicle", "Date", "Time", "Duration", "Price", "Status"};
            Object[][] data = new Object[filteredBookings.size()][9];
            
            for (int i = 0; i < filteredBookings.size(); i++) {
                Booking b = filteredBookings.get(i);
                data[i][0] = b.getBookingId();
                data[i][1] = b.getUserName();
                data[i][2] = b.getParkingName();
                data[i][3] = b.getVehicleNumber() + " (" + b.getVehicleType() + ")";
                data[i][4] = b.getBookingDate();
                data[i][5] = b.getBookingTime();
                data[i][6] = b.getDuration() + " hrs";
                data[i][7] = "₹ " + b.getTotalPrice();
                data[i][8] = b.getStatus();
            }
            
            DefaultTableModel model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            bookingTable.setModel(model);
            JOptionPane.showMessageDialog(this, "Found " + filteredBookings.size() + " active booking(s) at this parking location",
                    "Bookings Filtered", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Create user management panel
     */
    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        // Create table for users
        userTable = new JTable();
        userTable.setFont(new Font("Arial", Font.PLAIN, 12));
        userTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshUserTable());
        
        JButton viewDetailsButton = new JButton("VIEW DETAILS");
        viewDetailsButton.setBackground(new Color(100, 100, 100));
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.addActionListener(e -> viewUserDetails());
        
        JButton editButton = new JButton("EDIT USER");
        editButton.setBackground(new Color(200, 150, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.addActionListener(e -> openEditUserDialog());
        
        JButton deleteButton = new JButton("DELETE USER");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> handleDeleteUser());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load initial data
        refreshUserTable();
        
        return panel;
    }
    
    /**
     * Refresh users table
     */
    private void refreshUserTable() {
        List<User> users = userDAO.getAllUsers();
        
        String[] columns = {"User ID", "Name", "Email", "Phone", "Role", "Registration Date"};
        Object[][] data = new Object[users.size()][6];
        
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            data[i][0] = u.getUserId();
            data[i][1] = u.getName();
            data[i][2] = u.getEmail();
            data[i][3] = u.getPhone();
            data[i][4] = u.getRole();
            data[i][5] = u.getRegistrationDate();
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        userTable.setModel(model);
    }
    
    /**
     * View user details
     */
    private void viewUserDetails() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to view details!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) userTable.getValueAt(selectedRow, 0);
        User user = userDAO.getUserById(userId);
        
        if (user != null) {
            String details = "User ID: " + user.getUserId() + "\n" +
                           "Name: " + user.getName() + "\n" +
                           "Email: " + user.getEmail() + "\n" +
                           "Phone: " + user.getPhone() + "\n" +
                           "Role: " + user.getRole() + "\n" +
                           "Registration Date: " + user.getRegistrationDate();
            
            JOptionPane.showMessageDialog(this, details, "User Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Open dialog to edit user details
     */
    private void openEditUserDialog() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) userTable.getValueAt(selectedRow, 0);
        User user = userDAO.getUserById(userId);
        
        if (user != null) {
            JDialog editDialog = new JDialog(this, "Edit User", true);
            editDialog.setSize(450, 350);
            editDialog.setLocationRelativeTo(this);
            
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            int row = 0;
            
            // Name
            JLabel nameLabel = new JLabel("Name:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(nameLabel, gbc);
            JTextField nameField = new JTextField(user.getName(), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(nameField, gbc);
            row++;
            
            // Email
            JLabel emailLabel = new JLabel("Email:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(emailLabel, gbc);
            JTextField emailField = new JTextField(user.getEmail(), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(emailField, gbc);
            row++;
            
            // Phone
            JLabel phoneLabel = new JLabel("Phone:");
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(phoneLabel, gbc);
            JTextField phoneField = new JTextField(user.getPhone(), 20);
            gbc.gridx = 1; gbc.gridy = row;
            panel.add(phoneField, gbc);
            row++;
            
            // Buttons
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("SAVE");
            saveButton.setBackground(new Color(34, 139, 34));
            saveButton.setForeground(Color.WHITE);
            saveButton.addActionListener(e -> {
                user.setName(nameField.getText().trim());
                user.setEmail(emailField.getText().trim());
                user.setPhone(phoneField.getText().trim());
                
                if (userDAO.updateUser(user)) {
                    JOptionPane.showMessageDialog(editDialog, "User updated successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshUserTable();
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Failed to update user!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            JButton cancelButton = new JButton("CANCEL");
            cancelButton.setBackground(Color.RED);
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(e -> editDialog.dispose());
            
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            
            gbc.gridx = 0; gbc.gridy = row;
            gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);
            
            JScrollPane scrollPane = new JScrollPane(panel);
            editDialog.add(scrollPane);
            editDialog.setVisible(true);
        }
    }
    
    /**
     * Handle delete user
     */
    private void handleDeleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) userTable.getValueAt(selectedRow, 0);
        String userName = (String) userTable.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete user: " + userName + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (userDAO.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshUserTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
