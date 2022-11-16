package com.od.moviesapp.ViewModels;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.od.moviesapp.Models.MovieModel;
import com.od.moviesapp.Repositories.MovieRepository;
import com.od.moviesapp.RoomDatabase.AppRoomDatabase;
import com.od.moviesapp.RoomDatabase.FavouriteMovie;
import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieResponse;
    private LiveData<ArrayList<MovieModel>> movieResponseLiveData;
    static AppRoomDatabase appRoomDatabase;
    private LiveData<List<FavouriteMovie>> favouriteMovieList;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieResponse = new MovieRepository();
    }

    public void initializeDatabase(Context context){
        appRoomDatabase = AppRoomDatabase.getAppDatabase(context);
    }

    public LiveData<List<FavouriteMovie>> getAllFavourites() {
        this.favouriteMovieList = appRoomDatabase.favouriteMovieDao().getAll();
        return this.favouriteMovieList;
    }

    public LiveData<ArrayList<MovieModel>> getMovieResponseLiveData(Integer page, Integer limit, String query, Integer year) {
        this.movieResponseLiveData = movieResponse.getMovies(page,limit,query,year);
        return movieResponseLiveData;
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