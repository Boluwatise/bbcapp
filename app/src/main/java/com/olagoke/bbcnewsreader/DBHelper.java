package com.olagoke.bbcnewsreader;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

  /**
   * The Db helper helps us in managing the sqlite database
   *
   * @author  Alex Olagoke
   * @version 1.0
   * @since   2023-07-14
   */
  private static final String DATABASE_NAME = "FavoriteNews.db";
  private static final int DATABASE_VERSION = 1;

  private static final String CREATE_TABLE_NEWS = "CREATE TABLE news (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, date TEXT, link TEXT)";




  public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_NEWS);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS news");
    onCreate(db);
  }


  public ArrayList<News> getFavoriteNews() {
    SQLiteDatabase db = this.getReadableDatabase();
    ArrayList<News> newsList = new ArrayList<>();
    String query = "SELECT * FROM news ";
    Cursor cursor = db.rawQuery(query,null);
    //printCursor(cursor);
    int count=0;
    while (cursor.moveToNext()) {

      String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
      String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
      String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
      String link = cursor.getString(cursor.getColumnIndexOrThrow("link"));
      //News news = new News(title, description,date,link);
      //newsList.add(news);
      count++;

    }
    Log.i("Logs","Number of results in the cursor "+count);
    cursor.close();
    return newsList;
  }



  public void seedDatabase(SQLiteDatabase db) {

    ContentValues newsValues = new ContentValues();
    newsValues.put("title", "Mark Margolis: Breaking Bad and Better Call Saul actor dies at 83");
    newsValues.put("description", "Mark Margolis: Breaking Bad and Better Call Saul actor dies at 83");
    newsValues.put("date", "08/04/2023");
    newsValues.put("link", "https://www.bbc.com/news/world-us-canada-66411829");
    db.insert("news", null, newsValues);

    newsValues = new ContentValues();
    newsValues.put("title", "Maine woman, 87, fights off then feeds hungry burglar");
    newsValues.put("description", "Maine woman, 87, fights off then feeds hungry burglar");
    newsValues.put("date", "08/04/2023");
    newsValues.put("link", "https://www.bbc.com/news/world-us-canada-66410846");
    db.insert("news", null, newsValues);

    newsValues = new ContentValues();
    newsValues.put("title", "Forced to wait by the judge, Trump is out of his comfort zone");
    newsValues.put("description", "Forced to wait by the judge, Trump is out of his comfort zone");
    newsValues.put("date", "08/04/2023");
    newsValues.put("link", "https://www.bbc.com/news/world-us-canada-66399284");
    db.insert("news", null, newsValues);
  }


  }
