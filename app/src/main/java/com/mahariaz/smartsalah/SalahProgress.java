package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.ArrayList;
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

    /* for spinners*/
    Spinner spinner_salah,spinner_rakah;
    String sel_salah,sel_rakah,user_name;
    final DBAdapter db=new DBAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_progress);
        //spinner_salah
        spinner_salah = (Spinner) findViewById(R.id.spinner_salah);
        ArrayAdapter<String> Adapter_salah = new ArrayAdapter<String>(SalahProgress.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.salah));
        Adapter_salah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_salah.setAdapter(Adapter_salah);
        //spinner_rakah
        spinner_rakah = (Spinner) findViewById(R.id.spinner_rakah);
        ArrayAdapter<String> Adapter_rakah = new ArrayAdapter<String>(SalahProgress.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.rakah));
        Adapter_rakah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rakah.setAdapter(Adapter_rakah);
        // getting ids and values

        file_reading();
        qayam_avg=calculate_posture_avg_time(qayam_time,ruku_time);
        System.out.println("avg_qayam_time : "+qayam_avg);
        ruku_avg=calculate_posture_avg_time(ruku_time,qoum_time);
        System.out.println("avg_ruku_time : "+ruku_avg);
        qoum_avg=calculate_posture_avg_time(qoum_time,sajda1_time);
        System.out.println("avg_qoum_time : "+qoum_avg);
        sajda_avg=calculate_posture_avg_time(sajda1_time,jalsa1_time);
        System.out.println("avg_sajda_time : "+sajda_avg);
        jalsa_avg=calculate_posture_avg_time(jalsa1_time,sajda2_time);
        System.out.println("avg_jalsa_time : "+jalsa_avg);
        tash_avg=12;
        //populating values in database sqlite
        sqlite_storage();


    }

    private void sqlite_storage() {
        db.openDB();
        sel_salah = spinner_salah.getSelectedItem().toString();
        sel_rakah = spinner_rakah.getSelectedItem().toString();
        System.out.println("ffffffff  "+sel_salah);
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