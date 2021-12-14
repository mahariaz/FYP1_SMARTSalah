package com.mahariaz.smartsalah;

public class UserBioStorage {
    public UserBioStorage(String dp, String email, String username, String fname, String lname, String gender) {
        this.fname = fname;
        this.lname = lname;
        this.email=email;
        this.username=username;
        this.dp=dp;
        this.gender=gender;

    }

    public UserBioStorage() {

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String age) {
        this.gender = age;
    }

    String fname;
    String lname;
    String gender;
    String email;


    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    String dp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
