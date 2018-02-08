package com.hpm.sp.streaminfoportal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

/**
 * Created by kumardivyarajat on 2/8/18.
 */

public class Branch implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("loc")
    @Expose
    private String loc;

    private Boolean isHeader = false;

    public Branch() {
    }

    public Branch(String city, Boolean isHeader) {
        this.city = city;
        this.isHeader = isHeader;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public String getContactURI() {
        return this.contact.split("/")[0];
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getHeader() {
        return isHeader;
    }

    public void setHeader(Boolean header) {
        isHeader = header;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
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
        dest.writeString(this.contact);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeValue(this.v);
        dest.writeString(this.city);
        dest.writeString(this.loc);
        dest.writeValue(this.isHeader);
    }

    protected Branch(Parcel in) {
        this.id = in.readString();
        this.contact = in.readString();
        this.name = in.readString();
        this.location = in.readString();
        this.v = (Integer) in.readValue(Integer.class.getClassLoader());
        this.city = in.readString();
        this.loc = in.readString();
        this.isHeader = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel source) {
            return new Branch(source);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };
}