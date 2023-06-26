package com.android.aa45.coderevision.Firebase;

public class DataHolder {
    String title,link,date,difficulty,tag;
    public DataHolder() {
    }

    public DataHolder(String title, String link, String date, String difficulty, String tag) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.difficulty = difficulty;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
