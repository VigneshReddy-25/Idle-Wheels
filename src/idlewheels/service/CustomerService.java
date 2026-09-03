package idlewheels.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idlewheels.exception.CustomerNotFoundException;
import idlewheels.exception.InvalidInputException;
import idlewheels.model.Customer;
import idlewheels.model.Rental;

public class CustomerService {

    private final Map<String, Customer> customersById = new HashMap<>();
    private final List<Customer> customers = new ArrayList<>();
    private int nextCustomerNumber = 104;

    public Customer registerCustomer(String name, String phoneNumber, String email, String drivingLicenseNumber)
            throws InvalidInputException {
        validateCustomerFields(name, phoneNumber, email, drivingLicenseNumber);
        String customerId = "CU" + nextCustomerNumber++;
        Customer customer = new Customer(customerId, name.trim(), phoneNumber.trim(),
                email.trim(), drivingLicenseNumber.trim());
        customersById.put(customerId, customer);
        customers.add(customer);
        return customer;
    }

    public void addExistingCustomer(Customer customer) throws InvalidInputException {
        if (customer == null) {
            throw new InvalidInputException("Error: Customer cannot be null.");
        }
        if (customersById.containsKey(customer.getUserId())) {
            throw new InvalidInputException("Error: Customer ID already exists: " + customer.getUserId());
        }
        customersById.put(customer.getUserId(), customer);
        customers.add(customer);
    }

    public Customer getCustomerById(String customerId) throws CustomerNotFoundException {
        Customer customer = customersById.get(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Rental> getCustomerRentals(String customerId) throws CustomerNotFoundException {
        Customer customer = getCustomerById(customerId);
        return customer.getRentals();
    }

    private void validateCustomerFields(String name, String phoneNumber, String email, String drivingLicenseNumber)
            throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Error: Name cannot be empty.");
        }
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new InvalidInputException("Error: Phone number must be exactly 10 digits.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Error: Please enter a valid email address.");
        }
        if (drivingLicenseNumber == null || drivingLicenseNumber.trim().isEmpty()) {
            throw new InvalidInputException("Error: Driving license number cannot be empty.");
        }
    }
}
