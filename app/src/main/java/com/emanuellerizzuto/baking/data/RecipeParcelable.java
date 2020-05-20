package com.emanuellerizzuto.baking.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeParcelable implements Parcelable {

    private int id;
    private String name;
    private ArrayList<RecipeIngredientParcelable> ingredients;
    private ArrayList<RecipeStepParcelable> steps;

    public RecipeParcelable(int id, String name,
                            ArrayList<RecipeIngredientParcelable> ingredients,
                            ArrayList<RecipeStepParcelable> steps) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    protected RecipeParcelable(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(RecipeIngredientParcelable.CREATOR);
        steps = in.createTypedArrayList(RecipeStepParcelable.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeParcelable> CREATOR = new Creator<RecipeParcelable>() {
        @Override
        public RecipeParcelable createFromParcel(Parcel in) {
            return new RecipeParcelable(in);
        }

        @Override
        public RecipeParcelable[] newArray(int size) {
            return new RecipeParcelable[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<RecipeIngredientParcelable> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStepParcelable> getSteps() {
        return steps;
    }
}
