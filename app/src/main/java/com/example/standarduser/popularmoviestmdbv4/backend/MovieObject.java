package com.example.standarduser.popularmoviestmdbv4.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Standard user on 6/23/2017.
 */

public class MovieObject {
  @SerializedName("vote_count")
  private Integer ObjectVoteCount;

  @SerializedName("id")
  private Integer ObjectId;

  @SerializedName("video")
  private Boolean ObjectHasVideo;

  @SerializedName("vote_average")
  private float ObjectRating;

  @SerializedName("title")
  private String ObjectTitle;

  @SerializedName("popularity")
  private float ObjectPopularity;

  @SerializedName("poster_path")
  private String ObjectPosterPath;

  @SerializedName("original_language")
  private String ObjectOriginalLanguage;

  @SerializedName("genre_ids")
  private Integer[] ObjectGenreIds;

  @SerializedName("backdrop_path")
  private String ObjectBackdropPath;

  @SerializedName("adult")
  private Boolean ObjectIsAdultRated;

  @SerializedName("overview")
  private String ObjectDescription;

  @SerializedName("release_date")
  private String ObjectReleaseDate;

  public Integer getObjectVoteCount() {
    return ObjectVoteCount;
  }

  public void setObjectVoteCount(Integer objectVoteCount) {
    ObjectVoteCount = objectVoteCount;
  }

  public Integer getObjectId() {
    return ObjectId;
  }

  public void setObjectId(Integer objectId) {
    ObjectId = objectId;
  }

  public Boolean getObjectHasVideo() {
    return ObjectHasVideo;
  }

  public void setObjectHasVideo(Boolean objectHasVideo) {
    ObjectHasVideo = objectHasVideo;
  }

  public float getObjectRating() {
    return ObjectRating;
  }

  public void setObjectRating(float objectRating) {
    ObjectRating = objectRating;
  }

  public String getObjectTitle() {
    return ObjectTitle;
  }

  public void setObjectTitle(String objectTitle) {
    ObjectTitle = objectTitle;
  }

  public float getObjectPopularity() {
    return ObjectPopularity;
  }

  public void setObjectPopularity(float objectPopularity) {
    ObjectPopularity = objectPopularity;
  }

  public String getObjectPosterPath() {
    return ObjectPosterPath;
  }

  public void setObjectPosterPath(String objectPosterPath) {
    ObjectPosterPath = objectPosterPath;
  }

  public String getObjectOriginalLanguage() {
    return ObjectOriginalLanguage;
  }

  public void setObjectOriginalLanguage(String objectOriginalLanguage) {
    ObjectOriginalLanguage = objectOriginalLanguage;
  }

  public Integer[] getObjectGenreIds() {
    return ObjectGenreIds;
  }

  public void setObjectGenreIds(Integer[] objectGenreIds) {
    ObjectGenreIds = objectGenreIds;
  }

  public String getObjectBackdropPath() {
    return ObjectBackdropPath;
  }

  public void setObjectBackdropPath(String objectBackdropPath) {
    ObjectBackdropPath = objectBackdropPath;
  }

  public Boolean getObjectIsAdultRated() {
    return ObjectIsAdultRated;
  }

  public void setObjectIsAdultRated(Boolean objectIsAdultRated) {
    ObjectIsAdultRated = objectIsAdultRated;
  }

  public String getObjectDescription() {
    return ObjectDescription;
  }

  public void setObjectDescription(String objectDescription) {
    ObjectDescription = objectDescription;
  }

  public String getObjectReleaseDate() {
    return ObjectReleaseDate;
  }

  public void setObjectReleaseDate(String objectReleaseDate) {
    ObjectReleaseDate = objectReleaseDate;
  }
}
