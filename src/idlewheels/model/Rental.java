package idlewheels.model;

public class Rental {

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_COMPLETED = "COMPLETED";

    private final String rentalId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final int numberOfDays;
    private final double totalAmount;
    private String rentalStatus;

    public Rental(String rentalId, Customer customer, Vehicle vehicle,
                  int numberOfDays, double totalAmount) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.numberOfDays = numberOfDays;
        this.totalAmount = totalAmount;
        this.rentalStatus = STATUS_ACTIVE;
    }

    public void printReceipt() {
        Owner owner = vehicle.getOwner();
        System.out.println();
        System.out.println("========================================");
        System.out.println("             RENTAL RECEIPT");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Rental ID       : " + rentalId);
        System.out.println();
        System.out.println("Customer ID     : " + customer.getUserId());
        System.out.println("Customer Name   : " + customer.getName());
        System.out.println();
        System.out.println("Owner ID        : " + owner.getUserId());
        System.out.println("Owner Name      : " + owner.getName());
        System.out.println();
        System.out.println("Vehicle ID      : " + vehicle.getVehicleId());
        System.out.println("Vehicle         : " + vehicle.getDisplayName());
        System.out.println();
        System.out.println("Rent Per Day    : ₹" + Vehicle.formatAmount(vehicle.getRentPerDay()));
        System.out.println("Rental Days     : " + numberOfDays);
        System.out.println("Total Amount    : ₹" + Vehicle.formatAmount(totalAmount));
        System.out.println();
        System.out.println("Status          : " + rentalStatus);
        System.out.println();
        System.out.println("========================================");
        System.out.println("        THANK YOU FOR USING");
        System.out.println("             IDLE WHEELS");
        System.out.println("========================================");
        System.out.println();
    }

    public void printReturnReceipt() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("             RETURN RECEIPT");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Rental ID       : " + rentalId);
        System.out.println("Customer        : " + customer.getName());
        System.out.println("Owner           : " + vehicle.getOwner().getName());
        System.out.println("Vehicle         : " + vehicle.getDisplayName());
        System.out.println();
        System.out.println("Rental Days     : " + numberOfDays);
        System.out.println("Total Amount    : ₹" + Vehicle.formatAmount(totalAmount));
        System.out.println();
        System.out.println("Status          : " + rentalStatus);
        System.out.println("Vehicle Status  : AVAILABLE");
        System.out.println();
        System.out.println("========================================");
        System.out.println();
    }

    public String getRentalId() {
        return rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public boolean isActive() {
        return STATUS_ACTIVE.equals(rentalStatus);
    }

    public boolean isCompleted() {
        return STATUS_COMPLETED.equals(rentalStatus);
    }

    @Override
    public String toString() {
        return rentalId + " | " + customer.getName() + " rented " + vehicle.getDisplayName()
                + " from " + vehicle.getOwner().getName() + " | "
                + numberOfDays + " days | ₹" + Vehicle.formatAmount(totalAmount)
                + " | " + rentalStatus;
    }
}
