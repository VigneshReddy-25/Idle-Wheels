package idlewheels.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idlewheels.exception.InvalidInputException;
import idlewheels.exception.OwnerNotFoundException;
import idlewheels.model.Owner;
import idlewheels.model.Vehicle;

public class OwnerService {

    private final Map<String, Owner> ownersById = new HashMap<>();
    private final List<Owner> owners = new ArrayList<>();
    private final VehicleService vehicleService;
    private int nextOwnerNumber = 104;

    public OwnerService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public Owner registerOwner(String name, String phoneNumber, String email, String drivingLicenseNumber)
            throws InvalidInputException {
        validateOwnerFields(name, phoneNumber, email, drivingLicenseNumber);
        String ownerId = "O" + nextOwnerNumber++;
        Owner owner = new Owner(ownerId, name.trim(), phoneNumber.trim(), email.trim(), drivingLicenseNumber.trim());
        ownersById.put(ownerId, owner);
        owners.add(owner);
        return owner;
    }

    public void addExistingOwner(Owner owner) throws InvalidInputException {
        if (owner == null) {
            throw new InvalidInputException("Error: Owner cannot be null.");
        }
        if (ownersById.containsKey(owner.getUserId())) {
            throw new InvalidInputException("Error: Owner ID already exists: " + owner.getUserId());
        }
        ownersById.put(owner.getUserId(), owner);
        owners.add(owner);
    }

    public Owner getOwnerById(String ownerId) throws OwnerNotFoundException {
        Owner owner = ownersById.get(ownerId);
        if (owner == null) {
            throw new OwnerNotFoundException(ownerId);
        }
        return owner;
    }

    public List<Owner> getAllOwners() {
        return new ArrayList<>(owners);
    }

    public void addVehicleForOwner(Owner owner, Vehicle vehicle) throws InvalidInputException {
        if (owner == null) {
            throw new InvalidInputException("Error: Owner is required to register a vehicle.");
        }
        vehicleService.addVehicle(vehicle);
        owner.addOwnedVehicle(vehicle);
    }

    public List<Vehicle> getOwnerVehicles(String ownerId) throws OwnerNotFoundException {
        Owner owner = getOwnerById(ownerId);
        return owner.getVehicles();
    }

    public List<Vehicle> getAvailableOwnerVehicles(String ownerId) throws OwnerNotFoundException {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : getOwnerVehicles(ownerId)) {
            if (vehicle.isAvailable()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    private void validateOwnerFields(String name, String phoneNumber, String email, String drivingLicenseNumber)
            throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Error: Name cannot be empty.");
        }
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new InvalidInputException("Error: Phone number must be exactly 10 digits.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Error: Please enter a valid email address.");
        }
        if (drivingLicenseNumber == null || drivingLicenseNumber.trim().isEmpty()) {
            throw new InvalidInputException("Error: Driving license number cannot be empty.");
        }
    }
}
