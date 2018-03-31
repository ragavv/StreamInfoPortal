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

public class EkadashiDataObject implements Parcelable {
    @SerializedName("Maasa")
    @Expose
    private String maasaText;
    @SerializedName("Paksha")
    @Expose
    private String pakshaText;
    @SerializedName("Dashami")
    @Expose
    private String dashamiText;
    @SerializedName("Ekadashi")
    @Expose
    private String ekadashiText;
    @SerializedName("Dwadashi")
    @Expose
    private String dwadashiText;
    @SerializedName("Darsha")
    @Expose
    private String darshaText;
    @SerializedName("VishnuPanchaka")
    @Expose
    private String vishnuPanchakaText;





    public EkadashiDataObject(){}

    EkadashiDataObject(String text1, String text2, String text3, String text4, String text5, String text6, String text7) {
        maasaText = text1;
        pakshaText = text2;
        dashamiText=text3;
        ekadashiText=text4;
        dwadashiText=text5;
        darshaText=text6;
        vishnuPanchakaText=text7;
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

    public String getDashamiText() {
        return dashamiText;
    }

    public void setDashamiText(String dashamiText) {
        this.dashamiText = dashamiText;
    }

    public String getEkadashiText() {
        return ekadashiText;
    }

    public void setEkadashiText(String ekadashiText) {
        this.ekadashiText = ekadashiText;
    }

    public String getDwadashiText() {
        return dwadashiText;
    }

    public void setDwadashiText(String dwadashiText) {
        this.dwadashiText = dwadashiText;
    }

    public String getDarshaText() {
        return darshaText;
    }

    public void setDarshaText(String darshaText) {
        this.darshaText = darshaText;
    }

    public String getVishnuPanchakaText() {
        return vishnuPanchakaText;
    }

    public void setVishnuPanchakaText(String vishnuPanchakaText) {
        this.vishnuPanchakaText = vishnuPanchakaText;
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
        dest.writeString(this.maasaText);
        dest.writeString(this.pakshaText);
        dest.writeString(this.dashamiText);
        dest.writeString(this.ekadashiText);
        dest.writeString(this.dwadashiText);
        dest.writeString(this.darshaText);
        dest.writeString(this.vishnuPanchakaText);
    }

    protected EkadashiDataObject(Parcel in) {
        this.maasaText = in.readString();
        this.pakshaText = in.readString();
        this.dashamiText = in.readString();
        this.ekadashiText = in.readString();
        this.dwadashiText = in.readString();
        this.darshaText = in.readString();
        this.vishnuPanchakaText = in.readString();
    }

    public static final Creator<EkadashiDataObject> CREATOR = new Creator<EkadashiDataObject>() {
        @Override
        public EkadashiDataObject createFromParcel(Parcel source) {
            return new EkadashiDataObject(source);
        }

        @Override
        public EkadashiDataObject[] newArray(int size) {
            return new EkadashiDataObject[size];
        }
    };
}
