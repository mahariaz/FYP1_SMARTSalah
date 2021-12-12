package com.mahariaz.smartsalah;
public class SalahPostureNames {
    public SalahPostureNames(String postureName,int avgPostureTime) {
        this.PostureName = postureName;
        this.avgPostureTime=avgPostureTime;
    }

    public String getPostureName() {
        return PostureName;
    }

    public void setPostureName(String postureName) {
        PostureName = postureName;
    }

    String PostureName;

    public int getAvgPostureTime() {
        return avgPostureTime;
    }

    public void setAvgPostureTime(int avgPostureTime) {
        this.avgPostureTime = avgPostureTime;
    }

    int avgPostureTime;

}
