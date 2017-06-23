package com.example.standarduser.popularmoviestmdbv4.app;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.standarduser.popularmoviestmdbv4.R;

public class FragmentSettings extends PreferenceFragment {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.xml_gridmovie_sortby);
  }
}