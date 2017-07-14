package com.example.standarduser.popularmoviestmdbv4.backend.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Standard user on 6/23/2017.
 */

public class Fetcher {
  Retrofit theFetcher;
  final String BASE_URL = "https://api.themoviedb.org/3/";

  public Fetcher() {
    theFetcher = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public Retrofit getFetcher() {
    return theFetcher;
  }
}
