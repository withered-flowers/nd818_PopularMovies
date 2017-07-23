package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.standarduser.popularmoviestmdbv4.BuildConfig;
import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.List_MovieObjects;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.MovieObject;
import com.example.standarduser.popularmoviestmdbv4.backend.retrofit.APIEndpoint;
import com.example.standarduser.popularmoviestmdbv4.backend.retrofit.Fetcher;
import com.example.standarduser.popularmoviestmdbv4.backend.sqlite.MovieFavoriteContract.MovieFavoriteEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Standard user on 6/23/2017.
 */

public class FragmentGridMovie extends Fragment implements AdapterMovieObject.clickHandler {
  private static final String LOG_TAG = FragmentGridMovie.class.getSimpleName();
  private static final String PARCEL_TAG = "MovieObject";
  private static final String SAVE_TAG = "MovieState";

  private Call<List_MovieObjects> callMovieList;
  private ProgressBar pbrMovieList;
  private RecyclerView rvwGridMovie;
  
  private List<MovieObject> listMovie = null;

  @Override
  public void onImageViewClick(MovieObject obj) {
    //Put the data here
    Bundle args = new Bundle();
    args.putParcelable(PARCEL_TAG, obj);

    FragmentMovieDetail fmtMovieDetail = new FragmentMovieDetail();
    fmtMovieDetail.setArguments(args);

    FragmentTransaction trx = getFragmentManager().beginTransaction();

    trx.replace(R.id.main_framelayout, fmtMovieDetail);
    trx.addToBackStack(null);
    trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    trx.commit();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_gridmovie, container, false);

    //Create Fetcher & APIEndpoint here
    Fetcher theFetcher = new Fetcher();
    APIEndpoint theEndpoint = theFetcher.getFetcher().create(APIEndpoint.class);

    //get sorting value from PreferenceFragment
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    String sortBy = prefs.getString(
        getString(R.string.action_gridmovie_sortby_key),
        getString(R.string.action_gridmovie_sortby_option_default_value)
    );

    //Show ProgressBar & hide RecyleView here
    pbrMovieList = (ProgressBar) view.findViewById(R.id.gridmovie_progressbar_movielist);
    rvwGridMovie = (RecyclerView) view.findViewById(R.id.gridmovie_recyclerview_movielist);

    rvwGridMovie.setVisibility(View.INVISIBLE);
    pbrMovieList.setVisibility(View.VISIBLE);

    if(savedInstanceState != null) {
      listMovie = savedInstanceState.getParcelableArrayList(SAVE_TAG);
      allocateRecyclerView(listMovie, view);
  
      rvwGridMovie.setVisibility(View.VISIBLE);
      pbrMovieList.setVisibility(View.INVISIBLE);
    }
    else {
      if (!sortBy.equals(getString(R.string.action_gridmovie_sortby_option_favorite_value))) {
        //Fetch the Data From TMDb
        callMovieList = theEndpoint.getMoviesBySort(sortBy, BuildConfig.TMDB_API_KEY);
    
        callMovieList.enqueue(new Callback<List_MovieObjects>() {
          @Override
          public void onResponse(Call<List_MovieObjects> call, Response<List_MovieObjects> response) {
            List_MovieObjects listMovies = response.body();
        
            if (listMovies != null) {
              //Fill the data here
              listMovie = listMovies.getListMovieObject();
          
              allocateRecyclerView(listMovie, view);
          
              //TODO Turn this on for debug purpose
//          Log.d(LOG_TAG, "Total Film is: " + listMovie.size());
//
//          for(int i=0; i<listMovie.size(); i++) {
//            Log.d(LOG_TAG, "Film " + i + " is:" + listMovie.get(i).getObjectTitle());
//          }
            }
        
            rvwGridMovie.setVisibility(View.VISIBLE);
            pbrMovieList.setVisibility(View.INVISIBLE);
          }
      
          @Override
          public void onFailure(Call<List_MovieObjects> call, Throwable t) {
            Log.e(LOG_TAG, t.toString());
          }
        });
      } else {
      /* THIS IS THE OLD WAY (CONSUME DBHELPER & DB INSTANTLY) */
//      MovieFavoriteDbHelper dbHelper = new MovieFavoriteDbHelper(getActivity().getApplicationContext());
//      SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//      List<MovieObject> listMovie = dbHelper.getAllMovieObject(db);
//      db.close();
//      /* END OF OLD WAY */
    
        listMovie = allocateListMovieObject();
        if (listMovie != null) {
          allocateRecyclerView(listMovie, view);
        }
    
        rvwGridMovie.setVisibility(View.VISIBLE);
        pbrMovieList.setVisibility(View.INVISIBLE);
      }
    }

    return view;
  }
  
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if(listMovie != null && listMovie.size() > 0) {
      ArrayList<MovieObject> arrListMovie = new ArrayList<>(listMovie);
      outState.putParcelableArrayList(SAVE_TAG, arrListMovie);
    }
  }
  
  private void allocateRecyclerView(List<MovieObject> listMovie, View view) {
    AdapterMovieObject adpMovieObject = new AdapterMovieObject(listMovie);
    adpMovieObject.setOnImageViewClick(FragmentGridMovie.this);

    rvwGridMovie.setHasFixedSize(true);

    RecyclerView.LayoutManager rvwLayoutManager = new GridLayoutManager(view.getContext(), 2);
    rvwGridMovie.setLayoutManager(rvwLayoutManager);

    rvwGridMovie.setAdapter(adpMovieObject);
  }
  
  private List<MovieObject> allocateListMovieObject() {
    List<MovieObject> ListMovieObject = null;
    
    Cursor cursor = getActivity().getContentResolver().query(
        MovieFavoriteEntry.CONTENT_URI,
        null,
        null,
        null,
        null,
        null
    );
    
    if(cursor != null && cursor.moveToFirst()) {
      ListMovieObject = new ArrayList<>();
      
      do {
        MovieObject obj = new MovieObject();
  
        obj.setObjectId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_ID)));
        obj.setObjectTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_TITLE)));
        obj.setObjectPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_POSTER_PATH)));
        obj.setObjectDescription(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_DESCRIPTION)));
        obj.setObjectRating(cursor.getFloat(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_RATING)));
        obj.setObjectReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieFavoriteEntry.COLUMN_MOVIE_RELEASE_DATE)));
  
        ListMovieObject.add(obj);
      } while(cursor.moveToNext());
      
      if(!cursor.isClosed()) {
        cursor.close();
      }
    }
    
    return ListMovieObject;
  }
}
