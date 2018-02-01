package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hpm.sp.streaminfoportal.EventsActivity.EventDataObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public class ResultDataObject implements Parcelable {

    @SerializedName("result")
    @Expose
    private List<EventDataObject> event = null;

    @SerializedName("video")
    @Expose
    private List<Video> videos = null;

    public ResultDataObject() {
    }

    public List<EventDataObject> getEvent() {
        return event;
    }

    public void setEvent(List<EventDataObject> event) {
        this.event = event;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
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
        dest.writeTypedList(this.event);
        dest.writeTypedList(this.videos);
    }

    protected ResultDataObject(Parcel in) {
        this.event = in.createTypedArrayList(EventDataObject.CREATOR);
        this.videos = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Creator<ResultDataObject> CREATOR = new Creator<ResultDataObject>() {
        @Override
        public ResultDataObject createFromParcel(Parcel source) {
            return new ResultDataObject(source);
        }

        @Override
        public ResultDataObject[] newArray(int size) {
            return new ResultDataObject[size];
        }
    };
}
