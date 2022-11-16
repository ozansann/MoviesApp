package com.od.moviesapp.Retrofit;

import com.od.moviesapp.Models.MovieModel;
import com.od.moviesapp.Models.ResultModel;
import com.od.moviesapp.Models.TrailerModel;
import com.od.moviesapp.Models.TrailerResultModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("movies")
    Call<ResultModel<ArrayList<MovieModel>>> getMovies(@Header("X-RapidAPI-Key") String apiKey,
                                                       @Header("X-RapidAPI-Host") String apiHost,
                                                       @Query("page") Integer page,
                                                       @Query("limit") Integer limit,
                                                       @Query("query") String query,
                                                       @Query("year") Integer year);
    @GET("trailers/{id}")
    Call<TrailerResultModel<ArrayList<TrailerModel>>> getTrailers(@Header("X-RapidAPI-Key") String apiKey,
                                                                  @Header("X-RapidAPI-Host") String apiHost,
                                                                  @Path("id") String id);
}