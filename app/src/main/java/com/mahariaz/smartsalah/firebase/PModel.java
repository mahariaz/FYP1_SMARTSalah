package com.mahariaz.smartsalah.firebase;

import com.google.gson.annotations.SerializedName;

public class PModel {
    @SerializedName("salahName")
    String salahName;
    @SerializedName("rakahNumber")
    String rakahNumber;
    @SerializedName("missedPosture")
    String missedPosture;
    @SerializedName("missedRakah")
    String missedRakah;
    @SerializedName("salahStatus")
    String salahStatus;
    @SerializedName("qayamAvg")
    String qayamAvg;
    @SerializedName("rukuAvg")
    String rukuAvg;
    @SerializedName("qoumAvg")
    String qoumAvg;
    @SerializedName("sajdaAvg")
    String sajdaAvg;
    @SerializedName("jalsaAvg")
    String jalsaAvg;
    @SerializedName("tashAvg")
    String tashAvg;
    @SerializedName("salahUnit")
    String salahUnit;

    public String getSalahName() {
        return salahName;
    }

    public void setSalahName(String salahName) {
        this.salahName = salahName;
    }

    public String getRakahNumber() {
        return rakahNumber;
    }

    public void setRakahNumber(String rakahNumber) {
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

    public String getQayamAvg() {
        return qayamAvg;
    }

    public void setQayamAvg(String qayamAvg) {
        this.qayamAvg = qayamAvg;
    }

    public String getRukuAvg() {
        return rukuAvg;
    }

    public void setRukuAvg(String rukuAvg) {
        this.rukuAvg = rukuAvg;
    }

    public String getQoumAvg() {
        return qoumAvg;
    }

    public void setQoumAvg(String qoumAvg) {
        this.qoumAvg = qoumAvg;
    }

    public String getSajdaAvg() {
        return sajdaAvg;
    }

    public void setSajdaAvg(String sajdaAvg) {
        this.sajdaAvg = sajdaAvg;
    }

    public String getJalsaAvg() {
        return jalsaAvg;
    }

    public void setJalsaAvg(String jalsaAvg) {
        this.jalsaAvg = jalsaAvg;
    }

    public String getTashAvg() {
        return tashAvg;
    }

    public void setTashAvg(String tashAvg) {
        this.tashAvg = tashAvg;
    }

    public String getSalahUnit() {
        return salahUnit;
    }

    public void setSalahUnit(String salahUnit) {
        this.salahUnit = salahUnit;
    }

    public PModel() {

    }

    public PModel(String salahName, String rakahNumber, String missedPosture, String missedRakah, String salahStatus, String qayamAvg, String rukuAvg, String qoumAvg, String sajdaAvg, String jalsaAvg, String tashAvg, String salahUnit) {
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
    }
}
