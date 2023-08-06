package com.olagoke.bbcnewsreader;

import java.util.Arrays;

public class Newsbackup {



    private String[] title;

    private String description;
    private String url;
    private  String date;


    public void setDescription(String description) {
      this.description = description;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public Newsbackup() {
    }

    public Newsbackup(String [] title, String description, String url, String date) {
      this.title = title;
      this.description = description;
      this.url = url;
      this.date = date;
    }


    public void appendString(String value) {
      title = Arrays.copyOf(title, title.length + 1);
      title[title.length - 1] = value;
    }

    public String[] getTitle() {
      return title;
    }

    public String getDescription() {
      return description;
    }

    public String getUrl() {
      return url;
    }

    public String getDate() {
      return date;
    }

    @Override
    public String toString() {
      return "Title: " + getTitle() + "\n" +
        "Description: " + getDescription() + "\n" +
        "Link: " + getUrl() + "\n" +
        "Publication Date: " + getDate();
    }



}
