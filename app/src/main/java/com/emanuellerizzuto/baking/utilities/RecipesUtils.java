package com.emanuellerizzuto.baking.utilities;

import android.util.Log;

import com.emanuellerizzuto.baking.data.RecipeIngredientParcelable;
import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.emanuellerizzuto.baking.data.RecipeStepParcelable;
import com.emanuellerizzuto.baking.data.RecipesParcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class RecipesUtils {
    public static RecipesParcelable getRecipes() {
        ArrayList<RecipeParcelable> recipeList = new ArrayList<RecipeParcelable>();
        try {
            String response = NetworkUtils.getRecipes();
            if (response == null) {
                return null;
            }
            JSONArray jsonArray = new JSONArray(response);

            for (int j = 0; j < jsonArray.length(); j++) {

                JSONObject recipe =  jsonArray.getJSONObject(j);
                JSONArray ingredientsArray = recipe.getJSONArray("ingredients");
                JSONArray stepsArray = recipe.getJSONArray("steps");

                ArrayList<RecipeIngredientParcelable> recipeIngredientList = new ArrayList<RecipeIngredientParcelable>();
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    JSONObject ingredientData = ingredientsArray.getJSONObject(i);
                    int quantity = ingredientData.getInt("quantity");
                    String measure = ingredientData.getString("measure");
                    String ingredient = ingredientData.getString("ingredient");
                    recipeIngredientList.add(new RecipeIngredientParcelable(quantity, measure, ingredient));
                }

                ArrayList<RecipeStepParcelable> recipeStepList = new ArrayList<RecipeStepParcelable>();
                for (int i = 0; i < stepsArray.length(); i++) {
                    JSONObject stepData = stepsArray.getJSONObject(i);
                    int id = stepData.getInt("id");
                    String shortDescription = stepData.getString("shortDescription");
                    String description = stepData.getString("description");
                    String videoURL = stepData.getString("videoURL");
                    String thumbnail = (stepData.has("thumbnail") && !stepData.isNull("thumbnail"))
                            ? stepData.getString("thumbnail")
                            : null;
                    recipeStepList.add(new RecipeStepParcelable(id, shortDescription, description, videoURL, thumbnail));
                }

                int id = recipe.getInt("id");
                String name = recipe.getString("name");
                recipeList.add(new RecipeParcelable(id, name, recipeIngredientList, recipeStepList));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return new RecipesParcelable(recipeList);
    }
}
