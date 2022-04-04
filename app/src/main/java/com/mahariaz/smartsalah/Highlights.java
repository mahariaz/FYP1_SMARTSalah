package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Highlights extends AppCompatActivity {


    final DBAdapter db=new DBAdapter(this);
    String t4,t5,t6,t7,t8,t9;
    String h4,h5,h6,h7,h8,h9;
    String f4,f5,f6,f7,f8,f9;
    int two_rakah=0,temp1=0,c1=1;
    int three_rakah=0,temp2=0,c2=1;
    int four_rakah=0,temp3=0,c3=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlights);
        drawLineChart();







        
        db.openDB();
        Cursor c = db.getAllValues();
        while (c.moveToNext()) {
            String stored_rakah = c.getString(3);
            String get_salah=c.getString(2);


            if (stored_rakah.equalsIgnoreCase("2")) {
                t4 = c.getString(5);
                t5 = c.getString(6);
                t6 = c.getString(7);
                t7 = c.getString(8);
                t8 = c.getString(9);
                t9 = c.getString(10);
                temp1+=Integer.parseInt(t4)+Integer.parseInt(t5)+Integer.parseInt(t6)+
                        Integer.parseInt(t7)+Integer.parseInt(t8)+Integer.parseInt(t9);
                c1+=1;

            }
            if (stored_rakah.equalsIgnoreCase("3")) {
                h4 = c.getString(5);
                h5 = c.getString(6);
                h6 = c.getString(7);
                h7 = c.getString(8);
                h8 = c.getString(9);
                h9 = c.getString(10);
                temp2+=Integer.parseInt(h4)+Integer.parseInt(h5)+Integer.parseInt(h6)+
                        Integer.parseInt(h7)+Integer.parseInt(h8)+Integer.parseInt(h9);
                c2+=1;

            }
            if (stored_rakah.equalsIgnoreCase("4")) {
                f4 = c.getString(5);
                f5 = c.getString(6);
                f6 = c.getString(7);
                f7 = c.getString(8);
                f8 = c.getString(9);
                f9 = c.getString(10);
                temp3+=Integer.parseInt(f4)+Integer.parseInt(f5)+Integer.parseInt(f6)+
                        Integer.parseInt(f7)+Integer.parseInt(f8)+Integer.parseInt(f9);
                c3+=1;

            }

        }
        two_rakah=temp1/c1;
        System.out.println("twoooo"+two_rakah);
        three_rakah=temp2/c2;
        System.out.println("threeee"+three_rakah);
        four_rakah=temp3/c3;
        System.out.println("fourrrr"+four_rakah);

    }

    private void drawLineChart() {
        LineChart lineChart = findViewById(R.id.lineChart);
        List<Entry> lineEntries = getDataSet();
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "rakah");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.YELLOW);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(lineDataSet);
        lineChart.getDescription().setText("Rakah Average Time");
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f);
        lineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
        lineChart.setData(lineData);
    }

    private List<Entry> getDataSet() {
        List<Entry> lineEntries = new ArrayList<Entry>();
        lineEntries.add(new Entry(2, 32));
        lineEntries.add(new Entry(3, 60));
        lineEntries.add(new Entry(4, 150));

        return lineEntries;
    }


}