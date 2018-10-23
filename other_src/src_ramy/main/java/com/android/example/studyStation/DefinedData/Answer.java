package com.android.example.studyStation.DefinedData;

/**
 * Created by ramym on 12/15/2017.
 */

public class Answer {

    private String askerEmail;
    private String creationdate;
    private String content;
    private String replyierEmail;
    private String replyingdate;

    public Answer(String askerEmail, String creationdate, String content, String replyierEmail, String replyingdate) {
        this.askerEmail = askerEmail;
        this.creationdate = creationdate;
        this.content = content;
        this.replyierEmail = replyierEmail;
        this.replyingdate = replyingdate;
    }

    public Answer()
    {}


    public String getAskerEmail() {
        return askerEmail;
    }

    public void setAskerEmail(String askerEmail) {
        this.askerEmail = askerEmail;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyierEmail() {
        return replyierEmail;
    }

    public void setReplyierEmail(String replyierEmail) {
        this.replyierEmail = replyierEmail;
    }

    public String getReplyingdate() {
        return replyingdate;
    }

    public void setReplyingdate(String replyingdate) {
        this.replyingdate = replyingdate;
    }
}
