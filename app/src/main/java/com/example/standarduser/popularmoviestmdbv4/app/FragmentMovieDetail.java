package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieObject;

/**
 * Created by Standard user on 6/24/2017.
 */

public class FragmentMovieDetail extends Fragment {
  private static final String LOG_TAG = FragmentGridMovie.class.getSimpleName();
  private static final String PARCEL_TAG = "MovieObject";

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_moviedetail, container, false);

    if(getArguments().getParcelable(PARCEL_TAG) != null) {
      MovieObject parcel = getArguments().getParcelable(PARCEL_TAG);

      //TODO turn this on for debug purpose
//      Log.d(LOG_TAG, parcel.getObjectDescription());
    }

    return view;
  }
}
