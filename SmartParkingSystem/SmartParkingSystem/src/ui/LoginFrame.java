package ui;

import dao.UserDAO;
import models.User;
import utils.ValidationUtils;
import javax.swing.*;
import java.awt.*;

/**
 * Login Frame Class
 * Handles user login functionality
 */
public class LoginFrame extends JFrame {
    
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JLabel messageLabel;
    private UserDAO userDAO;
    private JFrame parentFrame;
    
    /**
     * Constructor - Initialize the login frame
     */
    public LoginFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.userDAO = new UserDAO();
        
        setTitle("Smart Parking System - Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
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
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(500, 60));
        
        JLabel titleLabel = new JLabel("User Login");
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
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Message label
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);
        
        // Email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(emailLabel, gbc);
        
        emailTextField = new JTextField(20);
        emailTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(emailTextField, gbc);
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        
        passwordTextField = new JPasswordField(20);
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordTextField, gbc);
        
        return panel;
    }
    
    /**
     * Create button panel with login and cancel buttons
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(new Color(240, 240, 240));
        
        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());
        
        // Cancel button
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());
        
        panel.add(loginButton);
        panel.add(cancelButton);
        
        return panel;
    }
    
    /**
     * Handle login button click
     */
    private void handleLogin() {
        String email = emailTextField.getText().trim();
        String password = new String(passwordTextField.getPassword());
        
        // Validate inputs
        if (!ValidationUtils.isNotEmpty(email)) {
            messageLabel.setText("Email cannot be empty!");
            return;
        }
        
        if (!ValidationUtils.isValidEmail(email)) {
            messageLabel.setText("Please enter a valid email address!");
            return;
        }
        
        if (!ValidationUtils.isNotEmpty(password)) {
            messageLabel.setText("Password cannot be empty!");
            return;
        }
        
        // Check credentials in database
        User user = userDAO.loginUser(email, password);
        
        if (user != null) {
            messageLabel.setForeground(new Color(34, 139, 34));
            messageLabel.setText("Login successful! Opening dashboard...");
            
            // Wait a moment then open appropriate dashboard based on role
            Timer timer = new Timer(1000, e -> {
                if ("admin".equalsIgnoreCase(user.getRole())) {
                    openAdminPanel(user);
                } else {
                    openUserDashboard(user);
                }
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Invalid email or password!");
        }
    }
    
    /**
     * Open user dashboard after successful login
     */
    private void openUserDashboard(User user) {
        UserDashboard userDashboard = new UserDashboard(user);
        userDashboard.setVisible(true);
    }
    
    /**
     * Open admin panel after successful login
     */
    private void openAdminPanel(User user) {
        AdminPanel adminPanel = new AdminPanel(user);
        adminPanel.setVisible(true);
    }
}
