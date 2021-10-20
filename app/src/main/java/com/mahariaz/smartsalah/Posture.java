package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Posture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posture);
        BarChart bchart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) 0; i < 10; i++) {
            float val = (float) (Math.random());
            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "Posture");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        bchart.setTouchEnabled(false);
        bchart.setData(data);

    }
}