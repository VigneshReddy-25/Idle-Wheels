package idlewheels.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private String drivingLicenseNumber;
    private final List<Rental> rentals;

    public Customer(String userId, String name, String phoneNumber, String email, String drivingLicenseNumber) {
        super(userId, name, phoneNumber, email);
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.rentals = new ArrayList<>();
    }

    @Override
    public String getUserRole() {
        return "Customer";
    }

    @Override
    protected void printExtraDetails() {
        System.out.println("License No       : " + drivingLicenseNumber);
        System.out.println("Total Rentals    : " + rentals.size());
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public List<Rental> getRentals() {
        return new ArrayList<>(rentals);
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    @Override
    public String toString() {
        return getUserId() + " - " + getName() + " (Customer)";
    }
}
