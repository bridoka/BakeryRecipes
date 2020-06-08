package com.emanuellerizzuto.baking.repository.service;

import android.util.Log;

import com.emanuellerizzuto.baking.repository.RetrofitConfig;
import com.emanuellerizzuto.baking.repository.model.Recipe;
import com.emanuellerizzuto.baking.repository.model.Recipes;
import com.emanuellerizzuto.baking.utilities.AppExecutors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class RecipesService {
    public interface GetRecipes {
        @GET("topher/2017/May/59121517_baking/baking.json")
        Call<ArrayList<Recipe>> getRecipes();
    }

    public ArrayList<Recipe> getAllRecipes() {
        RetrofitConfig retrofitConfig = new RetrofitConfig();
        Retrofit retrofit = retrofitConfig.getRetrofit();
        GetRecipes getRecipes = retrofit.create(GetRecipes.class);
        final Call<ArrayList<Recipe>> recipe = getRecipes.getRecipes();
        try {
            return recipe.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}