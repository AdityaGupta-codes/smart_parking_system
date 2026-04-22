package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Class
 * This class manages the connection to MySQL database
 * Used by all DAO classes to interact with the database
 */
public class DBConnection {
    
    // Database connection parameters
    // Change these values according to your MySQL setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smart_parking?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Eddy_1513"; 
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Establishes and returns a connection to the database
     * @return Connection object to the database
     * @throws SQLException if connection fails
     * @throws ClassNotFoundException if MySQL driver is not found
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName(DB_DRIVER);
            
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection established successfully!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            System.out.println("Please download and add mysql-connector-java JAR to your classpath");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database!");
            System.out.println("Please ensure MySQL is running and database 'smart_parking' exists");
            e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * Closes the database connection
     * @param conn Connection object to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed!");
            } catch (SQLException e) {
                System.out.println("Error closing connection!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Tests the database connection
     * Run this method to verify your database setup
     */
    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connection test successful!");
            closeConnection(conn);
        } else {
            System.out.println("Connection test failed!");
        }
    }
    
    /**
     * Main method to test database connection
     */
    public static void main(String[] args) {
        testConnection();
    }
}
