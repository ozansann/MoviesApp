package com.od.moviesapp.RoomDatabase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavouriteMovie.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static AppRoomDatabase INSTANCE;

    public abstract FavouriteMovieDao favouriteMovieDao();

    public static AppRoomDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppRoomDatabase.class, "favourite-movie-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}