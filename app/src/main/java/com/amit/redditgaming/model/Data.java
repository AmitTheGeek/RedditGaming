package com.amit.redditgaming.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    private String title;
    private String subreddit;
    private int score;
    private String permalink;
    private String thumbnail;

    public Data(String title, String subreddit, int score, String permalink, String thumbnail) {
        this.title = title;
        this.subreddit = subreddit;
        this.score = score;
        this.permalink = permalink;
        this.thumbnail = thumbnail;
    }

    protected Data(Parcel in) {
        title = in.readString();
        subreddit = in.readString();
        score = in.readInt();
        permalink = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPermalink() { return permalink; }

    public void setPermalink(String permalink) { this.permalink = permalink; }

    public String getThumbnail() { return thumbnail; }

    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subreddit);
        dest.writeInt(score);
        dest.writeString(permalink);
        dest.writeString(thumbnail);
    }
}
