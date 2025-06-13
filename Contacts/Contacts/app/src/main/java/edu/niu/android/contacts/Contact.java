// Contact.java
package edu.niu.android.contacts;

public class Contact {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long phone;

    public Contact(long id, String firstName, String lastName, String email, long phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    // Format phone number for display
    public String getFormattedPhone() {
        return String.format("(%d) %d-%d", phone / 10000000, (phone / 10000) % 1000, phone % 10000);
    }
}
