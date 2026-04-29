import dao.UserDAO;
import dao.ParkingDAO;
import dao.BookingDAO;
import models.User;
import models.ParkingLocation;
import models.Booking;
import database.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Backend Demo Class
 * Demonstrates backend functionality without GUI
 * Shows database operations for Smart Parking System
 */
public class BackendDemo {

    public static void main(String[] args) {
        System.out.println("=== Smart Parking System - Backend Demo ===\n");

        try {
            // Test database connection
            System.out.println("1. Testing Database Connection...");
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("✓ Database connection successful!\n");
                conn.close();
            } else {
                System.out.println("✗ Database connection failed!\n");
                return;
            }

            // Initialize DAOs
            UserDAO userDAO = new UserDAO();
            ParkingDAO parkingDAO = new ParkingDAO();
            BookingDAO bookingDAO = new BookingDAO();

            // Demo User Operations
            System.out.println("2. User Operations Demo:");
            
            // Try login with existing user
            User retrievedUser = userDAO.loginUser("john@example.com", "john123");
            if (retrievedUser != null) {
                System.out.println("   - Login successful: " + retrievedUser.getName() + " (" + retrievedUser.getEmail() + ")");
            } else {
                System.out.println("   - Login failed: user not found");
            }

            // Demo Parking Location Operations
            System.out.println("\n3. Parking Location Operations Demo:");
            List<ParkingLocation> locations = parkingDAO.getAllParkingLocations();
            System.out.println("   - Total parking locations: " + locations.size());
            if (!locations.isEmpty()) {
                ParkingLocation firstLocation = locations.get(0);
                System.out.println("   - First location: " + firstLocation.getParkingName() + " at " + firstLocation.getLocation());
            }

            // Demo Available Parkings
            System.out.println("\n4. Available Parkings Demo:");
            List<ParkingLocation> availableParkings = parkingDAO.getAvailableParkings();
            System.out.println("   - Available parking locations: " + availableParkings.size());
            if (!availableParkings.isEmpty()) {
                ParkingLocation available = availableParkings.get(0);
                System.out.println("   - Available slots in " + available.getParkingName() + ": " + available.getAvailableSlots());
            }

            // Demo Search by City
            System.out.println("\n5. Search Parking by City Demo:");
            List<ParkingLocation> cityParkings = parkingDAO.searchParkingByCity("Downtown");
            System.out.println("   - Parkings in 'Downtown': " + cityParkings.size());

            // Demo Booking Operations
            System.out.println("\n6. Booking Operations Demo:");
            if (retrievedUser != null && !locations.isEmpty()) {
                List<Booking> userBookings = bookingDAO.getUserBookings(retrievedUser.getUserId());
                System.out.println("   - User's bookings count: " + userBookings.size());
                if (!userBookings.isEmpty()) {
                    Booking lastBooking = userBookings.get(userBookings.size() - 1);
                    System.out.println("   - Last booking status: " + lastBooking.getStatus());
                    System.out.println("   - Last booking price: $" + lastBooking.getTotalPrice());
                }
            }

            // Demo Admin Operations
            System.out.println("\n7. Admin Operations Demo:");
            List<User> allUsers = userDAO.getAllUsers();
            System.out.println("   - Total users in system: " + allUsers.size());

            List<Booking> allBookings = bookingDAO.getAllBookings();
            System.out.println("   - Total bookings in system: " + allBookings.size());

            // Calculate total revenue
            double totalRevenue = 0.0;
            for (Booking booking : allBookings) {
                if ("Completed".equals(booking.getStatus())) {
                    totalRevenue += booking.getTotalPrice();
                }
            }
            System.out.println("   - Total completed booking revenue: $" + totalRevenue);

            System.out.println("\n=== Backend Demo Completed Successfully! ===");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}