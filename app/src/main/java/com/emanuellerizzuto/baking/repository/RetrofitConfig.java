package com.emanuellerizzuto.baking.repository;

import com.emanuellerizzuto.baking.repository.model.Recipe;
import com.emanuellerizzuto.baking.repository.service.RecipesService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig
{
    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
