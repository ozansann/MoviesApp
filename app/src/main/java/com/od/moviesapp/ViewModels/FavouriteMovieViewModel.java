package com.od.moviesapp.ViewModels;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.od.moviesapp.RoomDatabase.AppRoomDatabase;
import com.od.moviesapp.RoomDatabase.FavouriteMovie;
import java.util.List;

public class FavouriteMovieViewModel extends AndroidViewModel {
    private LiveData<List<FavouriteMovie>> favouriteMovieList;
    AppRoomDatabase appRoomDatabase;

    public FavouriteMovieViewModel(@NonNull Application application) {
        super(application);
    }

    public void initializeDatabase(Context context){
        appRoomDatabase = AppRoomDatabase.getAppDatabase(context);
    }

    public LiveData<List<FavouriteMovie>> getAllFavourites() {
        this.favouriteMovieList = appRoomDatabase.favouriteMovieDao().getAll();
        return this.favouriteMovieList;
    }

    public FavouriteMovie addMovie(FavouriteMovie movie) {
        appRoomDatabase.favouriteMovieDao().insertAll(movie);
        return movie;
    }

    public FavouriteMovie deleteMovie(FavouriteMovie movie) {
        appRoomDatabase.favouriteMovieDao().delete(movie);
        return movie;
    }
}