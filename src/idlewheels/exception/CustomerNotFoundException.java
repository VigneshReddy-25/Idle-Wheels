package idlewheels.exception;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(String customerId) {
        super("Error: Customer " + customerId + " was not found.");
    }
}
