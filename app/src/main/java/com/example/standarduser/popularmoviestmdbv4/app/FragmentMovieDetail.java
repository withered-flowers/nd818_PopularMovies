package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.standarduser.popularmoviestmdbv4.BuildConfig;
import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.APIEndpoint;
import com.example.standarduser.popularmoviestmdbv4.backend.Fetcher;
import com.example.standarduser.popularmoviestmdbv4.backend.List_MovieReviews;
import com.example.standarduser.popularmoviestmdbv4.backend.List_MovieTrailers;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieObject;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieReview;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Standard user on 6/24/2017.
 */

public class FragmentMovieDetail extends Fragment implements AdapterMovieTrailer.clickHandler {
  private static final String LOG_TAG = FragmentGridMovie.class.getSimpleName();
  private static final String PARCEL_TAG = "MovieObject";
  private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
  private static final String IMAGE_SIZE = "w185";
  private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

  private CoordinatorLayout lytMain;
  private ImageView imgMovie;
  private FloatingActionButton btnAddToFavorite;
  private NestedScrollView lytMainItem;
  private ProgressBar pgbMovieDetail;
  private TextView txtTitle;
  private TextView txtReleaseDate;
  private TextView txtVoteAverage;
  private TextView txtDescription;
  private RecyclerView rvwTrailers;
  private RecyclerView rvwReviews;

  private Call<List_MovieTrailers> callMovieTrailers;
  private Call<List_MovieReviews> callMovieReviews;

  @Override
  public void onLayoutClick(MovieTrailer obj) {
    String theUrl = BASE_YOUTUBE_URL + obj.getTrailerKey();

    startActivity(
        new Intent(Intent.ACTION_VIEW, Uri.parse(theUrl))
    );
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_moviedetail, container, false);

    lytMain = (CoordinatorLayout) view.findViewById(R.id.moviedetail_coordinatorlayout_mainlayout);
    lytMainItem = (NestedScrollView) view.findViewById(R.id.moviedetail_nestedscrollview_mainlayout);
    pgbMovieDetail = (ProgressBar) view.findViewById(R.id.moviedetail_progressbar_movietrailerandreview);

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

      Fetcher theFetcher = new Fetcher();
      APIEndpoint theEndpoint = theFetcher.getFetcher().create(APIEndpoint.class);

      callMovieTrailers = theEndpoint.getMovieTrailersById(objMovie.getObjectId(), BuildConfig.TMDB_API_KEY);
      callMovieReviews = theEndpoint.getMovieReviewsById(objMovie.getObjectId(), BuildConfig.TMDB_API_KEY);

      callMovieTrailers.enqueue(new Callback<List_MovieTrailers>() {
        @Override
        public void onResponse(Call<List_MovieTrailers> call, Response<List_MovieTrailers> response) {
          List_MovieTrailers listTrailers = response.body();

          if(listTrailers != null) {
            List<MovieTrailer> listTrailer = listTrailers.getTrailerResults();
            AdapterMovieTrailer adpMovieTrailer = new AdapterMovieTrailer(listTrailer);
            adpMovieTrailer.setOnLayoutClick(FragmentMovieDetail.this);

            rvwTrailers.setHasFixedSize(true);

            RecyclerView.LayoutManager rvwLayoutManager = new LinearLayoutManager(view.getContext());
            rvwTrailers.setLayoutManager(rvwLayoutManager);

            rvwTrailers.setAdapter(adpMovieTrailer);
          }
        }

        @Override
        public void onFailure(Call<List_MovieTrailers> call, Throwable t) {
          Log.e(LOG_TAG, t.toString());
        }
      });

      callMovieReviews.enqueue(new Callback<List_MovieReviews>() {
        @Override
        public void onResponse(Call<List_MovieReviews> call, Response<List_MovieReviews> response) {
          List_MovieReviews listReviews = response.body();

          if(listReviews != null) {
            List<MovieReview> listReview = listReviews.getReviewResults();
            AdapterMovieReview adpMovieReview = new AdapterMovieReview(listReview);

            rvwReviews.setHasFixedSize(true);

            RecyclerView.LayoutManager rvwLayoutManager = new LinearLayoutManager(view.getContext());
            rvwReviews.setLayoutManager(rvwLayoutManager);

            rvwReviews.setAdapter(adpMovieReview);
          }
        }

        @Override
        public void onFailure(Call<List_MovieReviews> call, Throwable t) {
          Log.e(LOG_TAG, t.toString());
        }
      });
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

  private void showView() {
    lytMainItem.setVisibility(View.VISIBLE);
    btnAddToFavorite.setVisibility(View.VISIBLE);

    pgbMovieDetail.setVisibility(View.INVISIBLE);
  }

  private void hideView() {
    lytMainItem.setVisibility(View.INVISIBLE);
    btnAddToFavorite.setVisibility(View.INVISIBLE);

    pgbMovieDetail.setVisibility(View.VISIBLE);
  }
}
