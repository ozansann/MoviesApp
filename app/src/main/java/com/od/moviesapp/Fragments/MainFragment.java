package com.od.moviesapp.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.od.moviesapp.Adapters.MovieAdapter;
import com.od.moviesapp.Dialogs.MovieFilterDialog;
import com.od.moviesapp.Interfaces.ClickInterface;
import com.od.moviesapp.Models.MovieModel;
import com.od.moviesapp.R;
import com.od.moviesapp.RoomDatabase.FavouriteMovie;
import com.od.moviesapp.ViewModels.MovieViewModel;
import com.od.moviesapp.databinding.FragmentMainBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MainFragment extends BaseFragment implements ClickInterface{
    private List<FavouriteMovie> favouriteMovieList;
    FragmentMainBinding binding;
    private MovieAdapter adapter;
    private ArrayList<MovieModel> movieArrayList, favouriteMovieArrayList;
    private List<MovieModel> movieList;
    private static MovieViewModel movieViewModel;
    private Bundle bundle;
    public static String filterMovieName = "";
    public static Integer filterMovieYear = 0;
    public ClickInterface listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieArrayList = new ArrayList<>();
        favouriteMovieList = new ArrayList<>();
        favouriteMovieArrayList = new ArrayList<>();
        listener = this;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents();
        setOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public static void setSearchFilters(String movieName, Integer movieYear){
        filterMovieName = movieName;
        filterMovieYear = movieYear;
    }

    private void setOptionsMenu(){
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.fragment_main_menu, menu);

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                MovieFilterDialog movieFilterDialog = new MovieFilterDialog(getContext());
                movieFilterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        getMovieList(1,filterMovieName,filterMovieYear);
                    }
                });
                movieFilterDialog.show();
                return false;

            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void initializeComponents() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setMotionEventSplittingEnabled(false);
        adapter = new MovieAdapter(getContext(), movieArrayList,favouriteMovieArrayList, listener);
        binding.recyclerView.setAdapter(adapter);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.initializeDatabase(getContext());
        getMovieList(1,filterMovieName,filterMovieYear);
        swipeRefresh();
    }

    public Boolean isFavourite(MovieModel movie){
        if(favouriteMovieList == null){
            return false;
        }
        for(MovieModel movieModel : favouriteMovieArrayList){
            if(movie.get_id().equals(movieModel.get_id())){
                return true;
            }
        }
        return false;
    }

    private void swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.loadingLayout.setVisibility(View.VISIBLE);
            binding.swipeRefresh.setRefreshing(false);
            setSearchFilters("",0);
            getMovieList(1,filterMovieName,filterMovieYear);
        });
    }

    private void getMovieList(Integer page, String movieName, Integer year) {
        movieViewModel.getMovieResponseLiveData(page,20,movieName,year).observe(getActivity(), movieResponse -> {
            if (movieResponse != null && !movieResponse.isEmpty()) {
                movieList = movieResponse;
                movieArrayList.clear();
                movieArrayList.addAll(movieList);
                binding.loadingLayout.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });
        movieViewModel.getAllFavourites().observe(getActivity(), movieResponse -> {
            if (movieResponse != null && !movieResponse.isEmpty()) {
                favouriteMovieList = movieResponse;
                for(FavouriteMovie favouriteMovie : favouriteMovieList){
                    favouriteMovieArrayList.add(new MovieModel(favouriteMovie.get_id(),favouriteMovie.getImage(),favouriteMovie.getTitle(),favouriteMovie.getRating(),favouriteMovie.getYear(),favouriteMovie.getDescription()));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void recyclerviewOnClick(int position) {
        MovieModel selectedMovie = movieArrayList.get(position);
        bundle = new Bundle();
        bundle.putSerializable("selectedMovie",selectedMovie);
        navigateWithBundle(R.id.movieDetailFragment,bundle);
    }

    @Override
    public void recyclerviewOnClickForFav(MovieModel movieModel) {
        if(!isFavourite(movieModel)){
            FavouriteMovie favouriteMovie = new FavouriteMovie(movieModel.get_id(),
                    movieModel.getImage(),movieModel.getTitle(),movieModel.getRating(),
                    movieModel.getYear(), movieModel.getDescription());
            movieViewModel.addMovie(favouriteMovie);
            adapter.notifyDataSetChanged();
        } else{
            FavouriteMovie favouriteMovie = getCurrentFav(movieModel);
            if(favouriteMovie != null){
                movieViewModel.deleteMovie(favouriteMovie);
                removeFavItemFromList(favouriteMovie);
                adapter.notifyDataSetChanged();
            }
        }
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
        for (ListIterator<MovieModel> it = favouriteMovieArrayList.listIterator(); it.hasNext();){
            MovieModel movie = it.next();
            if (movie.get_id().equals(favouriteMovie.get_id())) {
                it.remove();
            }
        }
    }
}