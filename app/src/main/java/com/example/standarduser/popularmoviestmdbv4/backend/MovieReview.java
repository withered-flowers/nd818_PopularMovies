package com.example.standarduser.popularmoviestmdbv4.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by standard on 7/7/17.
 */

public class MovieReview {
  @SerializedName("id")
  private String reviewId;

  @SerializedName("author")
  private String reviewAuthor;

  @SerializedName("content")
  private String reviewContent;

  @SerializedName("url")
  private String reviewUrl;

  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public String getReviewAuthor() {
    return reviewAuthor;
  }

  public void setReviewAuthor(String reviewAuthor) {
    this.reviewAuthor = reviewAuthor;
  }

  public String getReviewContent() {
    return reviewContent;
  }

  public void setReviewContent(String reviewContent) {
    this.reviewContent = reviewContent;
  }

  public String getReviewUrl() {
    return reviewUrl;
  }

  public void setReviewUrl(String reviewUrl) {
    this.reviewUrl = reviewUrl;
  }
}
