package com.od.moviesapp.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favouriteMovie")
public class FavouriteMovie {
    public FavouriteMovie(String _id, String image, String title, String rating, String year, String description) {
        this._id = _id;
        this.image = image;
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.description = description;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "_id")
    private String _id;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "rating")
    private String rating;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "description")
    private String description;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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