package com.jivan.travelapp;

public class Blog {
    private String title;
    private String date;
    private int views;
    private float rating;
    private String image;
    private String description;
    private Author author;

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public int getViews() {
        return views;
    }
    public float getRating() {
        return rating;
    }
    public String getImage() {
        return image;
    }
    public String getImageUrl(){
        return BlogHttpClient.BASE_URL + BlogHttpClient.PATH + getImage();
    }
    public String getDescription() {
        return description;
    }
    public Author getAuthor(){
        return author;
    }
}
