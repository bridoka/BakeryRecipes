package com.emanuellerizzuto.baking.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStepParcelable implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailUrl;

    public RecipeStepParcelable(int id, String shortDescription, String description, String videoURL, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected RecipeStepParcelable(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<RecipeStepParcelable> CREATOR = new Creator<RecipeStepParcelable>() {
        @Override
        public RecipeStepParcelable createFromParcel(Parcel in) {
            return new RecipeStepParcelable(in);
        }

        @Override
        public RecipeStepParcelable[] newArray(int size) {
            return new RecipeStepParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailUrl);
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
