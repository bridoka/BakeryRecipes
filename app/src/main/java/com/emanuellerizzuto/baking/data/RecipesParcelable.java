package com.emanuellerizzuto.baking.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipesParcelable implements Parcelable {

    ArrayList<RecipeParcelable> recipes;

    public RecipesParcelable(ArrayList<RecipeParcelable> recipes) {
        this.recipes = recipes;
    }

    protected RecipesParcelable(Parcel in) {
        recipes = in.createTypedArrayList(RecipeParcelable.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(recipes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipesParcelable> CREATOR = new Creator<RecipesParcelable>() {
        @Override
        public RecipesParcelable createFromParcel(Parcel in) {
            return new RecipesParcelable(in);
        }

        @Override
        public RecipesParcelable[] newArray(int size) {
            return new RecipesParcelable[size];
        }
    };

    public ArrayList<RecipeParcelable> getRecipes() {
        return recipes;
    }
}
