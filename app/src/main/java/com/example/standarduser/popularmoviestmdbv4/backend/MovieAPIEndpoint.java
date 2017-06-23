package com.example.standarduser.popularmoviestmdbv4.backend;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Standard user on 6/23/2017.
 */

public interface MovieAPIEndpoint {
  @GET("movie/{sortBy}")
  Call<MovieList> getMoviesBySort(@Path("sortBy") String sortBy, @Query("api_key") String apiKey);

  @GET("movie/{id}")
  Call<MovieList> getMoviesById(@Path ("id") int id, @Query ("api_key") String apiKey);
}
