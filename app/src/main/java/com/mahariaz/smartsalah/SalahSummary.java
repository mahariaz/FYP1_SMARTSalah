package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;



public class SalahSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_summary);

        //spinner_Salah
        Spinner spinner_salah = (Spinner) findViewById(R.id.Select_Salah);
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(SalahSummary.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.namaz));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_salah.setAdapter(myAdapter);

        BarChart bchart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) 0; i < 5; i++) {
            float val = (float) (Math.random());
            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "Salah");
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
