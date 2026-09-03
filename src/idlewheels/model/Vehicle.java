package idlewheels.model;

import idlewheels.interfaces.Rentable;

public abstract class Vehicle implements Rentable {

    public static final String CAR = "CAR";
    public static final String BIKE = "BIKE";

    private final String vehicleId;
    private final Owner owner;
    private String brand;
    private String model;
    private String registrationNumber;
    private double rentPerDay;
    private boolean available;
    private final String vehicleType;

    protected Vehicle(String vehicleId, Owner owner, String brand, String model,
                      String registrationNumber, double rentPerDay, String vehicleType) {
        this.vehicleId = vehicleId;
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.rentPerDay = rentPerDay;
        this.vehicleType = vehicleType;
        this.available = true;
    }

    public void displayDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("Vehicle ID       : " + vehicleId);
        System.out.println("Vehicle Type     : " + vehicleType);
        System.out.println("Owner            : " + owner.getName());
        System.out.println("Owner ID         : " + owner.getUserId());
        System.out.println("Brand            : " + brand);
        System.out.println("Model            : " + model);
        System.out.println("Registration No  : " + registrationNumber);
        printSpecificDetails();
        System.out.println("Rent Per Day     : ₹" + formatAmount(rentPerDay));
        System.out.println("Availability     : " + (available ? "AVAILABLE" : "RENTED"));
        System.out.println("--------------------------------------------------");
    }

    protected abstract void printSpecificDetails();

    @Override
    public void rent() {
        this.available = false;
    }

    @Override
    public void returnVehicle() {
        this.available = true;
    }

    @Override
    public double calculateRent(int days) {
        return rentPerDay * days;
    }

    public String getDisplayName() {
        return brand + " " + model;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public double getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(double rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public static String formatAmount(double amount) {
        if (amount == (long) amount) {
            return String.valueOf((long) amount);
        }
        return String.valueOf(amount);
    }

    @Override
    public String toString() {
        return vehicleId + " - " + getDisplayName() + " - ₹" + formatAmount(rentPerDay) + "/day"
                + " (Owner: " + owner.getName() + ")";
    }
}
