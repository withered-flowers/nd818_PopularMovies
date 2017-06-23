package com.example.standarduser.popularmoviestmdbv4.backend;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Standard user on 6/23/2017.
 */

public class MovieFetcher {
  Retrofit theFetcher;
  final String BASE_URL = "https://api.themoviedb.org/3/";

  public MovieFetcher() {
    theFetcher = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public Retrofit getFetcher() {
    return theFetcher;
  }
}
