package com.jivan.travelapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
public class Blog implements Parcelable {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yy");
    @PrimaryKey
   @NonNull private String id;
    private String title;
    private String date;
    private int views;
    private float rating;
    private String image;
    private String description;
    @Embedded
    private Author author;

    public Blog(String id, String title, String date, int views, float rating, String image, String description, Author author) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.views = views;
        this.rating = rating;
        this.image = image;
        this.description = description;
        this.author = author;
    }

    protected Blog(Parcel in) {
        id = in.readString();
        title = in.readString();
        date = in.readString();
        views = in.readInt();
        rating = in.readFloat();
        image = in.readString();
        description = in.readString();
        author = in.readParcelable(Author.class.getClassLoader());
    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeInt(views);
        parcel.writeFloat(rating);
        parcel.writeString(image);
        parcel.writeString(description);
        parcel.writeParcelable(author, i);
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public Long getDateMills() {
        try {
            Date date = dateFormat.parse(getDate());
            return date != null ? date.getTime() : null;
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
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

    public int hashCode() {
        return Objects.hash(id, author, title, date, image, description, views, rating);
    }

}
