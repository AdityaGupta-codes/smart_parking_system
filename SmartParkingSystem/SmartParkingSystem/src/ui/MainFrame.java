package ui;

import javax.swing.*;
import models.User;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Frame Class
 * This is the entry point of the application
 * Shows initial options to login or register
 */
public class MainFrame extends JFrame {
    
    private JPanel mainPanel;
    private User currentUser;
    
    /**
     * Constructor - Initialize the main frame
     */
    public MainFrame() {
        setTitle("Smart Parking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Create footer panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Create header panel with title
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(600, 80));
        
        JLabel titleLabel = new JLabel("Smart Parking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subLabel = new JLabel("Book Your Parking Slot Easily");
        subLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subLabel.setForeground(Color.WHITE);
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleLabel);
        panel.add(subLabel);
        
        return panel;
    }
    
    /**
     * Create button panel with login and registration buttons
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        panel.setBackground(new Color(240, 240, 240));
        
        // Login Button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> openLoginFrame());
        
        // Registration Button
        JButton registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setBackground(new Color(60, 120, 160));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> openRegistrationFrame());
        
        panel.add(loginButton);
        panel.add(registerButton);
        
        return panel;
    }
    
    /**
     * Create footer panel
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(600, 40));
        
        JLabel footerLabel = new JLabel("© 2024 Smart Parking System. All rights reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panel.add(footerLabel);
        
        return panel;
    }
    
    /**
     * Open login frame
     */
    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }
    
    /**
     * Open registration frame
     */
    private void openRegistrationFrame() {
        RegistrationFrame registrationFrame = new RegistrationFrame(this);
        registrationFrame.setVisible(true);
    }
    
    /**
     * Set current user and navigate to dashboard
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
