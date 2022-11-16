package com.od.moviesapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import com.od.moviesapp.Adapters.MovieAdapter;
import com.od.moviesapp.Interfaces.ClickInterface;
import com.od.moviesapp.Models.MovieModel;
import com.od.moviesapp.R;
import com.od.moviesapp.RoomDatabase.FavouriteMovie;
import com.od.moviesapp.ViewModels.FavouriteMovieViewModel;
import com.od.moviesapp.databinding.FragmentFavouriteMoviesBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class FavouriteMovieFragment extends BaseFragment implements ClickInterface {
    private List<FavouriteMovie> favouriteMovieList;
    FragmentFavouriteMoviesBinding binding;
    FavouriteMovieViewModel favouriteMovieViewModel;
    ArrayList<MovieModel> movieArrayList;
    MovieAdapter adapter;
    Bundle bundle;
    public ClickInterface listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouriteMovieList = new ArrayList<>();
        movieArrayList = new ArrayList<>();
        listener = this;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouriteMovieViewModel = ViewModelProviders.of(this).get(FavouriteMovieViewModel.class);
        favouriteMovieViewModel.initializeDatabase(getContext());
        initializeComponents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void initializeComponents() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setHasFixedSize(false);
        adapter = new MovieAdapter(getContext(), movieArrayList, listener);
        binding.recyclerView.setAdapter(adapter);
        favouriteMovieViewModel = ViewModelProviders.of(this).get(FavouriteMovieViewModel.class);
        favouriteMovieViewModel.initializeDatabase(getContext());
        getFavouriteMovieList();
    }

    private void getFavouriteMovieList() {
        favouriteMovieViewModel.getAllFavourites().observe(getActivity(), movieResponse -> {
            if (movieResponse != null && !movieResponse.isEmpty()) {
                favouriteMovieList = movieResponse;
                movieArrayList.clear();
                for(FavouriteMovie movie : favouriteMovieList){
                    MovieModel movieModel = new MovieModel(movie.get_id(),movie.getImage(),movie.getTitle(),
                            movie.getRating(),movie.getYear(),movie.getDescription());
                    movieArrayList.add(movieModel);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void recyclerviewOnClick(int position) {
        MovieModel selectedMovie = movieArrayList.get(position);
        FavouriteMovie favouriteMovie = new FavouriteMovie(selectedMovie.get_id(),
                selectedMovie.getImage(),selectedMovie.getTitle(),selectedMovie.getRating(),
                selectedMovie.getYear(), selectedMovie.getDescription());
        if(!isFavourite(selectedMovie)){
            favouriteMovieViewModel.addMovie(favouriteMovie);
        }
        bundle = new Bundle();
        bundle.putSerializable("selectedMovie",selectedMovie);
        navigateWithBundle(R.id.action_favouriteMovieFragment_to_movieDetailFragment,bundle);
    }

    @Override
    public void recyclerviewOnClickForFav(MovieModel movieModel) {
        FavouriteMovie favouriteMovie = getCurrentFav(movieModel);
        if(favouriteMovie != null){
            favouriteMovieViewModel.deleteMovie(favouriteMovie);
            removeFavItemFromList(favouriteMovie);
        }
        adapter.notifyDataSetChanged();
    }

    public Boolean isFavourite(MovieModel movie){
        if(favouriteMovieList == null){
            return false;
        }
        for(MovieModel movieModel : movieArrayList){
            if(movie.get_id().equals(movieModel.get_id())){
                return true;
            }
        }
        return false;
    }

    public FavouriteMovie getCurrentFav(MovieModel movieModel){
        for(FavouriteMovie movie : favouriteMovieList){
            if(movie.get_id().equals(movieModel.get_id())){
                return movie;
            }
        }
        return null;
    }

    public void removeFavItemFromList(FavouriteMovie favouriteMovie){
        for (ListIterator<MovieModel> it = movieArrayList.listIterator(); it.hasNext();){
            MovieModel movie = it.next();
            if (movie.get_id().equals(favouriteMovie.get_id())) {
                it.remove();
            }
        }
    }
}