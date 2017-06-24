package com.example.standarduser.popularmoviestmdbv4.app;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.MovieObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Standard user on 6/24/2017.
 */

public class AdapterMovieObject extends RecyclerView.Adapter<AdapterMovieObject.AdapterMovieObjectViewHolder> {
  private static final String LOG_TAG = AdapterMovieObject.class.getSimpleName();
  private static final String imageUrl = "http://image.tmdb.org/t/p/";
  private static final String imageSize = "w185";

  private List<MovieObject> listMovie;
  private MovieObject currentMovieObject;

  private clickHandler listener;

  public interface clickHandler {
    void onImageViewClick(MovieObject obj);
  }

  public AdapterMovieObject(List<MovieObject> listMovie) {
    this.listMovie = listMovie;
  }

  public void setOnImageViewClick(clickHandler listener) {
    this.listener = listener;
  }

  @Override
  public AdapterMovieObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    int idLayout = R.layout.adapter_movieobject_listitem;

    View view = inflater.inflate(idLayout, parent, false);

    return new AdapterMovieObjectViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AdapterMovieObjectViewHolder holder, int position) {
    currentMovieObject = listMovie.get(position);

    Uri uri = Uri.parse(imageUrl + imageSize + currentMovieObject.getObjectPosterPath());

    //TODO turn this on for debug purpose
//    Log.d(LOG_TAG, "The Uri is: " + uri.toString());

    Picasso.with(holder.imgListItem.getContext())
        .load(uri)
        .into(holder.imgListItem);

    holder.imgListItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(listener != null) {
          listener.onImageViewClick(currentMovieObject);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    if(listMovie == null) {
      return 0;
    }
    else {
      return listMovie.size();
    }
  }

  public class AdapterMovieObjectViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imgListItem;

    public AdapterMovieObjectViewHolder(View itemView) {
      super(itemView);
      imgListItem = (ImageView) itemView.findViewById(R.id.movieobject_listitem_imagemovie);
    }
  }
}
