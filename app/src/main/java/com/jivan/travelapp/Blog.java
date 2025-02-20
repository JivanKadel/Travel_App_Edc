package com.jivan.travelapp;

import java.util.Objects;

public class Blog {
    private String id;
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

    public String getImageUrl() {
        return BlogHttpClient.BASE_URL + BlogHttpClient.PATH + getImage();
    }

    public String getDescription() {
        return description;
    }

    public Author getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blog blog = (Blog) o;

        return views == blog.views && Float.compare(blog.rating, rating) == 0
                && Objects.equals(id, blog.id)
                && Objects.equals(author, blog.author)
                && Objects.equals(title, blog.title)
                && Objects.equals(date, blog.date)
                && Objects.equals(image, blog.image)
                && Objects.equals(description, blog.description);
    }

    public int hashCode(){
        return Objects.hash(id, author, title, date, image, description, views, rating);
    }
}
