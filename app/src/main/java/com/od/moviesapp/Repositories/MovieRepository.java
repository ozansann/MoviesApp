package com.od.moviesapp.Repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.od.moviesapp.Constants.AppConstants;
import com.od.moviesapp.Models.MovieModel;
import com.od.moviesapp.Models.ResultModel;
import com.od.moviesapp.Models.TrailerModel;
import com.od.moviesapp.Models.TrailerResultModel;
import com.od.moviesapp.Retrofit.ApiRequest;
import com.od.moviesapp.Retrofit.RetrofitRequest;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private final ApiRequest request;
    public MovieRepository() {
        request = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<ArrayList<MovieModel>> getMovies(Integer page, Integer limit, String query, Integer year) {
        final MutableLiveData<ArrayList<MovieModel>> data = new MutableLiveData<>();
        request.getMovies(AppConstants.MOVIES_API_KEY,AppConstants.MOVIES_API_HOST,page,limit,query,year)
                .enqueue(new Callback<ResultModel<ArrayList<MovieModel>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ResultModel<ArrayList<MovieModel>>> call,
                                           @NonNull Response<ResultModel<ArrayList<MovieModel>>> response) {
                        if (response.body() != null) {
                            data.setValue(response.body().getData());
                        } else{
                            data.setValue(null);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ResultModel<ArrayList<MovieModel>>> call, @NonNull Throwable t) {
                        data.setValue(null);

                    }
                });
        return data;
    }
    public LiveData<ArrayList<TrailerModel>> getTrailers(String id) {
        final MutableLiveData<ArrayList<TrailerModel>> data = new MutableLiveData<>();
        request.getTrailers(AppConstants.MOVIES_API_KEY,AppConstants.MOVIES_API_HOST,id)
                .enqueue(new Callback<TrailerResultModel<ArrayList<TrailerModel>>>() {
                    @Override
                    public void onResponse(@NonNull Call<TrailerResultModel<ArrayList<TrailerModel>>> call,
                                           @NonNull Response<TrailerResultModel<ArrayList<TrailerModel>>> response) {
                        if (response.body() != null) {
                            data.setValue(response.body().getData());
                        } else{
                            data.setValue(null);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<TrailerResultModel<ArrayList<TrailerModel>>> call, @NonNull Throwable t) {
                        data.setValue(null);

                    }
                });
        return data;
    }
}