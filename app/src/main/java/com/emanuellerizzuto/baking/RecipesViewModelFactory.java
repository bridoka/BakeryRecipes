package com.emanuellerizzuto.baking;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RecipesViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    public RecipesViewModelFactory(){

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipesViewModel();
    }
}
