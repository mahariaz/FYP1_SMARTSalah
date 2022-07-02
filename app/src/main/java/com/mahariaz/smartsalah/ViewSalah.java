package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahariaz.smartsalah.firebase.FirebasePrayer;
import com.mahariaz.smartsalah.firebase.PModel;
import com.opencsv.CSVReader;

public class ViewSalah extends AppCompatActivity {
    private Toolbar mTopToolbar;
    ImageView imgi1;

    // getting chart data
    String sel_salah,sel_rakah,sel_unit;
    // graph
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    Intent intent;
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();
    String qayamAvg="12",rukuAvg="2",qoumAvg="1",sajdaAvg="3",tashAvg="10",jalsaAvg,salahUnitTime,timelinessStatus;
    int qayamAvgInt=0,rukuAvgInt=0,qoumAvgInt=0,sajdaAvgInt=0,tashAvgInt=0,jalsaAvgInt=0,salahUnitTimeInt=0;
    String completeness="",correctness="";
    TextView salahNameTv,postureDict;
    ImageView iconStatusIv;
    Boolean isZuhrSunnah4Prayed=false,isZuhrFarz4Prayed=false,isZuhrSunnah2Prayed=false,isZuhrNafl2Prayed=false;
    String whichScreen="";
    String possMissedString,extraPostureString,extraRakahString,salahStatus,rakahMissedString,date1;
    LinearLayout sunnah4LL;
    LinearLayout farz4LL;
    LinearLayout farz3LL;
    LinearLayout sunnah2LL;
    LinearLayout nafal2LL;
    TextView sunnah4Rakah1Qayam,sunnah4Rakah1Ruku,sunnah4Rakah1Qoum,sunnah4Rakah1Sajda1,sunnah4Rakah1Jalsa,sunnah4Rakah1Sajda2;
    TextView sunnah4Rakah2Qayam,sunnah4Rakah2Ruku,sunnah4Rakah2Qoum,sunnah4Rakah2Sajda1,sunnah4Rakah2Jalsa,sunnah4Rakah2Sajda2,sunnah4Rakah2Tashahud;
    TextView sunnah4Rakah3Qayam,sunnah4Rakah3Ruku,sunnah4Rakah3Qoum,sunnah4Rakah3Sajda1,sunnah4Rakah3Jalsa,sunnah4Rakah3Sajda2;
    TextView sunnah4Rakah4Qayam,sunnah4Rakah4Ruku,sunnah4Rakah4Qoum,sunnah4Rakah4Sajda1,sunnah4Rakah4Jalsa,sunnah4Rakah4Sajda2,sunnah4Rakah4Tashahud;

    TextView farz4Rakah1Qayam,farz4Rakah1Ruku,farz4Rakah1Qoum,farz4Rakah1Sajda1,farz4Rakah1Jalsa,farz4Rakah1Sajda2;
    TextView farz4Rakah2Qayam,farz4Rakah2Ruku,farz4Rakah2Qoum,farz4Rakah2Sajda1,farz4Rakah2Jalsa,farz4Rakah2Sajda2,farz4Rakah2Tashahud;
    TextView farz4Rakah3Qayam,farz4Rakah3Ruku,farz4Rakah3Qoum,farz4Rakah3Sajda1,farz4Rakah3Jalsa,farz4Rakah3Sajda2;
    TextView farz4Rakah4Qayam,farz4Rakah4Ruku,farz4Rakah4Qoum,farz4Rakah4Sajda1,farz4Rakah4Jalsa,farz4Rakah4Sajda2,farz4Rakah4Tashahud;

    TextView farz3Rakah1Qayam,farz3Rakah1Ruku,farz3Rakah1Qoum,farz3Rakah1Sajda1,farz3Rakah1Jalsa,farz3Rakah1Sajda2;
    TextView farz3Rakah2Qayam,farz3Rakah2Ruku,farz3Rakah2Qoum,farz3Rakah2Sajda1,farz3Rakah2Jalsa,farz3Rakah2Sajda2,farz3Rakah2Tashahud;
    TextView farz3Rakah3Qayam,farz3Rakah3Ruku,farz3Rakah3Qoum,farz3Rakah3Sajda1,farz3Rakah3Jalsa,farz3Rakah3Sajda2,farz3Rakah3Tashahud;


