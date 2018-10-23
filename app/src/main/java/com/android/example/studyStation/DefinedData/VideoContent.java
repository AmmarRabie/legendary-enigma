package com.android.example.studyStation.DefinedData;

/**
 * Created by AmmarRabie on 18/12/2017.
 */

public class VideoContent {
    private String link;
    private String label;
    private String creatorEmail;
    private String creationDate;


    public VideoContent(String link, String label, String creatorEmail, String creationDate) {
        this.link = link;
        this.label = label;
        this.creatorEmail = creatorEmail;
        this.creationDate = creationDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
