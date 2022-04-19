package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;


import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity  {
    BarChart barChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        barChartView = (BarChart) findViewById(R.id.bar_chart1);
        String[] labels = {"","","4Sunnah", "", "4Farz", "","2Sunnah", "2Nafil", ""};
        String[] time = {"", "10", "20", "30", "40", "50", ""};


        ArrayList<BarEntry>  yValueGroup1  = new ArrayList<>();
        ArrayList<BarEntry>  yValueGroup2   = new ArrayList<>();
        ArrayList<BarEntry>  yValueGroup3   = new ArrayList<>();
        ArrayList<BarEntry>  yValueGroup4   = new ArrayList<>();


        for (int i = 0; i < 8 + 1; i++) {
            float val1 = 25;
            float val2 = 4;
            float val3 = 4;
            float val4 = 25;
            float val5 = 4;
            float val6 = 4;
            float val7 = 4;

            yValueGroup1.add(new BarEntry(i,
                    new float[]{val1+25, val2+25, val3+25,val4+25, val5+25, val6+25,val7+25},
                    getResources().getDrawable(R.drawable.flower)));
        }
        for (int i = 0; i < 8 + 1; i++) {
            float val1 = 25;
            float val2 = 4;
            float val3 = 4;
            float val4 = 25;
            float val5 = 4;
            float val6 = 4;
            float val7 = 4;

            yValueGroup2.add(new BarEntry(i,
                    new float[]{val1+25, val2+25, val3+25,val4+25, val5+25, val6+25,val7+25},
                    getResources().getDrawable(R.drawable.flower)));
        }
        for (int i = 0; i < 8 + 1; i++) {
            float val1 = 25;
            float val2 = 4;
            float val3 = 4;
            float val4 = 25;
            float val5 = 4;
            float val6 = 4;
            float val7 = 4;

            yValueGroup3.add(new BarEntry(i,
                    new float[]{val1+25, val2+25, val3+25,val4+25, val5+25, val6+25,val7+25},
                    getResources().getDrawable(R.drawable.flower)));
        }
        for (int i = 0; i < 8 + 1; i++) {
            float val1 = 25;
            float val2 = 4;
            float val3 = 4;
            float val4 = 25;
            float val5 = 4;
            float val6 = 4;
            float val7 = 4;

            yValueGroup4.add(new BarEntry(i,
                    new float[]{val1+25, val2+25, val3+25,val4+25, val5+25, val6+25,val7+25},
                    getResources().getDrawable(R.drawable.flower)));
        }

        BarDataSet barDataSet1 = new BarDataSet(yValueGroup1, "");
        barDataSet1.setColors(Color.BLUE, Color.RED,Color.BLUE, Color.YELLOW,Color.BLACK, Color.MAGENTA,Color.CYAN);
        barDataSet1.setDrawIcons(false);
        barDataSet1.setDrawValues(false);
        BarDataSet barDataSet2 = new BarDataSet(yValueGroup2, "");
        barDataSet2.setColors(Color.BLUE, Color.RED,Color.BLUE, Color.YELLOW,Color.BLACK, Color.MAGENTA,Color.CYAN);
        barDataSet2.setDrawIcons(false);
        barDataSet2.setDrawValues(false);
        BarDataSet barDataSet3 = new BarDataSet(yValueGroup3, "");
        barDataSet3.setColors(Color.BLUE, Color.RED,Color.BLUE, Color.YELLOW,Color.BLACK, Color.MAGENTA,Color.CYAN);
        barDataSet3.setDrawIcons(false);
        barDataSet3.setDrawValues(false);
        BarDataSet barDataSet4 = new BarDataSet(yValueGroup4, "");
        barDataSet4.setColors(Color.BLUE, Color.RED,Color.BLUE, Color.YELLOW,Color.BLACK, Color.MAGENTA,Color.CYAN);
        barDataSet4.setDrawIcons(false);
        barDataSet4.setDrawValues(false);
        barDataSet1.setBarBorderColor(Color.BLACK);


        BarData barData=new BarData(barDataSet1,barDataSet2,barDataSet3,barDataSet4);


        barChartView.getDescription().setEnabled(false);
        barChartView.setPinchZoom(false);
        barChartView.getAxisRight().setEnabled(false);
        barChartView.getLegend().setEnabled(false);
        barChartView.setScaleEnabled(false);
        barChartView.setVisibleXRangeMaximum(6f);
        barChartView.setData(barData);

        float groupSpace = 0f;
        float barSpace = 0f;
        float barWidth = 0.1f;
        barChartView.groupBars(1f, groupSpace, barSpace);

        barData.setBarWidth(barWidth);

        barChartView.invalidate();

        //Xaxis
        XAxis xAxis = barChartView.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day

        //YAxis
        barChartView.animateY(2000);
        YAxis leftAxis = barChartView.getAxisLeft();
        leftAxis.setLabelCount(8, true);

        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelRotationAngle(360);



    }

}