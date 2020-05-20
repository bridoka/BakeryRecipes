package com.emanuellerizzuto.baking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.emanuellerizzuto.baking.data.RecipesParcelable;
import com.emanuellerizzuto.baking.utilities.AppExecutors;

public class RecipesViewModel extends ViewModel {

    private MutableLiveData<RecipesParcelable> recipes = new MutableLiveData<RecipesParcelable>();
    private RecipeParcelable recipe;

    public RecipesViewModel() {
        setRecipes();
    }

    private Repository getRepository() {
        return new Repository();
    }

    private void setRecipes() {
        AppExecutors.getInstance().networkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        recipes.postValue(getRepository().getRecipes());
                    }
                }

        );
    }

    public LiveData<RecipesParcelable> getRecipes() {
        return this.recipes;
    }

    public RecipeParcelable getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeParcelable recipe) {
        this.recipe = recipe;
    }
}
