-- Smart Parking System - Database Setup Script
-- Run this in MySQL before starting the application

CREATE DATABASE IF NOT EXISTS smart_parking;
USE smart_parking;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    role VARCHAR(20) DEFAULT 'user',
    registration_date DATETIME DEFAULT NOW()
);

-- Parking locations table
CREATE TABLE IF NOT EXISTS parking_locations (
    parking_id INT PRIMARY KEY AUTO_INCREMENT,
    parking_name VARCHAR(100) NOT NULL,
    location VARCHAR(200),
    address VARCHAR(200),
    total_slots INT NOT NULL,
    available_slots INT NOT NULL,
    price_per_hour DOUBLE NOT NULL,
    city VARCHAR(100),
    zip_code VARCHAR(20),
    created_date DATETIME DEFAULT NOW()
);

-- Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    user_name VARCHAR(100),
    parking_id INT NOT NULL,
    parking_name VARCHAR(100),
    vehicle_number VARCHAR(20),
    vehicle_type VARCHAR(50),
    booking_date VARCHAR(20),
    booking_time VARCHAR(20),
    check_out_time VARCHAR(20),
    status VARCHAR(20) DEFAULT 'Active',
    duration INT,
    total_price DOUBLE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (parking_id) REFERENCES parking_locations(parking_id) ON DELETE CASCADE
);

-- Default admin user (password: admin123)
INSERT IGNORE INTO users (name, email, password, phone, role)
VALUES ('Admin', 'admin@example.com', 'admin123', '9999999999', 'admin');

-- Sample regular user (password: john123)
INSERT IGNORE INTO users (name, email, password, phone, role)
VALUES ('John Doe', 'john@example.com', 'john123', '9876543210', 'user');

-- Sample parking locations
INSERT IGNORE INTO parking_locations (parking_name, location, address, total_slots, available_slots, price_per_hour, city, zip_code)
VALUES
('Downtown Parking', 'City Center', '123 Main St', 50, 50, 50.00, 'Downtown', '110001'),
('Mall Parking', 'Shopping District', '45 Market Road', 100, 100, 30.00, 'Downtown', '110002'),
('Airport Parking', 'Near Terminal 1', 'Airport Road', 200, 200, 80.00, 'Airport Zone', '110003');

SELECT 'Database setup complete!' AS Status;
