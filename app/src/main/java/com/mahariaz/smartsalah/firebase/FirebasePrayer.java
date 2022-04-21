package com.mahariaz.smartsalah.firebase;

import com.google.gson.annotations.SerializedName;

public class FirebasePrayer {
    public FirebasePrayer() {

    }

    public PModel getFajr() {
        return Fajr;
    }

    public void setFajr(PModel fajr) {
        Fajr = fajr;
    }

    public PModel getZuhr() {
        return Zuhr;
    }

    public void setZuhr(PModel zuhr) {
        Zuhr = zuhr;
    }

    public PModel getAsr() {
        return Asr;
    }

    public void setAsr(PModel asr) {
        Asr = asr;
    }

    public PModel getMaghrib() {
        return Maghrib;
    }

    public void setMaghrib(PModel maghrib) {
        Maghrib = maghrib;
    }

    public PModel getIsha() {
        return Isha;
    }

    public void setIsha(PModel isha) {
        Isha = isha;
    }

    public FirebasePrayer(PModel fajr, PModel zuhr, PModel asr, PModel maghrib, PModel isha) {
        Fajr = fajr;
        Zuhr = zuhr;
        Asr = asr;
        Maghrib = maghrib;
        Isha = isha;
    }

    @SerializedName("Fajr")
    PModel Fajr;
    @SerializedName("Zuhr")
    PModel Zuhr;
    @SerializedName("Asr")
    PModel Asr;
    @SerializedName("Maghrib")
    PModel Maghrib;
    @SerializedName("Isha")
    PModel Isha;
}
