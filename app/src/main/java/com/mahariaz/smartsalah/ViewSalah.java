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
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    // graph
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    Intent intent;
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();
    String qayamAvg="12",rukuAvg="2",qoumAvg="1",sajdaAvg="3",tashAvg="10";
    String completeness="",correctness="";
    TextView salahNameTv,postureDict;
    ImageView iconStatusIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salah);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        postureDict=findViewById(R.id.postureDict);
        String sourceString = "<b>Q</b>"+"ayam "+"<b>R</b>"+"uku "+"<b>K</b>"+"ouma "+"<b>S</b>"+"ajda "+"<b>T</b>"+"ashahud";
        postureDict.setText(Html.fromHtml(sourceString));


        getIntents();
        populate();

        setSupportActionBar(mTopToolbar);
        // getting intents from previous activity

//        qayamAvg=intent.getStringExtra("qayamAvg");
//        rukuAvg=intent.getStringExtra("rukuAvg");
//        qoumAvg=intent.getStringExtra("qoumAvg");
//        sajdaAvg=intent.getStringExtra("sajdaAvg");
//        tashAvg=intent.getStringExtra("tashAvg");

        completeness=intent.getStringExtra("completeness");
        correctness=intent.getStringExtra("correctness");



        // make graph
        dataSets = new ArrayList<>();
        makeGraph();

    }

    private void getIntents() {
        intent=getIntent();
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
    }

    private void populate() {
        salahNameTv=findViewById(R.id.salahNameTv);
        iconStatusIv=findViewById(R.id.iconStatusIv);
        LinearLayout sunnah4LL=findViewById(R.id.sunnah4LL);
        LinearLayout farz4LL=findViewById(R.id.farz4LL);
        LinearLayout farz3LL=findViewById(R.id.farz3LL);
        LinearLayout sunnah2LL=findViewById(R.id.sunnah2LL);
        LinearLayout nafal2LL=findViewById(R.id.nafal2LL);
        // getting id of nafal2 to use it as farz2
        TextView nafal2Heading;
        if (sel_salah.equalsIgnoreCase("Fajr")){
            salahNameTv.setText("Fajr");
            iconStatusIv.setImageDrawable(getResources().getDrawable(R.drawable.tic));
            sunnah2LL.setVisibility(View.VISIBLE);
            nafal2LL.setVisibility(View.VISIBLE);
            nafal2Heading=findViewById(R.id.nafal2Heading);
            nafal2Heading.setText("Farz 2");

        }
        if (sel_salah.equalsIgnoreCase("Zuhr")){
            salahNameTv.setText("Zuhr");
            iconStatusIv.setImageDrawable(getResources().getDrawable(R.drawable.cross));
            sunnah4LL.setVisibility(View.VISIBLE);
            farz4LL.setVisibility(View.VISIBLE);
            sunnah2LL.setVisibility(View.VISIBLE);
            nafal2LL.setVisibility(View.VISIBLE);

        }
        if (sel_salah.equalsIgnoreCase("Asr")){
            salahNameTv.setText("Asr");
            iconStatusIv.setImageDrawable(getResources().getDrawable(R.drawable.error));
            sunnah4LL.setVisibility(View.VISIBLE);
            farz4LL.setVisibility(View.VISIBLE);

        }
        if (sel_salah.equalsIgnoreCase("Maghrib")){
            salahNameTv.setText("Maghrib");
            iconStatusIv.setImageDrawable(getResources().getDrawable(R.drawable.error));
            farz3LL.setVisibility(View.VISIBLE);
            sunnah2LL.setVisibility(View.VISIBLE);
            nafal2LL.setVisibility(View.VISIBLE);

        }
        if (sel_salah.equalsIgnoreCase("Isha")){
            salahNameTv.setText("Isha");
            iconStatusIv.setImageDrawable(getResources().getDrawable(R.drawable.error));
            farz4LL.setVisibility(View.VISIBLE);
            sunnah2LL.setVisibility(View.VISIBLE);
            farz3LL.setVisibility(View.VISIBLE);

        }

    }


    private void makeGraph() {
        barChart = findViewById(R.id.barChart);
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