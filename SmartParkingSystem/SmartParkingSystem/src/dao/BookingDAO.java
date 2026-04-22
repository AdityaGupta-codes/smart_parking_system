package dao;

import database.DBConnection;
import models.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Booking Data Access Object (DAO) Class
 * Handles all database operations related to bookings
 */
public class BookingDAO {

    /**
     * Create a new booking in database
     * 
     * @param booking Booking object with booking details
     * @return true if booking is created successfully
     */
    public boolean createBooking(Booking booking) {
        String query = "INSERT INTO bookings " +
                "(user_id, user_name, parking_id, parking_name, vehicle_number, " +
                "vehicle_type, booking_date, booking_time, status, duration, total_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, booking.getUserId());
            ps.setString(2, booking.getUserName());
            ps.setInt(3, booking.getParkingId());
            ps.setString(4, booking.getParkingName());
            ps.setString(5, booking.getVehicleNumber());
            ps.setString(6, booking.getVehicleType());
            ps.setDate(7, java.sql.Date.valueOf(booking.getBookingDate()));
            ps.setTime(8, java.sql.Time.valueOf(booking.getBookingTime()));
            ps.setString(9, booking.getStatus());
            ps.setInt(10, booking.getDuration());
            ps.setDouble(11, booking.getTotalPrice());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error creating booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all bookings from database
     * 
     * @return List of all Booking objects
     */
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings ORDER BY booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("parking_id"),
                        rs.getString("parking_name"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getString("booking_date"),
                        rs.getString("booking_time"),
                        rs.getString("check_out_time"),
                        rs.getString("status"),
                        rs.getInt("duration"),
                        rs.getDouble("total_price"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Get booking details by booking ID
     * 
     * @param bookingId Booking ID to search
     * @return Booking object if found, null otherwise
     */
    public Booking getBookingById(int bookingId) {
        String query = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("parking_id"),
                        rs.getString("parking_name"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getString("booking_date"),
                        rs.getString("booking_time"),
                        rs.getString("check_out_time"),
                        rs.getString("status"),
                        rs.getInt("duration"),
                        rs.getDouble("total_price"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving booking: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all bookings for a specific user
     * 
     * @param userId User ID to search bookings for
     * @return List of Booking objects for the user
     */
    public List<Booking> getUserBookings(int userId){
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE user_id = ? ORDER BY booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("parking_id"),
                        rs.getString("parking_name"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getString("booking_date"),
                        rs.getString("booking_time"),
                        rs.getString("check_out_time"),
                        rs.getString("status"),
                        rs.getInt("duration"),
                        rs.getDouble("total_price"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Get all active bookings for a specific parking location
     * 
     * @param parkingId Parking location ID
     * @return List of active Booking objects
     */
    public List<Booking> getActiveBookingsByParking(int parkingId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE parking_id = ? AND status = 'Active'" +
                " ORDER BY booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, parkingId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("parking_id"),
                        rs.getString("parking_name"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getString("booking_date"),
                        rs.getString("booking_time"),
                        rs.getString("check_out_time"),
                        rs.getString("status"),
                        rs.getInt("duration"),
                        rs.getDouble("total_price"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving active bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Update booking status and checkout time
     * 
     * @param bookingId    Booking ID to update
     * @param status       New status
     * @param checkOutTime Checkout time
     * @return true if update is successful
     */
    public boolean updateBookingStatus(int bookingId, String status, String checkOutTime) {
        String query = "UPDATE bookings SET status = ?, check_out_time = ? " +
                "WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setTime(2, java.sql.Time.valueOf(checkOutTime));
            ps.setInt(3, bookingId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating booking status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cancel a booking
     * 
     * @param bookingId Booking ID to cancel
     * @return true if cancellation is successful
     */
    public boolean cancelBooking(int bookingId) {
        String query = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookingId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete booking from database
     * 
     * @param bookingId Booking ID to delete
     * @return true if deletion is successful
     */
    public boolean deleteBooking(int bookingId) {
        String query = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookingId);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get count of active bookings for a parking location
     * 
     * @param parkingId Parking location ID
     * @return Number of active bookings
     */
    public int getActiveBookingCount(int parkingId) {
        String query = "SELECT COUNT(*) AS count FROM bookings WHERE parking_id = ? AND status = 'Active'";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, parkingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.out.println("Error getting booking count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
