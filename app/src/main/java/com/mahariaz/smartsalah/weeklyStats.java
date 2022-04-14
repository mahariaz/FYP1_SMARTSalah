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
    int avgFajar=0,avgZuhr,avgAsar=0,avgMaghrib=0,avgIsha=0;

    BarChart barChart;
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
        SalahWeeklyAverage();



        barChart = view.findViewById(R.id.idBarChart);
        barEntriesArrayList=new ArrayList<>();
        labelNames=new ArrayList<>();
        fillPostureList();
        for (int i=0;i<PostureNamesArrayList.size();i++){
            String postures=PostureNamesArrayList.get(i).getPostureName();
            int avgPostureTime=PostureNamesArrayList.get(i).getAvgPostureTime();
            barEntriesArrayList.add(new BarEntry(i,avgPostureTime));
            labelNames.add(postures);
        }
        BarDataSet barDataSet=new BarDataSet(barEntriesArrayList,"Postures");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        Description description=new Description();
        description.setText("");
        barChart.setDescription(description);
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis=barChart.getXAxis();
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLinesBehindData(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelNames.size());
        xAxis.setLabelRotationAngle(360);
        barChart.animateY(2000);
        barChart.invalidate();
        return view;
    }
    private void fillPostureList(){
        PostureNamesArrayList.clear();
        PostureNamesArrayList.add(new SalahPostureNames("Fajar",avgFajar));
        PostureNamesArrayList.add(new SalahPostureNames("Zuhr",avgZuhr));
        PostureNamesArrayList.add(new SalahPostureNames("Asar",avgAsar));
        PostureNamesArrayList.add(new SalahPostureNames("Maghrib",avgMaghrib));
        PostureNamesArrayList.add(new SalahPostureNames("Isha",avgIsha));
    }
    private void SalahWeeklyAverage() {
        // fajar
        int fjrSunnah=calculateAverage(fileFajarSunnah2);
        int fjrFarz=calculateAverage(fileFajarFarz2);
        avgFajar=fjrSunnah+fjrFarz;
        shared.fajarweek1=avgFajar;

        //Zuhr
        int zuhrSunnah4=calculateAverage(fileZuhrSunnah4);
        int zuhrFarz4=calculateAverage(fileZuhrFarz4);
        int zuhrSunnah2=calculateAverage(fileZuhrSunnah2);
        int zuhrNafil2=calculateAverage(fileZuhrNafil2);
        avgZuhr=zuhrSunnah4+zuhrFarz4+zuhrSunnah2+zuhrNafil2;
        shared.zuhrweek1=avgZuhr;

        //Asar
        int asarSunnah4=calculateAverage(fileAsarSunnah4);
        int asarFarz4=calculateAverage(fileAsarFarz4);
        avgAsar=asarSunnah4+asarFarz4;
        avgAsar=asarSunnah4+asarFarz4;
        shared.asrweek1=avgAsar;

        //Maghrib
        int mgbFarz3=calculateAverage(fileMaghribFarz3);
        int mgbSunnah2=calculateAverage(fileMaghribSunnah2);
        int mgbNafil2=calculateAverage(fileMaghribNafil2);
        avgMaghrib=mgbFarz3+mgbSunnah2+mgbNafil2;
        shared.mgbweek1=avgMaghrib;

        //Isha
        int ishaSunnah4=calculateAverage(fileIshaSunnah4);
        int ishaFarz4=calculateAverage(fileIshaFarz4);
        int ishaSunnah2=calculateAverage(fileIshaSunnah2);
        int ishaNafil2=calculateAverage(fileIshaNafil2);
        int ishaWitr=calculateAverage(fileIshaWitr);
        avgIsha=ishaSunnah4+ishaFarz4+ishaSunnah2+ishaNafil2+ishaWitr+ishaNafil2;
        shared.ishaweek1=avgIsha;


//        System.out.println("AVG ZUHR : "+avgZuhr);
//        System.out.println("AVG FAJAR : "+avgFajar);
//        System.out.println("AVG ASAR : "+avgAsar);
//        System.out.println("AVG MAGHRIB : "+avgMaghrib);
//        System.out.println("AVG ISHA : "+avgIsha);






    }
    private int calculateAverage(int [] array){
        InputStream is;
        BufferedReader reader;
        int unitSum=0;
        for (int i=0;i<7;i++) {
            is = getResources().openRawResource(array[i]);
            reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            // diffmin is returning minutes by extracting them from file
            long diffmin=filereading(reader);
            unitSum+=diffmin;
        }
        unitSum/=7;
    return unitSum;

    }
    private long filereading(BufferedReader reader) {

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
        return diffMinutes;
    }
}