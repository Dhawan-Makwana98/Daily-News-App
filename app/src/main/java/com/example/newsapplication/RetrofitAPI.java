package com.example.newsapp;


import com.example.newsapplication.NewsModal;

import retrofit2.http.GET;
import retrofit2.http.Url;
import retrofit2.Call;


public interface RetrofitAPI {
    @GET
    Call<NewsModal> getAllNews(@Url String url);

    @GET
    Call<NewsModal> getNewsByCategory(@Url String url);
}
