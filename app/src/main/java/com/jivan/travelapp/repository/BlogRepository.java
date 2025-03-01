package com.jivan.travelapp.repository;

import android.content.Context;

import com.jivan.travelapp.Blog;
import com.jivan.travelapp.BlogHttpClient;
import com.jivan.travelapp.database.AppDatabase;
import com.jivan.travelapp.database.BlogDAO;
import com.jivan.travelapp.database.DatabaseProvider;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlogRepository {
    private BlogHttpClient httpClient;
    private AppDatabase database;
    private Executor executor;

    public BlogRepository(Context context){
        httpClient = BlogHttpClient.INSTANCE;
        database = DatabaseProvider.getInstance(context.getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
    }

    public void loadDataFromDatabase(DataFromDatabaseCallback callback){
        executor.execute(()->callback.onSuccess(database.blogDao().getAll()));
    }

    public void loadDataFromNetwork(DataFromNetworkCallback callback){
        executor.execute(()->{
            List<Blog> blogList = httpClient.loadBlogArticles();
            if(blogList==null){
                callback.onError();
            }
            else{
                BlogDAO blogDao = database.blogDao();
                blogDao.deleteAll();
                blogDao.insertAll(blogList);
                callback.onSuccess(blogList);
            }
        });
    }
}
