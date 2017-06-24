package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
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

public class FragmentGridMovie extends Fragment implements AdapterMovieObject.clickHandler {
  private static final String LOG_TAG = FragmentGridMovie.class.getSimpleName();
  private static final String PARCEL_TAG = "MovieObject";

  private Call<MovieList> callMovieList;
  private ProgressBar pbrMovieList;
  private RecyclerView rvwGridMovie;

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
    MovieFetcher theFetcher = new MovieFetcher();
    MovieAPIEndpoint theEndpoint = theFetcher.getFetcher().create(MovieAPIEndpoint.class);

    //get sorting value from PreferenceFragment
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    String sortBy = prefs.getString(
        getString(R.string.action_gridmovie_sortby_key),
        getString(R.string.action_gridmovie_sortby_option_default_value)
    );

    //Show ProgressBar & hide RecyleView here
    pbrMovieList = (ProgressBar) view.findViewById(R.id.griddmovie_progressbar_movielist);
    rvwGridMovie = (RecyclerView) view.findViewById(R.id.gridmovie_recyclerview_movielist);

    rvwGridMovie.setVisibility(View.INVISIBLE);
    pbrMovieList.setVisibility(View.VISIBLE);

    //Fetch the Data
    callMovieList = theEndpoint.getMoviesBySort(sortBy, BuildConfig.TMDB_API_KEY);

    callMovieList.enqueue(new Callback<MovieList>() {
      @Override
      public void onResponse(Call<MovieList> call, Response<MovieList> response) {
        MovieList listMovies = response.body();

        if(listMovies != null) {
          //Fill the data here
          List<MovieObject> listMovie = listMovies.getListMovieObject();
          AdapterMovieObject adpMovieObject = new AdapterMovieObject(listMovie);
          adpMovieObject.setOnImageViewClick(FragmentGridMovie.this);

          rvwGridMovie.setHasFixedSize(true);

          RecyclerView.LayoutManager rvwLayoutManager = new GridLayoutManager(view.getContext(), 2);
          rvwGridMovie.setLayoutManager(rvwLayoutManager);

          rvwGridMovie.setAdapter(adpMovieObject);

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
      public void onFailure(Call<MovieList> call, Throwable t) {
        Log.e(LOG_TAG, t.toString());
      }
    });

    return view;
  }
}
