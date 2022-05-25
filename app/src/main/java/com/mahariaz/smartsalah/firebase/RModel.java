package com.mahariaz.smartsalah.firebase;

import com.google.gson.annotations.SerializedName;

public class RModel {
    public RModel() {

    }

    public RModel(String postureExtra, String postureMiss, String rakahNo, String status) {
        this.postureExtra = postureExtra;
        this.postureMiss = postureMiss;
        this.rakahNo = rakahNo;
        this.status = status;
    }

    public String getPostureExtra() {
        return postureExtra;
    }

    public void setPostureExtra(String postureExtra) {
        this.postureExtra = postureExtra;
    }

    public String getPostureMiss() {
        return postureMiss;
    }

    public void setPostureMiss(String postureMiss) {
        this.postureMiss = postureMiss;
    }

    public String getRakahNo() {
        return rakahNo;
    }

    public void setRakahNo(String rakahNo) {
        this.rakahNo = rakahNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("postureExtra")
    String postureExtra;
    @SerializedName("postureMiss")
    String postureMiss;
    @SerializedName("rakahNo")
    String rakahNo;
    @SerializedName("status")
    String status;
}
