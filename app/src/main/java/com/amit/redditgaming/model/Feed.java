package com.amit.redditgaming.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {

    private transient long id;
    private String kind;
    private DataRoot data;


    protected Feed(Parcel in) {
        id = getRandomNumber();
        kind = in.readString();
        data = in.readParcelable(DataRoot.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(kind);
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public DataRoot getData() {
        return data;
    }

    public void setData(DataRoot data) {
        this.data = data;
    }

    public static long getRandomNumber() {
        long x = (long) ((Math.random() * ((100000 - 0) + 1)) + 0);
        return x;
    }
}
