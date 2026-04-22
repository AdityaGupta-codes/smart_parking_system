package ui;

import dao.BookingDAO;
import dao.ParkingDAO;
import models.Booking;
import models.ParkingLocation;
import models.User;
import utils.ValidationUtils;
import javax.swing.*;
import javax.swing.SpinnerNumberModel;
import java.awt.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Booking Frame Class
 * Handles parking slot booking
 */
public class BookingFrame extends JFrame {

    private User user;
    private ParkingLocation parking;
    private BookingDAO bookingDAO;
    private ParkingDAO parkingDAO;
    private JComboBox<String> vehicleTypeCombo;
    private JTextField vehicleNumberTextField;
    private JSpinner durationSpinner;
    private JLabel priceLabel;
    private double pricePerHour;

    /**
     * Constructor - Initialize booking frame
     */
    public BookingFrame(JFrame parentFrame, User user, ParkingLocation parking,
            BookingDAO bookingDAO, ParkingDAO parkingDAO) {
        this.user = user;
        this.parking = parking;
        this.bookingDAO = bookingDAO;
        this.parkingDAO = parkingDAO;
        this.pricePerHour = parking.getPricePerHour();

        setTitle("Book Parking - " + parking.getParkingName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Create header panel
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(70, 130, 180);
                Color color2 = new Color(100, 149, 237);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setPreferredSize(new Dimension(600, 80));
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel("Booking Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel parkingLabel = new JLabel(
                "Parking: " + parking.getParkingName() + " | Available Slots: " + parking.getAvailableSlots());
        parkingLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        parkingLabel.setForeground(Color.WHITE);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleLabel);
        panel.add(parkingLabel);

        return panel;
    }

    /**
     * Create form panel with input fields
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(240, 248, 255);
                Color color2 = new Color(255, 255, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)));
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // User name (read-only)
        JLabel userLabel = new JLabel("User Name:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(userLabel, gbc);

        JTextField userTextField = new JTextField(user.getName());
        userTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        userTextField.setEditable(false);
        userTextField.setToolTipText("Your registered name");
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(userTextField, gbc);
        row++;

        // Vehicle Type
        JLabel vehicleTypeLabel = new JLabel("Vehicle Type:");
        vehicleTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(vehicleTypeLabel, gbc);

        String[] vehicleTypes = { "Car", "Bike", "Truck", "Bus", "Others" };
        vehicleTypeCombo = new JComboBox<>(vehicleTypes);
        vehicleTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        vehicleTypeCombo.addActionListener(e -> updatePrice());
        vehicleTypeCombo.setToolTipText("Select your vehicle type to calculate price");
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(vehicleTypeCombo, gbc);
        row++;

        // Vehicle Number
        JLabel vehicleNumberLabel = new JLabel("Vehicle Number:");
        vehicleNumberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(vehicleNumberLabel, gbc);

        vehicleNumberTextField = new JTextField(15);
        vehicleNumberTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        vehicleNumberTextField.setToolTipText("Format: DL-01-AB-1234");
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(vehicleNumberTextField, gbc);
        row++;

        // Duration
        JLabel durationLabel = new JLabel("Duration (Hours):");
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(durationLabel, gbc);

        SpinnerModel spinnerModel = new javax.swing.SpinnerNumberModel(1, 1, 24, 1);
        durationSpinner = new JSpinner(spinnerModel);
        durationSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        durationSpinner.addChangeListener(e -> updatePrice());
        ((JSpinner.DefaultEditor) durationSpinner.getEditor()).getTextField().setToolTipText("Number of hours to park");
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(durationSpinner, gbc);
        row++;

        // Price
        JLabel priceTextLabel = new JLabel("Total Price:");
        priceTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(priceTextLabel, gbc);

        priceLabel = new JLabel("₹ " + pricePerHour);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(priceLabel, gbc);
        row++;

        // Date and Time info
        JLabel dateTimeLabel = new JLabel("Booking Date & Time:");
        dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(dateTimeLabel, gbc);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateTime = sdf.format(new Date());
        JLabel dateTimeValueLabel = new JLabel(currentDateTime);
        dateTimeValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(dateTimeValueLabel, gbc);

        return panel;
    }

    private void updatePrice() {
        int duration = (int) durationSpinner.getValue();
        String vehicleType = (String) vehicleTypeCombo.getSelectedItem();
        double basePrice = getPriceForVehicleType(vehicleType);
        double totalPrice = basePrice * duration;
        priceLabel.setText("₹ " + String.format("%.2f", totalPrice));
    }

    /**
     * Get price per hour based on vehicle type
     */
    private double getPriceForVehicleType(String vehicleType) {
        switch (vehicleType) {
            case "Bike":
                return pricePerHour * 0.5; // 50% of base price
            case "Car":
                return pricePerHour; // Base price
            case "Truck":
                return pricePerHour * 2.0; // 200% of base price
            case "Bus":
                return pricePerHour * 1.5; // 150% of base price
            case "Others":
                return pricePerHour * 1.2; // 120% of base price
            default:
                return pricePerHour;
        }
    }

