package utils;

import java.util.regex.Pattern;

/**
 * Validation Utility Class
 * Contains methods for validating user inputs
 */
public class ValidationUtils {
    
    /**
     * Validates email format
     * @param email Email to validate
     * @return true if email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Simple email regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }
    
    /**
     * Validates password strength
     * Password must be at least 6 characters long
     * @param password Password to validate
     * @return true if password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.length() >= 6;
    }
    
    /**
     * Validates phone number format (10 digits)
     * @param phone Phone number to validate
     * @return true if phone is valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        // 10 digit phone number pattern
        String phoneRegex = "^[0-9]{10}$";
        return Pattern.matches(phoneRegex, phone);
    }
    
    /**
     * Validates name (should not be empty and contain only letters and spaces)
     * @param name Name to validate
     * @return true if name is valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Name should contain only letters and spaces
        String nameRegex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(nameRegex, name.trim());
    }
    
    /**
     * Validates vehicle number format (e.g., DL-01-AB-1234)
     * @param vehicleNumber Vehicle number to validate
     * @return true if vehicle number is valid, false otherwise
     */
    public static boolean isValidVehicleNumber(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isEmpty()) {
            return false;
        }
        // Simple vehicle number pattern (alphanumeric with dashes)
        String vehicleRegex = "^[A-Z]{2}[-]?[0-9]{2}[-]?[A-Z]{2}[-]?[0-9]{4}$";
        return Pattern.matches(vehicleRegex, vehicleNumber.toUpperCase());
    }
    
    /**
     * Validates if a number is positive
     * @param number Number to validate
     * @return true if positive, false otherwise
     */
    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
    
    /**
     * Validates if a string is not empty
     * @param text Text to validate
     * @return true if not empty, false otherwise
     */
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
    
    /**
     * Returns validation error message based on validation type
     * @param fieldName Name of the field being validated
     * @param validationType Type of validation that failed
     * @return Error message string
     */
    public static String getValidationErrorMessage(String fieldName, String validationType) {
        switch (validationType) {
            case "email":
                return "Please enter a valid email address!";
            case "password":
                return "Password must be at least 6 characters long!";
            case "phone":
                return "Phone number must be 10 digits!";
            case "name":
                return "Name should contain only letters and spaces!";
            case "vehicleNumber":
                return "Invalid vehicle number format!";
            case "empty":
                return fieldName + " cannot be empty!";
            case "positive":
                return fieldName + " must be a positive number!";
            default:
                return "Invalid input!";
        }
    }
}
