package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Salah_stats_history extends AppCompatActivity {
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();

    //db
    final DBAdapter db=new DBAdapter(this);
    String sel_salah,sel_rakah,date,rakah_per,qayam_avg="0",ruku_avg="0",qoum_avg="0",sajda_avg="0",jalsa_avg="0",tash_avg="0",get_salah="Missed/Qaza",get_rakah="0 Rakah";
    TextView salah_view,rakah_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_stats_history);
        get_intents();
        search_records(); // from sqlite of entered salah,rakah and date
        salah_view=findViewById(R.id.salah_view);
        rakah_view=findViewById(R.id.rakah_view);
        barChart = findViewById(R.id.idBarChart);
        salah_view.setText(get_salah); //getting from db
        rakah_view.setText(get_rakah);
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

    private void search_records() {
        db.openDB();
        Cursor c=db.getAllValues();
        while(c.moveToNext()){
            String get_time=c.getString(11);
            String stored_salah=c.getString(2);
            String stored_rakah=c.getString(3);
            String[] stored_date=get_time.split("/");
            if(stored_date[0].equalsIgnoreCase(date)){
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

    private void get_intents() {
        Intent intent=getIntent();
        date=intent.getStringExtra("date");
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
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