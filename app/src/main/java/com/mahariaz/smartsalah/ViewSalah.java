package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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

public class ViewSalah extends AppCompatActivity {
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salah);

        //reading csv file Amna Arshad Fajar

        InputStream is = getResources().openRawResource(R.raw.file1);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        String []split_qayam_time;
        String []split_ruku_time;

        ArrayList<String> qayam_time=new ArrayList<>();
        ArrayList<String> ruku_time=new ArrayList<>();
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

                        break;

                    }


                }

            }
            int i1,i2,i3,i4,var1,var2,var3,avg_qayam_time=0;
            System.out.println(qayam_time);
            System.out.println(ruku_time);
            for (int i=0;i<qayam_time.size();i++){
                String[] temp1=qayam_time.get(i).split(":");
                String[] temp2=ruku_time.get(i).split(":");
                 i1=Integer.parseInt(temp1[0]);
                 i2=Integer.parseInt(temp1[1]);
                 i3=Integer.parseInt(temp2[0]);
                 i4=Integer.parseInt(temp2[1]);
                 var1=i3-i1;var2=i4-i2;
                 if(var1==0){
                     var3=var2;
                 }
                 else{
                     var3=10;
                 }
                 avg_qayam_time+=var3/qayam_time.size();


            }
            System.out.println(avg_qayam_time);


        } catch (IOException e1) {
            Log.e("ViewSalah", "Error" + line, e1);
            e1.printStackTrace();
        }


        barChart = findViewById(R.id.idBarChart);
        barEntriesArrayList=new ArrayList<>();
        labelNames=new ArrayList<>();
        fillPostureList();
        for (int i=0;i<PostureNamesArrayList.size();i++){
            String postures=PostureNamesArrayList.get(i).getPostureName();
            int avgPostureTime=PostureNamesArrayList.get(i).getAvgPostureTime();
            barEntriesArrayList.add(new BarEntry(i,avgPostureTime));
            labelNames.add(postures);
        }
        BarDataSet barDataSet=new BarDataSet(barEntriesArrayList,"Postures");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description=new Description();
        description.setText("");
        barChart.setDescription(description);
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLinesBehindData(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelNames.size());
        xAxis.setLabelRotationAngle(360);
        barChart.animateY(2000);
        barChart.invalidate();

    }

    private void fillPostureList(){
        PostureNamesArrayList.clear();
        PostureNamesArrayList.add(new SalahPostureNames("Qayam",30));
        PostureNamesArrayList.add(new SalahPostureNames("Ruku",40));
        PostureNamesArrayList.add(new SalahPostureNames("Qouma",20));
        PostureNamesArrayList.add(new SalahPostureNames("Sajda",12));
        PostureNamesArrayList.add(new SalahPostureNames("Jalsa",15));
        PostureNamesArrayList.add(new SalahPostureNames("Tashahud",10));
    }
}