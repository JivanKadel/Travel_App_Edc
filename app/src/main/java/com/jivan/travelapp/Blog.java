package com.jivan.travelapp;

public class Blog {
    private String title;
    private String date;
    private String views;
    private String rating;
    private String image;
    private String description;
    private Author author;

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getViews() {
        return views;
    }
    public String getRating() {
        return rating;
    }
    public String getImage() {
        return image;
    }
    public String getDescription() {
        return description;
    }
    public Author getAuthor(){
        return author;
    }
}
