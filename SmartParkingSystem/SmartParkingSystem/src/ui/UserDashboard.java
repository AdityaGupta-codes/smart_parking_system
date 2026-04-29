package ui;

import dao.ParkingDAO;
import dao.BookingDAO;
import dao.UserDAO;
import models.ParkingLocation;
import models.Booking;
import models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User Dashboard Class
 * This is the main interface for users after login
 * Users can view parking locations, make bookings, and view history
 */
public class UserDashboard extends JFrame {
    
    private User currentUser;
    private ParkingDAO parkingDAO;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    private JTabbedPane tabbedPane;
    private JTable parkingTable;
    private JTable bookingHistoryTable;
    
    /**
     * Constructor - Initialize user dashboard
     */
    public UserDashboard(User currentUser) {
        this.currentUser = currentUser;
        this.parkingDAO = new ParkingDAO();
        this.bookingDAO = new BookingDAO();
        this.userDAO = new UserDAO();
        
        setTitle("Smart Parking System - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create tabbed pane for different views
        tabbedPane = new JTabbedPane();
        
        // Add parking locations tab
        JPanel parkingPanel = createParkingLocationsPanel();
        tabbedPane.addTab("Available Parkings", parkingPanel);
        
        // Add booking history tab
        JPanel bookingHistoryPanel = createBookingHistoryPanel();
        tabbedPane.addTab("My Bookings", bookingHistoryPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create footer panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Create header panel with user information
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(1000, 70));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
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
        
        JButton profileButton = new JButton("EDIT PROFILE");
        profileButton.setFont(new Font("Arial", Font.BOLD, 12));
        profileButton.setBackground(new Color(70, 130, 180));
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        profileButton.addActionListener(e -> openEditProfileDialog());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(70, 130, 180));
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);
        
        panel.add(welcomeLabel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Create parking locations panel
     */
    private JPanel createParkingLocationsPanel() {
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
        
        JButton bookButton = new JButton("BOOK PARKING");
        bookButton.setBackground(new Color(34, 139, 34));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.addActionListener(e -> handleBooking());
        
        JButton searchButton = new JButton("SEARCH BY CITY");
        searchButton.setBackground(new Color(100, 100, 100));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchByCity());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(searchButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load initial data
        refreshParkingTable();
        
        return panel;
    }
    
    /**
     * Create booking history panel
     */
    private JPanel createBookingHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        // Create table for booking history
        bookingHistoryTable = new JTable();
        bookingHistoryTable.setFont(new Font("Arial", Font.PLAIN, 12));
        bookingHistoryTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(bookingHistoryTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> refreshBookingHistoryTable());
        
        JButton cancelButton = new JButton("CANCEL BOOKING");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> handleCancelBooking());
        
        JButton deleteButton = new JButton("DELETE BOOKING");
        deleteButton.setBackground(new Color(139, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> handleDeleteBooking());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load initial data
        refreshBookingHistoryTable();
        
        return panel;
    }
    
