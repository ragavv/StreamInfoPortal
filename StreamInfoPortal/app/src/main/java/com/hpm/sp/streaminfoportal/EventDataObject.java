package com.hpm.sp.streaminfoportal;

/**
 * Created by mahesh on 25/04/17.
 */

public class EventDataObject {
    private String nameText;
    private String dateText;
    private String timeText;
    private String locationText;

    EventDataObject (String text1, String text2, String text3, String text4){
        nameText = text1;
        dateText = text2;
        timeText = text3;
        locationText = text3;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }
}
