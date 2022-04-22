package com.mahariaz.smartsalah;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahariaz.smartsalah.firebase.FirebasePrayer;
import com.mahariaz.smartsalah.firebase.FirebaseUser;
import com.mahariaz.smartsalah.firebase.PModel;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.CDL;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class dailyStats extends Fragment {






    int qayamAvg = 1, rukuAvg = 2, qoumAvg = 3, sajdaAvg = 5, tashAvg = 12;
    String lastTime[];
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList = new ArrayList<SalahPostureNames>();
    CardView fajrStats, zuhrStats, asrStats, maghribStats, ishaStats;
    String completeness = "No", correctness = "No";

    BarChart mChart;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_stats, container, false);




        getFirebaseData();


        fajrStats = view.findViewById(R.id.fajrStats);
        fajrStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // read fajar file and pass averages to viewsalah screen
                Intent intent = new Intent(getActivity(), ViewSalah.class);
                intent.putExtra("sel_salah", "Fajr");
                intent.putExtra("sel_rakah", "2");
                intent.putExtra("whichScreen", "dailyStats");
//                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
//                intent.putExtra("rukuAvg",String.valueOf(total_ruku));
//                intent.putExtra("qoumAvg",String.valueOf(total_qoum));
//                intent.putExtra("sajdaAvg",String.valueOf(total_sajda));
//                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness", "Yes");
                intent.putExtra("correctness", "Yes");
                startActivity(intent);

            }
        });
        zuhrStats = view.findViewById(R.id.zuhrStats);
        zuhrStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // read zuhr file and pass averages to viewsalah screen
                Intent intent = new Intent(getActivity(), ViewSalah.class);
                intent.putExtra("sel_salah", "Zuhr");
                intent.putExtra("sel_rakah", "4");
                intent.putExtra("whichScreen", "dailyStats");


                startActivity(intent);

            }
        });
        asrStats = view.findViewById(R.id.asrStats);
        asrStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // read asr file and pass averages to viewsalah screen
                Intent intent = new Intent(getActivity(), ViewSalah.class);
                intent.putExtra("sel_salah", "Asr");
                intent.putExtra("sel_rakah", "4");
                intent.putExtra("whichScreen", "dailyStats");
//                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
//                intent.putExtra("rukuAvg",String.valueOf(total_ruku));
//                intent.putExtra("qoumAvg",String.valueOf(total_qoum));
//                intent.putExtra("sajdaAvg",String.valueOf(total_sajda));
//                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness", "Yes");
                intent.putExtra("correctness", "No");
                startActivity(intent);

            }
        });
        maghribStats = view.findViewById(R.id.mgbStats);
        maghribStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // read mgb file and pass averages to viewsalah screen
                Intent intent = new Intent(getActivity(), ViewSalah.class);
                intent.putExtra("sel_salah", "Maghrib");
                intent.putExtra("sel_rakah", "3");
                intent.putExtra("whichScreen", "dailyStats");
//                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
//                intent.putExtra("rukuAvg",String.valueOf(total_ruku));
//                intent.putExtra("qoumAvg",String.valueOf(total_qoum));
//                intent.putExtra("sajdaAvg",String.valueOf(total_sajda));
//                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness", "Yes");
                intent.putExtra("correctness", "No");
                startActivity(intent);

            }
        });
        ishaStats = view.findViewById(R.id.ishaStats);
        ishaStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // read isha file and pass averages to viewsalah screen
                Intent intent = new Intent(getActivity(), ViewSalah.class);
                intent.putExtra("sel_salah", "Isha");
                intent.putExtra("sel_rakah", "4");
                intent.putExtra("whichScreen", "dailyStats");
//                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
//                intent.putExtra("rukuAvg",String.valueOf(total_qoum));
//                intent.putExtra("qoumAvg",String.valueOf(0));
//                intent.putExtra("sajdaAvg",String.valueOf(0));
//                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness", "Yes");
                intent.putExtra("correctness", "No");
                startActivity(intent);

            }
        });


        mChart = (BarChart) view.findViewById(R.id.mChart);
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
        mChart.setFitBars(true);
        leftAxis.setLabelCount(8, true);


        mChart.getAxisLeft().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        float[] farz4 = {0, 4, 4, 0, 4};
        float[] sunnah4 = {0, 5, 5, 0, 5};
        float[] sunnah2 = {2, 2, 0, 3, 2};
        float[] nafal2 = {2, 2, 0, 1, 2};
        float[] farz3 = {0, 0, 0, 3, 4};


        ArrayList<BarEntry> farzbar4 = new ArrayList<>();
        ArrayList<BarEntry> farzbar3 = new ArrayList<>();
        ArrayList<BarEntry> sunnahbar4 = new ArrayList<>();
        ArrayList<BarEntry> sunnahbar2 = new ArrayList<>();
        ArrayList<BarEntry> nafalbar = new ArrayList<>();

        for (int i = 0; i < farz4.length; i++) {
            farzbar4.add(new BarEntry(i, farz4[i]));
            farzbar3.add(new BarEntry(i, farz3[i]));
            sunnahbar4.add(new BarEntry(i, sunnah4[i]));
            sunnahbar2.add(new BarEntry(i, sunnah2[i]));
            nafalbar.add(new BarEntry(i, nafal2[i]));
//
        }
        BarDataSet set5 = new BarDataSet(sunnahbar2, "barTwo");
        set5.setColor(Color.parseColor("#809bce"));
        BarDataSet set6 = new BarDataSet(nafalbar, "barTwo");
        set6.setColor(Color.parseColor("#95b8d1"));
        BarDataSet set1 = new BarDataSet(farzbar4, "barOne");
        set1.setColor(Color.parseColor("#b8e0d2"));
        BarDataSet set4 = new BarDataSet(sunnahbar4, "barTwo");
        set4.setColor(Color.parseColor("#d6eadf"));
        BarDataSet set2 = new BarDataSet(farzbar3, "barTwo");
        set2.setColor(Color.parseColor("#eac4d5"));


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set5);
        dataSets.add(set6);
        dataSets.add(set1);
        dataSets.add(set4);
        dataSets.add(set2);


        BarData data = new BarData(dataSets);
        float groupSpace = 0.2f;
        float barSpace = 0f;
        float barWidth = 0.2f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length);
        mChart.setData(data);
        mChart.setScaleEnabled(false);
        mChart.setVisibleXRangeMaximum(6f);
        mChart.groupBars(1f, groupSpace, barSpace);

        mChart.invalidate();

        return view;
    }

    private void getFirebaseData() {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserBio");
        databaseReference.child("user123").child("firebasePrayer").child("zuhr").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.getValue(String.class);
//                System.out.println("AAAAAAA : " + value);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String stringValue = ds.child("salahUnit").getValue(String.class);
                    Log.i("SalahUnitttt", stringValue);
                    if(stringValue.equalsIgnoreCase("sunnah4")){
                        shared.isZuhrSunnah4Prayed=true;
                    }
                    if(stringValue.equalsIgnoreCase("farz4")){
                        shared.isZuhrFarz4Prayed=true;
                    }
                    if(stringValue.equalsIgnoreCase("sunnah2")){
                        shared.isZuhrSunnah2Prayed=true;
                    }
                    if(stringValue.equalsIgnoreCase("nafl2")){
                        shared.isZuhrNafl2Prayed=true;
                    }
                    System.out.println("shared.isZuhrFarz4Prayed : "+shared.isZuhrFarz4Prayed);
                    System.out.println(" shared.isZuhrSunnah4Prayed : "+ shared.isZuhrSunnah4Prayed);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("FAILED");

            }
        });

    }



}
