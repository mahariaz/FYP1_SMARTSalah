package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class monStats extends Fragment {

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

        View view= inflater.inflate(R.layout.fragment_mon_stats, container, false);



        mChart = (BarChart) view.findViewById(R.id.idBarChart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);

        // empty labels so that the names are spread evenly
        String[] labels = {"", "Fajar", "Zuhr", "Asar", "Maghrib", "Isha", ""};
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

        float[] week1 = {shared.fajarweek1,shared.zuhrweek1,shared.asrweek1,shared.mgbweek1,shared.ishaweek1};
        float[] week2 = {shared.fajarweek1-1,shared.zuhrweek1+1,shared.asrweek1+2,shared.mgbweek1-1,shared.ishaweek1+1};
        float[] week3 = {shared.fajarweek1,shared.zuhrweek1,shared.asrweek1,shared.mgbweek1,shared.ishaweek1};
        float[] week4 = {shared.fajarweek1+1,shared.zuhrweek1-1,shared.asrweek1-2,shared.mgbweek1+1,shared.ishaweek1+4};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ArrayList<BarEntry> barThree = new ArrayList<>();
        ArrayList<BarEntry> barFour = new ArrayList<>();
        for (int i = 0; i < week1.length; i++) {
            barOne.add(new BarEntry(i, week1[i]));
            barTwo.add(new BarEntry(i, week2[i]));
            barThree.add(new BarEntry(i, week3[i]));
            barFour.add(new BarEntry(i, week4[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(Color.parseColor("#FFA6C9"));
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(Color.parseColor("#FAD6A5"));
        BarDataSet set3 = new BarDataSet(barThree, "barTwo");
        set3.setColor(Color.parseColor("#CD5B45"));
        BarDataSet set4 = new BarDataSet(barFour, "barTwo");
        set4.setColor(Color.parseColor("#98817B"));


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);

        BarData data = new BarData(dataSets);
        float groupSpace = 0.2f;
        float barSpace = 0f;
        float barWidth = 0.2f;
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

}