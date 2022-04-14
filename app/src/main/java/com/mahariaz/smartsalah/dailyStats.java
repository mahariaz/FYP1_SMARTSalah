package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class dailyStats extends Fragment {
    int avgQayam=0,avgRuku=0,avgQoum=0,avgSajda=0,avgJalsa=0,avgTashahud=0;
    String lastTime[];
    BarChart barChart;
    // variable for our bar data.
    BarData barData;
    // variable for our bar data set.
    BarDataSet barDataSet;
    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();
    CardView fjrTile;
    /* for slpitting the times min:sec from timestamp*/
    String []split_qayam_time;
    String []split_ruku_time;
    String []split_qoum_time;
    String []split_sajda_time;
    String []split_jalsa_time;
    String []split_tash_time;

    /*for saving all min:sec of each posture*/
    ArrayList<String> qayam_time=new ArrayList<>();
    ArrayList<String> ruku_time=new ArrayList<>();
    ArrayList<String> qoum_time=new ArrayList<>();
    ArrayList<String> sajda_time=new ArrayList<>();
    ArrayList<String> jalsa_time=new ArrayList<>();
    ArrayList<String> tash_time=new ArrayList<>();
    /* for average time*/
    int qayam_avg,ruku_avg,qoum_avg,sajda_avg,jalsa_avg,tash_avg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        file_reading();
//        qayam_avg=calculate_posture_avg_time(qayam_time,ruku_time);
//        //System.out.println("avg_qayam_time : "+qayam_avg);
//        ruku_avg=calculate_posture_avg_time(ruku_time,qoum_time);
//        //System.out.println("avg_ruku_time : "+ruku_avg);
//        qoum_avg=calculate_posture_avg_time(qoum_time,sajda_time);
//        //System.out.println("avg_qoum_time : "+qoum_avg);
//        sajda_avg=calculate_posture_avg_time(sajda_time,jalsa_time);
//        //System.out.println("avg_sajda_time : "+sajda_avg);
//        tash_avg=12;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_daily_stats, container, false);


        fjrTile=view.findViewById(R.id.fjrTile);
        fjrTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ViewSalah.class);
                intent.putExtra("whichScreen","dailySalah");
                intent.putExtra("qayam_avg",qayam_avg);
                intent.putExtra("ruku_avg",ruku_avg);
                intent.putExtra("qoum_avg",qoum_avg);
                intent.putExtra("sajda_avg",sajda_avg);
                intent.putExtra("jalsa",jalsa_avg);
                intent.putExtra("tash_avg",tash_avg);
                startActivity(intent);

            }
        });
        barChart = view.findViewById(R.id.idBarChart);
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
        return view;
    }
    private void fillPostureList(){
        PostureNamesArrayList.clear();
        PostureNamesArrayList.add(new SalahPostureNames("Fajar",12));
        PostureNamesArrayList.add(new SalahPostureNames("Zuhr",3));
        PostureNamesArrayList.add(new SalahPostureNames("Asar",2));
        PostureNamesArrayList.add(new SalahPostureNames("Maghrib",5));
        PostureNamesArrayList.add(new SalahPostureNames("Isha",2));
    }
    private void getLastLine(){
        String lastline = "";
        String lastlineF = "";
        String tmp1[];
        InputStream is;
        BufferedReader reader;

        is = getResources().openRawResource(R.raw.maha_rakah2);
        reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));

        try {
            while ((lastline = reader.readLine()) != null) {
                lastlineF=lastline;
            }
            tmp1=lastlineF.split(",");
            lastTime=tmp1[0].split(":");
            System.out.println(lastTime[2]);
        } catch (IOException e1) {
            Log.e("ViewSalah", "Error" + lastlineF, e1);
            e1.printStackTrace();
        }

    }
    private void file_reading() {
        getLastLine();
        InputStream is;
        BufferedReader reader;

        is = getResources().openRawResource(R.raw.maha_rakah2);
        reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));

        filereading(reader);



    }

    private void filereading(BufferedReader reader) {
        int qayamT=0,rukuT=0,qoumT=0,sajdaT=0,jalsaT=0,tashT=0;
        int qayamTime=0,rukuTime=0,qoumTime=0,sajdaTime=0,jalsaTime=0,tashTime=0;
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split("\n");
                for (int i=0;i<tokens.length;i++){
                    String[] tokens2 = tokens[i].split(",");
//                    for (int j=0;j<tokens2.length;j++){
                        //configuring the activity/posture
                        String s1=tokens2[1];

                        if(tokens2[1].equalsIgnoreCase("Qayam")){
                            split_qayam_time=tokens2[0].split(":");
                            // saving inutes and seconds in qayam_time
                            qayam_time.add(split_qayam_time[1]+":"+split_qayam_time[2]);
                            String a[]=qayam_time.get(0).split(":");
                            qayamT=Integer.parseInt(a[1]);
                            System.out.println("qayam1 : "+qayamT);



                        }
                        if(tokens2[1].equalsIgnoreCase("Ruku")){
                            qayam_time.add("qayam1Done");
                            split_ruku_time=tokens2[0].split(":");
                            //System.out.println("Ruku : "+tokens2[1]+" min: "+split_ruku_time[1]+" sec: "+split_ruku_time[2]);
                            ruku_time.add(split_ruku_time[1]+":"+split_ruku_time[2]);
                            String a[]=ruku_time.get(0).split(":");
                            rukuT=Integer.parseInt(a[1]);
                            System.out.println("ruku1 : "+rukuT);
                             qayamTime=rukuT-qayamT;
                            System.out.println("qayamTime"+qayamTime);

                        }
                        if(tokens2[1].equalsIgnoreCase("Qoum")){
                            split_qoum_time=tokens2[0].split(":");
                            qoum_time.add(split_qoum_time[1]+":"+split_qoum_time[2]);
                            String a[]=qoum_time.get(0).split(":");
                             qoumT=Integer.parseInt(a[1]);
                            System.out.println("qoum1 : "+qoumT);
                             rukuTime=qoumT-rukuT;

                        }
                        if(tokens2[1].equalsIgnoreCase("Sajda")){
                            split_sajda_time=tokens2[0].split(":");
                            sajda_time.add(split_sajda_time[1]+":"+split_sajda_time[2]);
                            String a[]=sajda_time.get(0).split(":");
                             sajdaT=Integer.parseInt(a[1]);
                            System.out.println("sajda1 : "+sajdaT);
                             qoumTime=sajdaT-qoumT;

                        }
                        if(tokens2[1].equalsIgnoreCase("Jalsa")){
                            split_jalsa_time=tokens2[0].split(":");
                            jalsa_time.add(split_jalsa_time[1]+":"+split_jalsa_time[2]);
                            String a[]=jalsa_time.get(0).split(":");
                            int jalsa1=Integer.parseInt(a[1]);
                            System.out.println("jalsa1 : "+jalsa1);
                             sajdaTime=jalsaT-sajdaT;

                        }
                        if(tokens2[1].equalsIgnoreCase("Tashahud")){
                            split_tash_time=tokens2[0].split(":");
                            tash_time.add(split_tash_time[1]+":"+split_tash_time[2]);
                            String a[]=tash_time.get(0).split(":");
                            int tash1=Integer.parseInt(a[1]);
                            System.out.println("qayam1 : "+tash1);
                             tashTime=23-tashT;
//
                        }
                        avgQayam+=qayamTime;
                        avgRuku+=rukuTime;
                        avgQoum+=qoumTime;
                        avgSajda+=sajdaTime;
                        avgJalsa+=jalsaTime;
                        avgTashahud+=tashTime;
                        qayamTime=0;
                        rukuTime=0;
                        qoumTime=0;
                        sajdaTime=0;
                        jalsaTime=0;
                        tashTime=0;

                        break;

                   // }


                }

            }



        } catch (IOException e1) {
            Log.e("ViewSalah", "Error" + line, e1);
            e1.printStackTrace();
        }
        System.out.println("QAYAM AVG T : "+avgQayam/2);
        System.out.println("RUKU AVG T : "+avgRuku/2);
        System.out.println("QOUM AVG T : "+avgQoum/2);
        System.out.println("SAJDA AVG T : "+avgSajda/2);
        System.out.println("JALSA AVG T : "+avgJalsa/2);
        System.out.println("TASH AVG T : "+avgTashahud/2);
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