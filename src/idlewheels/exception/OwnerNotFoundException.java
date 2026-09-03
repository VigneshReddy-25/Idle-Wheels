package idlewheels.exception;

public class OwnerNotFoundException extends Exception {

    public OwnerNotFoundException(String ownerId) {
        super("Error: Owner " + ownerId + " was not found.");
    }
}
