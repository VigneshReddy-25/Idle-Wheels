package idlewheels.model;

public class Bike extends Vehicle {

    private int engineCapacity;
    private String bikeType;

    public Bike(String vehicleId, Owner owner, String brand, String model, String registrationNumber,
                double rentPerDay, int engineCapacity, String bikeType) {
        super(vehicleId, owner, brand, model, registrationNumber, rentPerDay, BIKE);
        this.engineCapacity = engineCapacity;
        this.bikeType = bikeType;
    }

    @Override
    protected void printSpecificDetails() {
        System.out.println("Engine Capacity  : " + engineCapacity + " cc");
        System.out.println("Bike Type        : " + bikeType);
    }

    @Override
    public double calculateRent(int days) {
        return getRentPerDay() * days;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }

    @Override
    public String toString() {
        return getVehicleId() + " - " + getBrand() + " " + getModel()
                + " - ₹" + formatAmount(getRentPerDay()) + "/day";
    }
}