    /**
     * Refresh parking locations table
     */
    private void refreshParkingTable() {
        List<ParkingLocation> parkings = parkingDAO.getAvailableParkings();
        
        String[] columns = {"ID", "Parking Name", "Location", "Total Slots", "Available Slots", "Price/Hour"};
        Object[][] data = new Object[parkings.size()][6];
        
        for (int i = 0; i < parkings.size(); i++) {
            ParkingLocation p = parkings.get(i);
            data[i][0] = p.getParkingId();
            data[i][1] = p.getParkingName();
            data[i][2] = p.getLocation();
            data[i][3] = p.getTotalSlots();
            data[i][4] = p.getAvailableSlots();
            data[i][5] = "₹ " + p.getPricePerHour();
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
     * Refresh booking history table
     */
    private void refreshBookingHistoryTable() {
        List<Booking> bookings = bookingDAO.getUserBookings(currentUser.getUserId());
        
        String[] columns = {"Booking ID", "Parking", "Vehicle", "Date", "Time", "Duration", "Price", "Status"};
        Object[][] data = new Object[bookings.size()][8];
        
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            data[i][0] = b.getBookingId();
            data[i][1] = b.getParkingName();
            data[i][2] = b.getVehicleNumber();
            data[i][3] = b.getBookingDate();
            data[i][4] = b.getBookingTime();
            data[i][5] = b.getDuration() + " hrs";
            data[i][6] = "₹ " + b.getTotalPrice();
            data[i][7] = b.getStatus();
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bookingHistoryTable.setModel(model);
    }
    
    /**
     * Handle booking button click
     */
    private void handleBooking() {
        int selectedRow = parkingTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a parking location to book!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int parkingId = (int) parkingTable.getValueAt(selectedRow, 0);
        ParkingLocation parking = parkingDAO.getParkingLocationById(parkingId);
        
        if (parking != null) {
            BookingFrame bookingFrame = new BookingFrame(this, currentUser, parking, bookingDAO, parkingDAO);
            bookingFrame.setVisible(true);
        }
    }
    
    /**
     * Handle cancel booking button click
     */
    private void handleCancelBooking() {
        int selectedRow = bookingHistoryTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int bookingId = (int) bookingHistoryTable.getValueAt(selectedRow, 0);
        String status = (String) bookingHistoryTable.getValueAt(selectedRow, 7);
        
        if (!status.equals("Active")) {
            JOptionPane.showMessageDialog(this, "Only active bookings can be cancelled!",
                    "Invalid Status", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?",
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookingDAO.cancelBooking(bookingId)) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshBookingHistoryTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Search parking by city
     */
    private void searchByCity() {
        String city = JOptionPane.showInputDialog(this, "Enter city name to search:");
        
        if (city != null && !city.trim().isEmpty()) {
            List<ParkingLocation> parkings = parkingDAO.searchParkingByCity(city);
            
            if (parkings.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No parking found in the city: " + city,
                        "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String[] columns = {"ID", "Parking Name", "Location", "Total Slots", "Available Slots", "Price/Hour"};
                Object[][] data = new Object[parkings.size()][6];
                
                for (int i = 0; i < parkings.size(); i++) {
                    ParkingLocation p = parkings.get(i);
                    data[i][0] = p.getParkingId();
                    data[i][1] = p.getParkingName();
                    data[i][2] = p.getLocation();
                    data[i][3] = p.getTotalSlots();
                    data[i][4] = p.getAvailableSlots();
                    data[i][5] = "₹ " + p.getPricePerHour();
                }
                
                DefaultTableModel model = new DefaultTableModel(data, columns) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                
                parkingTable.setModel(model);
            }
        }
    }
    
    /**
     * Create footer panel
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(1000, 40));
        
        JLabel footerLabel = new JLabel("© 2024 Smart Parking System. All rights reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panel.add(footerLabel);
        
        return panel;
    }
    
    /**
     * Handle logout button click
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
     * Open edit profile dialog
     */
    private void openEditProfileDialog() {
        JDialog editDialog = new JDialog(this, "Edit Profile", true);
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
        JTextField nameField = new JTextField(currentUser.getName(), 20);
        gbc.gridx = 1; gbc.gridy = row;
        panel.add(nameField, gbc);
        row++;
        
        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(emailLabel, gbc);
        JTextField emailField = new JTextField(currentUser.getEmail(), 20);
        gbc.gridx = 1; gbc.gridy = row;
        panel.add(emailField, gbc);
        row++;
        
        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(phoneLabel, gbc);
        JTextField phoneField = new JTextField(currentUser.getPhone(), 20);
        gbc.gridx = 1; gbc.gridy = row;
        panel.add(phoneField, gbc);
        row++;
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("SAVE");
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            currentUser.setName(nameField.getText().trim());
            currentUser.setEmail(emailField.getText().trim());
            currentUser.setPhone(phoneField.getText().trim());
            
            if (userDAO.updateUser(currentUser)) {
                JOptionPane.showMessageDialog(editDialog, "Profile updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                editDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(editDialog, "Failed to update profile!",
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
    
    /**
     * Handle delete booking
     */
    private void handleDeleteBooking() {
        int selectedRow = bookingHistoryTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to delete!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int bookingId = (int) bookingHistoryTable.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this booking permanently?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookingDAO.deleteBooking(bookingId)) {
                JOptionPane.showMessageDialog(this, "Booking deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshBookingHistoryTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete booking!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
