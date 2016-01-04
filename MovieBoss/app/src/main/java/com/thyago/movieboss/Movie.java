package com.thyago.movieboss;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by thyago on 1/3/16.
 */
public class Movie implements Parcelable {
    private String title;
    private String originalTitle;
    private double rating;
    private Date releaseDate;
    private String plot;
    private String posterURL;

    public Movie() {

    }

    protected Movie(Parcel in) {
        title = in.readString();
        originalTitle = in.readString();
        rating = in.readDouble();
        plot = in.readString();
        posterURL = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPosterURL(String image) {
        this.posterURL = image;
    }

    public String getPosterURL() {
        return posterURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeDouble(rating);
        dest.writeString(plot);
        dest.writeString(posterURL);
    }
}
