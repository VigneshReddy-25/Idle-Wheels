package idlewheels.model;

public abstract class User {

    private final String userId;
    private String name;
    private String phoneNumber;
    private String email;

    protected User(String userId, String name, String phoneNumber, String email) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public abstract String getUserRole();

    public void displayDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("User ID          : " + userId);
        System.out.println("Role             : " + getUserRole());
        System.out.println("Name             : " + name);
        System.out.println("Phone            : " + phoneNumber);
        System.out.println("Email            : " + email);
        printExtraDetails();
        System.out.println("--------------------------------------------------");
    }

    protected abstract void printExtraDetails();

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return userId + " - " + name;
    }
}
