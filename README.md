# idlewheels
# Idle Wheels – Vehicle Sharing and Rental System

## Project Overview

**Idle Wheels** is a Core Java console-based vehicle sharing and rental application.

The system allows **vehicle owners to register and list their cars and bikes**, while **customers can browse, search, rent, and return available vehicles**.

An administrator can view information about owners, customers, vehicles, and rentals.

The project is developed using **Core Java and Object-Oriented Programming concepts**, with data stored temporarily using Java Collections.

---

## Problem Statement

Vehicle owners may have cars or bikes that remain unused for long periods. At the same time, customers may need vehicles for short-term transportation.

**Idle Wheels** provides a simple platform where:

* Owners can list their vehicles.
* Customers can find available vehicles.
* Customers can rent vehicles for a specific number of days.
* Customers can return rented vehicles.
* Owners can view their rental history and earnings.
* Admin can monitor the overall system.

---

## Objective

The main objectives of Idle Wheels are:

* Provide a simple vehicle-sharing platform.
* Allow owners to manage their vehicles.
* Allow customers to search and rent vehicles.
* Track vehicle availability.
* Maintain rental records.
* Calculate rental charges automatically.
* Maintain rental history.
* Calculate owner earnings.
* Demonstrate Core Java and OOP concepts in a real-world project.

---

# Features

## Owner Features

Owners can:

* Register as an owner.
* Add cars and bikes.
* View their vehicles.
* View available vehicles.
* Update vehicle details.
* Remove vehicles.
* View rental history of their vehicles.
* View total earnings from completed rentals.

### Owner Rules

* Every vehicle must belong to an owner.
* An owner can manage only their own vehicles.
* An owner cannot rent their own vehicle.
* A rented vehicle cannot be removed.
* Vehicle ID and registration number must be unique.

---

## Customer Features

Customers can:

* Register as a customer.
* View available cars.
* View available bikes.
* Search vehicles.
* View vehicle details.
* Rent a vehicle.
* Return a vehicle.
* View active rentals.
* View rental history.

### Customer Rental Rules

* Only available vehicles can be rented.
* Rental days must be greater than zero.
* A customer cannot rent their own vehicle.
* Once rented, the vehicle becomes unavailable.
* After returning, the vehicle becomes available again.
* Completed rentals remain in rental history.

---

## Admin Features

Admin can:

* View all owners.
* View all customers.
* View all vehicles.
* View available vehicles.
* View rented vehicles.
* View all rentals.
* View active rentals.
* View completed rentals.

---

# Project Structure

```text
Idle-Wheels/
│
├── src/
│   └── idlewheels/
│       │
│       ├── model/
│       │   ├── User.java
│       │   ├── Owner.java
│       │   ├── Customer.java
│       │   ├── Vehicle.java
│       │   ├── Car.java
│       │   ├── Bike.java
│       │   └── Rental.java
│       │
│       ├── interfaces/
│       │   └── Rentable.java
│       │
│       ├── service/
│       │   ├── OwnerService.java
│       │   ├── CustomerService.java
│       │   ├── VehicleService.java
│       │   └── RentalService.java
│       │
│       ├── exception/
│       │   ├── OwnerNotFoundException.java
│       │   ├── CustomerNotFoundException.java
│       │   ├── VehicleNotFoundException.java
│       │   ├── VehicleNotAvailableException.java
│       │   ├── RentalNotFoundException.java
│       │   └── InvalidInputException.java
│       │
│       ├── util/
│       │   └── InputUtil.java
│       │
│       └── main/
│           └── IdleWheelsApplication.java
│
├── README.md
├── .gitignore
└── LICENSE
```

---

# System Architecture

The application follows a simple layered structure:

```text
                 Idle Wheels
                      │
                      ▼
          IdleWheelsApplication
                      │
                      ▼
                Service Layer
       ┌────────┬─────────┬─────────┐
       ▼        ▼         ▼         ▼
     Owner   Customer   Vehicle   Rental
     Service Service    Service   Service
       │        │         │         │
       └────────┴─────────┴─────────┘
                      │
                      ▼
                Model Classes
                      │
                      ▼
              Java Collections
             ArrayList / HashMap
```

