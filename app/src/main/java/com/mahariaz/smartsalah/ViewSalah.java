package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.opencsv.CSVReader;

public class ViewSalah extends AppCompatActivity {
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();
    // getting chart data
    String sel_salah,sel_rakah,rakah_per,qayam_avg,ruku_avg,qoum_avg,sajda_avg,jalsa_avg,tash_avg,get_salah,get_rakah;
    final DBAdapter db=new DBAdapter(this);
    TextView salah_view,rakah_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salah);

        // getting intents from previous activity
        sel_salah=shared.curr_salah;
        sel_rakah=shared.curr_rakah;
        salah_view=findViewById(R.id.salah_view);
        rakah_view=findViewById(R.id.rakah_view);
        get_chart_data();
        salah_view.setText(get_salah); //getting from db
        rakah_view.setText(get_rakah);
        barChart = findViewById(R.id.idBarChart);
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
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description=new Description();
        description.setText("");
        barChart.setDescription(description);
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLinesBehindData(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelNames.size());
        xAxis.setLabelRotationAngle(360);
        barChart.animateY(2000);
        barChart.invalidate();


    }

    private void get_chart_data() {
        String u;
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss:SSS");
        String temp1=input.format(new Date());
        String curr_time[]=temp1.split("/");
        db.openDB();
        Cursor c=db.getAllValues();
        while(c.moveToNext()){
            u=c.getString(1);
            System.out.println(u);
            String get_time=c.getString(11);
            String stored_salah=c.getString(2);
            String stored_rakah=c.getString(3);
            String[] stored_time=get_time.split("/");
            if(stored_time[0].equalsIgnoreCase(curr_time[0])){
                if(stored_salah.equalsIgnoreCase(sel_salah) && stored_rakah.equalsIgnoreCase(sel_rakah)){
                    get_salah=c.getString(2);
                    get_rakah=c.getString(3);
                    rakah_per=c.getString(4);
                    qayam_avg=c.getString(5);
                    ruku_avg=c.getString(6);
                    qoum_avg=c.getString(7);
                    sajda_avg=c.getString(8);
                    jalsa_avg=c.getString(9);
                    tash_avg=c.getString(10);

                }

            }





        }
    }

    private void fillPostureList(){
        PostureNamesArrayList.clear();
        PostureNamesArrayList.add(new SalahPostureNames("Qayam",Integer.parseInt(qayam_avg)));
        PostureNamesArrayList.add(new SalahPostureNames("Ruku",Integer.parseInt(ruku_avg)));
        PostureNamesArrayList.add(new SalahPostureNames("Qouma",Integer.parseInt(qoum_avg)));
        PostureNamesArrayList.add(new SalahPostureNames("Sajda",Integer.parseInt(sajda_avg)));
        PostureNamesArrayList.add(new SalahPostureNames("Jalsa",Integer.parseInt(jalsa_avg)));
        PostureNamesArrayList.add(new SalahPostureNames("Tashahud",Integer.parseInt(tash_avg)));
    }
}