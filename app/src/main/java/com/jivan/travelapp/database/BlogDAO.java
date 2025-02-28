package com.jivan.travelapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jivan.travelapp.Blog;

import java.util.List;

@Dao
public interface BlogDAO {

    @Query("Select * from blog")
    List<Blog>  getAll();

    @Insert
    void insertAll(List<Blog> blogList);

    @Query("Delete from blog")
    void deleteAll();
}
