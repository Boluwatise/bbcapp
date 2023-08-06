package com.olagoke.bbcnewsreader;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

  private DrawerLayout mDrawer;
  private Toolbar toolbar;
  private NavigationView nvDrawer;

  // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
  private ActionBarDrawerToggle drawerToggle;

  private final static String url = "https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
  private ListView listView;
  private NewsAdapter newsAdapter;
  ArrayList<News> newsList;


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    SharedPreferences prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);

    toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // This will display an Up icon (<-), we will replace it with hamburger later
    // Find our drawer view
    mDrawer =  findViewById(R.id.drawer_layout);
    drawerToggle = setupDrawerToggle();

    // Setup toggle to display hamburger icon with nice animation
    drawerToggle.setDrawerIndicatorEnabled(true);
    drawerToggle.syncState();

    // Tie DrawerLayout events to the ActionBarToggle
    mDrawer.addDrawerListener(drawerToggle);

    // Find our drawer view
    mDrawer =  findViewById(R.id.drawer_layout);
    nvDrawer = findViewById(R.id.nvView);
    // Setup drawer view
    setupDrawerContent(nvDrawer);

      boolean isFirstLaunch = prefs.getBoolean("is_first_launch", true);

/*      if (isFirstLaunch) {
        // Run the method you want to execute on the first launch here
        // ...
        DBHelper dbHelper = new DBHelper(this);

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {

          dbHelper.seedDatabase(db);
        } catch (SQLiteException e) {
          // Handle database errors here
          throw (e);
        }*/
    GetNewsFeed getNewsFeed = new GetNewsFeed(this, url);
    listView = findViewById(R.id.news_list);

    try {
      getNewsFeed.execute(url).get();
      newsAdapter = new NewsAdapter(this,getNewsFeed.getNewsItems());
      listView.setAdapter(newsAdapter);

    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }



    Log.i("size",""+getNewsFeed.getNewsItems().size());


    // Set the flag to indicate that the app has been launched
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_launch", false);
        editor.apply();
      }






  private ActionBarDrawerToggle setupDrawerToggle() {
    // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
    // and will not render the hamburger icon without it.
    return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
      new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
          selectDrawerItem(menuItem);
          return true;
        }
      });
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_info, menu);
    return true;
  }

  public void selectDrawerItem(MenuItem menuItem) {
    Class<?> activityClass = null;
    int itemId = menuItem.getItemId();

    if (itemId == R.id.fragment_host) {
      activityClass = MainActivity.class;
    } else if (itemId == R.id.nav_second_fragment) {
      activityClass = FavoritesActivity.class;
    } else if (itemId == R.id.nav_third_fragment) {
      finishAffinity();
      return;
    } else {
      return;
    }

    // Check if the selected activity is already running
    if (!activityClass.isInstance(this)) {
      Intent intent = new Intent(this, activityClass);
      startActivity(intent);
      finish();
    }

    // Highlight the selected item has been done by NavigationView
    //menuItem.setChecked(true);
    // Set action bar title
    setTitle(menuItem.getTitle());
    // Close the navigation drawer
    mDrawer.closeDrawers();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // The action bar home/up action should open or close the drawer.
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawer.openDrawer(GravityCompat.START);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {

    finishAffinity();

  }









}
