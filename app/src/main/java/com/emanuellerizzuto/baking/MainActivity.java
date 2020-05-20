package com.emanuellerizzuto.baking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.emanuellerizzuto.baking.data.RecipeParcelable;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int container = R.id.container;
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            RecipesFragment recipesFragment = new RecipesFragment();
            fragmentManager.beginTransaction()
                    .add(container, recipesFragment)
                    .commit();
        }
    }

    @Override
    public void onRecipeSelected(RecipeParcelable recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}
