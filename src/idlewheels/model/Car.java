package idlewheels.model;

public class Car extends Vehicle {

    private int numberOfSeats;
    private String fuelType;
    private String transmissionType;

    public Car(String vehicleId, Owner owner, String brand, String model, String registrationNumber,
               double rentPerDay, int numberOfSeats, String fuelType, String transmissionType) {
        super(vehicleId, owner, brand, model, registrationNumber, rentPerDay, CAR);
        this.numberOfSeats = numberOfSeats;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
    }

    @Override
    protected void printSpecificDetails() {
        System.out.println("Seats            : " + numberOfSeats);
        System.out.println("Fuel Type        : " + fuelType);
        System.out.println("Transmission     : " + transmissionType);
    }

    @Override
    public double calculateRent(int days) {
        return getRentPerDay() * days;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    @Override
    public String toString() {
        return getVehicleId() + " - " + getBrand() + " " + getModel()
                + " - ₹" + formatAmount(getRentPerDay()) + "/day";
    }
}
