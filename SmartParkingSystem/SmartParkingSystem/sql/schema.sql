-- Smart Parking System - Database Schema


CREATE DATABASE IF NOT EXISTS smart_parking;
USE smart_parking;

-- Users Table
-- Stores information about all users (regular users and admins)

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user',  -- 'user' or 'admin'
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT email_unique UNIQUE (email)
);

-- Parking Locations Table
-- Stores information about parking locations/areas

CREATE TABLE IF NOT EXISTS parking_locations (
    parking_id INT PRIMARY KEY AUTO_INCREMENT,
    parking_name VARCHAR(100) NOT NULL,
    location VARCHAR(150) NOT NULL,
    address VARCHAR(200) NOT NULL,
    total_slots INT NOT NULL,
    available_slots INT NOT NULL,
    price_per_hour DOUBLE NOT NULL,
    city VARCHAR(50) NOT NULL,
    zip_code VARCHAR(10),
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- Bookings Table
-- Stores information about parking bookings

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    parking_id INT NOT NULL,
    parking_name VARCHAR(100) NOT NULL,
    vehicle_number VARCHAR(20) NOT NULL,
    vehicle_type VARCHAR(30) NOT NULL,  -- Car, Bike, Truck, Bus, Others
    booking_date DATE NOT NULL,
    booking_time TIME NOT NULL,
    check_out_time TIME,
    status VARCHAR(20) NOT NULL DEFAULT 'Active',  -- Active, Completed, Cancelled
    duration INT NOT NULL,  -- Duration in hours
    total_price DOUBLE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (parking_id) REFERENCES parking_locations(parking_id) ON DELETE CASCADE
);

-- Insert Initial Test Data

-- Insert admin user
INSERT INTO users (name, email, password, phone, role, registration_date) 
VALUES 
('Admin User', 'admin@parking.com', 'admin123', '9999999999', 'admin', NOW()),
('John Doe', 'john@example.com', 'john123', '8888888888', 'user', NOW()),
('Jane Smith', 'jane@example.com', 'jane123', '7777777777', 'user', NOW());

-- Insert parking locations
INSERT INTO parking_locations (parking_name, location, address, total_slots, available_slots, price_per_hour, city, zip_code, created_date)
VALUES
('Central Parking', 'Main Street', '123 Main Street, Downtown', 50, 45, 50.00, 'Delhi', '110001', NOW()),
('Mall Parking', 'Shopping Mall', 'City Center Mall, 3rd Floor', 100, 85, 30.00, 'Delhi', '110002', NOW()),
('Airport Parking', 'Near Airport', 'Airport Road', 200, 150, 100.00, 'Delhi', '110003', NOW()),
('Hospital Parking', 'Medical Area', '456 Hospital Road', 30, 25, 20.00, 'Delhi', '110004', NOW()),
('Railway Station Parking', 'Railway Station', 'Station Plaza', 75, 60, 40.00, 'Delhi', '110005', NOW());


-- INDEXES for Performance Optimization

CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_booking_user_id ON bookings(user_id);
CREATE INDEX idx_booking_parking_id ON bookings(parking_id);
CREATE INDEX idx_booking_date ON bookings(booking_date);
CREATE INDEX idx_parking_city ON parking_locations(city);

-- Sample Queries for Testing

-- Get all users:
-- SELECT * FROM users;

-- Get all parking locations:
-- SELECT * FROM parking_locations;

-- Get all bookings:
-- SELECT * FROM bookings;

-- Get available parkings:
-- SELECT * FROM parking_locations WHERE available_slots > 0;

-- Get user bookings:
-- SELECT * FROM bookings WHERE user_id = 2;

-- Get active bookings:
-- SELECT * FROM bookings WHERE status = 'Active';

