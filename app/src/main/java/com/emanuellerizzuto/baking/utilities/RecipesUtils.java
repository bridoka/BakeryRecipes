package com.emanuellerizzuto.baking.utilities;

import android.util.Log;

import com.emanuellerizzuto.baking.data.RecipeIngredientParcelable;
import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.emanuellerizzuto.baking.data.RecipeStepParcelable;
import com.emanuellerizzuto.baking.data.RecipesParcelable;
import com.emanuellerizzuto.baking.repository.model.Ingredient;
import com.emanuellerizzuto.baking.repository.model.Recipe;
import com.emanuellerizzuto.baking.repository.model.Step;
import com.emanuellerizzuto.baking.repository.service.RecipesService;

import java.util.ArrayList;
import java.util.List;

public class RecipesUtils {
    public static RecipesParcelable getRecipes() {
        ArrayList<RecipeParcelable> recipeList = new ArrayList<RecipeParcelable>();

        RecipesService recipesService = new RecipesService();
        ArrayList<Recipe> allRecipes = recipesService.getAllRecipes();

        for (int j = 0; j < allRecipes.size(); j++) {

            Recipe recipe = allRecipes.get(j);
            List<Ingredient> ingredientsArray = recipe.getIngredients();
            List<Step> stepsArray = recipe.getSteps();

            ArrayList<RecipeIngredientParcelable> recipeIngredientList = new ArrayList<RecipeIngredientParcelable>();
            for (int i = 0; i < ingredientsArray.size(); i++) {
                Ingredient ingredientData = ingredientsArray.get(i);
                float quantity = ingredientData.getQuantity();
                String measure = ingredientData.getMeasure();
                String ingredient = ingredientData.getIngredient();
                recipeIngredientList.add(new RecipeIngredientParcelable(quantity, measure, ingredient));
            }

            ArrayList<RecipeStepParcelable> recipeStepList = new ArrayList<RecipeStepParcelable>();
            for (int i = 0; i < stepsArray.size(); i++) {
                Step stepData = stepsArray.get(i);
                int id = stepData.getId();
                String shortDescription = stepData.getShortDescription();
                String description = stepData.getDescription();
                String videoURL = stepData.getVideoURL();
                String thumbnail = stepData.getThumbnailURL();
                recipeStepList.add(new RecipeStepParcelable(id, shortDescription, description, videoURL, thumbnail));
            }

            int id = recipe.getId();
            String name = recipe.getName();
            recipeList.add(new RecipeParcelable(id, name, recipeIngredientList, recipeStepList));
        }

        return new RecipesParcelable(recipeList);
    }
}
