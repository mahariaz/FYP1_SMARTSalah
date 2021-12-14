package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.opencsv.CSVReader;

public class SalahProgress extends AppCompatActivity {

    /* for slpitting the times min:sec from timestamp*/
    String []split_qayam_time;
    String []split_ruku_time;
    String []split_qoum_time;
    String []split_sajda1_time;
    String []split_sajda2_time;
    String []split_jalsa1_time;
    String []split_tash_time;

    /*for saving all min:sec of each posture*/
    ArrayList<String> qayam_time=new ArrayList<>();
    ArrayList<String> ruku_time=new ArrayList<>();
    ArrayList<String> qoum_time=new ArrayList<>();
    ArrayList<String> sajda1_time=new ArrayList<>();
    ArrayList<String> sajda2_time=new ArrayList<>();
    ArrayList<String> jalsa1_time=new ArrayList<>();
    ArrayList<String> tash_time=new ArrayList<>();
    /* for average time*/
    int qayam_avg,ruku_avg,qoum_avg,sajda_avg,jalsa_avg,tash_avg;

    String sel_salah,sel_rakah,rakah_performed,user_name,timestamp;
    TextView rakah1,rakah2,rakah3,rakah4;
    ProgressBar bar1,bar2,bar3,bar4;

    boolean is_bar1_filled=false,is_bar2_filled=false,is_bar3_filled=false,is_bar4_filled=false;
    boolean one_fill=false,two_fill=false,three_fill=false,four_fill=false;
    final DBAdapter db=new DBAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_progress);
        get_intents();
        get_bar_ids();
        display_progressBars();
        // filling progress bars
        fill_bar1(bar1);


        file_reading();
        qayam_avg=calculate_posture_avg_time(qayam_time,ruku_time);
        //System.out.println("avg_qayam_time : "+qayam_avg);
        ruku_avg=calculate_posture_avg_time(ruku_time,qoum_time);
        //System.out.println("avg_ruku_time : "+ruku_avg);
        qoum_avg=calculate_posture_avg_time(qoum_time,sajda1_time);
        //System.out.println("avg_qoum_time : "+qoum_avg);
        sajda_avg=calculate_posture_avg_time(sajda1_time,jalsa1_time);
        //System.out.println("avg_sajda_time : "+sajda_avg);
        jalsa_avg=calculate_posture_avg_time(jalsa1_time,sajda2_time);
        //System.out.println("avg_jalsa_time : "+jalsa_avg);
        tash_avg=12;
        //populating values in database sqlite
        Button end_salah=findViewById(R.id.end_salah_btn);
        Button view_salah=findViewById(R.id.view_salah_btn);

        end_salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlite_storage();

            }
        });
        view_salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.curr_rakah=sel_rakah;
                shared.curr_salah=sel_salah;
                Intent intent=new Intent(SalahProgress.this,ViewSalah.class);
                startActivity(intent);

            }
        });
    }

    private void fill_bar1(ProgressBar bar) {

        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=10;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                    is_bar1_filled=true;
                    if (sel_rakah.equalsIgnoreCase("2") ||
                            sel_rakah.equalsIgnoreCase("3") ||
                            sel_rakah.equalsIgnoreCase("4"))
                        fill_bar2(bar2);
                }

            }
        };
        t.schedule(tt,0,100);

    }
    private void fill_bar2(ProgressBar bar) {

        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=10;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                    if (sel_rakah.equalsIgnoreCase("3") ||
                            sel_rakah.equalsIgnoreCase("4")){
                        fill_bar3(bar3);
                    }
                }

            }
        };
        t.schedule(tt,0,100);

    }
    private void fill_bar3(ProgressBar bar) {

        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=10;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                    if (sel_rakah.equalsIgnoreCase("4")){
                        fill_bar4(bar4);
                    }
                }

            }
        };
        t.schedule(tt,0,100);

    }
    private void fill_bar4(ProgressBar bar) {

        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=10;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                }

            }
        };
        t.schedule(tt,0,100);

    }


    private void get_intents() {
        Intent intent=getIntent();
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
    }

    private void get_bar_ids() {

        rakah1=findViewById(R.id.rakah1);
        rakah2=findViewById(R.id.rakah2);
        rakah3=findViewById(R.id.rakah3);
        rakah4=findViewById(R.id.rakah4);
        bar1=findViewById(R.id.bar1);
        bar2=findViewById(R.id.bar2);
        bar3=findViewById(R.id.bar3);
        bar4=findViewById(R.id.bar4);
    }

    private void display_progressBars() {
        if(sel_rakah.equalsIgnoreCase("1")){
            rakah1.setVisibility(View.VISIBLE);
            bar1.setVisibility(View.VISIBLE);
            one_fill=true;

        }
        if(sel_rakah.equalsIgnoreCase("2")){
            rakah1.setVisibility(View.VISIBLE);
            bar1.setVisibility(View.VISIBLE);
            rakah2.setVisibility(View.VISIBLE);
            bar2.setVisibility(View.VISIBLE);
            two_fill=true;

        }
        if(sel_rakah.equalsIgnoreCase("3")){
            rakah1.setVisibility(View.VISIBLE);
            bar1.setVisibility(View.VISIBLE);
            rakah2.setVisibility(View.VISIBLE);
            bar2.setVisibility(View.VISIBLE);
            rakah3.setVisibility(View.VISIBLE);
            bar3.setVisibility(View.VISIBLE);
            three_fill=true;


        }
        if(sel_rakah.equalsIgnoreCase("4")){
            rakah1.setVisibility(View.VISIBLE);
            bar1.setVisibility(View.VISIBLE);
            rakah2.setVisibility(View.VISIBLE);
            bar2.setVisibility(View.VISIBLE);
            rakah3.setVisibility(View.VISIBLE);
            bar3.setVisibility(View.VISIBLE);
            rakah4.setVisibility(View.VISIBLE);
            bar4.setVisibility(View.VISIBLE);
            four_fill=true;


        }
    }

    private void sqlite_storage() {
        /*db.openDB();
        db.delete_records();*/
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss:SSS");
        timestamp=input.format(new Date());
        user_name=shared.username;
        db.openDB();
        rakah_performed=sel_rakah;
        long result=db.add(user_name,
                sel_salah,
                sel_rakah,
                rakah_performed,
                String.valueOf(qayam_avg),
                String.valueOf(ruku_avg),
                String.valueOf(qoum_avg),
                String.valueOf(sajda_avg),
                String.valueOf(jalsa_avg),
                String.valueOf(tash_avg),
                timestamp.toString()
        );
        if(result!=0){

        }else{
            Toast.makeText(SalahProgress.this,"Failure",Toast.LENGTH_LONG);

        }
        //to see the records
        /*db.openDB();
        Cursor c=db.getAllValues();
        while(c.moveToNext()){
            String get_time=c.getString(11);
            System.out.println("time : "+get_time);
        }*/



    }

    private void file_reading() {
        //reading csv file Amna Arshad Fajar

        InputStream is = getResources().openRawResource(R.raw.file1);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";




        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split("\n");
                for (int i=0;i<tokens.length;i++){
                    //System.out.println(tokens[i]);
                    String[] tokens2 = tokens[i].split(",");
                    for (int j=0;j<tokens2.length;j++){
                        //System.out.println(tokens2[j]);
                        if(tokens2[2].equalsIgnoreCase("Qayam")){

                            split_qayam_time=tokens2[1].split(":");
                            //System.out.println("Qayam : "+tokens2[1]+" min: "+split_qayam_time[1]+" sec: "+split_qayam_time[2]);
                            qayam_time.add(split_qayam_time[1]+":"+split_qayam_time[2]);
                        }
                        if(tokens2[2].equalsIgnoreCase("Ruku")){
                            split_ruku_time=tokens2[1].split(":");
                            //System.out.println("Ruku : "+tokens2[1]+" min: "+split_ruku_time[1]+" sec: "+split_ruku_time[2]);
                            ruku_time.add(split_ruku_time[1]+":"+split_ruku_time[2]);
                        }
                        if(tokens2[2].equalsIgnoreCase("Qoum")){
                            split_qoum_time=tokens2[1].split(":");
                            qoum_time.add(split_qoum_time[1]+":"+split_qoum_time[2]);
                        }
                        if(tokens2[2].equalsIgnoreCase("Sajda1")){
                            split_sajda1_time=tokens2[1].split(":");
                            sajda1_time.add(split_sajda1_time[1]+":"+split_sajda1_time[2]);
                        }
                        if(tokens2[2].equalsIgnoreCase("Sajda2")){
                            split_sajda2_time=tokens2[1].split(":");
                            sajda2_time.add(split_sajda2_time[1]+":"+split_sajda2_time[2]);
                        }
                        if(tokens2[2].equalsIgnoreCase("jalsa1")){
                            split_jalsa1_time=tokens2[1].split(":");
                            jalsa1_time.add(split_jalsa1_time[1]+":"+split_jalsa1_time[2]);
                        }
                        if(tokens2[2].equalsIgnoreCase("Tashahud")){
                            split_tash_time=tokens2[1].split(":");
                            tash_time.add(split_tash_time[1]+":"+split_tash_time[2]);
                        }

                        break;

                    }


                }

            }



        } catch (IOException e1) {
            Log.e("ViewSalah", "Error" + line, e1);
            e1.printStackTrace();
        }

    }

    private int calculate_posture_avg_time(ArrayList<String> curr,ArrayList<String>next) {
        int posture_avg_time=0,atime,var1,var2;
        for (int i=0;i<curr.size();i++){
            String[] curr_posture=curr.get(i).split(":");
            String[] next_posture=next.get(i).split(":");
            var1=Integer.parseInt(next_posture[0])-Integer.parseInt(curr_posture[0]); //min diff
            var2=Integer.parseInt(next_posture[1])-Integer.parseInt(curr_posture[1]); // sec diff
            if (var1==0){
                atime=var2;
            }
            else{
                atime=10;
            }
            posture_avg_time+=atime;
        }
        return posture_avg_time/curr.size();

    }

}