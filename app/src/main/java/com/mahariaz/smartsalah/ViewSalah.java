package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private Toolbar mTopToolbar;


    // getting chart data
    String sel_salah,sel_rakah;
    TextView salah_view,rakah_view;
    // graph
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    Intent intent;
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();
    String qayamAvg,rukuAvg,qoumAvg,sajdaAvg,tashAvg;
    TextView posmissedR1,posmissedR2,posmissedR3,posmissedR4;
    RelativeLayout rakah1Box,rakah2Box,rakah3Box,rakah4Box;
    ImageView ticR1,ticR2,ticR3,ticR4;
    CardView compltenessTile,correctnessTile;
    String completeness="",correctness="";
    ImageView ticInsideComp,ticInsideCorr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salah);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        posmissedR1=findViewById(R.id.posmissedR1);
        posmissedR2=findViewById(R.id.posmissedR2);
        posmissedR3=findViewById(R.id.posmissedR3);
        posmissedR4=findViewById(R.id.posmissedR4);
        rakah1Box=findViewById(R.id.rakah1Box);
        rakah2Box=findViewById(R.id.rakah2Box);
        rakah3Box=findViewById(R.id.rakah3Box);
        rakah4Box=findViewById(R.id.rakah4Box);
        ticR1=findViewById(R.id.ticR1);
        ticR2=findViewById(R.id.ticR2);
        ticR3=findViewById(R.id.ticR3);
        ticR4=findViewById(R.id.ticR4);
        compltenessTile=findViewById(R.id.completenessTile);
        correctnessTile=findViewById(R.id.correctnessTile);
        ticInsideComp=findViewById(R.id.ticInsideCompTile);
        ticInsideCorr=findViewById(R.id.ticInsideCorrTile);
        setSupportActionBar(mTopToolbar);
        // getting intents from previous activity
        intent=getIntent();
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
        qayamAvg=intent.getStringExtra("qayamAvg");
        rukuAvg=intent.getStringExtra("rukuAvg");
        qoumAvg=intent.getStringExtra("qoumAvg");
        sajdaAvg=intent.getStringExtra("sajdaAvg");
        tashAvg=intent.getStringExtra("tashAvg");

        completeness=intent.getStringExtra("completeness");
        correctness=intent.getStringExtra("correctness");

        displayRakahNum();
        checkMissed();
        fillbox();
        // make graph
        dataSets = new ArrayList<>();
        makeGraph();
        salah_view=findViewById(R.id.salahName);
        rakah_view=findViewById(R.id.rakahPrayed);
        salah_view.setText(sel_salah);
        rakah_view.setText(sel_rakah);
    }

    private void fillbox() {
        if(correctness.equalsIgnoreCase("Yes")){
            correctnessTile.setCardBackgroundColor(getResources().getColor(R.color.alien_green));
            ticInsideCorr.setImageDrawable(getResources().getDrawable(R.drawable.tic));

        }else if(correctness.equalsIgnoreCase("No")){
            correctnessTile.setCardBackgroundColor(getResources().getColor(R.color.bean_red));

        }
        if(completeness.equalsIgnoreCase("Yes")){
            compltenessTile.setCardBackgroundColor(getResources().getColor(R.color.alien_green));
            ticInsideComp.setImageDrawable(getResources().getDrawable(R.drawable.tic));
        }else if(completeness.equalsIgnoreCase("No")){
            compltenessTile.setCardBackgroundColor(getResources().getColor(R.color.bean_red));

        }
    }

    private void displayRakahNum() {
        if (sel_rakah.equalsIgnoreCase("2")) {
            rakah1Box.setVisibility(View.VISIBLE);
            rakah2Box.setVisibility(View.VISIBLE);

        }
        if (sel_rakah.equalsIgnoreCase("3")) {
            rakah1Box.setVisibility(View.VISIBLE);
            rakah2Box.setVisibility(View.VISIBLE);
            rakah3Box.setVisibility(View.VISIBLE);

        }
        if (sel_rakah.equalsIgnoreCase("4")) {
            rakah1Box.setVisibility(View.VISIBLE);
            rakah2Box.setVisibility(View.VISIBLE);
            rakah3Box.setVisibility(View.VISIBLE);
            rakah4Box.setVisibility(View.VISIBLE);

        }
    }

    private void checkMissed() {
        if (sel_salah.equalsIgnoreCase("Fajar")){
            ticR1.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR2.setImageDrawable(getResources().getDrawable(R.drawable.tic));
        }

        if (sel_salah.equalsIgnoreCase("Zuhr")){
            if(rukuAvg.equalsIgnoreCase("0")){
                posmissedR2.setText("Ruku Missed");

            }
            ticR1.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR3.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR4.setImageDrawable(getResources().getDrawable(R.drawable.tic));
        }
        if (sel_salah.equalsIgnoreCase("Asr")){
            ticR1.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR2.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR3.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR4.setImageDrawable(getResources().getDrawable(R.drawable.tic));
        }
        if (sel_salah.equalsIgnoreCase("Maghrib")){
            ticR1.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR2.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR3.setImageDrawable(getResources().getDrawable(R.drawable.tic));
        }
        if (sel_salah.equalsIgnoreCase("Isha")){
            if(qoumAvg.equalsIgnoreCase("0")){
                posmissedR1.setText("Qouma Missed");
                posmissedR3.setText("Sajda Missed");
            }
            if(sajdaAvg.equalsIgnoreCase("0")){
                posmissedR3.setText("Sajda Missed");
            }
            ticR4.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            ticR2.setImageDrawable(getResources().getDrawable(R.drawable.tic));
        }

    }

    private void makeGraph() {
        barChart = findViewById(R.id.idBarChart);
        barEntriesArrayList=new ArrayList<>();
        labelNames=new ArrayList<>();
        fillPostureList2();

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


    }
    private void fillPostureList2(){
        PostureNamesArrayList.clear();
        PostureNamesArrayList.add(new SalahPostureNames("Qayam",Integer.parseInt(qayamAvg)));
        PostureNamesArrayList.add(new SalahPostureNames("Ruku",Integer.parseInt(rukuAvg)));
        PostureNamesArrayList.add(new SalahPostureNames("Qouma",Integer.parseInt(qoumAvg)));
        PostureNamesArrayList.add(new SalahPostureNames("Sajda",Integer.parseInt(sajdaAvg)));
        PostureNamesArrayList.add(new SalahPostureNames("Tashahud",Integer.parseInt(tashAvg)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            //Toast.makeText(Calender.this, "Action clicked", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(ViewSalah.this,Home.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}