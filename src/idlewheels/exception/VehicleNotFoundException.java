package idlewheels.exception;

public class VehicleNotFoundException extends Exception {

    public VehicleNotFoundException(String vehicleId) {
        super("Error: Vehicle " + vehicleId + " was not found.");
    }
}
