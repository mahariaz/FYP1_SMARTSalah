package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.opencsv.CSVReader;

public class ViewSalah extends AppCompatActivity {

    // getting chart data
    String sel_salah,sel_rakah,rakah_per,qayam_avg,ruku_avg,qoum_avg,sajda_avg,jalsa_avg,tash_avg,get_salah,get_rakah;
    final DBAdapter db=new DBAdapter(this);
    TextView salah_view,rakah_view;
    // graph
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Takbir","Qayam", "Ruku", "Qouma", "Sajda", "Tashahud"));
    List<Entry> postures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salah);
        // making graph
        postures = getPostureAverageTime();
        dataSets = new ArrayList<>();
        makeGraph();



        // getting intents from previous activity
        sel_salah=shared.curr_salah;
        sel_rakah=shared.curr_rakah;
        salah_view=findViewById(R.id.salahName);
        rakah_view=findViewById(R.id.rakahPrayed);
//       salah_view.setText(get_salah); //getting from db
//        rakah_view.setText(get_rakah);


    }

    private void makeGraph() {
        LineDataSet set1;

        set1 = new LineDataSet(postures, "Postures");
        set1.setColor(Color.rgb(65, 168, 121));
        set1.setValueTextColor(Color.rgb(55, 70, 73));
        set1.setValueTextSize(10f);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawFilled(true);
        set1.setFillColor(getResources().getColor(R.color.teal_200));
        dataSets.add(set1);

//customization
        LineChart mLineGraph = findViewById(R.id.lineChart);
        mLineGraph.setTouchEnabled(true);
        mLineGraph.setDragEnabled(true);
        mLineGraph.setScaleEnabled(true);
        mLineGraph.setPinchZoom(true);
        mLineGraph.setDrawGridBackground(true);
        mLineGraph.setExtraLeftOffset(15);
        mLineGraph.setExtraRightOffset(15);
//to hide background lines
        mLineGraph.getXAxis().setDrawGridLines(true);
        mLineGraph.getAxisLeft().setDrawGridLines(true);
        mLineGraph.getAxisRight().setDrawGridLines(true);

//to hide right Y and top X border
        YAxis rightYAxis = mLineGraph.getAxisRight();
        rightYAxis.setEnabled(false);
        YAxis leftYAxis = mLineGraph.getAxisLeft();
        leftYAxis.setEnabled(true);
        XAxis topXAxis = mLineGraph.getXAxis();
        topXAxis.setEnabled(true);


        XAxis xAxis = mLineGraph.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        set1.setLineWidth(4f);
        set1.setCircleRadius(3f);
        set1.setDrawValues(false);
        set1.setCircleHoleColor(getResources().getColor(R.color.teal_200));
        set1.setCircleColor(getResources().getColor(R.color.black));

//String setter in x-Axis
        mLineGraph.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        LineData data = new LineData(dataSets);
        mLineGraph.setData(data);
        mLineGraph.animateX(2000);
        mLineGraph.invalidate();
        mLineGraph.getLegend().setEnabled(true);
        mLineGraph.getDescription().setEnabled(false);

    }

    private List<Entry> getPostureAverageTime() {
        ArrayList<Entry> posture = new ArrayList<>();

        posture.add(new Entry(1, 12));
        posture.add(new Entry(2, 2));
        posture.add(new Entry(3, 2));
        posture.add(new Entry(4, 3));
        posture.add(new Entry(5, 2));
        posture.add(new Entry(6, 12));


        return posture.subList(0, 6);
    }


//PostureNamesArrayList.add(new SalahPostureNames("Qayam",Integer.parseInt(qayam_avg)));


}