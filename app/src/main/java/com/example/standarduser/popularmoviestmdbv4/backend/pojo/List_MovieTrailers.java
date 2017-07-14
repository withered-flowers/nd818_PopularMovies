package com.example.standarduser.popularmoviestmdbv4.backend.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by standard on 7/7/17.
 */

public class List_MovieTrailers {
  @SerializedName("id")
  private Integer movieId;

  @SerializedName("results")
  private List<MovieTrailer> trailerResults;

  public Integer getMovieId() {
    return movieId;
  }

  public void setMovieId(Integer movieId) {
    this.movieId = movieId;
  }

  public List<MovieTrailer> getTrailerResults() {
    return trailerResults;
  }

  public void setTrailerResults(List<MovieTrailer> trailerResults) {
    this.trailerResults = trailerResults;
  }
}
