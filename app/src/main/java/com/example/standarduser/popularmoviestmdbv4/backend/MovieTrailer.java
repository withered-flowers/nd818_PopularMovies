package com.example.standarduser.popularmoviestmdbv4.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by standard on 7/7/17.
 */

public class MovieTrailer {
  @SerializedName("id")
  private String trailerId;

  @SerializedName("iso_639_1")
  private String trailer_iso_639_1;

  @SerializedName("iso_3166_1")
  private String trailer_iso_3166_1;

  @SerializedName("key")
  private String trailerKey;

  @SerializedName("name")
  private String trailerName;

  @SerializedName("site")
  private String trailerSite;

  @SerializedName("size")
  private Integer trailerSize;

  @SerializedName("type")
  private String trailerType;

  public String getTrailerId() {
    return trailerId;
  }

  public void setTrailerId(String trailerId) {
    this.trailerId = trailerId;
  }

  public String getTrailer_iso_639_1() {
    return trailer_iso_639_1;
  }

  public void setTrailer_iso_639_1(String trailer_iso_639_1) {
    this.trailer_iso_639_1 = trailer_iso_639_1;
  }

  public String getTrailer_iso_3166_1() {
    return trailer_iso_3166_1;
  }

  public void setTrailer_iso_3166_1(String trailer_iso_3166_1) {
    this.trailer_iso_3166_1 = trailer_iso_3166_1;
  }

  public String getTrailerKey() {
    return trailerKey;
  }

  public void setTrailerKey(String trailerKey) {
    this.trailerKey = trailerKey;
  }

  public String getTrailerName() {
    return trailerName;
  }

  public void setTrailerName(String trailerName) {
    this.trailerName = trailerName;
  }

  public String getTrailerSite() {
    return trailerSite;
  }

  public void setTrailerSite(String trailerSite) {
    this.trailerSite = trailerSite;
  }

  public Integer getTrailerSize() {
    return trailerSize;
  }

  public void setTrailerSize(Integer trailerSize) {
    this.trailerSize = trailerSize;
  }

  public String getTrailerType() {
    return trailerType;
  }

  public void setTrailerType(String trailerType) {
    this.trailerType = trailerType;
  }
}