    /**
     * Create button panel with book and cancel buttons
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(new Color(240, 240, 240));

        // Book button
        JButton bookButton = new JButton("CONFIRM BOOKING") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(0, 100, 0));
                } else {
                    g2.setColor(getBackground());
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setBackground(new Color(34, 139, 34));
        bookButton.setForeground(Color.WHITE);
        bookButton.setPreferredSize(new Dimension(150, 35));
        bookButton.setFocusPainted(false);
        bookButton.setBorderPainted(false);
        bookButton.addActionListener(e -> handleBooking());

        // Cancel button
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        // Navigate button
        JButton navigateButton = new JButton("NAVIGATE") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(50, 100, 150));
                } else {
                    g2.setColor(getBackground());
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        navigateButton.setFont(new Font("Arial", Font.BOLD, 14));
        navigateButton.setBackground(new Color(70, 130, 180));
        navigateButton.setForeground(Color.WHITE);
        navigateButton.setPreferredSize(new Dimension(120, 35));
        navigateButton.setFocusPainted(false);
        navigateButton.setBorderPainted(false);
        navigateButton.addActionListener(e -> openGoogleMaps());

        panel.add(navigateButton);
        panel.add(bookButton);
        panel.add(cancelButton);

        return panel;
    }

    /**
     * Handle booking confirmation
     */
    private void handleBooking() {
        // Validate vehicle number
        String vehicleNumber = vehicleNumberTextField.getText().trim();
        if (!ValidationUtils.isNotEmpty(vehicleNumber)) {
            JOptionPane.showMessageDialog(this, "Vehicle number cannot be empty!",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!ValidationUtils.isValidVehicleNumber(vehicleNumber)) {
            JOptionPane.showMessageDialog(this, "Invalid vehicle number format!\nFormat: DL-01-AB-1234",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get other details
        String vehicleType = (String) vehicleTypeCombo.getSelectedItem();
        int duration = (int) durationSpinner.getValue();
        double totalPrice = getPriceForVehicleType(vehicleType) * duration;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        String bookingDate = sdf.format(new Date());
        String bookingTime = stf.format(new Date());

        // Create booking object
        Booking booking = new Booking(
                user.getUserId(),
                user.getName(),
                parking.getParkingId(),
                parking.getParkingName(),
                vehicleNumber.toUpperCase(),
                vehicleType,
                bookingDate,
                bookingTime,
                "Active",
                duration,
                totalPrice);

        // Check if parking has available slots
        if (parking.getAvailableSlots() <= 0) {
            JOptionPane.showMessageDialog(this, "No parking slots available!",
                    "Booking Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save booking
        if (bookingDAO.createBooking(booking)) {
            // Update available slots
            parkingDAO.updateAvailableSlots(parking.getParkingId(), 1);

            JOptionPane.showMessageDialog(this,
                    "Booking confirmed successfully!\nBooking ID will be sent to your email.\n" +
                            "Total Price: ₹ " + String.format("%.2f", totalPrice),
                    "Booking Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        }
    }

    /**
     * Open Google Maps for navigation to parking location
     */
    private void openGoogleMaps() {
        try {
            String address = parking.getAddress() + ", " + parking.getCity() + ", " + parking.getZipCode();
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String url = "https://www.google.com/maps/search/?api=1&query=" + encodedAddress;
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to open Google Maps: " + e.getMessage(),
                    "Navigation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
