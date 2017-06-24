package com.example.standarduser.popularmoviestmdbv4.backend;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Standard user on 6/23/2017.
 */

public class MovieObject implements Parcelable {
  @SerializedName("adult")
  private Boolean ObjectIsAdultRated;

  @SerializedName("backdrop_path")
  private String ObjectBackdropPath;

  @SerializedName("genre_ids")
  private List<Integer> ObjectGenreIds;

  @SerializedName("id")
  private Integer ObjectId;

  @SerializedName("original_language")
  private String ObjectOriginalLanguage;

  @SerializedName("original_title")
  private String ObjectOriginalTitle;

  @SerializedName("overview")
  private String ObjectDescription;

  @SerializedName("popularity")
  private float ObjectPopularity;

  @SerializedName("poster_path")
  private String ObjectPosterPath;

  @SerializedName("release_date")
  private String ObjectReleaseDate;

  @SerializedName("runtime")
  private Integer ObjectRuntime;

  @SerializedName("title")
  private String ObjectTitle;

  @SerializedName("video")
  private Boolean ObjectHasVideo;

  @SerializedName("vote_average")
  private float ObjectRating;

  @SerializedName("vote_count")
  private Integer ObjectVoteCount;

  //PARCELABLE IMPLEMENTATION
  @Override
  public int describeContents() {
    return 0;
  }

  //Write to Parcel with the order or serialization
  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(getObjectIsAdultRated() ? 1 : 0);
    dest.writeString(getObjectBackdropPath());
    dest.writeList(getObjectGenreIds());
    dest.writeInt(getObjectId());
    dest.writeString(getObjectOriginalLanguage());
    dest.writeString(getObjectOriginalTitle());
    dest.writeString(getObjectDescription());
    dest.writeFloat(getObjectPopularity());
    dest.writeString(getObjectPosterPath());
    dest.writeString(getObjectReleaseDate());
    dest.writeInt(getObjectRuntime() == null ? 0 : getObjectRuntime());
    dest.writeString(getObjectTitle());
    dest.writeInt(getObjectHasVideo() ? 1 : 0);
    dest.writeFloat(getObjectRating());
    dest.writeInt(getObjectVoteCount());
  }

  //Read from Parcel with the order of serialization
  protected MovieObject(Parcel in) {
    setObjectIsAdultRated(in.readInt() == 1);
    setObjectBackdropPath(in.readString());
    in.readList(ObjectGenreIds, List.class.getClassLoader());
    setObjectId(in.readInt());
    setObjectOriginalLanguage(in.readString());
    setObjectOriginalTitle(in.readString());
    setObjectDescription(in.readString());
    setObjectPopularity(in.readFloat());
    setObjectPosterPath(in.readString());
    setObjectReleaseDate(in.readString());
    setObjectRuntime(in.readInt());
    setObjectTitle(in.readString());
    setObjectHasVideo(in.readInt() == 1);
    setObjectRating(in.readFloat());
    setObjectVoteCount(in.readInt());
  }

  public static final Creator<MovieObject> CREATOR = new Creator<MovieObject>() {
    @Override
    public MovieObject createFromParcel(Parcel in) {
      return new MovieObject(in);
    }

    @Override
    public MovieObject[] newArray(int size) {
      return new MovieObject[size];
    }
  };

  //GETTER & SETTER HERE
  public Boolean getObjectIsAdultRated() {
    return ObjectIsAdultRated;
  }

  public void setObjectIsAdultRated(Boolean objectIsAdultRated) {
    ObjectIsAdultRated = objectIsAdultRated;
  }

  public String getObjectBackdropPath() {
    return ObjectBackdropPath;
  }

  public void setObjectBackdropPath(String objectBackdropPath) {
    ObjectBackdropPath = objectBackdropPath;
  }

  public List<Integer> getObjectGenreIds() {
    return ObjectGenreIds;
  }

  public void setObjectGenreIds(List<Integer> objectGenreIds) {
    ObjectGenreIds = objectGenreIds;
  }

  public Integer getObjectId() {
    return ObjectId;
  }

  public void setObjectId(Integer objectId) {
    ObjectId = objectId;
  }

  public String getObjectOriginalLanguage() {
    return ObjectOriginalLanguage;
  }

  public void setObjectOriginalLanguage(String objectOriginalLanguage) {
    ObjectOriginalLanguage = objectOriginalLanguage;
  }

  public String getObjectOriginalTitle() {
    return ObjectOriginalTitle;
  }

  public void setObjectOriginalTitle(String objectOriginalTitle) {
    ObjectOriginalTitle = objectOriginalTitle;
  }

  public String getObjectDescription() {
    return ObjectDescription;
  }

  public void setObjectDescription(String objectDescription) {
    ObjectDescription = objectDescription;
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

  public String getObjectReleaseDate() {
    return ObjectReleaseDate;
  }

  public void setObjectReleaseDate(String objectReleaseDate) {
    ObjectReleaseDate = objectReleaseDate;
  }

  public Integer getObjectRuntime() {
    return ObjectRuntime;
  }

  public void setObjectRuntime(Integer objectRuntime) {
    ObjectRuntime = objectRuntime;
  }

  public String getObjectTitle() {
    return ObjectTitle;
  }

  public void setObjectTitle(String objectTitle) {
    ObjectTitle = objectTitle;
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

  public Integer getObjectVoteCount() {
    return ObjectVoteCount;
  }

  public void setObjectVoteCount(Integer objectVoteCount) {
    ObjectVoteCount = objectVoteCount;
  }


}
