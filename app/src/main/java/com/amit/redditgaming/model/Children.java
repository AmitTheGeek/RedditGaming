package com.amit.redditgaming.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Children implements Parcelable {
    private String kind;
    private Data data;
    private transient long id;


    protected Children(Parcel in) {
        id = getRandomNumber();
        kind = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<Children> CREATOR = new Creator<Children>() {
        @Override
        public Children createFromParcel(Parcel in) {
            return new Children(in);
        }

        @Override
        public Children[] newArray(int size) {
            return new Children[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeParcelable(data, flags);
    }


    public static DiffUtil.ItemCallback<Children> DIFF_CALLBACK = new DiffUtil.ItemCallback<Children>() {
        @Override
        public boolean areItemsTheSame(@NonNull Children oldItem, @NonNull Children newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Children oldItem, @NonNull Children newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static long getRandomNumber() {
        long x = (long) ((Math.random() * ((100000 - 0) + 1)) + 0);
        return x;
    }
}
