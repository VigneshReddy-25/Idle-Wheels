package idlewheels.main;

import java.util.List;
import java.util.Scanner;

import idlewheels.exception.CustomerNotFoundException;
import idlewheels.exception.InvalidInputException;
import idlewheels.exception.OwnerNotFoundException;
import idlewheels.exception.RentalNotFoundException;
import idlewheels.exception.VehicleNotAvailableException;
import idlewheels.exception.VehicleNotFoundException;
import idlewheels.model.Bike;
import idlewheels.model.Car;
import idlewheels.model.Customer;
import idlewheels.model.Owner;
import idlewheels.model.Rental;
import idlewheels.model.Vehicle;
import idlewheels.service.CustomerService;
import idlewheels.service.OwnerService;
import idlewheels.service.RentalService;
import idlewheels.service.VehicleService;
import idlewheels.util.InputUtil;

public class IdleWheelsApplication {

    private static final String ADMIN_ID = "ADMIN";

    private final VehicleService vehicleService;
    private final OwnerService ownerService;
    private final CustomerService customerService;
    private final RentalService rentalService;
    private final InputUtil inputUtil;

    public IdleWheelsApplication(VehicleService vehicleService, OwnerService ownerService,
                                 CustomerService customerService, RentalService rentalService,
                                 InputUtil inputUtil) {
        this.vehicleService = vehicleService;
        this.ownerService = ownerService;
        this.customerService = customerService;
        this.rentalService = rentalService;
        this.inputUtil = inputUtil;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            VehicleService vehicleService = new VehicleService();
            OwnerService ownerService = new OwnerService(vehicleService);
            CustomerService customerService = new CustomerService();
            RentalService rentalService = new RentalService(vehicleService, customerService);
            InputUtil inputUtil = new InputUtil(scanner);

            loadSampleData(ownerService, customerService, vehicleService);

            IdleWheelsApplication application = new IdleWheelsApplication(
                    vehicleService, ownerService, customerService, rentalService, inputUtil);
            application.start();
        } finally {
            scanner.close();
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMainMenu();
            try {
                int choice = inputUtil.readInt("Enter choice: ");
                System.out.println();
                switch (choice) {
                    case 1:
                        handleOwnerEntry();
                        break;
                    case 2:
                        handleCustomerEntry();
                        break;
                    case 3:
                        handleAdminEntry();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using Idle Wheels. Goodbye!");
                        break;
                    default:
                        System.out.println("Error: Invalid menu choice. Please try again.");
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
            if (running) {
                System.out.println();
            }
        }
    }

    private void printMainMenu() {
        System.out.println("========================================");
        System.out.println("              IDLE WHEELS");
        System.out.println("      CAR & BIKE SHARING PLATFORM");
        System.out.println("========================================");
        System.out.println();
        System.out.println("1. Owner");
        System.out.println("2. Customer");
        System.out.println("3. Admin");
        System.out.println("0. Exit");
        System.out.println();
    }

    private void handleOwnerEntry() throws InvalidInputException, OwnerNotFoundException {
        System.out.println("1. Register Owner");
        System.out.println("2. Login");
        System.out.println("0. Back");
        int choice = inputUtil.readInt("Enter choice: ");
        if (choice == 1) {
            Owner owner = registerOwner();
            ownerDashboard(owner);
        } else if (choice == 2) {
            String ownerId = inputUtil.readRequiredString("Enter Owner ID: ", "Owner ID");
            Owner owner = ownerService.getOwnerById(ownerId);
            System.out.println("Welcome, " + owner.getName() + "!");
            owner.displayDetails();
            ownerDashboard(owner);
        } else if (choice != 0) {
            System.out.println("Error: Invalid choice.");
        }
    }

    private Owner registerOwner() throws InvalidInputException {
        String name = inputUtil.readRequiredString("Enter name: ", "Name");
        String phone = inputUtil.readPhoneNumber("Enter 10-digit phone number: ");
        String email = inputUtil.readEmail("Enter email: ");
        String license = inputUtil.readRequiredString("Enter driving license number: ", "Driving license number");
        Owner owner = ownerService.registerOwner(name, phone, email, license);
        System.out.println("Owner registered successfully. Your Owner ID is " + owner.getUserId());
        owner.displayDetails();
        return owner;
    }

    private void ownerDashboard(Owner owner) {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("             OWNER DASHBOARD");
            System.out.println("========================================");
            System.out.println();
            System.out.println("1. Add Vehicle");
            System.out.println("2. View My Vehicles");
            System.out.println("3. View Available My Vehicles");
            System.out.println("4. Update My Vehicle");
            System.out.println("5. Remove My Vehicle");
            System.out.println("6. View My Rental History");
            System.out.println("7. View My Earnings");
            System.out.println("0. Logout");
            System.out.println();
            try {
                int choice = inputUtil.readInt("Enter choice: ");
                System.out.println();
                switch (choice) {
                    case 1:
                        addVehicleForOwner(owner);
                        break;
                    case 2:
                        viewOwnerVehicles(owner);
                        break;
                    case 3:
                        viewAvailableOwnerVehicles(owner);
                        break;
                    case 4:
                        updateOwnerVehicle(owner);
                        break;
                    case 5:
                        removeOwnerVehicle(owner);
                        break;
                    case 6:
                        viewOwnerRentalHistory(owner);
                        break;
                    case 7:
                        viewOwnerEarnings(owner);
                        break;
                    case 0:
                        inDashboard = false;
                        System.out.println("Logged out of owner dashboard.");
                        break;
                    default:
                        System.out.println("Error: Invalid menu choice.");
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void addVehicleForOwner(Owner owner) throws InvalidInputException {
        System.out.println("========================================");
        System.out.println("          ADD YOUR VEHICLE");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Owner ID: " + owner.getUserId());
        System.out.println();
        System.out.println("1. Add Car");
        System.out.println("2. Add Bike");
        int choice = inputUtil.readInt("Enter choice: ");
        if (choice != 1 && choice != 2) {
            throw new InvalidInputException("Error: Invalid choice. Please select 1 or 2.");
        }

        String brand = inputUtil.readRequiredString("Brand: ", "Brand");
        String model = inputUtil.readRequiredString("Model: ", "Model");
        String registration = inputUtil.readRequiredString("Registration Number: ", "Registration number");
        double rentPerDay = inputUtil.readPositiveDouble("Rent Per Day: ");

        Vehicle vehicle;
        if (choice == 1) {
            int seats = inputUtil.readPositiveInt("Number of Seats: ");
            String fuelType = inputUtil.readFuelType();
            String transmissionType = inputUtil.readTransmissionType();
            vehicle = new Car(vehicleService.generateCarId(), owner, brand, model, registration,
                    rentPerDay, seats, fuelType, transmissionType);
        } else {
            int engineCapacity = inputUtil.readPositiveInt("Engine Capacity (cc): ");
            String bikeType = inputUtil.readBikeType();
            vehicle = new Bike(vehicleService.generateBikeId(), owner, brand, model, registration,
                    rentPerDay, engineCapacity, bikeType);
        }

        ownerService.addVehicleForOwner(owner, vehicle);
        System.out.println();
        System.out.println("Vehicle registered successfully!");
        System.out.println();
        System.out.println("Vehicle ID: " + vehicle.getVehicleId());
        System.out.println("Owner: " + owner.getName());
        System.out.println("Status: AVAILABLE");
    }

    private void viewOwnerVehicles(Owner owner) {
        List<Vehicle> vehicles = owner.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("You have not registered any vehicles yet.");
            return;
        }
        owner.displayMyVehicles();
        System.out.println();
        displayVehicles(vehicles);
    }

    private void viewAvailableOwnerVehicles(Owner owner) throws OwnerNotFoundException {
        List<Vehicle> vehicles = ownerService.getAvailableOwnerVehicles(owner.getUserId());
        if (vehicles.isEmpty()) {
            System.out.println("None of your vehicles are currently available.");
            return;
        }
        System.out.println("YOUR AVAILABLE VEHICLES");
        displayVehicles(vehicles);
    }

    private void updateOwnerVehicle(Owner owner)
            throws InvalidInputException, VehicleNotFoundException {
        String vehicleId = inputUtil.readRequiredString("Enter vehicle ID to update: ", "Vehicle ID");
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        if (!vehicle.getOwner().getUserId().equals(owner.getUserId())) {
            throw new InvalidInputException("Error: You can update only your own vehicles.");
        }
        vehicle.displayDetails();

        String brand = inputUtil.readRequiredString("Enter new brand: ", "Brand");
        String model = inputUtil.readRequiredString("Enter new model: ", "Model");
        String registration = inputUtil.readRequiredString("Enter new registration number: ", "Registration number");
        double rentPerDay = inputUtil.readPositiveDouble("Enter new rent per day: ");
        vehicleService.updateVehicle(vehicleId, owner, brand, model, registration, rentPerDay);

        if (vehicle instanceof Car) {
            int seats = inputUtil.readPositiveInt("Enter new number of seats: ");
            vehicleService.updateCarDetails((Car) vehicle, seats, inputUtil.readFuelType(),
                    inputUtil.readTransmissionType());
        } else if (vehicle instanceof Bike) {
            int engineCapacity = inputUtil.readPositiveInt("Enter new engine capacity (cc): ");
            vehicleService.updateBikeDetails((Bike) vehicle, engineCapacity, inputUtil.readBikeType());
        }

        System.out.println("Vehicle updated successfully.");
        vehicle.displayDetails();
    }

    private void removeOwnerVehicle(Owner owner) throws InvalidInputException, VehicleNotFoundException {
        String vehicleId = inputUtil.readRequiredString("Enter vehicle ID to remove: ", "Vehicle ID");
        vehicleService.removeVehicle(vehicleId, owner);
        System.out.println("Vehicle " + vehicleId + " removed successfully.");
    }

    private void viewOwnerRentalHistory(Owner owner) {
        List<Rental> history = rentalService.getOwnerRentalHistory(owner.getUserId());
        if (history.isEmpty()) {
            System.out.println("No rental history for your vehicles yet.");
            return;
        }
        System.out.println("MY VEHICLE RENTAL HISTORY");
        printRentalList(history);
    }

    private void viewOwnerEarnings(Owner owner) throws OwnerNotFoundException {
        List<Rental> history = rentalService.getOwnerRentalHistory(owner.getUserId());
        System.out.println("Owner: " + owner.getName());
        System.out.println();
        double total = 0;
        for (Rental rental : history) {
            if (rental.isCompleted()) {
                System.out.println(rental.getVehicle().getDisplayName() + ": "
                        + rental.getRentalId() + " = ₹" + Vehicle.formatAmount(rental.getTotalAmount()));
                total += rental.getTotalAmount();
            }
        }
        if (total == 0) {
            System.out.println("No completed rentals yet. Total Earnings = ₹0");
            return;
        }
        System.out.println();
        System.out.println("Total Earnings = ₹" + Vehicle.formatAmount(
                rentalService.calculateOwnerEarnings(owner.getUserId())));
    }

    private void handleCustomerEntry() throws InvalidInputException, CustomerNotFoundException {
        System.out.println("1. Register Customer");
        System.out.println("2. Login");
        System.out.println("0. Back");
        int choice = inputUtil.readInt("Enter choice: ");
        if (choice == 1) {
            Customer customer = registerCustomer();
            customerDashboard(customer);
        } else if (choice == 2) {
            String customerId = inputUtil.readRequiredString("Enter Customer ID: ", "Customer ID");
            Customer customer = customerService.getCustomerById(customerId);
            System.out.println("Welcome, " + customer.getName() + "!");
            customer.displayDetails();
            customerDashboard(customer);
        } else if (choice != 0) {
            System.out.println("Error: Invalid choice.");
        }
    }

    private Customer registerCustomer() throws InvalidInputException {
        String name = inputUtil.readRequiredString("Enter name: ", "Name");
        String phone = inputUtil.readPhoneNumber("Enter 10-digit phone number: ");
        String email = inputUtil.readEmail("Enter email: ");
        String license = inputUtil.readRequiredString("Enter driving license number: ", "Driving license number");
        Customer customer = customerService.registerCustomer(name, phone, email, license);
        System.out.println("Customer registered successfully. Your Customer ID is " + customer.getUserId());
        customer.displayDetails();
        return customer;
    }

    private void customerDashboard(Customer customer) {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("           CUSTOMER DASHBOARD");
            System.out.println("========================================");
            System.out.println();
            System.out.println("1. View Available Cars");
            System.out.println("2. View Available Bikes");
            System.out.println("3. Search Vehicles");
            System.out.println("4. View Vehicle Details");
            System.out.println("5. Rent Vehicle");
            System.out.println("6. Return Vehicle");
            System.out.println("7. View Active Rentals");
            System.out.println("8. View Rental History");
            System.out.println("0. Logout");
            System.out.println();
            try {
                int choice = inputUtil.readInt("Enter choice: ");
                System.out.println();
                switch (choice) {
                    case 1:
                        displayVehiclesOrEmpty(vehicleService.getAvailableCars(), "No cars are currently available.");
                        break;
                    case 2:
                        displayVehiclesOrEmpty(vehicleService.getAvailableBikes(), "No bikes are currently available.");
                        break;
                    case 3:
                        searchVehicles();
                        break;
                    case 4:
                        viewVehicleDetails();
                        break;
                    case 5:
                        rentVehicle(customer);
                        break;
                    case 6:
                        returnVehicle(customer);
                        break;
                    case 7:
                        viewCustomerActiveRentals(customer);
                        break;
                    case 8:
                        viewCustomerHistory(customer);
                        break;
                    case 0:
                        inDashboard = false;
                        System.out.println("Logged out of customer dashboard.");
                        break;
                    default:
                        System.out.println("Error: Invalid menu choice.");
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void searchVehicles() throws InvalidInputException {
        String searchText = inputUtil.readRequiredString(
                "Enter brand, model, or vehicle type: ", "Search text");
        List<Vehicle> results = vehicleService.searchVehicles(searchText);
        if (results.isEmpty()) {
            System.out.println("No matching vehicles found.");
            return;
        }
        System.out.println("Results:");
        for (Vehicle vehicle : results) {
            System.out.println(vehicle.getVehicleId() + " - " + vehicle.getDisplayName()
                    + " - ₹" + Vehicle.formatAmount(vehicle.getRentPerDay()) + "/day"
                    + " - " + (vehicle.isAvailable() ? "AVAILABLE" : "RENTED"));
        }
        System.out.println();
        displayVehicles(results);
    }

    private void viewVehicleDetails() throws InvalidInputException, VehicleNotFoundException {
        String vehicleId = inputUtil.readRequiredString("Enter vehicle ID: ", "Vehicle ID");
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        vehicle.displayDetails();
        System.out.println("Owner details:");
        vehicle.getOwner().displayDetails();
    }

    private void rentVehicle(Customer customer) throws InvalidInputException, CustomerNotFoundException,
            VehicleNotFoundException, VehicleNotAvailableException {
        String vehicleId = inputUtil.readRequiredString("Enter vehicle ID to rent: ", "Vehicle ID");
        int days = inputUtil.readPositiveInt("Enter number of days: ");
        Rental rental = rentalService.rentVehicle(customer.getUserId(), vehicleId, days);
        rental.printReceipt();
    }

    private void returnVehicle(Customer customer) throws InvalidInputException, RentalNotFoundException {
        String rentalId = inputUtil.readRequiredString("Enter rental ID: ", "Rental ID");
        Rental rental = rentalService.returnVehicle(rentalId, customer.getUserId());
        rental.printReturnReceipt();
    }

    private void viewCustomerActiveRentals(Customer customer) {
        List<Rental> activeRentals = rentalService.getActiveRentalsForCustomer(customer.getUserId());
        if (activeRentals.isEmpty()) {
            System.out.println("You have no active rentals.");
            return;
        }
        System.out.println("YOUR ACTIVE RENTALS");
        printRentalList(activeRentals);
    }

    private void viewCustomerHistory(Customer customer) {
        List<Rental> history = customer.getRentals();
        if (history.isEmpty()) {
            System.out.println("You have no rental history yet.");
            return;
        }
        System.out.println("YOUR RENTAL HISTORY");
        printRentalList(history);
    }

    private void handleAdminEntry() throws InvalidInputException {
        String adminId = inputUtil.readRequiredString("Enter Admin ID: ", "Admin ID");
        if (!ADMIN_ID.equalsIgnoreCase(adminId)) {
            throw new InvalidInputException("Error: Invalid admin ID. Use ADMIN.");
        }
        adminDashboard();
    }

    private void adminDashboard() {
        boolean inDashboard = true;
        while (inDashboard) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("             ADMIN DASHBOARD");
            System.out.println("========================================");
            System.out.println();
            System.out.println("1. View All Owners");
            System.out.println("2. View All Customers");
            System.out.println("3. View All Vehicles");
            System.out.println("4. View Available Vehicles");
            System.out.println("5. View Rented Vehicles");
            System.out.println("6. View All Rentals");
            System.out.println("7. View Active Rentals");
            System.out.println("8. View Completed Rentals");
            System.out.println("0. Logout");
            System.out.println();
            try {
                int choice = inputUtil.readInt("Enter choice: ");
                System.out.println();
                switch (choice) {
                    case 1:
                        viewAllOwners();
                        break;
                    case 2:
                        viewAllCustomers();
                        break;
                    case 3:
                        displayVehiclesOrEmpty(vehicleService.getAllVehicles(), "No vehicles found.");
                        break;
                    case 4:
                        displayVehiclesOrEmpty(vehicleService.getAvailableVehicles(), "No vehicles are available.");
                        break;
                    case 5:
                        displayVehiclesOrEmpty(vehicleService.getRentedVehicles(), "No vehicles are currently rented.");
                        break;
                    case 6:
                        printRentalListOrEmpty(rentalService.getRentalHistory(), "No rentals found.");
                        break;
                    case 7:
                        printRentalListOrEmpty(rentalService.getActiveRentals(), "No active rentals.");
                        break;
                    case 8:
                        printRentalListOrEmpty(rentalService.getCompletedRentals(), "No completed rentals.");
                        break;
                    case 0:
                        inDashboard = false;
                        System.out.println("Logged out of admin dashboard.");
                        break;
                    default:
                        System.out.println("Error: Invalid menu choice.");
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void viewAllOwners() {
        List<Owner> owners = ownerService.getAllOwners();
        if (owners.isEmpty()) {
            System.out.println("No owners found.");
            return;
        }
        for (Owner owner : owners) {
            owner.displayDetails();
            owner.displayMyVehicles();
            System.out.println();
        }
    }

    private void viewAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        for (Customer customer : customers) {
            customer.displayDetails();
        }
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            vehicle.displayDetails();
        }
    }

    private void displayVehiclesOrEmpty(List<Vehicle> vehicles, String emptyMessage) {
        if (vehicles.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        displayVehicles(vehicles);
    }

    private void printRentalList(List<Rental> rentals) {
        System.out.println("--------------------------------------------------");
        for (Rental rental : rentals) {
            System.out.println(rental);
        }
        System.out.println("--------------------------------------------------");
    }

    private void printRentalListOrEmpty(List<Rental> rentals, String emptyMessage) {
        if (rentals.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        printRentalList(rentals);
    }

    private static void loadSampleData(OwnerService ownerService, CustomerService customerService,
                                       VehicleService vehicleService) {
        try {
            Owner ramesh = new Owner("O101", "Ramesh", "9876500001", "ramesh@idlewheels.com", "TS09OWN0001");
            Owner suresh = new Owner("O102", "Suresh", "9876500002", "suresh@idlewheels.com", "TS09OWN0002");
            Owner priya = new Owner("O103", "Priya", "9876500003", "priya@idlewheels.com", "TS09OWN0003");
            ownerService.addExistingOwner(ramesh);
            ownerService.addExistingOwner(suresh);
            ownerService.addExistingOwner(priya);

            Customer rahul = new Customer("CU101", "Rahul", "9876510001", "rahul@idlewheels.com", "TS09CUS0001");
            Customer kiran = new Customer("CU102", "Kiran", "9876510002", "kiran@idlewheels.com", "TS09CUS0002");
            Customer anjali = new Customer("CU103", "Anjali", "9876510003", "anjali@idlewheels.com", "TS09CUS0003");
            customerService.addExistingCustomer(rahul);
            customerService.addExistingCustomer(kiran);
            customerService.addExistingCustomer(anjali);

            Vehicle creta = new Car("C101", ramesh, "Hyundai", "Creta", "TS09AB1234", 2200,
                    5, "PETROL", "AUTOMATIC");
            Vehicle innova = new Car("C102", suresh, "Toyota", "Innova", "TS07CD5678", 2500,
                    7, "DIESEL", "MANUAL");
            Vehicle nexon = new Car("C103", priya, "Tata", "Nexon", "TS08EF9012", 1800,
                    5, "ELECTRIC", "AUTOMATIC");
            Vehicle classic = new Bike("B101", ramesh, "Royal Enfield", "Classic 350", "TS09GH3456", 900,
                    349, "CRUISER");
            Vehicle r15 = new Bike("B102", suresh, "Yamaha", "R15", "TS10JK7890", 1000,
                    155, "SPORTS");
            Vehicle activa = new Bike("B103", priya, "Honda", "Activa", "TS11LM2345", 500,
                    110, "SCOOTER");

            ownerService.addVehicleForOwner(ramesh, creta);
            ownerService.addVehicleForOwner(suresh, innova);
            ownerService.addVehicleForOwner(priya, nexon);
            ownerService.addVehicleForOwner(ramesh, classic);
            ownerService.addVehicleForOwner(suresh, r15);
            ownerService.addVehicleForOwner(priya, activa);
        } catch (InvalidInputException exception) {
            System.out.println("Could not load sample data: " + exception.getMessage());
        }
    }
}
