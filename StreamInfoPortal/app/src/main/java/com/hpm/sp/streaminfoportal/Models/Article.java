package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Created by kumardivyarajat on 06/02/18.
 */

public class Article implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("pdfLink")
    @Expose
    private String pdfLink;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("publishedDate")
    @Expose
    private String publishedDate;
    @SerializedName("showOnlyPdf")
    @Expose
    private Boolean showOnlyPdf = false;

    public Article() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Boolean getShowOnlyPdf() {
        return showOnlyPdf;
    }

    public void setShowOnlyPdf(Boolean showOnlyPdf) {
        this.showOnlyPdf = showOnlyPdf;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.details);
        dest.writeString(this.imageUrl);
        dest.writeString(this.pdfLink);
        dest.writeValue(this.v);
        dest.writeString(this.publishedDate);
        dest.writeValue(this.showOnlyPdf);
    }

    protected Article(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.details = in.readString();
        this.imageUrl = in.readString();
        this.pdfLink = in.readString();
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
        this.publishedDate = in.readString();
        this.showOnlyPdf = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}


