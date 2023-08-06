package com.olagoke.bbcnewsreader;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class FavoritesActivity extends FragmentActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null){
      getSupportFragmentManager().beginTransaction()
        .add(android.R.id.content, new Favorites()).commit();}
  }
}