    TextView sunnah2Rakah1Qayam,sunnah2Rakah1Ruku,sunnah2Rakah1Qoum,sunnah2Rakah1Sajda1,sunnah2Rakah1Jalsa,sunnah2Rakah1Sajda2;
    TextView sunnah2Rakah2Qayam,sunnah2Rakah2Ruku,sunnah2Rakah2Qoum,sunnah2Rakah2Sajda1,sunnah2Rakah2Jalsa,sunnah2Rakah2Sajda2,sunnah2Rakah2Tashahud;

    TextView nafal2Rakah1Qayam,nafal2Rakah1Ruku,nafal2Rakah1Qoum,nafal2Rakah1Sajda1,nafal2Rakah1Jalsa,nafal2Rakah1Sajda2;
    TextView nafal2Rakah2Qayam,nafal2Rakah2Ruku,nafal2Rakah2Qoum,nafal2Rakah2Sajda1,nafal2Rakah2Jalsa,nafal2Rakah2Sajda2,nafal2Rakah2Tashahud;

    String salahNames[]={"Fajr","Zuhr","Asr","Maghrib","Isha"};
    int salahIcons[]={R.drawable.moon,R.drawable.sun2,R.drawable.miday,R.drawable.sunset2,R.drawable.night};
    TextView timetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salah);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        postureDict=findViewById(R.id.postureDict);
        String sourceString = "<b>Q</b>"+"ayam "+"<b>R</b>"+"uku "+"<b>K</b>"+"ouma "+"<b>S</b>"+"ajda "+"<b>T</b>"+"ashahud";
        postureDict.setText(Html.fromHtml(sourceString));
        salahNameTv=findViewById(R.id.salahNameTv);
