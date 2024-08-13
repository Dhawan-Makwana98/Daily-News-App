package com.example.newsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.RetrofitAPI;
import com.example.newsapplication.Articles;
import com.example.newsapplication.CategoryRVAdapter;
import com.example.newsapplication.CategoryRVModal;
import com.example.newsapplication.NewsRVAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalsArrayList;
    private NewsRVAdapter newsRVAdapter;
    private CategoryRVAdapter categoryRVAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalsArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalsArrayList, this, this::onDeveloperClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategory();
        getNews("All");

        newsRVAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getCategory() {
        categoryRVModalsArrayList.add(new CategoryRVModal("All", "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryRVModalsArrayList.add(new CategoryRVModal("Technology", "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryRVModalsArrayList.add(new CategoryRVModal("Science", "https://plus.unsplash.com/premium_photo-1676325102583-0839e57d7a1f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryRVModalsArrayList.add(new CategoryRVModal("Sports", "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryRVModalsArrayList.add(new CategoryRVModal("General", "https://images.unsplash.com/photo-1499750310107-5fef28a66643?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryRVModalsArrayList.add(new CategoryRVModal("Business", "https://images.unsplash.com/photo-1504711434969-e33886168f5c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryRVModalsArrayList.add(new CategoryRVModal("Entertainment", "https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8fHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));
        categoryRVModalsArrayList.add(new CategoryRVModal("Health", "https://images.unsplash.com/photo-1447452001602-7090c7ab2db3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"));

        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apiKey=109358310bf14ce5b0ec3cf60aa41a61";
        String url = "https://newsapi.org/v2/everything?q=india&from=2024-07-15&sortBy=publishedAt&apiKey=109358310bf14ce5b0ec3cf60aa41a61";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofit1 = retrofit.create(RetrofitAPI.class);
        com.example.newsapp.RetrofitAPI retrofitAPI = retrofit.create(com.example.newsapp.RetrofitAPI.class);

        Call<NewsModal> call;
        call = retrofitAPI.getAllNews(categoryURL);

        if (category.equals("All")){
            call= retrofitAPI.getAllNews(url);
        }
        else{
            call= retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<NewsModal> call, @NonNull Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                assert newsModal != null;
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(
                            articles.get(i).getTitle(),
                            articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),
                            articles.get(i).getContent()
                    ));
                }

                newsRVAdapter.notifyDataSetChanged();

             }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDeveloperClick(int position) {
        String category = categoryRVModalsArrayList.get(position).getCategory();
        getNews(category);
    }
}