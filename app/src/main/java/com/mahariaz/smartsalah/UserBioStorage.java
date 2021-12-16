package com.mahariaz.smartsalah;

public class UserBioStorage {
    public UserBioStorage(String dp, String email, String username, String height, String age, String gender) {
        this.height = height;
        this.age = age;
        this.email=email;
        this.username=username;
        this.dp=dp;
        this.gender=gender;

    }

    public UserBioStorage() {

    }



    public String getGender() {
        return gender;
    }

    public void setGender(String age) {
        this.gender = age;
    }


    String gender;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    String height;
    String age;
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
