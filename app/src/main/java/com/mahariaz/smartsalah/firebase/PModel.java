package com.mahariaz.smartsalah.firebase;

import com.google.gson.annotations.SerializedName;

public class PModel {
    @SerializedName("salahName")
    String salahName;
    @SerializedName("rakahNumber")
    int rakahNumber;
    @SerializedName("missedPosture")
    String missedPosture;
    @SerializedName("missedRakah")
    String missedRakah;
    @SerializedName("salahStatus")
    String salahStatus;
    @SerializedName("qayamAvg")
    int qayamAvg;
    @SerializedName("rukuAvg")
    int rukuAvg;



    @SerializedName("qoumAvg")
    int qoumAvg;
    @SerializedName("sajdaAvg")
    int sajdaAvg;
    @SerializedName("jalsaAvg")
    int jalsaAvg;
    @SerializedName("tashAvg")
    int tashAvg;
    @SerializedName("salahUnit")
    String salahUnit;
    @SerializedName("date1")
    String date1;
    @SerializedName("salahUnitTime")
    int salahUnitTime;
    @SerializedName("extraRakah")
    String extraRakah;
    @SerializedName("extraPosture")
    String extraPosture;

    public PModel() {

    }


    public PModel(String salahName, int rakahNumber, String missedPosture, String missedRakah, String salahStatus, int qayamAvg, int rukuAvg, int qoumAvg, int sajdaAvg, int jalsaAvg, int tashAvg, String salahUnit, int salahUnitTime, String extraPosture,String extraRakah,String date1) {
        this.salahName = salahName;
        this.rakahNumber = rakahNumber;
        this.missedPosture = missedPosture;
        this.missedRakah = missedRakah;
        this.salahStatus = salahStatus;
        this.qayamAvg = qayamAvg;
        this.rukuAvg = rukuAvg;
        this.qoumAvg = qoumAvg;
        this.sajdaAvg = sajdaAvg;
        this.jalsaAvg = jalsaAvg;
        this.tashAvg = tashAvg;
        this.salahUnit = salahUnit;
        this.date1 = date1;
        this.salahUnitTime=salahUnitTime;
        this.extraPosture=extraPosture;
        this.extraRakah=extraRakah;

    }

    public String getExtraRakah() {
        return extraRakah;
    }

    public void setExtraRakah(String extraRakah) {
        this.extraRakah = extraRakah;
    }

    public String getExtraPosture() {
        return extraPosture;
    }

    public void setExtraPosture(String extraPosture) {
        this.extraPosture = extraPosture;
    }

    public int getSalahUnitTime() {
        return salahUnitTime;
    }

    public void setSalahUnitTime(int salahUnitTime) {
        this.salahUnitTime = salahUnitTime;
    }

    public String getSalahName() {
        return salahName;
    }

    public void setSalahName(String salahName) {
        this.salahName = salahName;
    }

    public int getRakahNumber() {
        return rakahNumber;
    }

    public void setRakahNumber(int rakahNumber) {
        this.rakahNumber = rakahNumber;
    }

    public String getMissedPosture() {
        return missedPosture;
    }

    public void setMissedPosture(String missedPosture) {
        this.missedPosture = missedPosture;
    }

    public String getMissedRakah() {
        return missedRakah;
    }

    public void setMissedRakah(String missedRakah) {
        this.missedRakah = missedRakah;
    }

    public String getSalahStatus() {
        return salahStatus;
    }

    public void setSalahStatus(String salahStatus) {
        this.salahStatus = salahStatus;
    }

    public int getQayamAvg() {
        return qayamAvg;
    }

    public void setQayamAvg(int qayamAvg) {
        this.qayamAvg = qayamAvg;
    }

    public int getRukuAvg() {
        return rukuAvg;
    }

    public void setRukuAvg(int rukuAvg) {
        this.rukuAvg = rukuAvg;
    }

    public int getQoumAvg() {
        return qoumAvg;
    }

    public void setQoumAvg(int qoumAvg) {
        this.qoumAvg = qoumAvg;
    }

    public int getSajdaAvg() {
        return sajdaAvg;
    }

    public void setSajdaAvg(int sajdaAvg) {
        this.sajdaAvg = sajdaAvg;
    }

    public int getJalsaAvg() {
        return jalsaAvg;
    }

    public void setJalsaAvg(int jalsaAvg) {
        this.jalsaAvg = jalsaAvg;
    }

    public int getTashAvg() {
        return tashAvg;
    }

    public void setTashAvg(int tashAvg) {
        this.tashAvg = tashAvg;
    }

    public String getSalahUnit() {
        return salahUnit;
    }

    public void setSalahUnit(String salahUnit) {
        this.salahUnit = salahUnit;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }
}