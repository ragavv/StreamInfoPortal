package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mahesh on 25/04/17.
 */

public class AradhaneDataObject implements Parcelable {
    @SerializedName("name")
    @Expose
    private String nameText;
    @SerializedName("name")
    @Expose
    private String maasaText;
    @SerializedName("maasa")
    @Expose
    private String pakshaText;
    @SerializedName("paksha")
    @Expose
    private String thithiText;
    @SerializedName("thithi")
    @Expose
    private String brindavanaText;
    @SerializedName("brindavana")
    @Expose
    private String dateText;
    @SerializedName("date")
    @Expose
    private String id;

    public AradhaneDataObject(){}

    AradhaneDataObject(String text1, String text2, String text3, String text4, String text5, String text6) {
        nameText = text1;
        maasaText = text2;
        pakshaText = text3;
        thithiText = text4;
        brindavanaText = text5;
        dateText = text6;

    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getMaasaText() {
        return maasaText;
    }

    public void setMaasaText(String maasaText) {
        this.maasaText = maasaText;
    }

    public String getPakshaText() {
        return pakshaText;
    }

    public void setPakshaText(String pakshaText) {
        this.pakshaText = pakshaText;
    }

    public String getThithiText() {
        return thithiText;
    }

    public void setThithiText(String thithiText) {
        this.thithiText = thithiText;
    }

    public String getBrindavanaText() {
        return brindavanaText;
    }

    public void setBrindavanaText(String brindavanaText) {
        this.brindavanaText = brindavanaText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
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
        dest.writeString(this.maasaText);
        dest.writeString(this.pakshaText);
        dest.writeString(this.thithiText);
        dest.writeString(this.brindavanaText);
        dest.writeString(this.dateText);
    }

    protected AradhaneDataObject(Parcel in) {
        this.nameText = in.readString();
        this.maasaText = in.readString();
        this.pakshaText = in.readString();
        this.thithiText = in.readString();
        this.brindavanaText = in.readString();
        this.dateText = in.readString();
     }

    public static final Creator<AradhaneDataObject> CREATOR = new Creator<AradhaneDataObject>() {
        @Override
        public AradhaneDataObject createFromParcel(Parcel source) {
            return new AradhaneDataObject(source);
        }

        @Override
        public AradhaneDataObject[] newArray(int size) {
            return new AradhaneDataObject[size];
        }
    };
}
