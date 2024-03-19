package com.example.mspathologylab;


public class HomeVisit {

    private String name;
    private String contact;
    private String email;
    private String address;
    private boolean expanded;

    public HomeVisit(String name, String contact, String email, String address) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.expanded = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "Center{" +
                "center='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", opentill='" + email + '\'' +
                ", address='" + address + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}
