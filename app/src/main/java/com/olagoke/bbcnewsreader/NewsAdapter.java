package com.olagoke.bbcnewsreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

  public NewsAdapter(Context context, ArrayList<News> news) {
    super(context, 0, news);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    News news = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
    }
    // Lookup view for data population


    TextView title = (TextView) convertView.findViewById(R.id.title);



    // Populate the data into the template view using the data object
    title.setText(news.getTitle());
 /*     description.setText(news.getDescription());
  url.setText(news.getUrl());
    date.setText(news.getDate());*/


    // Return the completed view to render on screen
    return convertView;
  }
}
