package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public class Video implements Parcelable {

    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;
    public final static Parcelable.Creator<Video> CREATOR = new Creator<Video>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return (new Video[size]);
        }

    };

    protected Video(Parcel in) {
        this.etag = ((String) in.readValue((String.class.getClassLoader())));
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Id) in.readValue((Id.class.getClassLoader())));
        this.snippet = ((Snippet) in.readValue((Snippet.class.getClassLoader())));
    }

    public Video() {
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public String getVideoLink() {
        return "http://www.youtube.com/watch?v=" + this.id.getVideoId();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(etag);
        dest.writeValue(kind);
        dest.writeValue(id);
        dest.writeValue(snippet);
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