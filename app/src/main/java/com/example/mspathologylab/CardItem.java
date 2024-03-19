package com.example.mspathologylab;

public class CardItem {

   // Unique ID for each item
    private int imageResource;
    private String title;
    private String subtitle;
    private  String price;

    public CardItem(int imageResource, String title, String subtitle, String price) {
        this.imageResource = imageResource;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
    }



    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPrice() {
        return price;
    }
}


