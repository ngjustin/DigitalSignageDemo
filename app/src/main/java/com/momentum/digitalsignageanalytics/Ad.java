package com.momentum.digitalsignageanalytics;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Justin on 10/11/2016.
 */

public class Ad implements Parcelable {
    private String title;
    private String details;
    private String description;
    private String link;
    private String thumbnail;
    private String picture;

    public Ad(String title, String details, String description, String link, String thumbnail, String picture) {
        this.title = title;
        this.details = details;
        this.description = description;
        this.link = link;
        this.thumbnail = thumbnail;
        this.picture = picture;
    }

    public Ad(Parcel in) {
        title = in.readString();
        details = in.readString();
        description = in.readString();
        link = in.readString();
        thumbnail = in.readString();
        picture = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(thumbnail);
        dest.writeString(picture);
    }

    public static final Parcelable.Creator<Ad> CREATOR = new Parcelable.Creator<Ad>() {
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }

        public Ad[] newArray(int size) {
            return new Ad[size];

        }
    };

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) { this.picture = picture; }
}