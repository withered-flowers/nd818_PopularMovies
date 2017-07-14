package com.example.standarduser.popularmoviestmdbv4.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.standarduser.popularmoviestmdbv4.R;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.MovieReview;

import java.util.List;

/**
 * Created by Standard user on 7/13/2017.
 */

public class AdapterMovieReview extends RecyclerView.Adapter<AdapterMovieReview.AdapterMovieReviewViewHolder> {
  private List<MovieReview> listReview;
  private MovieReview currentMovieReview;

  public AdapterMovieReview(List<MovieReview> listReview) {
    this.listReview = listReview;
  }

  @Override
  public AdapterMovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    int idLayout = R.layout.adapter_moviedetail_review;

    View view = inflater.inflate(idLayout, parent, false);

    return new AdapterMovieReviewViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AdapterMovieReviewViewHolder holder, int position) {
    currentMovieReview = listReview.get(position);

    holder.txtAuthor.setText(currentMovieReview.getReviewAuthor());
    holder.txtReview.setText(currentMovieReview.getReviewContent());
  }

  @Override
  public int getItemCount() {
    if(listReview == null) {
      return 0;
    }
    else {
      return listReview.size();
    }
  }

  public class AdapterMovieReviewViewHolder extends RecyclerView.ViewHolder {
    public final TextView txtAuthor;
    public final TextView txtReview;

    public AdapterMovieReviewViewHolder(View itemView) {
      super(itemView);
      txtAuthor = (TextView) itemView.findViewById(R.id.moviedetail_review_reviewauthor);
      txtReview = (TextView) itemView.findViewById(R.id.moviedetail_review_reviewcontent);
    }
  }
}
