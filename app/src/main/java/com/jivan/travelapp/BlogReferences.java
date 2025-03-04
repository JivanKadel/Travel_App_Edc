package com.jivan.travelapp;

import android.content.Context;
import android.content.SharedPreferences;

public class BlogReferences {
    private static final String KEY_LOGIN_STATE  = "key_login_state";
    private SharedPreferences preferences;
    public BlogReferences(Context context){
        preferences = context.getSharedPreferences("travel-blog", Context.MODE_PRIVATE);
    }
    public boolean isLoggedIn(){
        return preferences.getBoolean(KEY_LOGIN_STATE, false);
    }
    public void setLoggedIn(boolean loggedIn){
        preferences.edit().putBoolean(KEY_LOGIN_STATE, loggedIn).apply();
    }
}
