package com.emanuellerizzuto.baking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder>
{
    private List<RecipeParcelable> recipes;

    OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener {
        void onRecipeSelected(RecipeParcelable position);
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView tv_recipe;
        public final MaterialCardView card_recipe;
        public RecipesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_recipe = itemView.findViewById(R.id.tv_recipe);
            card_recipe = itemView.findViewById(R.id.card_recipe);
        }
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mCallback = (OnRecipeClickListener) context;
        int layoutIdItem = R.layout.row_recipe;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdItem, parent, false);
        return new RecipesAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        String text = recipes.get(position).getName();
        holder.tv_recipe.setText(text);
        holder.card_recipe.setTag(recipes.get(position));
        holder.card_recipe.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onRecipeSelected((RecipeParcelable) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }

    public void setRecipes(List<RecipeParcelable> recipesList) {
        recipes = recipesList;
        notifyDataSetChanged();
    }
}
