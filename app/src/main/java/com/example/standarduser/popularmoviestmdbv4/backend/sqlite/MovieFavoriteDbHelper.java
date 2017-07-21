package com.example.standarduser.popularmoviestmdbv4.backend.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.standarduser.popularmoviestmdbv4.backend.pojo.MovieObject;
import com.example.standarduser.popularmoviestmdbv4.backend.sqlite.MovieFavoriteContract.MovieFavoriteEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Standard user on 7/14/2017.
 */

public class MovieFavoriteDbHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "moviefavorite.db";
  
  private static final int DATABASE_VERSION = 1;
  
  public MovieFavoriteDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public List<MovieObject> getAllMovieObject(SQLiteDatabase db) {
    List<MovieObject> ListMovieObject = null;

    String[] projection = {
        MovieFavoriteEntry.COLUMN_MOVIE_ID,
        MovieFavoriteEntry.COLUMN_MOVIE_TITLE,
        MovieFavoriteEntry.COLUMN_MOVIE_POSTER_PATH,
        MovieFavoriteEntry.COLUMN_MOVIE_DESCRIPTION,
        MovieFavoriteEntry.COLUMN_MOVIE_RATING,
        MovieFavoriteEntry.COLUMN_MOVIE_RELEASE_DATE
    };

    Cursor cursor = db.query(
        MovieFavoriteEntry.TABLE_NAME,
        projection,
        null,
        null,
        null,
        null,
        null
    );

    if(cursor != null) {
      ListMovieObject = new ArrayList<>();

      while(cursor.moveToNext()) {
        MovieObject obj = new MovieObject();

        obj.setObjectId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_ID)));
        obj.setObjectTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_TITLE)));
        obj.setObjectPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_POSTER_PATH)));
        obj.setObjectDescription(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_DESCRIPTION)));
        obj.setObjectRating(cursor.getFloat(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_RATING)));
        obj.setObjectReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_RELEASE_DATE)));

        ListMovieObject.add(obj);
      }
    }

    return ListMovieObject;
  }

  public boolean isExistMovieObject(SQLiteDatabase db, MovieObject obj) {
    String[] projection = {
        MovieFavoriteEntry.COLUMN_MOVIE_ID
    };
  
    String selection = MovieFavoriteEntry.COLUMN_MOVIE_ID + " = ?";
    String[] selectionArgs = new String[]{String.valueOf(obj.getObjectId())};
  
    Cursor cursor = db.query(
        MovieFavoriteEntry.TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null, null, null, null
    );
  
    return cursor != null && cursor.moveToFirst();
  }

  public boolean insertMovieObject(SQLiteDatabase db, MovieObject obj) {
    ContentValues val = new ContentValues();
    val.put(MovieFavoriteEntry.COLUMN_MOVIE_ID, obj.getObjectId());
    val.put(MovieFavoriteEntry.COLUMN_MOVIE_TITLE, obj.getObjectTitle());
    val.put(MovieFavoriteEntry.COLUMN_MOVIE_POSTER_PATH, obj.getObjectPosterPath());
    val.put(MovieFavoriteEntry.COLUMN_MOVIE_DESCRIPTION, obj.getObjectDescription());
    val.put(MovieFavoriteEntry.COLUMN_MOVIE_RATING, obj.getObjectRating());
    val.put(MovieFavoriteEntry.COLUMN_MOVIE_RELEASE_DATE, obj.getObjectReleaseDate());

    return db.insert(MovieFavoriteEntry.TABLE_NAME, null, val) > 0;
  }

  public boolean deleteMovieObject(SQLiteDatabase db, MovieObject obj) {
    String whereClause = MovieFavoriteEntry.COLUMN_MOVIE_ID + " = ?";
    String[] whereArgs = new String[] {String.valueOf(obj.getObjectId())};

    return db.delete(MovieFavoriteEntry.TABLE_NAME, whereClause, whereArgs) > 0;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String SQL_CREATE = "CREATE TABLE " + MovieFavoriteEntry.TABLE_NAME + " (" +
        MovieFavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        MovieFavoriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_RATING + " REAL NOT NULL, " +
        MovieFavoriteEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
        "UNIQUE (" + MovieFavoriteEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE" +
        ");";
    
    db.execSQL(SQL_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + MovieFavoriteEntry.TABLE_NAME);
    onCreate(db);
  }
}
