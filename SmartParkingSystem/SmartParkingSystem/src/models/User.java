package models;

/**
 * User Model Class
 * Represents a user in the Smart Parking System
 * Contains user details and role information
 */
public class User {
    
    private int userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;        // "user" or "admin"
    private String registrationDate;
    
    /**
     * Constructor for User object
     */
    public User(int userId, String name, String email, String password, 
                String phone, String role, String registrationDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.registrationDate = registrationDate;
    }
    
    /**
     * Constructor for User object without userId (for new users)
     */
    public User(String name, String email, String password, String phone, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }
    
    // ===== Getters =====
    
    public int getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getRegistrationDate() {
        return registrationDate;
    }
    
    // ===== Setters =====
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    /**
     * Returns a string representation of User object
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}
