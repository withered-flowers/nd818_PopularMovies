package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.standarduser.popularmoviestmdbv4.BuildConfig;
import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieAPIEndpoint;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieFetcher;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieList;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Standard user on 6/23/2017.
 */

public class FragmentGridMovie extends Fragment {
  private static final String LOG_TAG = FragmentGridMovie.class.getSimpleName();

  private Call<MovieList> callMovieList;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_gridmovie, container, false);

    //TODO Create Fetcher & APIEndpoint here !
    MovieFetcher theFetcher = new MovieFetcher();
    MovieAPIEndpoint theEndpoint = theFetcher.getFetcher().create(MovieAPIEndpoint.class);

    //TODO get sorting value from PreferenceFragment
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    String sortBy = prefs.getString(
        getString(R.string.action_gridmovie_sortby_key),
        getString(R.string.action_gridmovie_sortby_option_default_value)
    );

    //TODO Fetch the Data !
    callMovieList = theEndpoint.getMoviesBySort(sortBy, BuildConfig.TMDB_API_KEY);

    callMovieList.enqueue(new Callback<MovieList>() {
      @Override
      public void onResponse(Call<MovieList> call, Response<MovieList> response) {
        MovieList listMovies = response.body();

        if(listMovies != null) {
          List<MovieObject> listMovie = listMovies.getListMovieObject();
          Log.d(LOG_TAG, "Total Film is: " + listMovie.size());
        }
      }

      @Override
      public void onFailure(Call<MovieList> call, Throwable t) {
        Log.e(LOG_TAG, t.toString());
      }
    });

    return view;
  }
}
