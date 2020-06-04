package com.emanuellerizzuto.baking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.emanuellerizzuto.baking.data.RecipesParcelable;

public class RecipeDetailFragment extends Fragment {

    private RecipeParcelable recipe;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;
    private RecipeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        Context context = getContext();
        TextView title = view.findViewById(R.id.title_recipe);
        title.setText(viewModel.getRecipe().getName());
        setupIngredientsRecyclerView(view,context);
        setupStepsRecyclerView(view, context);
        return view;
    }

    private void setupIngredientsRecyclerView(View view, Context context) {
        recipe = viewModel.getRecipe();
        ingredientsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_ingredients);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.setIngredients(recipe.getIngredients());
    }

    private void setupStepsRecyclerView(View view, Context context) {
        recipe = viewModel.getRecipe();
        stepsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_steps);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
        StepsAdapter stepsAdapter = new StepsAdapter();
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setSteps(recipe.getSteps());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
    }
}
