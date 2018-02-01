package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public class Id implements Parcelable {

    @SerializedName("channelId")
    @Expose
    private Object channelId;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("playlistId")
    @Expose
    private Object playlistId;
    @SerializedName("videoId")
    @Expose
    private String videoId;
    public final static Parcelable.Creator<Id> CREATOR = new Creator<Id>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Id createFromParcel(Parcel in) {
            return new Id(in);
        }

        public Id[] newArray(int size) {
            return (new Id[size]);
        }

    };

    protected Id(Parcel in) {
        this.channelId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.playlistId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.videoId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Id() {
    }

    public Object getChannelId() {
        return channelId;
    }

    public void setChannelId(Object channelId) {
        this.channelId = channelId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Object getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Object playlistId) {
        this.playlistId = playlistId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(channelId);
        dest.writeValue(kind);
        dest.writeValue(playlistId);
        dest.writeValue(videoId);
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName());
        result.append(" Object {");
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                //requires access to private field:
                result.append(field.get(this));
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");
        return result.toString();
    }

}