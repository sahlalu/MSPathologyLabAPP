package com.example.mspathologylab;

public class ShowOrder {

    private String patientName;
    private String contact;
    private String drawn;
    private String email;
    private String orderTime;
    private String tests;
    private boolean expanded;

    public ShowOrder(String patientName, String contact, String drawn, String email, String orderTime, String tests) {
        this.patientName = patientName;
        this.contact = contact;
        this.drawn = drawn;
        this.email = email;
        this.orderTime = orderTime;
        this.tests = tests;
        this.expanded = false;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDrawn() {
        return drawn;
    }

    public void setDrawn(String drawn) {
        this.drawn = drawn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "ShowOrder{" +
                "patientName='" + patientName + '\'' +
                ", contact='" + contact + '\'' +
                ", drawn='" + drawn + '\'' +
                ", email='" + email + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", tests='" + tests + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}
