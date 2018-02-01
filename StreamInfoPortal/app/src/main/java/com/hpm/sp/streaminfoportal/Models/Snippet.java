package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public class Snippet implements Parcelable {

    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("channelTitle")
    @Expose
    private String channelTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("liveBroadcastContent")
    @Expose
    private String liveBroadcastContent;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;
    public final static Parcelable.Creator<Snippet> CREATOR = new Creator<Snippet>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Snippet createFromParcel(Parcel in) {
            return new Snippet(in);
        }

        public Snippet[] newArray(int size) {
            return (new Snippet[size]);
        }

    };

    protected Snippet(Parcel in) {
        this.channelId = ((String) in.readValue((String.class.getClassLoader())));
        this.channelTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.liveBroadcastContent = ((String) in.readValue((String.class.getClassLoader())));
        this.publishedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnails = ((Thumbnails) in.readValue((Thumbnails.class.getClassLoader())));
    }

    public Snippet() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(channelId);
        dest.writeValue(channelTitle);
        dest.writeValue(description);
        dest.writeValue(liveBroadcastContent);
        dest.writeValue(publishedAt);
        dest.writeValue(title);
        dest.writeValue(thumbnails);
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