package com.android.aa45.coderevision.Adapters;

public class TopicsDataHolder {
    String Topic;
    int num;

    public TopicsDataHolder() {
    }

    public TopicsDataHolder(String topic, int num) {
        Topic = topic;
        this.num = num;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
