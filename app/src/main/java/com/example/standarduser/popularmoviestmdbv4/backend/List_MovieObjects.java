package com.example.standarduser.popularmoviestmdbv4.backend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Standard user on 6/23/2017.
 */

public class List_MovieObjects {
  @SerializedName("page")
  private Integer listPageCurrent;

  @SerializedName("total_pages")
  private Integer listPageTotal;

  @SerializedName("total_results")
  private Integer listTotalResults;

  @SerializedName("results")
  private List<MovieObject> listMovieObject;

  public Integer getListPageCurrent() {
    return listPageCurrent;
  }

  public void setListPageCurrent(Integer listPageCurrent) {
    this.listPageCurrent = listPageCurrent;
  }

  public Integer getListPageTotal() {
    return listPageTotal;
  }

  public void setListPageTotal(Integer listPageTotal) {
    this.listPageTotal = listPageTotal;
  }

  public Integer getListTotalResults() {
    return listTotalResults;
  }

  public void setListTotalResults(Integer listTotalResults) {
    this.listTotalResults = listTotalResults;
  }

  public List<MovieObject> getListMovieObject() {
    return listMovieObject;
  }

  public void setListMovieObject(List<MovieObject> listMovieObject) {
    this.listMovieObject = listMovieObject;
  }
}