---

# Class Relationships

## User Hierarchy

```text
              User
             /    \
            /      \
        Owner      Customer
```

`User` is an abstract class containing common user information.

`Owner` and `Customer` extend the `User` class.

---

## Vehicle Hierarchy

```text
             Vehicle
             /     \
            /       \
          Car       Bike
```

`Vehicle` is an abstract class containing common vehicle information.

`Car` and `Bike` extend `Vehicle`.

---

# Object-Oriented Programming Concepts

## 1. Encapsulation

Data members are kept private and accessed through getters and setters.

Example:

```java
private String name;

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}
```

---

## 2. Inheritance

Common properties and behavior are reused through inheritance.

```text
User
 ├── Owner
 └── Customer
```

and:

```text
Vehicle
 ├── Car
 └── Bike
```

---

## 3. Abstraction

Abstract classes are used to represent common concepts.

```java
abstract class User
```

and:

```java
abstract class Vehicle
```

---

## 4. Polymorphism

A parent reference can refer to a child object.

```java
Vehicle vehicle = new Car();
```

or:

```java
Vehicle vehicle = new Bike();
```

---

## 5. Interface

The `Rentable` interface defines rental-related behavior.

```java
public interface Rentable {

    void rent();

    void returnVehicle();

    double calculateRent(int days);
}
```

The `Vehicle` class implements this interface.

---

# Collections Used

The project uses Java Collections to store and manage application data.

## ArrayList

Used when maintaining lists of objects such as:

```java
List<Vehicle>
List<Rental>
List<Owner>
List<Customer>
```

## HashMap

Used for quick lookup using unique IDs.

Examples:

```java
Map<String, Owner>
Map<String, Customer>
Map<String, Vehicle>
Map<String, Rental>
```

For example:

```text
Vehicle ID → Vehicle Object

C101 → Hyundai Creta
C102 → Toyota Innova
B101 → Royal Enfield
```

---

# Exception Handling

The application uses custom exceptions to handle business-related errors.

Custom exceptions include:

* `OwnerNotFoundException`
* `CustomerNotFoundException`
* `VehicleNotFoundException`
* `VehicleNotAvailableException`
* `RentalNotFoundException`
* `InvalidInputException`

Example:

```java
throw new VehicleNotAvailableException(
    "Vehicle is currently not available"
);
```

This makes error handling more meaningful and easier to understand.

---

# Rental Flow

The basic rental process is:

```text
Customer
    │
    ▼
Select Vehicle
    │
    ▼
Check Customer
    │
    ▼
Check Vehicle
    │
    ▼
Check Availability
    │
    ▼
Check Owner
    │
    ▼
Enter Number of Days
    │
    ▼
Calculate Rent
    │
    ▼
Create Rental
    │
    ▼
Vehicle → Unavailable
    │
    ▼
Rental Status → ACTIVE
```

---

# Return Flow

```text
Customer
    │
    ▼
Enter Rental ID
    │
    ▼
Find Rental
    │
    ▼
Verify Customer
    │
    ▼
Check Rental Status
    │
    ▼
Complete Rental
    │
    ├── Rental → COMPLETED
    │
    └── Vehicle → AVAILABLE
```

---

# Rental Calculation

Rental amount is calculated based on:

```text
Total Rent = Rent Per Day × Number Of Days
```

### Example

```text
Vehicle: Hyundai Creta
Rent Per Day: ₹2200
Number of Days: 2

Total Rent = 2200 × 2
           = ₹4400
```

---

# Owner Earnings

Owner earnings are calculated from completed rentals of vehicles owned by that owner.

```text
Owner Earnings
      =
Sum of completed rental amounts
```

Active rentals are not included until the rental is completed.

---

# Sample Data

The application contains sample owners, customers, and vehicles.

### Owners

| ID   | Name   |
| ---- | ------ |
| O101 | Ramesh |
| O102 | Suresh |
| O103 | Priya  |

### Customers

| ID    | Name   |
| ----- | ------ |
| CU101 | Rahul  |
| CU102 | Kiran  |
| CU103 | Anjali |

### Vehicles

