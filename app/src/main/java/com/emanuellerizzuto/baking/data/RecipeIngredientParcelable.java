package com.emanuellerizzuto.baking.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredientParcelable implements Parcelable
{
    private float quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredientParcelable(float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected RecipeIngredientParcelable(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeIngredientParcelable> CREATOR = new Creator<RecipeIngredientParcelable>() {
        @Override
        public RecipeIngredientParcelable createFromParcel(Parcel in) {
            return new RecipeIngredientParcelable(in);
        }

        @Override
        public RecipeIngredientParcelable[] newArray(int size) {
            return new RecipeIngredientParcelable[size];
        }
    };

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
