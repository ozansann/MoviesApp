package com.od.moviesapp.Models;

import java.io.Serializable;

public class MovieModel implements Serializable {
    private String _id;
    private String image;
    private String title;
    private String rating;
    private String year;
    private String description;

    public MovieModel(String _id, String image, String title, String rating, String year, String description) {
        this._id = _id;
        this.image = image;
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.description = description;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}