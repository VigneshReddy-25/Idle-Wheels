package idlewheels.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idlewheels.exception.CustomerNotFoundException;
import idlewheels.exception.InvalidInputException;
import idlewheels.exception.RentalNotFoundException;
import idlewheels.exception.VehicleNotAvailableException;
import idlewheels.exception.VehicleNotFoundException;
import idlewheels.model.Customer;
import idlewheels.model.Owner;
import idlewheels.model.Rental;
import idlewheels.model.Vehicle;

public class RentalService {

    private final List<Rental> rentals = new ArrayList<>();
    private final Map<String, Rental> rentalsById = new HashMap<>();
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private int nextRentalNumber = 1001;

    public RentalService(VehicleService vehicleService, CustomerService customerService) {
        this.vehicleService = vehicleService;
        this.customerService = customerService;
    }

    public Rental rentVehicle(String customerId, String vehicleId, int numberOfDays)
            throws CustomerNotFoundException, VehicleNotFoundException,
            VehicleNotAvailableException, InvalidInputException {
        if (numberOfDays <= 0) {
            throw new InvalidInputException("Error: Number of days must be greater than 0.");
        }

        Customer customer = customerService.getCustomerById(customerId);
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        if (!vehicle.isAvailable()) {
            throw new VehicleNotAvailableException("Vehicle is currently not available");
        }

        Owner owner = vehicle.getOwner();
        if (isSamePerson(customer, owner)) {
            throw new InvalidInputException("Error: An owner cannot rent their own vehicle.");
        }

        double totalAmount = calculateRent(vehicle, numberOfDays);
        vehicle.rent();

        Rental rental = new Rental(generateRentalId(), customer, vehicle, numberOfDays, totalAmount);
        rentals.add(rental);
        rentalsById.put(rental.getRentalId(), rental);
        customer.addRental(rental);
        return rental;
    }

    public Rental returnVehicle(String rentalId, String customerId)
            throws RentalNotFoundException, InvalidInputException {
        Rental rental = rentalsById.get(rentalId);
        if (rental == null) {
            throw new RentalNotFoundException(rentalId);
        }
        if (!rental.getCustomer().getUserId().equals(customerId)) {
            throw new InvalidInputException("Error: This rental does not belong to customer " + customerId + ".");
        }
        if (!rental.isActive()) {
            throw new InvalidInputException("Error: Rental " + rentalId + " is already completed.");
        }

        rental.setRentalStatus(Rental.STATUS_COMPLETED);
        rental.getVehicle().returnVehicle();
        return rental;
    }

    public double calculateRent(Vehicle vehicle, int numberOfDays) {
        return vehicle.calculateRent(numberOfDays);
    }

    public List<Rental> getActiveRentals() {
        List<Rental> activeRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.isActive()) {
                activeRentals.add(rental);
            }
        }
        return activeRentals;
    }

    public List<Rental> getCompletedRentals() {
        List<Rental> completedRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.isCompleted()) {
                completedRentals.add(rental);
            }
        }
        return completedRentals;
    }

    public List<Rental> getRentalHistory() {
        return new ArrayList<>(rentals);
    }

    public List<Rental> getCustomerRentals(String customerId) throws CustomerNotFoundException {
        return customerService.getCustomerRentals(customerId);
    }

    public List<Rental> getOwnerRentalHistory(String ownerId) {
        List<Rental> ownerRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getVehicle().getOwner().getUserId().equals(ownerId)) {
                ownerRentals.add(rental);
            }
        }
        return ownerRentals;
    }

    public double calculateOwnerEarnings(String ownerId) {
        double totalEarnings = 0;
        for (Rental rental : getOwnerRentalHistory(ownerId)) {
            if (rental.isCompleted()) {
                totalEarnings += rental.getTotalAmount();
            }
        }
        return totalEarnings;
    }

    public List<Rental> getActiveRentalsForCustomer(String customerId) {
        List<Rental> activeRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.isActive() && rental.getCustomer().getUserId().equals(customerId)) {
                activeRentals.add(rental);
            }
        }
        return activeRentals;
    }

    private boolean isSamePerson(Customer customer, Owner owner) {
        return customer.getPhoneNumber().equals(owner.getPhoneNumber())
                || customer.getEmail().equalsIgnoreCase(owner.getEmail())
                || customer.getDrivingLicenseNumber().equalsIgnoreCase(owner.getDrivingLicenseNumber());
    }

    private String generateRentalId() {
        return "R" + nextRentalNumber++;
    }
}
