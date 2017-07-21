package com.example.standarduser.popularmoviestmdbv4.backend.sqlite;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Standard user on 7/14/2017.
 */

public class MovieFavoriteContract {
  public static final String CONTENT_AUTHORITY = "com.example.standarduser.popularmoviestmdbv4";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  public static final String PATH_FAVORITE = "favorite";
  
  public static final class MovieFavoriteEntry implements BaseColumns {
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_FAVORITE)
        .build();
    
    public static final String TABLE_NAME = "favorite";

    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
    public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";
    public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String COLUMN_MOVIE_RATING = "movie_rating";
    public static final String COLUMN_MOVIE_DESCRIPTION = "movie_description";
    
    public static Uri buildFavoriteUriWithMovieId(int movieId) {
      return CONTENT_URI.buildUpon().appendPath(Integer.toString(movieId)).build();
    };
  }
}
