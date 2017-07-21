package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.List_MovieReviews;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.List_MovieTrailers;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.MovieObject;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.MovieReview;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.MovieTrailer;
import com.example.standarduser.popularmoviestmdbv4.backend.retrofit.APIEndpoint;
import com.example.standarduser.popularmoviestmdbv4.backend.retrofit.Fetcher;
import com.example.standarduser.popularmoviestmdbv4.backend.sqlite.MovieFavoriteContract;
import com.example.standarduser.popularmoviestmdbv4.backend.sqlite.MovieFavoriteDbHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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

  private static final int BUTTON_FAB_ON  = android.R.drawable.btn_star_big_on;
  private static final int BUTTON_FAB_OFF = android.R.drawable.btn_star_big_off;

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

  private boolean isFetchMovieTrailerComplete = false;
  private boolean isFetchMovieReviewComplete = false;

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
      final MovieObject objMovie = getArguments().getParcelable(PARCEL_TAG);

      assert objMovie != null;

      hideView();
      setFavoriteButton(objMovie);

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

            isFetchMovieTrailerComplete = true;

            if(isFetchMovieReviewComplete && isFetchMovieTrailerComplete) {
              showView();
            }
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

            isFetchMovieReviewComplete = true;

            if(isFetchMovieReviewComplete && isFetchMovieTrailerComplete) {
              showView();
            }
          }
        }

        @Override
        public void onFailure(Call<List_MovieReviews> call, Throwable t) {
          Log.e(LOG_TAG, t.toString());
        }
      });

      Observable<Integer> obsFloatingActionButton = createButtonClickObservable();

      obsFloatingActionButton
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer tagNumber) throws Exception {
              MovieFavoriteDbHelper dbHelper = new MovieFavoriteDbHelper(getActivity().getApplicationContext());
              SQLiteDatabase db = dbHelper.getWritableDatabase();

              String snackbarString = "";

              /* THIS IS THE OLD WAY (CONSUME DBHELPER & DB INSTANTLY) */
//              if(tagNumber == BUTTON_FAB_ON) {
//                btnAddToFavorite.setImageResource(BUTTON_FAB_OFF);
//                btnAddToFavorite.setTag(BUTTON_FAB_OFF);
//
//                if(dbHelper.deleteMovieObject(db, objMovie)) {
//                  snackbarString = "Movie delete success";
//                } else {
//                  snackbarString = "Movie delete error";
//                }
//              }
//              else if (tagNumber == BUTTON_FAB_OFF) {
//                btnAddToFavorite.setImageResource(BUTTON_FAB_ON);
//                btnAddToFavorite.setTag(BUTTON_FAB_ON);
//
//                if(dbHelper.insertMovieObject(db, objMovie)) {
//                  snackbarString = "Movie insert success";
//                } else {
//                  snackbarString = "Movie insert error";
//                }
//              }
//
//              db.close();
              /* END OF THE OLD WAY */

              if(tagNumber == BUTTON_FAB_ON) {
                btnAddToFavorite.setImageResource(BUTTON_FAB_OFF);
                btnAddToFavorite.setTag(BUTTON_FAB_OFF);
  
                String whereClause = MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_ID + " = ?";
                String[] whereArgs = new String[] {String.valueOf(objMovie.getObjectId())};
                
                int deleteDb = getActivity()
                    .getContentResolver()
                    .delete(MovieFavoriteContract.BASE_CONTENT_URI, whereClause, whereArgs);
                
                if(deleteDb > 0) {
                  snackbarString = "Movie delete success";
                }
                else {
                  snackbarString = "Movie delete error";
                }
              }
              else if (tagNumber == BUTTON_FAB_OFF) {
                btnAddToFavorite.setImageResource(BUTTON_FAB_ON);
                btnAddToFavorite.setTag(BUTTON_FAB_ON);
  
                ContentValues val = createContentValues(objMovie);
  
                Uri insertDb = getActivity()
                    .getContentResolver()
                    .insert(MovieFavoriteContract.BASE_CONTENT_URI, val);
  
                if(insertDb.toString().contains("Failed to add record")) {
                  snackbarString = "Movie insert error";
                }
                else {
                  snackbarString = "Movie insert success";
                }
              }
              
              Snackbar.make(lytMain, snackbarString, Snackbar.LENGTH_SHORT).show();
            }
          });
    }

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

  private ContentValues createContentValues(MovieObject obj) {
    ContentValues val = new ContentValues();
  
    val.put(MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_ID, obj.getObjectId());
    val.put(MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_TITLE, obj.getObjectTitle());
    val.put(MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_POSTER_PATH, obj.getObjectPosterPath());
    val.put(MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_DESCRIPTION, obj.getObjectDescription());
    val.put(MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_RATING, obj.getObjectRating());
    val.put(MovieFavoriteContract.MovieFavoriteEntry.COLUMN_MOVIE_RELEASE_DATE, obj.getObjectReleaseDate());
    
    return val;
  }
  
  private void setFavoriteButton(MovieObject obj) {
    /* THIS IS THE OLD WAY (CONSUME DBHELPER & DB INSTANTLY) */
//    MovieFavoriteDbHelper dbHelper = new MovieFavoriteDbHelper(getActivity().getApplicationContext());
//    SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//    boolean isExist = dbHelper.isExistMovieObject(db, obj);
    /* END OF THE OLD WAY */

    Uri uri = MovieFavoriteContract.MovieFavoriteEntry.buildFavoriteUriWithMovieId(obj.getObjectId());
  
    Cursor cursor = getActivity().getContentResolver().query(
      uri,
      null,
      null,
      null,
      null,
      null
    );
    
    boolean isExist = (cursor != null && cursor.moveToFirst());
    
    if(!cursor.isClosed()) {
      cursor.close();
    }
    
    if(isExist) {
      btnAddToFavorite.setImageResource(BUTTON_FAB_ON);
      btnAddToFavorite.setTag(BUTTON_FAB_ON);
    }
    else {
      btnAddToFavorite.setImageResource(BUTTON_FAB_OFF);
      btnAddToFavorite.setTag(BUTTON_FAB_OFF);
    }

//    db.close();
  }

  private Observable<Integer> createButtonClickObservable() {
    return Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(@NonNull final ObservableEmitter<Integer> e) throws Exception {
        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int btnTag = Integer.parseInt(btnAddToFavorite.getTag().toString());
            e.onNext(btnTag);
          }
        });

        e.setCancellable(new Cancellable() {
          @Override
          public void cancel() throws Exception {
            btnAddToFavorite.setOnClickListener(null);
          }
        });
      }
    });
  }
}
