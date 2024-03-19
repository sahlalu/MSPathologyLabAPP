package com.example.mspathologylab;

public class SVGCardItem {

    private int iconResource;
    private String testName;
    private String price;

    public SVGCardItem(int iconResource, String testName, String price) {
        this.iconResource = iconResource;
        this.testName = testName;
        this.price = price;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getTestName() {
        return testName;
    }

    public String getPrice() {
        return price;
    }
}
