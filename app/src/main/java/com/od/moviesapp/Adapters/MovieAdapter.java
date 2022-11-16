package com.od.moviesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.od.moviesapp.Interfaces.ClickInterface;
import com.od.moviesapp.Models.MovieModel;
import com.od.moviesapp.R;
import com.od.moviesapp.databinding.RowMovieBinding;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<MovieModel> movieList,favouriteMovieList;
    private ClickInterface listener;
    private Boolean isFav;

    public MovieAdapter(Context context, ArrayList<MovieModel> movieList, ArrayList<MovieModel> favouriteMovieList, ClickInterface listener) {
        this.context = context;
        this.movieList = movieList;
        this.favouriteMovieList = favouriteMovieList;
        this.listener = listener;
        this.isFav = false;
    }

    public MovieAdapter(Context context, ArrayList<MovieModel> movieList, ClickInterface listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
        this.isFav = true;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(RowMovieBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MovieModel movie = movieList.get(i);
        viewHolder.binding.textViewMovieName.setText(movie.getTitle());
        viewHolder.binding.textViewMovieYear.setText(movie.getYear());
        viewHolder.binding.textViewMovieRating.setText(movie.getRating());
        Glide.with(context)
                .load(movie.getImage())
                .into(viewHolder.binding.imageViewMovie);
        Boolean isFav = isFavourite(movie);
        if(isFav){
            viewHolder.binding.imageViewFav.setImageResource(R.drawable.heart_checked);
        } else{
            viewHolder.binding.imageViewFav.setImageResource(R.drawable.heart_not_checked);
        }
        viewHolder.binding.imageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.recyclerviewOnClickForFav(movie);
            }
        });
        viewHolder.binding.imageViewMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.recyclerviewOnClick(viewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RowMovieBinding binding;

        public ViewHolder(RowMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public Boolean isFavourite(MovieModel movie){
        if(isFav){
            return true;
        }
        if(favouriteMovieList == null){
            return false;
        }
        for(MovieModel movieModel : favouriteMovieList){
            if(movie.get_id().equals(movieModel.get_id())){
                return true;
            }
        }
        return false;
    }
}