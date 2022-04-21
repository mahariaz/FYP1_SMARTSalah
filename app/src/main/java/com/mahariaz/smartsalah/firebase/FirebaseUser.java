package com.mahariaz.smartsalah.firebase;

import com.google.gson.annotations.SerializedName;

public class FirebaseUser {

    @SerializedName("dp")
    String dp;

    @SerializedName("username")
    String username;

    @SerializedName("gender")
    String gender;

    @SerializedName("height")
    String height;

    @SerializedName("age")
    String age;


    @SerializedName("prayers")
    FirebasePrayer firebasePrayer;

    public FirebasePrayer getFirebasePrayer() {
        return firebasePrayer;
    }

    public void setFirebasePrayer(FirebasePrayer firebasePrayer) {
        this.firebasePrayer = firebasePrayer;
    }

    public FirebaseUser() {
    }

    @SerializedName("email")
    String email;



    public FirebaseUser(String dp, String username, String gender, String height, String age, String email) {
        this.dp = dp;
        this.username = username;
        this.gender = gender;
        this.height = height;
        this.age = age;
        this.email = email;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
