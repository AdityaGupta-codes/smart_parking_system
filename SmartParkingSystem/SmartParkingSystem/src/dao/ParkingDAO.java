package dao;

import database.DBConnection;
import models.ParkingLocation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parking Location Data Access Object (DAO) Class
 * Handles all database operations related to parking locations
 */
public class ParkingDAO {
    
    /**
     * Add a new parking location to database
     * @param parking ParkingLocation object with parking details
     * @return true if parking location is added successfully
     */
    public boolean addParkingLocation(ParkingLocation parking) {
        String query = "INSERT INTO parking_locations " +
                       "(parking_name, location, address, total_slots, available_slots, " +
                       "price_per_hour, city, zip_code, created_date) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, parking.getParkingName());
            ps.setString(2, parking.getLocation());
            ps.setString(3, parking.getAddress());
            ps.setInt(4, parking.getTotalSlots());
            ps.setInt(5, parking.getAvailableSlots());
            ps.setDouble(6, parking.getPricePerHour());
            ps.setString(7, parking.getCity());
            ps.setString(8, parking.getZipCode());
            
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding parking location: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all parking locations from database
     * @return List of all ParkingLocation objects
     */
    public List<ParkingLocation> getAllParkingLocations() {
        List<ParkingLocation> parkingLocations = new ArrayList<>();
        String query = "SELECT * FROM parking_locations";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ParkingLocation parking = new ParkingLocation(
                    rs.getInt("parking_id"),
                    rs.getString("parking_name"),
                    rs.getString("location"),
                    rs.getString("address"),
                    rs.getInt("total_slots"),
                    rs.getInt("available_slots"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("city"),
                    rs.getString("zip_code"),
                    rs.getString("created_date")
                );
                parkingLocations.add(parking);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving parking locations: " + e.getMessage());
            e.printStackTrace();
        }
        return parkingLocations;
    }
    
    /**
     * Get parking location details by ID
     * @param parkingId Parking location ID
     * @return ParkingLocation object if found, null otherwise
     */
    public ParkingLocation getParkingLocationById(int parkingId) {
        String query = "SELECT * FROM parking_locations WHERE parking_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, parkingId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new ParkingLocation(
                    rs.getInt("parking_id"),
                    rs.getString("parking_name"),
                    rs.getString("location"),
                    rs.getString("address"),
                    rs.getInt("total_slots"),
                    rs.getInt("available_slots"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("city"),
                    rs.getString("zip_code"),
                    rs.getString("created_date")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving parking location: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Search parking locations by city
     * @param city City name to search
     * @return List of ParkingLocation objects in the city
     */
    public List<ParkingLocation> searchParkingByCity(String city) {
        List<ParkingLocation> parkingLocations = new ArrayList<>();
        String query = "SELECT * FROM parking_locations WHERE city LIKE ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, "%" + city + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ParkingLocation parking = new ParkingLocation(
                    rs.getInt("parking_id"),
                    rs.getString("parking_name"),
                    rs.getString("location"),
                    rs.getString("address"),
                    rs.getInt("total_slots"),
                    rs.getInt("available_slots"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("city"),
                    rs.getString("zip_code"),
                    rs.getString("created_date")
                );
                parkingLocations.add(parking);
            }
            
        } catch (SQLException e) {
            System.out.println("Error searching parking locations: " + e.getMessage());
            e.printStackTrace();
        }
        return parkingLocations;
    }
    
    /**
     * Get parking locations with available slots
     * @return List of ParkingLocation objects with available slots
     */
    public List<ParkingLocation> getAvailableParkings() {
        List<ParkingLocation> parkingLocations = new ArrayList<>();
        String query = "SELECT * FROM parking_locations WHERE available_slots > 0";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ParkingLocation parking = new ParkingLocation(
                    rs.getInt("parking_id"),
                    rs.getString("parking_name"),
                    rs.getString("location"),
                    rs.getString("address"),
                    rs.getInt("total_slots"),
                    rs.getInt("available_slots"),
                    rs.getDouble("price_per_hour"),
                    rs.getString("city"),
                    rs.getString("zip_code"),
                    rs.getString("created_date")
                );
                parkingLocations.add(parking);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving available parkings: " + e.getMessage());
            e.printStackTrace();
        }
        return parkingLocations;
    }
    
    /**
     * Update available slots when a booking is made
     * @param parkingId Parking location ID
     * @param slotsToReduce Number of slots to reduce
     * @return true if update is successful
     */
    public boolean updateAvailableSlots(int parkingId, int slotsToReduce) {
        String query = "UPDATE parking_locations SET available_slots = available_slots - ? " +
                       "WHERE parking_id = ? AND available_slots >= ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, slotsToReduce);
            ps.setInt(2, parkingId);
            ps.setInt(3, slotsToReduce);
            
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating available slots: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update parking location details
     * @param parking ParkingLocation object with updated details
     * @return true if update is successful
     */
    public boolean updateParkingLocation(ParkingLocation parking) {
        String query = "UPDATE parking_locations SET parking_name = ?, location = ?, " +
                       "address = ?, total_slots = ?, available_slots = ?, " +
                       "price_per_hour = ?, city = ?, zip_code = ? " +
                       "WHERE parking_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, parking.getParkingName());
            ps.setString(2, parking.getLocation());
            ps.setString(3, parking.getAddress());
            ps.setInt(4, parking.getTotalSlots());
            ps.setInt(5, parking.getAvailableSlots());
            ps.setDouble(6, parking.getPricePerHour());
            ps.setString(7, parking.getCity());
            ps.setString(8, parking.getZipCode());
            ps.setInt(9, parking.getParkingId());
            
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating parking location: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete parking location by ID
     * @param parkingId Parking location ID to delete
     * @return true if delete is successful
     */
    public boolean deleteParkingLocation(int parkingId) {
        String query = "DELETE FROM parking_locations WHERE parking_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, parkingId);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting parking location: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
