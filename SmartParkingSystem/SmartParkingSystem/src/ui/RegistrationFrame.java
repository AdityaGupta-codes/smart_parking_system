package ui;

import dao.UserDAO;
import models.User;
import utils.ValidationUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Registration Frame Class
 * Handles user registration functionality
 */
public class RegistrationFrame extends JFrame {
    
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JPasswordField confirmPasswordTextField;
    private JTextField phoneTextField;
    private JLabel messageLabel;
    private UserDAO userDAO;
    private JFrame parentFrame;
    
    /**
     * Constructor - Initialize the registration frame
     */
    public RegistrationFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.userDAO = new UserDAO();
        
        setTitle("Smart Parking System - Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create form panel with scroll
        JPanel formPanel = createFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Create header panel
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(550, 60));
        
        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel);
        return panel;
    }
    
    /**
     * Create form panel with input fields
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Message label
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);
        
        int row = 1;
        
        // Name field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);
        
        nameTextField = new JTextField(20);
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(nameTextField, gbc);
        row++;
        
        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(emailLabel, gbc);
        
        emailTextField = new JTextField(20);
        emailTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(emailTextField, gbc);
        row++;
        
        // Phone field
        JLabel phoneLabel = new JLabel("Phone (10 digits):");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(phoneLabel, gbc);
        
        phoneTextField = new JTextField(20);
        phoneTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(phoneTextField, gbc);
        row++;
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(passwordLabel, gbc);
        
        passwordTextField = new JPasswordField(20);
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(passwordTextField, gbc);
        row++;
        
        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(confirmPasswordLabel, gbc);
        
        confirmPasswordTextField = new JPasswordField(20);
        confirmPasswordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(confirmPasswordTextField, gbc);
        
        return panel;
    }
    
    /**
     * Create button panel with register and cancel buttons
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(new Color(240, 240, 240));
        
        // Register button
        JButton registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(120, 35));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> handleRegistration());
        
        // Cancel button
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());
        
        panel.add(registerButton);
        panel.add(cancelButton);
        
        return panel;
    }
    
    /**
     * Handle registration button click
     */
    private void handleRegistration() {
        String name = nameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        String password = new String(passwordTextField.getPassword());
        String confirmPassword = new String(confirmPasswordTextField.getPassword());
        
        // Validate name
        if (!ValidationUtils.isNotEmpty(name)) {
            messageLabel.setText("Name cannot be empty!");
            return;
        }
        
        if (!ValidationUtils.isValidName(name)) {
            messageLabel.setText("Name should contain only letters and spaces!");
            return;
        }
        
        // Validate email
        if (!ValidationUtils.isNotEmpty(email)) {
            messageLabel.setText("Email cannot be empty!");
            return;
        }
        
        if (!ValidationUtils.isValidEmail(email)) {
            messageLabel.setText("Please enter a valid email address!");
            return;
        }
        
        // Check if email already exists
        if (userDAO.isEmailExists(email)) {
            messageLabel.setText("Email already exists! Please use a different email.");
            return;
        }
        
        // Validate phone
        if (!ValidationUtils.isNotEmpty(phone)) {
            messageLabel.setText("Phone cannot be empty!");
            return;
        }
        
        if (!ValidationUtils.isValidPhone(phone)) {
            messageLabel.setText("Phone number must be 10 digits!");
            return;
        }
        
        // Validate password
        if (!ValidationUtils.isNotEmpty(password)) {
            messageLabel.setText("Password cannot be empty!");
            return;
        }
        
        if (!ValidationUtils.isValidPassword(password)) {
            messageLabel.setText("Password must be at least 6 characters long!");
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match!");
            return;
        }
        
        // Create new user and register
        User newUser = new User(name, email, password, phone, "user");
        
        if (userDAO.registerUser(newUser)) {
            messageLabel.setForeground(new Color(34, 139, 34));
            messageLabel.setText("Registration successful! Please login with your credentials.");
            
            // Clear fields
            nameTextField.setText("");
            emailTextField.setText("");
            phoneTextField.setText("");
            passwordTextField.setText("");
            confirmPasswordTextField.setText("");
            
            // Close after 2 seconds
            Timer timer = new Timer(2000, e -> dispose());
            timer.setRepeats(false);
            timer.start();
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Registration failed! Please try again.");
        }
    }
}