| ID   | Vehicle                   | Type | Rent/Day |
| ---- | ------------------------- | ---- | -------: |
| C101 | Hyundai Creta             | Car  |    ₹2200 |
| C102 | Toyota Innova             | Car  |    ₹2500 |
| C103 | Tata Nexon                | Car  |    ₹1800 |
| B101 | Royal Enfield Classic 350 | Bike |     ₹900 |
| B102 | Yamaha R15                | Bike |    ₹1000 |
| B103 | Honda Activa              | Bike |     ₹500 |

---

# Main Menu

```text
========================================
          IDLE WHEELS
========================================

1. Owner
2. Customer
3. Admin
0. Exit
```

---

# Owner Dashboard

```text
1. Add Vehicle
2. View My Vehicles
3. View Available My Vehicles
4. Update My Vehicle
5. Remove My Vehicle
6. View My Rental History
7. View My Earnings
0. Logout
```

---

# Customer Dashboard

```text
1. View Available Cars
2. View Available Bikes
3. Search Vehicles
4. View Vehicle Details
5. Rent Vehicle
6. Return Vehicle
7. View Active Rentals
8. View Rental History
0. Logout
```

---

# Admin Dashboard

```text
1. View All Owners
2. View All Customers
3. View All Vehicles
4. View Available Vehicles
5. View Rented Vehicles
6. View All Rentals
7. View Active Rentals
8. View Completed Rentals
0. Logout
```

---

# Technologies Used

* **Java**
* **Core Java**
* **Object-Oriented Programming**
* **Java Collections Framework**
* **ArrayList**
* **HashMap**
* **Exception Handling**
* **Scanner**
* **File/Package Organization**

No external frameworks or libraries are required.

---

# Current Limitations

This version is intentionally implemented as a Core Java console application.

* No database
* No web application
* No REST API
* No Spring Framework
* No external libraries
* Data is stored in memory using Java Collections.
* Data is lost when the application is stopped.

---

# How to Run

## Prerequisites

Install:

* Java JDK 8 or higher
* Git (optional, for GitHub)

Check Java installation:

```bash
java -version
```

Check compiler:

```bash
javac -version
```

---

## Compile

From the project root:

```bash
javac -d out src/idlewheels/*/*.java
```

---

## Run

```bash
java -cp out idlewheels.main.IdleWheelsApplication
```

---

# Example Execution

```text
========================================
          IDLE WHEELS
========================================

1. Owner
2. Customer
3. Admin
0. Exit

Enter choice: 2

========== CUSTOMER LOGIN ==========

Enter Customer ID: CU101

Welcome Rahul!

1. View Available Cars
2. View Available Bikes
3. Search Vehicles
4. View Vehicle Details
5. Rent Vehicle
6. Return Vehicle
7. View Active Rentals
8. View Rental History
0. Logout

Enter choice: 5

Enter Vehicle ID: C101
Enter Number of Days: 2

Vehicle rented successfully!

Vehicle       : Hyundai Creta
Rental ID     : R1001
Days          : 2
Rent Per Day  : ₹2200
Total Amount  : ₹4400
Status        : ACTIVE
```

After returning:

```text
Vehicle returned successfully!

Rental ID : R1001
Vehicle   : Hyundai Creta
Amount    : ₹4400
Status    : COMPLETED
```

---

# Future Enhancements

The following features can be added in future versions:

* Database integration using MySQL
* User authentication and password management
* Online payment integration
* Vehicle images
* Location-based vehicle search
* Vehicle ratings and reviews
* Date-based vehicle booking
* Email/SMS notifications
* Web application
* Mobile application
* Owner verification
* Driving-license/document verification
* Advanced booking and cancellation system

---

# What This Project Demonstrates

This project demonstrates practical understanding of:

* Classes and Objects
* Constructors
* Encapsulation
* Inheritance
* Abstraction
* Polymorphism
* Interfaces
* Method Overloading/Overriding
* ArrayList
* HashMap
* CRUD Operations
* Searching
* Exception Handling
* Input Validation
* Service Layer Design
* Real-world Business Logic

---

### Project

**Idle Wheels – Vehicle Sharing and Rental System**

Built using **Core Java** for learning and demonstrating object-oriented programming and Java Collections.
