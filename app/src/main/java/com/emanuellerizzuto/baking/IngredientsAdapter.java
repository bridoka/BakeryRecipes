package com.emanuellerizzuto.baking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.baking.data.RecipeIngredientParcelable;
import com.emanuellerizzuto.baking.data.RecipeParcelable;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder>
{
    private List<RecipeIngredientParcelable> ingredients;

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView tv_ingredient;
        public final TextView tv_ingredient_quantity;
        public final TextView tv_ingredient_measure;

        public IngredientsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ingredient = itemView.findViewById(R.id.tv_ingredient);
            tv_ingredient_quantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            tv_ingredient_measure = itemView.findViewById(R.id.tv_ingredient_measure);
        }
    }

    @NonNull
    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdItem = R.layout.row_ingredient;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdItem, parent, false);
        return new IngredientsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapterViewHolder holder, int position) {
        RecipeIngredientParcelable ingredient = ingredients.get(position);
        String description = ingredient.getIngredient();
        String quantity = String.valueOf(ingredient.getQuantity());
        String measure = ingredient.getMeasure();

        holder.tv_ingredient.setText(description);
        holder.tv_ingredient_quantity.setText(quantity);
        holder.tv_ingredient_measure.setText(measure);
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) {
            return 0;
        }
        return ingredients.size();
    }

    public void setIngredients(List<RecipeIngredientParcelable> ingredientsList) {
        ingredients = ingredientsList;
        notifyDataSetChanged();
    }
}
