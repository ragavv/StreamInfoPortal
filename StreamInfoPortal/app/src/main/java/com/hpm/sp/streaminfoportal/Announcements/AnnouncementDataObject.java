package com.hpm.sp.streaminfoportal.Announcements;

/**
 * Created by mahesh on 25/04/17.
 */

public class AnnouncementDataObject {
    private String detailsText;

    AnnouncementDataObject(String text1){
        detailsText = text1;
    }



    public String getDetailsText() {
        return detailsText;
    }

    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }
}
