package com.example.standarduser.popularmoviestmdbv4.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieTrailer;

import java.util.List;

/**
 * Created by Standard user on 7/13/2017.
 */

public class AdapterMovieTrailer extends RecyclerView.Adapter<AdapterMovieTrailer.AdapterMovieTrailerViewHolder> {
  private List<MovieTrailer> listTrailer;
  private MovieTrailer currentMovieTrailer;

  @Override
  public AdapterMovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(AdapterMovieTrailerViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    if(listTrailer == null) {
      return 0;
    }
    else {
      return listTrailer.size();
    }
  }

  public class AdapterMovieTrailerViewHolder extends RecyclerView.ViewHolder {
    //TODO LinearLayout onClick to open the trailer!
    public final ImageView imgTrailer;

    public AdapterMovieTrailerViewHolder(View itemView) {
      super(itemView);
      imgTrailer = (ImageView) itemView.findViewById(R.id.moviedetail_trailer_trailerimage);
    }
  }
}
