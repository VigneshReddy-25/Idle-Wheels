package idlewheels.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idlewheels.exception.InvalidInputException;
import idlewheels.exception.VehicleNotFoundException;
import idlewheels.model.Bike;
import idlewheels.model.Car;
import idlewheels.model.Owner;
import idlewheels.model.Vehicle;

public class VehicleService {

    private final Map<String, Vehicle> vehiclesById = new HashMap<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private int nextCarNumber = 104;
    private int nextBikeNumber = 104;

    public void addVehicle(Vehicle vehicle) throws InvalidInputException {
        if (vehicle == null) {
            throw new InvalidInputException("Error: Vehicle cannot be null.");
        }
        if (vehicle.getOwner() == null) {
            throw new InvalidInputException("Error: Every vehicle must belong to an owner.");
        }
        if (vehicle.getRentPerDay() <= 0) {
            throw new InvalidInputException("Error: Rent per day must be greater than 0.");
        }
        if (vehiclesById.containsKey(vehicle.getVehicleId())) {
            throw new InvalidInputException("Error: Vehicle ID already exists: " + vehicle.getVehicleId());
        }
        if (isRegistrationNumberTaken(vehicle.getRegistrationNumber(), null)) {
            throw new InvalidInputException(
                    "Error: Registration number already exists: " + vehicle.getRegistrationNumber());
        }
        vehiclesById.put(vehicle.getVehicleId(), vehicle);
        vehicles.add(vehicle);
    }

    public String generateCarId() {
        return "C" + nextCarNumber++;
    }

    public String generateBikeId() {
        return "B" + nextBikeNumber++;
    }

    public void removeVehicle(String vehicleId, Owner owner)
            throws VehicleNotFoundException, InvalidInputException {
        Vehicle vehicle = getVehicleById(vehicleId);
        if (!vehicle.getOwner().getUserId().equals(owner.getUserId())) {
            throw new InvalidInputException("Error: You can remove only your own vehicles.");
        }
        if (!vehicle.isAvailable()) {
            throw new InvalidInputException(
                    "Error: Vehicle " + vehicleId + " is currently rented and cannot be removed.");
        }
        vehiclesById.remove(vehicleId);
        vehicles.remove(vehicle);
        owner.removeOwnedVehicle(vehicle);
    }

    public void updateVehicle(String vehicleId, Owner owner, String brand, String model,
                              String registrationNumber, double rentPerDay)
            throws VehicleNotFoundException, InvalidInputException {
        Vehicle vehicle = getVehicleById(vehicleId);
        if (!vehicle.getOwner().getUserId().equals(owner.getUserId())) {
            throw new InvalidInputException("Error: You can update only your own vehicles.");
        }
        if (brand == null || brand.trim().isEmpty()) {
            throw new InvalidInputException("Error: Brand cannot be empty.");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new InvalidInputException("Error: Model cannot be empty.");
        }
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new InvalidInputException("Error: Registration number cannot be empty.");
        }
        if (rentPerDay <= 0) {
            throw new InvalidInputException("Error: Rent per day must be greater than 0.");
        }
        if (isRegistrationNumberTaken(registrationNumber.trim(), vehicleId)) {
            throw new InvalidInputException("Error: Registration number already exists: " + registrationNumber.trim());
        }
        vehicle.setBrand(brand.trim());
        vehicle.setModel(model.trim());
        vehicle.setRegistrationNumber(registrationNumber.trim());
        vehicle.setRentPerDay(rentPerDay);
    }

    public Vehicle getVehicleById(String vehicleId) throws VehicleNotFoundException {
        Vehicle vehicle = vehiclesById.get(vehicleId);
        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleId);
        }
        return vehicle;
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public List<Vehicle> getAvailableCars() {
        return filterAvailableByType(Vehicle.CAR);
    }

    public List<Vehicle> getAvailableBikes() {
        return filterAvailableByType(Vehicle.BIKE);
    }

    public List<Vehicle> getRentedVehicles() {
        List<Vehicle> rentedVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.isAvailable()) {
                rentedVehicles.add(vehicle);
            }
        }
        return rentedVehicles;
    }

    public List<Vehicle> searchVehicles(String keyword) {
        List<Vehicle> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }
        String searchText = keyword.trim().toLowerCase();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getBrand().toLowerCase().contains(searchText)
                    || vehicle.getModel().toLowerCase().contains(searchText)
                    || vehicle.getVehicleType().toLowerCase().contains(searchText)) {
                results.add(vehicle);
            }
        }
        return results;
    }

    public List<Vehicle> getVehiclesByOwner(String ownerId) {
        List<Vehicle> ownerVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getOwner().getUserId().equals(ownerId)) {
                ownerVehicles.add(vehicle);
            }
        }
        return ownerVehicles;
    }

    public void updateCarDetails(Car car, int numberOfSeats, String fuelType, String transmissionType)
            throws InvalidInputException {
        if (numberOfSeats <= 0) {
            throw new InvalidInputException("Error: Number of seats must be greater than 0.");
        }
        car.setNumberOfSeats(numberOfSeats);
        car.setFuelType(fuelType);
        car.setTransmissionType(transmissionType);
    }

    public void updateBikeDetails(Bike bike, int engineCapacity, String bikeType)
            throws InvalidInputException {
        if (engineCapacity <= 0) {
            throw new InvalidInputException("Error: Engine capacity must be greater than 0.");
        }
        bike.setEngineCapacity(engineCapacity);
        bike.setBikeType(bikeType);
    }

    private boolean isRegistrationNumberTaken(String registrationNumber, String excludeVehicleId) {
        if (registrationNumber == null) {
            return false;
        }
        for (Vehicle vehicle : vehicles) {
            if (excludeVehicleId != null && vehicle.getVehicleId().equals(excludeVehicleId)) {
                continue;
            }
            if (vehicle.getRegistrationNumber().equalsIgnoreCase(registrationNumber.trim())) {
                return true;
            }
        }
        return false;
    }

    private List<Vehicle> filterAvailableByType(String vehicleType) {
        List<Vehicle> results = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable() && vehicle.getVehicleType().equalsIgnoreCase(vehicleType)) {
                results.add(vehicle);
            }
        }
        return results;
    }
}
