package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kumardivyarajat on 04/02/18.
 */

public class Guru implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("brindavan")
    @Expose
    private String brindavan;
    @SerializedName("aradhana")
    @Expose
    private String aradhana;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBrindavan() {
        return brindavan;
    }

    public void setBrindavan(String brindavan) {
        this.brindavan = brindavan;
    }

    public String getAradhana() {
        return aradhana;
    }

    public void setAradhana(String aradhana) {
        this.aradhana = aradhana;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.period);
        dest.writeString(this.brindavan);
        dest.writeString(this.aradhana);
        dest.writeString(this.profileImage);
        dest.writeValue(this.v);
    }

    public Guru() {
    }

    protected Guru(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.period = in.readString();
        this.brindavan = in.readString();
        this.aradhana = in.readString();
        this.profileImage = in.readString();
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Guru> CREATOR = new Parcelable.Creator<Guru>() {
        @Override
        public Guru createFromParcel(Parcel source) {
            return new Guru(source);
        }

        @Override
        public Guru[] newArray(int size) {
            return new Guru[size];
        }
    };
}