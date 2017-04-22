package com.hpm.sp.streaminfoportal;

/**
 * Created by mahesh on 22/04/17.
 */

public class BranchDataObject {

    String name = "";
    String location = "";
    String contact = "";

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public BranchDataObject(String name, String location, String contact){
        this.name = name;
        this.contact = contact;
        this.location = location;
    }
}
