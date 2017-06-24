package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.standarduser.popularmoviestmdbv4.R;

/**
 * Created by Standard user on 6/24/2017.
 */

public class FragmentMovieDetail extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_moviedetail, container, false);
    return view;
  }
}
