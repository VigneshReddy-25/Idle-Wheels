package idlewheels.interfaces;

/**
 * Rentable defines what a rentable object should do.
 * Vehicle implements those operations.
 */
public interface Rentable {

    void rent();

    void returnVehicle();

    double calculateRent(int days);
}

