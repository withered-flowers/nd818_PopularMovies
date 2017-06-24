package com.example.standarduser.popularmoviestmdbv4.app;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.standarduser.popularmoviestmdbv4.R;

public class ActivityMain extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if(savedInstanceState == null) {
      FragmentTransaction trx = getFragmentManager().beginTransaction();
      FragmentGridMovie fmtGridMovie = new FragmentGridMovie();

      trx.replace(R.id.main_framelayout, fmtGridMovie);
      trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      trx.commit();
    }
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
      FragmentTransaction trx = getFragmentManager().beginTransaction();
      FragmentSettings fmtSettings = new FragmentSettings();

      trx.replace(R.id.main_framelayout, fmtSettings);
      trx.addToBackStack(null);
      trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      trx.commit();
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if(getFragmentManager().getBackStackEntryCount() > 0)
      getFragmentManager().popBackStack();
    else
      super.onBackPressed();
  }
}
