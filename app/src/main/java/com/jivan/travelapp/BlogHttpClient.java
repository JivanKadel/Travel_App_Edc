package com.jivan.travelapp;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BlogHttpClient {
    private Executor executor;
    private OkHttpClient client;
    private Gson gson;
    public static final BlogHttpClient INSTANCE = new BlogHttpClient();

//    private static final String baseUrl = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/";
//
//    private static final String BLOG_ARTICLES_URL = baseUrl + "8550ef2064bf14fcf3b9ff322287a2e056c7e153/blog_articles.json";

//    private static final String BASE_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/";
//    private static final String BLOG_ARTICLES_URL = BASE_URL + "647f4270e4271fbff28f1d80e2f2d12b3bd4a1cd/blog_articles.json";

    public static final String BASE_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources";
    public static final String PATH = "/raw/3eede691af3e8ff795bf6d31effb873d484877be";
    private static final String BLOG_ARTICLES_URL = BASE_URL + PATH + "/blog_articles.json";

    private  BlogHttpClient(){
        executor = Executors.newFixedThreadPool(4);
        client = new OkHttpClient();
        gson = new Gson();
    }
    public void loadBlogArticles(BlogArticlesCallback callback){
        Request request = new Request.Builder().get().url(BLOG_ARTICLES_URL).build();

        executor.execute(()->{
            try {
                Response response = client.newCall(request).execute();
                ResponseBody responseBody = response.body();
                if(responseBody != null){
                    String json = responseBody.string();
                    BlogData blogData = gson.fromJson(json, BlogData.class);
                    if(blogData != null){
                        callback.onSuccess(blogData.getData());
                        return;
                    }
                }
            } catch (IOException e) {
                Log.e("BlogHttpClient", "Error loading blog articles", e);
            }
            callback.onError();
        });
    }
}
