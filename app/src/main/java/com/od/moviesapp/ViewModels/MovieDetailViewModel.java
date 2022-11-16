package com.od.moviesapp.ViewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.od.moviesapp.Models.TrailerModel;
import com.od.moviesapp.Repositories.MovieRepository;
import java.util.ArrayList;

public class MovieDetailViewModel extends AndroidViewModel {
    private MovieRepository movieResponse;
    private LiveData<ArrayList<TrailerModel>> trailerResponseLiveData;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieResponse = new MovieRepository();
    }

    public LiveData<ArrayList<TrailerModel>> getTrailerResponseLiveData(String id) {
        this.trailerResponseLiveData = movieResponse.getTrailers(id);
        return trailerResponseLiveData;
    }
}

