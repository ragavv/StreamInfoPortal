package com.hpm.sp.streaminfoportal.EventsActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by mahesh on 25/04/17.
 */

public class EventDataObject implements Parcelable {
    @SerializedName("name")
    @Expose
    private String nameText;
    @SerializedName("dateTime")
    @Expose
    private Date dateText;
    @SerializedName("location")
    @Expose
    private String locationText;
    @SerializedName("details")
    @Expose
    private String detailsText;
    @SerializedName("id")
    @Expose
    private String id;

    EventDataObject(String text1, Date text2, String text3, String text4, String text5) {
        nameText = text1;
        dateText = text2;
        locationText = text4;
        detailsText = text5;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public Date getDateText() {
        return dateText;
    }

    public void setDateText(Date dateText) {
        this.dateText = dateText;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getDetailsText() {
        return detailsText;
    }

    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        dest.writeString(this.nameText);
        dest.writeLong(this.dateText != null ? this.dateText.getTime() : -1);
        dest.writeString(this.locationText);
        dest.writeString(this.detailsText);
        dest.writeString(this.id);
    }

    protected EventDataObject(Parcel in) {
        this.nameText = in.readString();
        long tmpDateText = in.readLong();
        this.dateText = tmpDateText == -1 ? null : new Date(tmpDateText);
        this.locationText = in.readString();
        this.detailsText = in.readString();
        this.id = in.readString();
    }

    public static final Creator<EventDataObject> CREATOR = new Creator<EventDataObject>() {
        @Override
        public EventDataObject createFromParcel(Parcel source) {
            return new EventDataObject(source);
        }

        @Override
        public EventDataObject[] newArray(int size) {
            return new EventDataObject[size];
        }
    };
}
