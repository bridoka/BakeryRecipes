package com.emanuellerizzuto.baking;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.emanuellerizzuto.baking.data.RecipeStepParcelable;

public class RecipeActivity extends AppCompatActivity
        implements StepsAdapter.OnStepClickListener {

    private RecipeViewModel viewModel;
    private boolean twoPainels = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        FragmentManager fragmentManager = getSupportFragmentManager();

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            twoPainels = findViewById(R.id.two_painels) != null;
        }
        if (savedInstanceState == null) {
            viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
            Intent intent = getIntent();
            RecipeParcelable recipe = intent.getParcelableExtra("recipe");
            if(recipe != null) {
                viewModel.setRecipe(recipe);
            }
            int layoutId = R.id.recipe_container;
            if (twoPainels) {
                layoutId = R.id.recipe_detail_fragment;
                onStepSelected(recipe.getSteps().get(0));
                Fragment fragment = getSupportFragmentManager().findFragmentById(layoutId);
                if (fragment != null) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
            }
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            fragmentManager.beginTransaction()
                    .add(layoutId, recipeDetailFragment)
                    .commit();
        }

    }

    @Override
    public void onStepSelected(RecipeStepParcelable step) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStep(step);
        FragmentManager fragmentManager = getSupportFragmentManager();

        int layoutId = R.id.recipe_container;
        if (twoPainels) {
            layoutId = R.id.step_detail_fragment;
            Fragment fragment = getSupportFragmentManager().findFragmentById(layoutId);
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }
        fragmentManager.beginTransaction()
                .replace(layoutId, stepDetailFragment, null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
