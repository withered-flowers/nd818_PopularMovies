package com.example.standarduser.popularmoviestmdbv4.backend.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Standard user on 7/14/2017.
 */

public class MovieFavoriteContract {
  public static final class MovieFavoriteEntry implements BaseColumns {
    public static final String TABLE_NAME = "favorite";

    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";
    public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String COLUMN_MOVIE_RATING = "movie_rating";
    public static final String COLUMN_MOVIE_DESCRIPTION = "movie_description";
  }
}
