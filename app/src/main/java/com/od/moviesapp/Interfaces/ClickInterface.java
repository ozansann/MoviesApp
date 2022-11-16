package com.od.moviesapp.Interfaces;

import com.od.moviesapp.Models.MovieModel;

public interface ClickInterface {
    public void recyclerviewOnClick(int position);
    public void recyclerviewOnClickForFav(MovieModel movieModel);
}
