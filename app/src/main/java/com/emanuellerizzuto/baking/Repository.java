package com.emanuellerizzuto.baking;

import com.emanuellerizzuto.baking.data.RecipesParcelable;
import com.emanuellerizzuto.baking.utilities.AppExecutors;
import com.emanuellerizzuto.baking.utilities.RecipesUtils;

public class Repository
{
    public RecipesParcelable getRecipes() {
        return RecipesUtils.getRecipes();
    }
}
