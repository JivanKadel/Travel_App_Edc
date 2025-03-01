package com.jivan.travelapp.repository;

import com.jivan.travelapp.Blog;

import java.util.List;

public interface DataFromNetworkCallback {
    void onSuccess(List<Blog> blogList);
    void onError();
}
