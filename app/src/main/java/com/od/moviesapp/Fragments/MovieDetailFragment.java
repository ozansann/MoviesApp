package com.od.moviesapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.od.moviesapp.Constants.AppConstants;
import com.od.moviesapp.Dialogs.LongTextDialog;
import com.od.moviesapp.Models.MovieModel;
import com.od.moviesapp.Models.TrailerModel;
import com.od.moviesapp.R;
import com.od.moviesapp.ViewModels.MovieDetailViewModel;
import com.od.moviesapp.databinding.FragmentMovieDetailBinding;
import java.util.List;

public class MovieDetailFragment extends BaseFragment{
    FragmentMovieDetailBinding binding;
    private MovieModel selectedMovie;
    private YouTubePlayer currentYouTubePlayer;
    MovieDetailViewModel movieDetailViewModel;
    private List<TrailerModel> trailerList;
    String movieDesc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedMovie = (MovieModel) getArguments().getSerializable("selectedMovie");
        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        getTrailers(selectedMovie.get_id());
        binding.textViewMovieTitle.setText(selectedMovie.getTitle());
        binding.textViewMovieYear.setText(selectedMovie.getYear());
        binding.textViewMovieRating.setText(selectedMovie.getRating());
        movieDesc = selectedMovie.getDescription();
        binding.textViewMovieDesc.setText(movieDesc.length() > 200 ? movieDesc.substring(0,200) + " ..." : movieDesc);
        binding.movieDescReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLongTextDialog(movieDesc);
            }
        });
        Glide.with(getContext())
                .load(selectedMovie.getImage())
                .into(binding.imageViewMovieDetail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void showLongTextDialog(String desc){
        LongTextDialog longTextDialog = new LongTextDialog(getContext(),desc);
        longTextDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        longTextDialog.show();
    }

    private void getTrailers(String id) {
        movieDetailViewModel.getTrailerResponseLiveData(id).observe(getActivity(), trailerResponse -> {
            if (trailerResponse != null && !trailerResponse.isEmpty()) {
                trailerList = trailerResponse;
                setMovieTrailer(trailerList.get(0).getVideoId());
            }
        });
    }

    private void setMovieTrailer(String id){
        YouTubePlayerSupportFragmentX youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        if (!isAdded()) return;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_fragment, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(AppConstants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    currentYouTubePlayer = youTubePlayer;
                    currentYouTubePlayer.setFullscreen(false);
                    currentYouTubePlayer.setShowFullscreenButton(false);
                    currentYouTubePlayer.cueVideo(id);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub
            }
        });
    }
}