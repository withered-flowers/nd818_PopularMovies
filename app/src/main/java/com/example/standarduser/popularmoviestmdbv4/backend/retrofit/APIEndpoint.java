package com.example.standarduser.popularmoviestmdbv4.backend.retrofit;

import com.example.standarduser.popularmoviestmdbv4.backend.pojo.List_MovieObjects;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.List_MovieReviews;
import com.example.standarduser.popularmoviestmdbv4.backend.pojo.List_MovieTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Standard user on 6/23/2017.
 */

public interface APIEndpoint {
  @GET("movie/{sortBy}")
  Call<List_MovieObjects> getMoviesBySort(@Path("sortBy") String sortBy, @Query("api_key") String apiKey);

  @GET("movie/{id}/reviews")
  Call<List_MovieReviews> getMovieReviewsById(@Path ("id") int id, @Query ("api_key") String apiKey);

  @GET("movie/{id}/videos")
  Call<List_MovieTrailers> getMovieTrailersById(@Path ("id") int id, @Query ("api_key") String apiKey);
}