//        iconStatusIv=findViewById(R.id.iconStatusIv);
        timetv=findViewById(R.id.timetv);
        sunnah4LL=findViewById(R.id.sunnah4LL);
        farz4LL=findViewById(R.id.farz4LL);
        farz3LL=findViewById(R.id.farz3LL);
        sunnah2LL=findViewById(R.id.sunnah2LL);
        nafal2LL=findViewById(R.id.nafal2LL);
        sunnah4Rakah1Qayam=findViewById(R.id.sunnah4Rakah1Qayam);
        sunnah4Rakah1Ruku=findViewById(R.id.sunnah4Rakah1Ruku);
        sunnah4Rakah1Qoum=findViewById(R.id.sunnah4Rakah1Qoum);
        sunnah4Rakah1Sajda1=findViewById(R.id.sunnah4Rakah1Sajda1);
        sunnah4Rakah1Jalsa=findViewById(R.id.sunnah4Rakah1Jalsa);
        sunnah4Rakah1Sajda2=findViewById(R.id.sunnah4Rakah1Sajda2);

        sunnah4Rakah2Qayam=findViewById(R.id.sunnah4Rakah2Qayam);
        sunnah4Rakah2Ruku=findViewById(R.id.sunnah4Rakah2Ruku);
        sunnah4Rakah2Qoum=findViewById(R.id.sunnah4Rakah2Qoum);
        sunnah4Rakah2Sajda1=findViewById(R.id.sunnah4Rakah2Sajda1);
        sunnah4Rakah2Jalsa=findViewById(R.id.sunnah4Rakah2Jalsa);
        sunnah4Rakah2Sajda2=findViewById(R.id.sunnah4Rakah2Sajda2);
        sunnah4Rakah2Tashahud=findViewById(R.id.sunnah4Rakah2Tashahud);

        sunnah4Rakah3Qayam=findViewById(R.id.sunnah4Rakah3Qayam);
        sunnah4Rakah3Ruku=findViewById(R.id.sunnah4Rakah3Ruku);
        sunnah4Rakah3Qoum=findViewById(R.id.sunnah4Rakah3Qoum);
        sunnah4Rakah3Sajda1=findViewById(R.id.sunnah4Rakah3Sajda1);
        sunnah4Rakah3Jalsa=findViewById(R.id.sunnah4Rakah3Jalsa);
        sunnah4Rakah3Sajda2=findViewById(R.id.sunnah4Rakah3Sajda2);

        sunnah4Rakah4Qayam=findViewById(R.id.sunnah4Rakah4Qayam);
        sunnah4Rakah4Ruku=findViewById(R.id.sunnah4Rakah4Ruku);
        sunnah4Rakah4Qoum=findViewById(R.id.sunnah4Rakah4Qoum);
        sunnah4Rakah4Sajda1=findViewById(R.id.sunnah4Rakah4Sajda1);
        sunnah4Rakah4Jalsa=findViewById(R.id.sunnah4Rakah4Jalsa);
        sunnah4Rakah4Sajda2=findViewById(R.id.sunnah4Rakah4Sajda2);
        sunnah4Rakah4Tashahud=findViewById(R.id.sunnah4Rakah4Tashahud);

        //farz4
        farz4Rakah1Qayam=findViewById(R.id.farz4Rakah1Qayam);
        farz4Rakah1Ruku=findViewById(R.id.farz4Rakah1Ruku);
        farz4Rakah1Qoum=findViewById(R.id.farz4Rakah1Qoum);
        farz4Rakah1Sajda1=findViewById(R.id.farz4Rakah1Sajda1);
        farz4Rakah1Jalsa=findViewById(R.id.farz4Rakah1Jalsa);
        farz4Rakah1Sajda2=findViewById(R.id.farz4Rakah1Sajda2);

        farz4Rakah2Qayam=findViewById(R.id.farz4Rakah2Qayam);
        farz4Rakah2Ruku=findViewById(R.id.farz4Rakah2Ruku);
        farz4Rakah2Qoum=findViewById(R.id.farz4Rakah2Qoum);
        farz4Rakah2Sajda1=findViewById(R.id.farz4Rakah2Sajda1);
        farz4Rakah2Jalsa=findViewById(R.id.farz4Rakah2Jalsa);
        farz4Rakah2Sajda2=findViewById(R.id.farz4Rakah2Sajda2);
        farz4Rakah2Tashahud=findViewById(R.id.farz4Rakah2Tashahud);

        farz4Rakah3Qayam=findViewById(R.id.farz4Rakah3Qayam);
        farz4Rakah3Ruku=findViewById(R.id.farz4Rakah3Ruku);
        farz4Rakah3Qoum=findViewById(R.id.farz4Rakah3Qoum);
        farz4Rakah3Sajda1=findViewById(R.id.farz4Rakah3Sajda1);
        farz4Rakah3Jalsa=findViewById(R.id.farz4Rakah3Jalsa);
        farz4Rakah3Sajda2=findViewById(R.id.farz4Rakah3Sajda2);

        farz4Rakah4Qayam=findViewById(R.id.farz4Rakah4Qayam);
        farz4Rakah4Ruku=findViewById(R.id.farz4Rakah4Ruku);
        farz4Rakah4Qoum=findViewById(R.id.farz4Rakah4Qoum);
        farz4Rakah4Sajda1=findViewById(R.id.farz4Rakah4Sajda1);
        farz4Rakah4Jalsa=findViewById(R.id.farz4Rakah4Jalsa);
        farz4Rakah4Sajda2=findViewById(R.id.farz4Rakah4Sajda2);
        farz4Rakah4Tashahud=findViewById(R.id.farz4Rakah4Tashahud);

        //farz3/witr3
        farz3Rakah1Qayam=findViewById(R.id.farz3Rakah1Qayam);
        farz3Rakah1Ruku=findViewById(R.id.farz3Rakah1Ruku);
        farz3Rakah1Qoum=findViewById(R.id.farz3Rakah1Qoum);
        farz3Rakah1Sajda1=findViewById(R.id.farz3Rakah1Sajda1);
        farz3Rakah1Jalsa=findViewById(R.id.farz3Rakah1Jalsa);
        farz3Rakah1Sajda2=findViewById(R.id.farz3Rakah1Sajda2);

        farz3Rakah2Qayam=findViewById(R.id.farz3Rakah2Qayam);
        farz3Rakah2Ruku=findViewById(R.id.farz3Rakah2Ruku);
        farz3Rakah2Qoum=findViewById(R.id.farz3Rakah2Qoum);
        farz3Rakah2Sajda1=findViewById(R.id.farz3Rakah2Sajda1);
        farz3Rakah2Jalsa=findViewById(R.id.farz3Rakah2Jalsa);
        farz3Rakah2Sajda2=findViewById(R.id.farz3Rakah2Sajda2);
        farz3Rakah2Tashahud=findViewById(R.id.farz3Rakah2Tashahud);

        farz3Rakah3Qayam=findViewById(R.id.farz3Rakah3Qayam);
        farz3Rakah3Ruku=findViewById(R.id.farz3Rakah3Ruku);
        farz3Rakah3Qoum=findViewById(R.id.farz3Rakah3Qoum);
        farz3Rakah3Sajda1=findViewById(R.id.farz3Rakah3Sajda1);
        farz3Rakah3Jalsa=findViewById(R.id.farz3Rakah3Jalsa);
        farz3Rakah3Sajda2=findViewById(R.id.farz3Rakah3Sajda2);
        farz3Rakah3Tashahud=findViewById(R.id.farz3Rakah3Tashahud);

        //sunnah2
        sunnah2Rakah1Qayam=findViewById(R.id.sunnah2Rakah1Qayam);
        sunnah2Rakah1Ruku=findViewById(R.id.sunnah2Rakah1Ruku);
        sunnah2Rakah1Qoum=findViewById(R.id.sunnah2Rakah1Qoum);
        sunnah2Rakah1Sajda1=findViewById(R.id.sunnah2Rakah1Sajda1);
        sunnah2Rakah1Jalsa=findViewById(R.id.sunnah2Rakah1Jalsa);
        sunnah2Rakah1Sajda2=findViewById(R.id.sunnah2Rakah1Sajda2);

        sunnah2Rakah2Qayam=findViewById(R.id.sunnah2Rakah2Qayam);
        sunnah2Rakah2Ruku=findViewById(R.id.sunnah2Rakah2Ruku);
        sunnah2Rakah2Qoum=findViewById(R.id.sunnah2Rakah2Qoum);
        sunnah2Rakah2Sajda1=findViewById(R.id.sunnah2Rakah2Sajda1);
        sunnah2Rakah2Jalsa=findViewById(R.id.sunnah2Rakah2Jalsa);
        sunnah2Rakah2Sajda2=findViewById(R.id.sunnah2Rakah2Sajda2);
        sunnah2Rakah2Tashahud=findViewById(R.id.sunnah2Rakah2Tashahud);

        nafal2Rakah1Qayam=findViewById(R.id.nafal2Rakah1Qayam);
        nafal2Rakah1Ruku=findViewById(R.id.nafal2Rakah1Ruku);
        nafal2Rakah1Qoum=findViewById(R.id.nafal2Rakah1Qoum);
        nafal2Rakah1Sajda1=findViewById(R.id.nafal2Rakah1Sajda1);
        nafal2Rakah1Jalsa=findViewById(R.id.nafal2Rakah1Jalsa);
        nafal2Rakah1Sajda2=findViewById(R.id.nafal2Rakah1Sajda2);

        nafal2Rakah2Qayam=findViewById(R.id.nafal2Rakah2Qayam);
        nafal2Rakah2Ruku=findViewById(R.id.nafal2Rakah2Ruku);
        nafal2Rakah2Qoum=findViewById(R.id.nafal2Rakah2Qoum);
        nafal2Rakah2Sajda1=findViewById(R.id.nafal2Rakah2Sajda1);
        nafal2Rakah2Jalsa=findViewById(R.id.nafal2Rakah2Jalsa);
        nafal2Rakah2Sajda2=findViewById(R.id.nafal2Rakah2Sajda2);
        nafal2Rakah2Tashahud=findViewById(R.id.nafal2Rakah2Tashahud);
        imgi1=findViewById(R.id.imgi1);

        /* getting intents from prev activity
        just for checking whichScreen here*/
        intent=getIntent();

        /* whichScreen is for the purpose of displaying customized data
        based on the screen the user has came from*/
        whichScreen=intent.getStringExtra("whichScreen");


        /* If prev screen is SalahProgress,
        it will show the user stats of currently performed Salah*/
        if (whichScreen.equalsIgnoreCase("SalahProgress")){

            getIntents();
            showCurrentStats();
            makeGraph();
        }
        /* If prev screen is DailySalah,
        it will fetch data of today's performed Salahs from firebase*/
        if (whichScreen.equalsIgnoreCase("dailyStats")){
            intent=getIntent();
            /* setting the name of Salah as heading*/
            sel_salah=intent.getStringExtra("sel_salah");
            for (int i=0;i<salahNames.length;i++) {

                if (sel_salah.equalsIgnoreCase(salahNames[i])) {
                    salahNameTv.setText(salahNames[i]);
                    imgi1.setImageDrawable(getResources().getDrawable(salahIcons[i]));
                }
            }

            getData();
        }

        //populate();
        setSupportActionBar(mTopToolbar);
        // make graph
        dataSets = new ArrayList<>();


    }

    private void showCurrentStats() {



        /* loop for the specific name of salah to be appeared when clicked*/
        for (int k=0;k<salahNames.length;k++) {

            if (sel_salah.equalsIgnoreCase(salahNames[k])) {
                // writing the salah name as head
                salahNameTv.setText(salahNames[k]);
                imgi1.setImageDrawable(getResources().getDrawable(salahIcons[k]));
                // show timeliness status
                timetv.setText(timelinessStatus);


                // Salah Instance=4
                if (sel_rakah.equalsIgnoreCase("4")) {
                    if (sel_unit.equalsIgnoreCase("Sunnah")) {
                        sunnah4LL.setVisibility(View.VISIBLE);
                        // function calling for displaying missed/extra steps
                        checkSunnah4();
                    }
                    if (sel_unit.equalsIgnoreCase("Farz")) {
                        farz4LL.setVisibility(View.VISIBLE);
                        checkFarz4();
                    }

                }
                // Salah Instance=2
                if (sel_rakah.equalsIgnoreCase("2")) {

                    if (sel_unit.equalsIgnoreCase("Sunnah")) {
                        sunnah2LL.setVisibility(View.VISIBLE);
                        checkSunnah2();
                    }
                    if (sel_unit.equalsIgnoreCase("Nafl")) {
                        nafal2LL.setVisibility(View.VISIBLE);
                        checkNafl();
                    }
                    if (sel_unit.equalsIgnoreCase("Farz")) {
                        // inlplace of farz use the generic thing as Nafl
                        nafal2LL.setVisibility(View.VISIBLE);
                        checkNafl();
                    }
                }
                // Salah instance 3
                if (sel_rakah.equalsIgnoreCase("3")) {

                    if (sel_unit.equalsIgnoreCase("Witr")) {

                        farz3LL.setVisibility(View.VISIBLE);
                        checkFarz3Witr3();

                    }
                    if (sel_unit.equalsIgnoreCase("Farz")) {

                        farz3LL.setVisibility(View.VISIBLE);
                        checkFarz3Witr3();

                    }
                }
            }
        }//here close for loop of salah names

    }

    private void checkNafl() {
        String[] rakahMissedTokens = rakahMissedString.split("AND");
        for (int i=0;i<rakahMissedTokens.length;i++) {
            if (rakahMissedTokens[i].equalsIgnoreCase("1")) {
                nafal2Rakah1Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah1Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah1Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah1Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
            }
            if (rakahMissedTokens[i].equalsIgnoreCase("2")) {
                nafal2Rakah2Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah2Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah2Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah2Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                nafal2Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));

            }


        }
        //checking missed posture
        String[] postureMissedTokens=possMissedString.split("-");
        for(int i=0;i<postureMissedTokens.length;i++){
            String[] postureMissedTokens2=postureMissedTokens[i].split("IN");
            //postureMissedTokens2[0] // Missed Posture
            //postureMissedTokens2[1] // Rakah Number
            // now checking missing postures inside each rakah
            if(postureMissedTokens2[1].equalsIgnoreCase("1")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    nafal2Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    nafal2Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("2")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    nafal2Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    nafal2Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Tashahud")){
                    nafal2Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }

        }


    }

    private void checkSunnah2() {
        String[] rakahMissedTokens = rakahMissedString.split("AND");
        for (int i=0;i<rakahMissedTokens.length;i++) {
            if (rakahMissedTokens[i].equalsIgnoreCase("1")) {
                sunnah2Rakah1Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah1Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah1Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah1Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
            }
            if (rakahMissedTokens[i].equalsIgnoreCase("2")) {
                sunnah2Rakah2Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah2Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah2Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah2Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                sunnah2Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));

            }

        }
        //checking missed posture
        String[] postureMissedTokens=possMissedString.split("-");
        for(int i=0;i<postureMissedTokens.length;i++){
            String[] postureMissedTokens2=postureMissedTokens[i].split("IN");
            //postureMissedTokens2[0] // Missed Posture
            //postureMissedTokens2[1] // Rakah Number
            // now checking missing postures inside each rakah
            if(postureMissedTokens2[1].equalsIgnoreCase("1")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah2Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah2Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("2")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah2Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah2Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Tashahud")){
                    sunnah2Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }

        }
    }
    private void checkFarz3Witr3() {
        String[] rakahMissedTokens = rakahMissedString.split("AND");
        for (int i=0;i<rakahMissedTokens.length;i++) {
            if (rakahMissedTokens[i].equalsIgnoreCase("1")) {
                farz3Rakah1Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah1Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah1Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah1Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
            }
            if (rakahMissedTokens[i].equalsIgnoreCase("2")) {
                farz3Rakah2Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah2Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah2Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah2Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));

            }
            if (rakahMissedTokens[i].equalsIgnoreCase("3")) {
                farz3Rakah3Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah3Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah3Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah3Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah3Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah3Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                farz3Rakah3Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));

            }


        }
        //checking missed posture
        String[] postureMissedTokens=possMissedString.split("-");
        for(int i=0;i<postureMissedTokens.length;i++){
            String[] postureMissedTokens2=postureMissedTokens[i].split("IN");
            //postureMissedTokens2[0] // Missed Posture
            //postureMissedTokens2[1] // Rakah Number
            // now checking missing postures inside each rakah
            if(postureMissedTokens2[1].equalsIgnoreCase("1")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah2Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah2Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("2")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah2Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah2Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Tashahud")){
                    sunnah2Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }

        }
    }

    private void checkFarz4() {
        // checking missed rakah
        String[] rakahMissedTokens = rakahMissedString.split("AND");
        for (int i=0;i<rakahMissedTokens.length;i++) {
            if (rakahMissedTokens[i].equalsIgnoreCase("1")) {
                farz4Rakah1Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah1Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah1Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah1Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
            }
            if (rakahMissedTokens[i].equalsIgnoreCase("2")) {
                farz4Rakah2Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah2Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah2Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah2Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));

            }
            if (rakahMissedTokens[i].equalsIgnoreCase("3")) {
                farz4Rakah3Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah3Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah3Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah3Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah3Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah3Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
            }
            if (rakahMissedTokens[i].equalsIgnoreCase("4")) {
                farz4Rakah4Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah4Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah4Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah4Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah4Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah4Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                farz4Rakah4Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));

            }
        }
        //checking missed posture
        String[] postureMissedTokens=possMissedString.split("-");
        for(int i=0;i<postureMissedTokens.length;i++){
            String[] postureMissedTokens2=postureMissedTokens[i].split("IN");
            //postureMissedTokens2[0] // Missed Posture
            //postureMissedTokens2[1] // Rakah Number
            // now checking missing postures inside each rakah
            if(postureMissedTokens2[1].equalsIgnoreCase("1")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    farz4Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    farz4Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("2")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    farz4Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    farz4Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Tashahud")){
                    farz4Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("3")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    farz4Rakah3Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    farz4Rakah3Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("4")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    farz4Rakah4Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    farz4Rakah4Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Tashahud")){
                    farz4Rakah4Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }

        }
        // check extra posture
        /*
        String[] extraPostureTokens=extraPostureString.split("-");
        for(int i=0;i<extraPostureTokens.length;i++) {
//            System.out.println("extraPostureTokens["+i+"] : "+extraPostureTokens[i]);
            String[] extraPostureTokens2=postureMissedTokens[i].split("IN");
            System.out.println("outputs "+extraPostureTokens2[0]+" "+extraPostureTokens2[1]);
            //extraPostureTokens2[0] // Extra Posture
            //extraPostureTokens2[1] // Rakah Number
            // now checking extra postures inside each rakah
            if(extraPostureTokens2[1].equalsIgnoreCase("1")){
                // check postures in rakah 1
                // only checking ruku in rakah1 for now for testing
                if(extraPostureTokens2[0].equalsIgnoreCase("Ruku")){
                    System.out.println("ruku is extra in rakah1");
                }
            }
            if(extraPostureTokens2[1].equalsIgnoreCase("1")){
                // check postures in rakah 1
                // only checking tash in rakah3 for now for testing
                if(extraPostureTokens2[0].equalsIgnoreCase("Tashahud")){
                    System.out.println("tashahud is extra in rakah3");
                }
            }
        }*/



    }

    private void checkSunnah4() {

        // checking missed rakah


//        System.out.println("extraPostureString : "+extraPostureString);
//        System.out.println("extraRakahString : "+extraRakahString);
        System.out.println("rakahMissedString : "+rakahMissedString);

            String[] rakahMissedTokens = rakahMissedString.split("AND");
            for (int i = 0; i < rakahMissedTokens.length; i++) {
                if (rakahMissedTokens[i].equalsIgnoreCase("1")) {
                    sunnah4Rakah1Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah1Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah1Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah1Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if (rakahMissedTokens[i].equalsIgnoreCase("2")) {
                    sunnah4Rakah2Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah2Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah2Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah2Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));

                }
                if (rakahMissedTokens[i].equalsIgnoreCase("3")) {
                    sunnah4Rakah3Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah3Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah3Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah3Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah3Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah3Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if (rakahMissedTokens[i].equalsIgnoreCase("4")) {
                    sunnah4Rakah4Qayam.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah4Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah4Qoum.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah4Sajda1.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah4Jalsa.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah4Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                    sunnah4Rakah4Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));

                }

        }
        //checking missed posture

        System.out.println("possMissedString: "+possMissedString);
        String[] postureMissedTokens=possMissedString.split("-");
        for(int i=0;i<postureMissedTokens.length;i++){
            String[] postureMissedTokens2=postureMissedTokens[i].split("IN");
            //postureMissedTokens2[0] // Missed Posture
            //postureMissedTokens2[1] // Rakah Number
            // now checking missing postures inside each rakah
            if(postureMissedTokens2[1].equalsIgnoreCase("1")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah4Rakah1Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah4Rakah1Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("2")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah4Rakah2Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah4Rakah2Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Tashahud")){
                    sunnah4Rakah2Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("3")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah4Rakah3Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah4Rakah3Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }
            if(postureMissedTokens2[1].equalsIgnoreCase("4")){
                // check postures in rakah
                if(postureMissedTokens2[0].equalsIgnoreCase("Sajda2")){
                    sunnah4Rakah4Sajda2.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Ruku")){
                    sunnah4Rakah4Ruku.setBackgroundColor(Color.parseColor("#D58C87"));
                }
                if(postureMissedTokens2[0].equalsIgnoreCase("Tashahud")){
                    sunnah4Rakah4Tashahud.setBackgroundColor(Color.parseColor("#D58C87"));
                }
            }

        }
    }

    private void getIntents() {
        intent=getIntent();
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
        sel_unit=intent.getStringExtra("sel_unit");
        qayamAvg=intent.getStringExtra("qayamAvg");
        rukuAvg=intent.getStringExtra("rukuAvg");
        qoumAvg=intent.getStringExtra("qoumAvg");
        sajdaAvg=intent.getStringExtra("sajdaAvg");
        jalsaAvg=intent.getStringExtra("jalsaAvg");
        tashAvg=intent.getStringExtra("tashAvg");
        possMissedString=intent.getStringExtra("possMissed");
        extraPostureString=intent.getStringExtra("extraPosture");
        System.out.println("gotten extraPostureString :"+extraPostureString);
        extraRakahString=intent.getStringExtra("extraRakah");
        salahStatus=intent.getStringExtra("salahStatus");
        rakahMissedString=intent.getStringExtra("rakahMissed");
        date1=intent.getStringExtra("date1");
        whichScreen=intent.getStringExtra("whichScreen");
        timelinessStatus=intent.getStringExtra("salahTimelinessStatus");
    }




    private void makeGraph() {
        barChart = findViewById(R.id.barChart);
        barEntriesArrayList=new ArrayList<>();
        labelNames=new ArrayList<>();
        if(whichScreen.equalsIgnoreCase("SalahProgress")){
            fillPostureList2();
        }

        if(whichScreen.equalsIgnoreCase("dailyStats")){
            fillPostureListDaily();
        }

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
        PostureNamesArrayList.add(new SalahPostureNames("Sajda",Integer.parseInt(sajdaAvg)));
        PostureNamesArrayList.add(new SalahPostureNames("jalsa",Integer.parseInt(jalsaAvg)));
        PostureNamesArrayList.add(new SalahPostureNames("Tashahud",Integer.parseInt(tashAvg)));
    }
    private void fillPostureListDaily(){
        PostureNamesArrayList.clear();
        PostureNamesArrayList.add(new SalahPostureNames("Qayam",qayamAvgInt));
        PostureNamesArrayList.add(new SalahPostureNames("Ruku",rukuAvgInt));
        PostureNamesArrayList.add(new SalahPostureNames("Sajda",sajdaAvgInt));
        PostureNamesArrayList.add(new SalahPostureNames("Jalsa",jalsaAvgInt));
        PostureNamesArrayList.add(new SalahPostureNames("Tashahud",tashAvgInt));
        System.out.println("ffffffffff:"+qayamAvgInt+rukuAvgInt);
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

    private void getData() {
        //rakah missed and posture missed are done, rakah extra+pos extra are left
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserBio");
        databaseReference.child("user123").child("firebasePrayer").child(sel_salah).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String salahUnit1 = ds.child("salahUnit").getValue(String.class);
                    String salahStatus1 = ds.child("salahStatus").getValue(String.class);
                    String currDate1 = ds.child("currDate").getValue(String.class);
                    int rakahNumber1=ds.child("rakahNumber").getValue(Integer.class);
                     qayamAvgInt=ds.child("qayamAvg").getValue(Integer.class);
                     rukuAvgInt=ds.child("rukuAvg").getValue(Integer.class);
                     sajdaAvgInt=ds.child("sajdaAvg").getValue(Integer.class);
                     jalsaAvgInt=ds.child("jalsaAvg").getValue(Integer.class);
                     tashAvgInt=ds.child("tashAvg").getValue(Integer.class);
                     salahUnitTimeInt=ds.child("salahUnitTime").getValue(Integer.class);
                     possMissedString=ds.child("missedPosture").getValue(String.class);
                     extraPostureString=ds.child("extraPosture").getValue(String.class);
                     extraRakahString=ds.child("extraRakah").getValue(String.class);
                     rakahMissedString=ds.child("missedRakah").getValue(String.class);
                     timelinessStatus=ds.child("salahTimelinessStatus").getValue(String.class);
                     timetv.setText(timelinessStatus);





                    if(salahStatus1.equalsIgnoreCase("Error")){
//                        iconStatusIv.setImageDrawable(getResources().getDrawable(R.drawable.error));
                    }
                    if(salahStatus1.equalsIgnoreCase("Complete")){
//                        iconStatusIv.setImageDrawable(getResources().getDrawable(R.drawable.tic));
                    }

                    if(salahUnit1.equalsIgnoreCase("Sunnah")){
                        if(rakahNumber1==4){
                            checkSunnah4();
                            sunnah4LL.setVisibility(View.VISIBLE);
                        }else if(rakahNumber1==2){
                            checkSunnah2();
                            sunnah2LL.setVisibility(View.VISIBLE);
                        }
                    }
                    if(salahUnit1.equalsIgnoreCase("Farz")){
                        if(rakahNumber1==4){
                            checkFarz4();
                            farz4LL.setVisibility(View.VISIBLE);
                        }else if(rakahNumber1==2){
                            checkNafl();
                            nafal2LL.setVisibility(View.VISIBLE);
                        }
                        else if(rakahNumber1==3){
                            checkFarz3Witr3();
                            farz3LL.setVisibility(View.VISIBLE);
                        }
                    }
                    if(salahUnit1.equalsIgnoreCase("Nafl")){
                        if(rakahNumber1==2){
                            checkNafl();
                            nafal2LL.setVisibility(View.VISIBLE);
                        }
                    }

                }
                makeGraph();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("FAILED");

            }
        });


    }







}