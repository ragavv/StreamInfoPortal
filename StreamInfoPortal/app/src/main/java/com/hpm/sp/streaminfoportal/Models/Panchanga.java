package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Created by kumardivyarajat on 05/02/18.
 */

public class Panchanga implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("paksham")
    @Expose
    private String paksham;
    @SerializedName("tithi")
    @Expose
    private String tithi;
    @SerializedName("nakshatram")
    @Expose
    private String nakshatram;
    @SerializedName("rahukalam")
    @Expose
    private String rahukalam;
    @SerializedName("__v")
    @Expose
    private Integer v;
    public final static Parcelable.Creator<Panchanga> CREATOR = new Creator<Panchanga>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Panchanga createFromParcel(Parcel in) {
            return new Panchanga(in);
        }

        public Panchanga[] newArray(int size) {
            return (new Panchanga[size]);
        }

    };

    protected Panchanga(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.month = ((String) in.readValue((String.class.getClassLoader())));
        this.paksham = ((String) in.readValue((String.class.getClassLoader())));
        this.tithi = ((String) in.readValue((String.class.getClassLoader())));
        this.nakshatram = ((String) in.readValue((String.class.getClassLoader())));
        this.rahukalam = ((String) in.readValue((String.class.getClassLoader())));
        this.v = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Panchanga() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPaksham() {
        return paksham;
    }

    public void setPaksham(String paksham) {
        this.paksham = paksham;
    }

    public String getTithi() {
        return tithi;
    }

    public void setTithi(String tithi) {
        this.tithi = tithi;
    }

    public String getNakshatram() {
        return nakshatram;
    }

    public void setNakshatram(String nakshatram) {
        this.nakshatram = nakshatram;
    }

    public String getRahukalam() {
        return rahukalam;
    }

    public void setRahukalam(String rahukalam) {
        this.rahukalam = rahukalam;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(date);
        dest.writeValue(month);
        dest.writeValue(paksham);
        dest.writeValue(tithi);
        dest.writeValue(nakshatram);
        dest.writeValue(rahukalam);
        dest.writeValue(v);
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

