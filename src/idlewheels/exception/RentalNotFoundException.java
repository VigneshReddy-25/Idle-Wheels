package idlewheels.exception;

public class RentalNotFoundException extends Exception {

    public RentalNotFoundException(String rentalId) {
        super("Error: Rental " + rentalId + " was not found.");
    }
}
