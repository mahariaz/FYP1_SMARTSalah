package com.mahariaz.smartsalah;

public class statsStorage {
    String salahName;
    String rakahNumber;
    String date1;
    int qayamAvg,rukuAvg,qoumAvg,sajdaAvg,jalsaAvg,tashAvg;

    public statsStorage(String salahName, String rakahNumber,String rakahMissed, String salahStatus, String salahUnit,
                        int qayamAvg, int rukuAvg, int qoumAvg, int sajdaAvg, int jalsaAvg, int tashAvg,
                        String date1) {
        this.salahName = salahName;
        this.rakahNumber = rakahNumber;
        this.qayamAvg = qayamAvg;
        this.rukuAvg = rukuAvg;
        this.qoumAvg = qoumAvg;
        this.sajdaAvg = sajdaAvg;
        this.jalsaAvg = jalsaAvg;
        this.tashAvg = tashAvg;
        this.rakahMissed = rakahMissed;
        this.salahStatus = salahStatus;
        this.salahUnit = salahUnit;
        this.date1=date1;

    }

    String rakahMissed;
    String salahStatus;
    String salahUnit;





}
