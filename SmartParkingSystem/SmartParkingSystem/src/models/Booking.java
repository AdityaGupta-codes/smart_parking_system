package models;

/**
 * Booking Model Class
 * Represents a parking slot booking
 * Contains booking details like user, parking location, and timing
 */
public class Booking {
    
    private int bookingId;
    private int userId;
    private String userName;
    private int parkingId;
    private String parkingName;
    private String vehicleNumber;
    private String vehicleType;
    private String bookingDate;
    private String bookingTime;
    private String checkOutTime;
    private String status;          // "Active", "Completed", "Cancelled"
    private int duration;           // duration in hours
    private double totalPrice;
    
    /**
     * Constructor for Booking object (with ID)
     */
    public Booking(int bookingId, int userId, String userName, int parkingId, String parkingName,
                  String vehicleNumber, String vehicleType, String bookingDate, String bookingTime,
                  String checkOutTime, String status, int duration, double totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.userName = userName;
        this.parkingId = parkingId;
        this.parkingName = parkingName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
        this.duration = duration;
        this.totalPrice = totalPrice;
    }
    
    /**
     * Constructor for new Booking (without ID and checkOutTime)
     */
    public Booking(int userId, String userName, int parkingId, String parkingName,
                  String vehicleNumber, String vehicleType, String bookingDate,
                  String bookingTime, String status, int duration, double totalPrice) {
        this.userId = userId;
        this.userName = userName;
        this.parkingId = parkingId;
        this.parkingName = parkingName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.status = status;
        this.duration = duration;
        this.totalPrice = totalPrice;
    }
    
    // ===== Getters =====
    
    public int getBookingId() {
        return bookingId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public int getParkingId() {
        return parkingId;
    }
    
    public String getParkingName() {
        return parkingName;
    }
    
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public String getBookingDate() {
        return bookingDate;
    }
    
    public String getBookingTime() {
        return bookingTime;
    }
    
    public String getCheckOutTime() {
        return checkOutTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    // ===== Setters =====
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }
    
    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }
    
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    /**
     * Returns a string representation of Booking object
     */
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", parkingId=" + parkingId +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", status='" + status + '\'' +
                ", duration=" + duration +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
