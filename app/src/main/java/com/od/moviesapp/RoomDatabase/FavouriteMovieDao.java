package com.od.moviesapp.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FavouriteMovieDao {

    @Query("SELECT * FROM favouriteMovie")
    LiveData<List<FavouriteMovie>> getAll();

    @Query("SELECT * FROM favouriteMovie where title LIKE  :title")
    FavouriteMovie findByTitle(String title);

    @Query("SELECT COUNT(*) from favouriteMovie")
    int countMovies();

    @Insert
    void insertAll(FavouriteMovie... movies);

    @Delete
    void delete(FavouriteMovie movie);
}