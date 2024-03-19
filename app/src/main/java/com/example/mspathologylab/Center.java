package com.example.mspathologylab;


public class Center {

    private String center;
    private String contact;
    private String opentill;
    private String address;
    private boolean expanded;

    public Center(String center, String contact, String opentill, String address) {
        this.center = center;
        this.contact = contact;
        this.opentill = opentill;
        this.address = address;
        this.expanded = false;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOpentill() {
        return opentill;
    }

    public void setOpentill(String opentill) {
        this.opentill = opentill;
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
                "center='" + center + '\'' +
                ", contact='" + contact + '\'' +
                ", opentill='" + opentill + '\'' +
                ", address='" + address + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}
