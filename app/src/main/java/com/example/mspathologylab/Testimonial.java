package com.example.mspathologylab;

public class Testimonial {

    private int roundimage;
    private String comment;
    private String name;
    private int stars;

    public Testimonial(int roundimage, String comment, String name, int stars) {
        this.roundimage = roundimage;
        this.comment = comment;
        this.name = name;
        this.stars = stars;
    }
    public  int getRoundimage() {return roundimage; }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }
}