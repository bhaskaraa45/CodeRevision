package com.bhaskar.aa45.coderevision.Firebase;

public class DataHolder {
    String title,link,date,difficulty,tag,code,slNo,tab,summary;
    public DataHolder() {
    }

    public DataHolder(String title, String link, String date, String difficulty, String tag,String code,String slNo,String tab,String summary) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.difficulty = difficulty;
        this.tag = tag;
        this.code = code;
        this.slNo=slNo;
        this.tab=tab;
        this.summary =summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
