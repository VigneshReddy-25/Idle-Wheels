package idlewheels.util;

import java.util.Scanner;

import idlewheels.exception.InvalidInputException;

public class InputUtil {

    private final Scanner scanner;

    public InputUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readRequiredString(String prompt, String fieldName) throws InvalidInputException {
        String value = readString(prompt);
        if (value.isEmpty()) {
            throw new InvalidInputException("Error: " + fieldName + " cannot be empty.");
        }
        return value;
    }

    public int readInt(String prompt) throws InvalidInputException {
        String value = readString(prompt);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Error: Please enter a valid whole number.");
        }
    }

    public double readDouble(String prompt) throws InvalidInputException {
        String value = readString(prompt);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Error: Please enter a valid number.");
        }
    }

    public int readPositiveInt(String prompt) throws InvalidInputException {
        int value = readInt(prompt);
        if (value <= 0) {
            throw new InvalidInputException("Error: Value must be greater than 0.");
        }
        return value;
    }

    public double readPositiveDouble(String prompt) throws InvalidInputException {
        double value = readDouble(prompt);
        if (value <= 0) {
            throw new InvalidInputException("Error: Value must be greater than 0.");
        }
        return value;
    }

    public String readPhoneNumber(String prompt) throws InvalidInputException {
        String phoneNumber = readString(prompt);
        if (!phoneNumber.matches("\\d{10}")) {
            throw new InvalidInputException("Error: Phone number must be exactly 10 digits.");
        }
        return phoneNumber;
    }

    public String readEmail(String prompt) throws InvalidInputException {
        String email = readString(prompt);
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');
        if (email.isEmpty() || atIndex < 1 || dotIndex < atIndex + 2 || dotIndex == email.length() - 1) {
            throw new InvalidInputException("Error: Please enter a valid email address.");
        }
        return email;
    }

    public String readFuelType() throws InvalidInputException {
        System.out.println();
        System.out.println("Select Fuel Type");
        System.out.println();
        System.out.println("1. PETROL");
        System.out.println("2. DIESEL");
        System.out.println("3. ELECTRIC");
        System.out.println("4. CNG");
        System.out.println();
        int choice = readInt("Enter choice: ");
        if (choice == 1) {
            return "PETROL";
        }
        if (choice == 2) {
            return "DIESEL";
        }
        if (choice == 3) {
            return "ELECTRIC";
        }
        if (choice == 4) {
            return "CNG";
        }
        throw new InvalidInputException("Error: Invalid fuel type. Please select 1 to 4.");
    }

    public String readTransmissionType() throws InvalidInputException {
        System.out.println();
        System.out.println("Select Transmission Type");
        System.out.println();
        System.out.println("1. MANUAL");
        System.out.println("2. AUTOMATIC");
        System.out.println();
        int choice = readInt("Enter choice: ");
        if (choice == 1) {
            return "MANUAL";
        }
        if (choice == 2) {
            return "AUTOMATIC";
        }
        throw new InvalidInputException("Error: Invalid transmission type. Please select 1 or 2.");
    }

    public String readBikeType() throws InvalidInputException {
        System.out.println();
        System.out.println("Select Bike Type");
        System.out.println();
        System.out.println("1. SPORTS");
        System.out.println("2. CRUISER");
        System.out.println("3. COMMUTER");
        System.out.println("4. SCOOTER");
        System.out.println();
        int choice = readInt("Enter choice: ");
        if (choice == 1) {
            return "SPORTS";
        }
        if (choice == 2) {
            return "CRUISER";
        }
        if (choice == 3) {
            return "COMMUTER";
        }
        if (choice == 4) {
            return "SCOOTER";
        }
        throw new InvalidInputException("Error: Invalid bike type. Please select 1 to 4.");
    }
}
