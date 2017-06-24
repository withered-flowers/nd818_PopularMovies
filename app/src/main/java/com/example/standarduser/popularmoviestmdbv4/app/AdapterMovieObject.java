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

  public AdapterMovieObject(List<MovieObject> listMovie) {
    this.listMovie = listMovie;
  }

  @Override
  public AdapterMovieObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    int idLayout = R.layout.adapter_movieobject_listitem;
    boolean flagAttachImmediate = false;

    View view = inflater.inflate(idLayout, parent, flagAttachImmediate);

    return new AdapterMovieObjectViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AdapterMovieObjectViewHolder holder, int position) {
    MovieObject currentMovieObject = listMovie.get(position);

    Uri uri = Uri.parse(imageUrl + imageSize + currentMovieObject.getObjectPosterPath());

//    TODO turn this on for debug purpose
//    Log.d(LOG_TAG, "The Uri is: " + uri.toString());

    Picasso.with(holder.imgListItem.getContext())
        .load(uri)
        .into(holder.imgListItem);
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