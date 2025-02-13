package com.jivan.travelapp;

public class Author {
    private String name;
    private String avatar;

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
    public String getAvatarUrl(){
        return BlogHttpClient.BASE_URL + BlogHttpClient.PATH + getAvatar();
    }
}
