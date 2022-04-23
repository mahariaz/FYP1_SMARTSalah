package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class weeklyStats extends Fragment {
    int fileFajarFarz2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fileFajarSunnah2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fileZuhrSunnah4[]={R.raw.user1_file1_zuhr_s,R.raw.user1_file2_zuhr_s,R.raw.user1_file3_zuhr_s,R.raw.user1_file4_zuhr_s,
            R.raw.user1_file5_zuhr_s,R.raw.user1_file6_zuhr_s,R.raw.user1_file7_zuhr_s};
    int fileZuhrFarz4[]={R.raw.user1_file1_zuhr_s,R.raw.user1_file2_zuhr_s,R.raw.user1_file3_zuhr_s,R.raw.user1_file4_zuhr_s,
            R.raw.user1_file5_zuhr_s,R.raw.user1_file6_zuhr_s,R.raw.user1_file7_zuhr_s};
    int fileZuhrSunnah2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fileZuhrNafil2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fileAsarFarz4[]={R.raw.user1_file1_zuhr_s,R.raw.user1_file2_zuhr_s,R.raw.user1_file3_zuhr_s,R.raw.user1_file4_zuhr_s,
            R.raw.user1_file5_zuhr_s,R.raw.user1_file6_zuhr_s,R.raw.user1_file7_zuhr_s};
    int fileAsarSunnah4[]={R.raw.user1_file1_zuhr_s,R.raw.user1_file2_zuhr_s,R.raw.user1_file3_zuhr_s,R.raw.user1_file4_zuhr_s,
            R.raw.user1_file5_zuhr_s,R.raw.user1_file6_zuhr_s,R.raw.user1_file7_zuhr_s};
    int fileIshaSunnah4[]={R.raw.user1_file1_zuhr_s,R.raw.user1_file2_zuhr_s,R.raw.user1_file3_zuhr_s,R.raw.user1_file4_zuhr_s,
            R.raw.user1_file5_zuhr_s,R.raw.user1_file6_zuhr_s,R.raw.user1_file7_zuhr_s};
    int fileIshaFarz4[]={R.raw.user1_file1_zuhr_s,R.raw.user1_file2_zuhr_s,R.raw.user1_file3_zuhr_s,R.raw.user1_file4_zuhr_s,
            R.raw.user1_file5_zuhr_s,R.raw.user1_file6_zuhr_s,R.raw.user1_file7_zuhr_s};
    int fileIshaSunnah2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fileIshaNafil2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fileIshaWitr[]={R.raw.user1_file1_3,R.raw.user1_file2_3,R.raw.user1_file3_3,R.raw.user1_file4_3,
            R.raw.user1_file5_3,R.raw.user1_file6_3,R.raw.user1_file7_3};
    int fileMaghribFarz3[]={R.raw.user1_file1_3,R.raw.user1_file2_3,R.raw.user1_file3_3,R.raw.user1_file4_3,
            R.raw.user1_file5_3,R.raw.user1_file6_3,R.raw.user1_file7_3};
    int fileMaghribSunnah2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fileMaghribNafil2[]={R.raw.user1_file1_2,R.raw.user1_file2_2,R.raw.user1_file3_2,R.raw.user1_file4_2,
            R.raw.user1_file5_2,R.raw.user1_file6_2,R.raw.user1_file7_2};
    int fjrTimeSpent=0,zuhrTimeSpent,asrTimeSpent=0,mgbTimeSpent=0,ishaTimeSpent=0;



    BarChart mChart;
    // variable for our bar data.
    BarData barData;
    // variable for our bar data set.
    BarDataSet barDataSet;
    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();


    private Toolbar mTopToolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_weekly_stats, container, false);
        mChart = (BarChart) view.findViewById(R.id.idBarChart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);

        // empty labels so that the names are spread evenly
        String[] labels = {"", "Mon", "Tue","Wed", "Thurs", "Fri","Sat","Sun","",""};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day



        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelRotationAngle(360);
        mChart.animateY(2000);
        YAxis leftAxis = mChart.getAxisLeft();




        leftAxis.setLabelCount(8, true);


        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        weeklySalah();

        float[] fjrVal = {fjrTimeSpent+1,fjrTimeSpent+1,fjrTimeSpent-2,fjrTimeSpent-2,fjrTimeSpent-1,fjrTimeSpent-1,fjrTimeSpent-1};
        float[] zuhrVal = {zuhrTimeSpent+1,zuhrTimeSpent+2,zuhrTimeSpent-3,zuhrTimeSpent+1,zuhrTimeSpent,zuhrTimeSpent,zuhrTimeSpent+1};
        float[] asrVal = {asrTimeSpent+1,0,asrTimeSpent+1,0,asrTimeSpent+3,asrTimeSpent+1,asrTimeSpent+2};
        float[] mgbVal = {mgbTimeSpent+2,mgbTimeSpent+1,mgbTimeSpent+1,mgbTimeSpent+1,0,mgbTimeSpent+1,mgbTimeSpent+2};
        float[] ishaVal = {ishaTimeSpent-1,ishaTimeSpent+2,ishaTimeSpent+1,0,ishaTimeSpent+3,ishaTimeSpent+2,ishaTimeSpent+1};




        ArrayList<BarEntry> fjrBar = new ArrayList<>();
        ArrayList<BarEntry> zuhrBar = new ArrayList<>();
        ArrayList<BarEntry> asrBar = new ArrayList<>();
        ArrayList<BarEntry> mgbBar = new ArrayList<>();
        ArrayList<BarEntry> ishaBar = new ArrayList<>();
        for (int i = 0; i < fjrVal.length; i++) {
            fjrBar.add(new BarEntry(i, fjrVal[i]));
            zuhrBar.add(new BarEntry(i, zuhrVal[i]));
            asrBar.add(new BarEntry(i, asrVal[i]));
            mgbBar.add(new BarEntry(i, mgbVal[i]));
            ishaBar.add(new BarEntry(i, ishaVal[i]));
        }

        BarDataSet fjrSet = new BarDataSet(fjrBar, "Fajr");
        fjrSet.setColor(Color.parseColor("#809bce"));
        BarDataSet zuhrSet = new BarDataSet(zuhrBar, "Zuhr");
        zuhrSet.setColor(Color.parseColor("#95b8d1"));
        BarDataSet asrSet = new BarDataSet(asrBar, "Asr");
        asrSet.setColor(Color.parseColor("#b8e0d2"));
        BarDataSet mgbSet = new BarDataSet(mgbBar, "Maghreb");
        mgbSet.setColor(Color.parseColor("#d6eadf"));
        BarDataSet ishaSet = new BarDataSet(ishaBar, "Isha");
        ishaSet.setColor(Color.parseColor("#eac4d5"));


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(fjrSet);
        dataSets.add(zuhrSet);
        dataSets.add(asrSet);
        dataSets.add(mgbSet);
        dataSets.add(ishaSet);

        BarData data = new BarData(dataSets);
        float groupSpace = 0.02f;
        float barSpace = 0f;
        float barWidth = 0.16f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        mChart.setData(data);
        mChart.setScaleEnabled(false);
        mChart.setVisibleXRangeMaximum(6f);
        mChart.groupBars(1f, groupSpace, barSpace);

        mChart.invalidate();





        return view;
    }


    private void weeklySalah() {
        // fajar
        int fjrSunnah=calculateTime(fileFajarSunnah2);
        int fjrFarz=calculateTime(fileFajarFarz2);
        fjrTimeSpent=fjrSunnah+fjrFarz;
        shared.fajarweek1=fjrTimeSpent;

        //Zuhr
        int zuhrSunnah4=calculateTime(fileZuhrSunnah4);
        int zuhrFarz4=calculateTime(fileZuhrFarz4);
        int zuhrSunnah2=calculateTime(fileZuhrSunnah2);
        int zuhrNafil2=calculateTime(fileZuhrNafil2);
        zuhrTimeSpent=zuhrSunnah4+zuhrFarz4+zuhrSunnah2+zuhrNafil2;
        shared.zuhrweek1=zuhrTimeSpent;

        //Asar
        int asarSunnah4=calculateTime(fileAsarSunnah4);
        int asarFarz4=calculateTime(fileAsarFarz4);
        asrTimeSpent=asarSunnah4+asarFarz4;
        shared.asrweek1=asrTimeSpent;

        //Maghrib
        int mgbFarz3=calculateTime(fileMaghribFarz3);
        int mgbSunnah2=calculateTime(fileMaghribSunnah2);
        int mgbNafil2=calculateTime(fileMaghribNafil2);
        mgbTimeSpent=mgbFarz3+mgbSunnah2+mgbNafil2;
        shared.mgbweek1=mgbTimeSpent;

        //Isha
        int ishaSunnah4=calculateTime(fileIshaSunnah4);
        int ishaFarz4=calculateTime(fileIshaFarz4);
        int ishaSunnah2=calculateTime(fileIshaSunnah2);
        int ishaNafil2=calculateTime(fileIshaNafil2);
        int ishaWitr=calculateTime(fileIshaWitr);
        ishaTimeSpent=ishaSunnah4+ishaFarz4+ishaSunnah2+ishaNafil2+ishaWitr+ishaNafil2;
        shared.ishaweek1=ishaTimeSpent;

        shared.sunnah2Time=zuhrSunnah2;
        shared.farz2Time=zuhrNafil2;
        shared.farz3Time=mgbFarz3;
        shared.sunnah4Time=zuhrSunnah4;
        shared.farz4Time=zuhrFarz4;


    }
    private int calculateTime(int [] array) {
        InputStream is;
        BufferedReader reader;
        is = getResources().openRawResource(array[0]);
        reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String firstLine="",firstLineF="",lastline="",lastlineF="";
        String firstTime[],lastTime[];
        String startTime="",endTime="";

        // first line
        try {
            while ((firstLine = reader.readLine()) != null) {
                firstLineF=firstLine;
                break;
            }
            firstTime=firstLineF.split(",");
            startTime = firstTime[0];
        } catch (IOException e1) {        }
        // last line

        try {
            while ((lastline = reader.readLine()) != null) {
                lastlineF=lastline;
            }
            lastTime=lastlineF.split(",");
            endTime = lastTime[0];
        } catch (IOException e1){        }
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(startTime);
            d2 = format.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int salahMin=(int)diffMinutes;
        return salahMin;
    }
}