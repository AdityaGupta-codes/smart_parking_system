package models;

/**
 * Parking Location Model Class
 * Represents a parking location in the system
 * Contains information about a parking area
 */
public class ParkingLocation {
    
    private int parkingId;
    private String parkingName;
    private String location;
    private String address;
    private int totalSlots;
    private int availableSlots;
    private double pricePerHour;
    private String city;
    private String zipCode;
    private String createdDate;
    
    /**
     * Constructor for ParkingLocation object (with ID)
     */
    public ParkingLocation(int parkingId, String parkingName, String location, String address,
                          int totalSlots, int availableSlots, double pricePerHour,
                          String city, String zipCode, String createdDate) {
        this.parkingId = parkingId;
        this.parkingName = parkingName;
        this.location = location;
        this.address = address;
        this.totalSlots = totalSlots;
        this.availableSlots = availableSlots;
        this.pricePerHour = pricePerHour;
        this.city = city;
        this.zipCode = zipCode;
        this.createdDate = createdDate;
    }
    
    /**
     * Constructor for new ParkingLocation (without ID)
     */
    public ParkingLocation(String parkingName, String location, String address,
                          int totalSlots, double pricePerHour,
                          String city, String zipCode) {
        this.parkingName = parkingName;
        this.location = location;
        this.address = address;
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots;
        this.pricePerHour = pricePerHour;
        this.city = city;
        this.zipCode = zipCode;
    }
    
    // ===== Getters =====
    
    public int getParkingId() {
        return parkingId;
    }
    
    public String getParkingName() {
        return parkingName;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getAddress() {
        return address;
    }
    
    public int getTotalSlots() {
        return totalSlots;
    }
    
    public int getAvailableSlots() {
        return availableSlots;
    }
    
    public double getPricePerHour() {
        return pricePerHour;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public String getCreatedDate() {
        return createdDate;
    }
    
    // ===== Setters =====
    
    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }
    
    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }
    
    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }
    
    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * Returns a string representation of ParkingLocation object
     */
    @Override
    public String toString() {
        return "ParkingLocation{" +
                "parkingId=" + parkingId +
                ", parkingName='" + parkingName + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", totalSlots=" + totalSlots +
                ", availableSlots=" + availableSlots +
                ", pricePerHour=" + pricePerHour +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
