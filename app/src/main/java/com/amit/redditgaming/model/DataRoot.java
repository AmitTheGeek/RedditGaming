package com.amit.redditgaming.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DataRoot implements Parcelable {

    public int dist;
    private List<Children> children;
    private String after;

    protected DataRoot(Parcel in) {
        dist = in.readInt();
    }

    public static final Creator<DataRoot> CREATOR = new Creator<DataRoot>() {
        @Override
        public DataRoot createFromParcel(Parcel in) {
            return new DataRoot(in);
        }

        @Override
        public DataRoot[] newArray(int size) {
            return new DataRoot[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dist);
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
