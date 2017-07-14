package com.example.standarduser.popularmoviestmdbv4.backend.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.standarduser.popularmoviestmdbv4.backend.sqlite.MovieFavoriteContract.MovieFavoriteEntry;

/**
 * Created by Standard user on 7/14/2017.
 */

public class MovieFavoriteDbHelper extends SQLiteOpenHelper {
  public static final String DATABASE_NAME = "moviefavorite.db";

  private static final int DATABASE_VERSION = 1;

  public MovieFavoriteDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE =
        "CREATE TABLE " + MovieFavoriteEntry.TABLE_NAME + " (" +
        MovieFavoriteEntry._ID                        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        MovieFavoriteEntry.COLUMN_MOVIE_ID            + " INTEGER NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_POSTER_PATH   + " TEXT NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_DESCRIPTION   + " TEXT NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_RATING        + " REAL NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_RELEASE_DATE  + " STRING NOT NULL " +
        "UNIQUE (" + MovieFavoriteEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE;";

    db.execSQL(SQL_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + MovieFavoriteEntry.TABLE_NAME);
    onCreate(db);
  }
}
