package com.example.standarduser.popularmoviestmdbv4.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieTrailer;

import java.util.List;

/**
 * Created by Standard user on 7/13/2017.
 */

public class AdapterMovieTrailer extends RecyclerView.Adapter<AdapterMovieTrailer.AdapterMovieTrailerViewHolder> {
  private List<MovieTrailer> listTrailer;
  private MovieTrailer currentMovieTrailer;

  private clickHandler listener;

  public AdapterMovieTrailer(List<MovieTrailer> listTrailer) {
    this.listTrailer = listTrailer;
  }

  public interface clickHandler {
    void onLayoutClick(MovieTrailer obj);
  }

  @Override
  public AdapterMovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    int idLayout = R.layout.adapter_moviedetail_trailer;

    View view = inflater.inflate(idLayout, parent, false);

    return new AdapterMovieTrailerViewHolder(view);
  }

  public void setOnLayoutClick(clickHandler listener) {
    this.listener = listener;
  }

  @Override
  public void onBindViewHolder(final AdapterMovieTrailerViewHolder holder, int position) {
    currentMovieTrailer = listTrailer.get(position);

    holder.txtTrailer.setText(currentMovieTrailer.getTrailerName());

    holder.lytTrailer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(listener != null) {
          listener.onLayoutClick(listTrailer.get(holder.getAdapterPosition()));
        }
      }
    });
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
    public final LinearLayout lytTrailer;
    public final ImageView imgTrailer;
    public final TextView txtTrailer;

    public AdapterMovieTrailerViewHolder(View itemView) {
      super(itemView);
      lytTrailer = (LinearLayout) itemView.findViewById(R.id.moviedetail_trailer_trailerlayout);
      imgTrailer = (ImageView) itemView.findViewById(R.id.moviedetail_trailer_trailerimage);
      txtTrailer = (TextView) itemView.findViewById(R.id.moviedetail_trailer_trailername);
    }
  }
}
