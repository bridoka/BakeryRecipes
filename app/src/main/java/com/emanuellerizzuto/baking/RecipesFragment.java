package com.emanuellerizzuto.baking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.baking.data.RecipesParcelable;

public class RecipesFragment extends Fragment {

    private RecipesViewModel viewModel;
    private RecyclerView recipesRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    public RecipesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        Context context = getContext();
        recipesRecyclerView = (RecyclerView) view.findViewById(R.id.rv_recipes);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recipesRecyclerView.setLayoutManager(linearLayoutManager);
        final RecipesAdapter adapter = new RecipesAdapter();
        recipesRecyclerView.setAdapter(adapter);
        this.viewModel.getRecipes().observe(this, new Observer<RecipesParcelable>() {
            @Override
            public void onChanged(RecipesParcelable recipesParcelable) {
                adapter.setRecipes(recipesParcelable.getRecipes());
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
    }
}
