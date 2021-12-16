package com.mahariaz.smartsalah;


public class ModelClass {
    public String getNotename() {
        return notename;
    }

    public void setNotename(String notename) {
        this.notename = notename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNotetime() {
        return notetime;
    }

    public void setNotetime(String notetime) {
        this.notetime = notetime;
    }

    public String getNusername() {
        return nusername;
    }

    public void setNusername(String nusername) {
        this.nusername = nusername;
    }

    public ModelClass(String content, String notename,String notetime, String nusername) {
        this.notename = notename;
        this.content = content;
        this.notetime = notetime;
        this.nusername = nusername;
    }

    String notename;
    String content;
    String pic;
    String notetime;
    String nusername;


}


