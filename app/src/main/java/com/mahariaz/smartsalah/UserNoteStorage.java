package com.mahariaz.smartsalah;
public class UserNoteStorage {
    public UserNoteStorage(String content, String note_name, String note_time, String nusername) {
        this.content = content;
        this.note_name = note_name;
        this.note_time = note_time;
        this.nusername = nusername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote_name() {
        return note_name;
    }

    public void setNote_name(String note_name) {
        this.note_name = note_name;
    }

    public String getNote_time() {
        return note_time;
    }

    public void setNote_time(String note_time) {
        this.note_time = note_time;
    }

    public String getNusername() {
        return nusername;
    }

    public void setNusername(String nusername) {
        this.nusername = nusername;
    }

    String content,note_name,note_time,nusername;
}
