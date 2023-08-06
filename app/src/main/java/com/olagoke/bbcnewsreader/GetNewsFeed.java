package com.olagoke.bbcnewsreader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetNewsFeed extends AsyncTask<String, Void, ArrayList<News>>  {
  private MainActivity activity;
  private String url;
  private XmlPullParserFactory xmlFactoryObject;
  private ProgressDialog pDialog;

  private ArrayList<News> newsItems = new ArrayList<>();

  public GetNewsFeed(MainActivity activity, String url) {
    this.activity = activity;
    this.url = url;
  }


  public ArrayList<News> getNewsItems() {
    return newsItems;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    pDialog = new ProgressDialog(activity);
    pDialog.setTitle("Get News Information from XML");
    pDialog.setMessage("Loading...");
    pDialog.show();
  }


  @Override
  protected ArrayList<News> doInBackground(String... urls) {

    try {
      URL url = new URL(urls[0]);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      InputStream inputStream = connection.getInputStream();

      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(false);
      XmlPullParser parser = factory.newPullParser();
      parser.setInput(inputStream, null);

      int eventType = parser.getEventType();
      News currentNewsItem = null;
      ArrayList<String> newsBuffer=new ArrayList<>();

      while (eventType != XmlPullParser.END_DOCUMENT) {
        String tagName = parser.getName();

        switch (eventType) {
          case XmlPullParser.START_TAG:
            if ("item".equals(tagName)) {
              currentNewsItem = new News(); // Create a new News object for each <item>
            }
            break;
          case XmlPullParser.TEXT:
            if (currentNewsItem != null) {
              String text = parser.getText();
              newsBuffer.add(text);
              Log.i("addCount",text);

              Log.i("test",text);
              if(text!=null){
              }

            }
            break;
          case XmlPullParser.END_TAG:
            if (currentNewsItem != null && "item".equals(tagName)) {


             for (int i = 1; i < newsBuffer.size(); i += 6) {
               if(i==1){
                Log.i("cleanPrint", newsBuffer.get(i+2));
               }
                String title = newsBuffer.get(i);
                String description = newsBuffer.get(i + 2);
                String link = newsBuffer.get(i + 4);
                String date = newsBuffer.get(i + 8);
                newsItems.add(new News(title, description, link, date));
              }



              //newsBuffer.clear();
              //currentNewsItem = null;

            }
            break;
        }
        eventType = parser.next();
      }

    } catch (Exception e) {
      Log.e("XmlParserTask", "Error: " + e.getMessage());
    }

    return newsItems;
  }
  @Override
  protected void onPostExecute(ArrayList<News> newsItems) {
    // Here you can update your UI with the parsed news items.
    pDialog.dismiss();

Log.i("sizeEl",""+newsItems.size());
  }
}
