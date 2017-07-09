package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieObject;
import com.squareup.picasso.Picasso;

/**
 * Created by Standard user on 6/24/2017.
 */

public class FragmentMovieDetail extends Fragment {
  private static final String LOG_TAG = FragmentGridMovie.class.getSimpleName();
  private static final String PARCEL_TAG = "MovieObject";
  private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
  private static final String IMAGE_SIZE = "w185";

  private CoordinatorLayout lytMain;
  private ImageView imgMovie;
  private FloatingActionButton btnAddToFavorite;
  private TextView txtTitle;
  private TextView txtReleaseDate;
  private TextView txtVoteAverage;
  private TextView txtDescription;
  private RecyclerView rvwTrailers;
  private RecyclerView rvwReviews;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_moviedetail, container, false);

    lytMain = (CoordinatorLayout) view.findViewById(R.id.moviedetail_coordinatorlayout_mainlayout);
    imgMovie = (ImageView) view.findViewById(R.id.moviedetail_imageview_movieimage);
    btnAddToFavorite = (FloatingActionButton) view.findViewById(R.id.moviedetail_button_movieaddfavorite);
    txtTitle = (TextView) view.findViewById(R.id.moviedetail_textview_movietitle);
    txtReleaseDate = (TextView) view.findViewById(R.id.moviedetail_textview_moviereleasedate);
    txtVoteAverage = (TextView) view.findViewById(R.id.moviedetail_textview_movievoteaverage);
    txtDescription = (TextView) view.findViewById(R.id.moviedetail_textview_moviedescription);
    rvwTrailers = (RecyclerView) view.findViewById(R.id.moviedetail_recyclerview_movietrailers);
    rvwReviews = (RecyclerView) view.findViewById(R.id.moviedetail_recyclerview_moviereviews);

    if(getArguments().getParcelable(PARCEL_TAG) != null) {
      MovieObject objMovie = getArguments().getParcelable(PARCEL_TAG);

      assert objMovie != null;
      Uri uri = Uri.parse(IMAGE_URL + IMAGE_SIZE + objMovie.getObjectPosterPath());

      Picasso.with(imgMovie.getContext())
          .load(uri)
          .into(imgMovie);

      txtTitle.setText(objMovie.getObjectTitle());
      txtReleaseDate.setText(objMovie.getObjectReleaseDate());
      txtVoteAverage.setText(String.valueOf(objMovie.getObjectRating()));
      txtDescription.setText(objMovie.getObjectDescription());

      //TODO turn this on for debug purpose
//      Log.d(LOG_TAG, parcel.getObjectDescription());
    }

    btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //TODO [1] Add parcelable (id, name) to database
        Snackbar.make(lytMain, "This is just a click", Snackbar.LENGTH_SHORT).show();
      }
    });

    return view;
  }
}
