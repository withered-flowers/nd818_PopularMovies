package com.example.standarduser.popularmoviestmdbv4.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.standarduser.popularmoviestmdbv4.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_gridmovie, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();

    if(itemId == R.id.action_gridmovie_sortby) {
      getFragmentManager().beginTransaction()
          .add(android.R.id.content, new SettingsFragment())
          .commit();
    }

    return super.onOptionsItemSelected(item);
  }
}
