package com.hpm.sp.streaminfoportal;

/**
 * Created by mahesh on 22/04/17.
 */

public class TatvaDataObject {
    private String nameText;
    private String authorText;
    private String fileLocation;

    TatvaDataObject (String text1, String text2, String text3){
        nameText = text1;
        authorText = text2;
        fileLocation = text3;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getAuthorText() {
        return authorText;
    }

    public void setAuthorText(String authorText) {
        this.authorText = authorText;
    }

    public String getFileLocation() {
        return fileLocation;
    }
}
