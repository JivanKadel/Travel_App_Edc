package com.jivan.travelapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jivan.travelapp.Blog;

@Database(entities = {Blog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BlogDAO blogDao();
}
