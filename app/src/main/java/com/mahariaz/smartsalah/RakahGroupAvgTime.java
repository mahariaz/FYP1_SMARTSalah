package com.mahariaz.smartsalah;
public class RakahGroupAvgTime {
    public RakahGroupAvgTime(int rakahunit, int avg_time) {
        this.rakahunit = rakahunit;
        this.avg_time = avg_time;
    }

    public int getRakahunit() {
        return rakahunit;
    }

    public void setRakahunit(int rakahunit) {
        this.rakahunit = rakahunit;
    }

    public int getAvg_time() {
        return avg_time;
    }

    public void setAvg_time(int avg_time) {
        this.avg_time = avg_time;
    }

    int rakahunit;
    int avg_time;
}
