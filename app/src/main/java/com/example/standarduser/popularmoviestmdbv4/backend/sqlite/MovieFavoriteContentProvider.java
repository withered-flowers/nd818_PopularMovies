package com.example.standarduser.popularmoviestmdbv4.backend.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by standard on 21/07/17.
 */

public class MovieFavoriteContentProvider extends ContentProvider {
  static final int CODE_FAVORITE = 100;
  static final int CODE_FAVORITE_WITH_MOVIEID = 101;
  
  static final UriMatcher sUriMatcher;
  
  static {
    sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    sUriMatcher.addURI(MovieFavoriteContract.CONTENT_AUTHORITY, MovieFavoriteContract.PATH_FAVORITE, CODE_FAVORITE);
    sUriMatcher.addURI(MovieFavoriteContract.CONTENT_AUTHORITY, MovieFavoriteContract.PATH_FAVORITE + "/#", CODE_FAVORITE_WITH_MOVIEID);
  }
  
  private MovieFavoriteDbHelper dbHelper;
  private SQLiteDatabase db;
  
  @Override
  public boolean onCreate() {
    Context context = getContext();
    dbHelper = new MovieFavoriteDbHelper(context);
    db = dbHelper.getWritableDatabase();
    
    return dbHelper != null && db != null;
  }
  
  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Cursor cursor = null;
    
    switch(sUriMatcher.match(uri)) {
      case CODE_FAVORITE:
        cursor = db.query(
            MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );
        break;
      
      case CODE_FAVORITE_WITH_MOVIEID:
        String movieId = uri.getLastPathSegment();
        String[] selectionArguments = {movieId};
        
        cursor = db.query(
            MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME,
            projection,
            MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_ID + " = ? ",
            selectionArguments,
            null,
            null,
            sortOrder
        );
        break;
      
      default:
        throw new SQLException("Unknown URI " + uri.toString());
    }
    
    return cursor;
  }
  
  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    switch (sUriMatcher.match(uri)) {
      case CODE_FAVORITE:
        return "vnd.android.cursor.dir/vnd.com.example.standarduser.popularmoviestmdbv4.favorite";
      
      case CODE_FAVORITE_WITH_MOVIEID:
        return "vnd.android.cursor.item/vnd.com.example.standarduser.popularmoviestmdbv4.favorite";
      
      default:
        throw new IllegalArgumentException("Unsupported URI " + uri);
    }
  }
  
  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    long rowId = db.insert(MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME, null, values);
    
    if(rowId > 0) {
      return MovieFavoriteContract.MovieFavoriteEntry.buildFavoriteUriWithMovieId(
          values.getAsInteger(MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_ID)
      );
    }
    
    throw new SQLException("Failed to add record " + uri);
  }
  
  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    int count = db.delete(
        MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME,
        selection,
        selectionArgs
    );
    
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }
  
  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }
}
