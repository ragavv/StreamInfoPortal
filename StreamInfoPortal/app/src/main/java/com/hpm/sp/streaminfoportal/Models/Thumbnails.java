package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public class Thumbnails implements Parcelable {

    @SerializedName("default")
    @Expose
    private Default _default;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("high")
    @Expose
    private High high;
    public final static Parcelable.Creator<Thumbnails> CREATOR = new Creator<Thumbnails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Thumbnails createFromParcel(Parcel in) {
            return new Thumbnails(in);
        }

        public Thumbnails[] newArray(int size) {
            return (new Thumbnails[size]);
        }

    };

    protected Thumbnails(Parcel in) {
        this._default = ((Default) in.readValue((Default.class.getClassLoader())));
        this.medium = ((Medium) in.readValue((Medium.class.getClassLoader())));
        this.high = ((High) in.readValue((High.class.getClassLoader())));
    }

    public Thumbnails() {
    }

    public Default getDefault() {
        return _default;
    }

    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_default);
        dest.writeValue(medium);
        dest.writeValue(high);
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

