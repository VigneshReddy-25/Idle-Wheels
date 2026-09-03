package idlewheels.model;

import java.util.ArrayList;
import java.util.List;

public class Owner extends User {

    private String drivingLicenseNumber;
    private final List<Vehicle> vehicles;

    public Owner(String userId, String name, String phoneNumber, String email, String drivingLicenseNumber) {
        super(userId, name, phoneNumber, email);
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.vehicles = new ArrayList<>();
    }

    @Override
    public String getUserRole() {
        return "Owner";
    }

    @Override
    protected void printExtraDetails() {
        System.out.println("License No       : " + drivingLicenseNumber);
        System.out.println("Vehicles Owned   : " + vehicles.size());
    }

    public void addOwnedVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeOwnedVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public void displayMyVehicles() {
        System.out.println("Owner ID       : " + getUserId());
        System.out.println("Owner Name     : " + getName());
        System.out.println("Phone          : " + getPhoneNumber());
        System.out.println();
        if (vehicles.isEmpty()) {
            System.out.println("My Vehicles: none");
            return;
        }
        System.out.println("My Vehicles:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.getVehicleId() + " - " + vehicle.getDisplayName()
                    + " - " + (vehicle.isAvailable() ? "AVAILABLE" : "RENTED"));
        }
    }

    @Override
    public String toString() {
        return getUserId() + " - " + getName() + " (Owner, " + vehicles.size() + " vehicles)";
    }
}
