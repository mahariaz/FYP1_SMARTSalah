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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class dailyStats extends Fragment {
    int fileFajarFarz2[]={R.raw.user1_file1_2};
    int fileFajarSunnah2[]={R.raw.user1_file1_2};
    int fileZuhrSunnah4[]={R.raw.user1_file1_zuhr_s};
    int fileZuhrFarz4[]={R.raw.user1_file1_zuhr_s};
    int fileZuhrSunnah2[]={R.raw.user1_file1_2};
    int fileZuhrNafil2[]={R.raw.user1_file1_2};
    int fileAsarFarz4[]={R.raw.user1_file1_zuhr_s};
    int fileAsarSunnah4[]={R.raw.user1_file1_zuhr_s};
    int fileIshaSunnah4[]={R.raw.user1_file1_zuhr_s};
    int fileIshaFarz4[]={R.raw.user1_file1_zuhr_s};
    int fileIshaSunnah2[]={R.raw.user1_file1_2};
    int fileIshaNafil2[]={R.raw.user1_file1_2};
    int fileIshaWitr[]={R.raw.user1_file1_3};
    int fileMaghribFarz3[]={R.raw.user1_file1_3};
    int fileMaghribSunnah2[]={R.raw.user1_file1_2};
    int fileMaghribNafil2[]={R.raw.user1_file1_2};

    int fjrTime=0,zuhrTime=0,asrTime=0,mgbTime=0,ishaTime=0;

    int qayamAvg=1,rukuAvg=2,qoumAvg=3,sajdaAvg=5,tashAvg=12;
    String lastTime[];
    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();
    CardView fjrTile,zuhrTile,asrTile,mgbTile,ishaTile;
    String completeness="No",correctness="No";
    String []split_time1;
    String []split_time2;
    InputStream inputStream;
    BufferedReader reader;
    int qayamT=0,rukuT=0,qoumT=0,sajdaT=0,jalsaT=0,tashT=0;
    String s1="";
    String s2="";
    String t1="";
    String t2="";
    String token_value="";
    String token_name="";
    int total_qayam=0,total_ruku=0,total_qoum=0,total_sajda=0,total_jalsa=0,total_tash=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_daily_stats, container, false);

        SalahTimes();


        fjrTile=view.findViewById(R.id.fjrTile);
        fjrTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvgTime(fileFajarFarz2);
                // read fajar file and pass averages to viewsalah screen
                Intent intent=new Intent(getActivity(),ViewSalah.class);
                intent.putExtra("sel_salah","Fajar");
                intent.putExtra("sel_rakah","2");
                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
                intent.putExtra("rukuAvg",String.valueOf(total_ruku));
                intent.putExtra("qoumAvg",String.valueOf(total_qoum));
                intent.putExtra("sajdaAvg",String.valueOf(total_sajda));
                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness","Yes");
                intent.putExtra("correctness","Yes");
                startActivity(intent);

            }
        });
        zuhrTile=view.findViewById(R.id.ZhrTile);
        zuhrTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvgTime(fileZuhrFarz4);
                // read zuhr file and pass averages to viewsalah screen
                Intent intent=new Intent(getActivity(),ViewSalah.class);
                intent.putExtra("sel_salah","Zuhr");
                intent.putExtra("sel_rakah","4");
                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
                intent.putExtra("rukuAvg",String.valueOf(0));
                intent.putExtra("qoumAvg",String.valueOf(total_qoum));
                intent.putExtra("sajdaAvg",String.valueOf(total_sajda));
                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness","Yes");
                intent.putExtra("correctness","No");
                startActivity(intent);

            }
        });
        asrTile=view.findViewById(R.id.AsrTile);
        asrTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvgTime(fileAsarFarz4);
                // read asr file and pass averages to viewsalah screen
                Intent intent=new Intent(getActivity(),ViewSalah.class);
                intent.putExtra("sel_salah","Asr");
                intent.putExtra("sel_rakah","4");
                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
                intent.putExtra("rukuAvg",String.valueOf(total_ruku));
                intent.putExtra("qoumAvg",String.valueOf(total_qoum));
                intent.putExtra("sajdaAvg",String.valueOf(total_sajda));
                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness","Yes");
                intent.putExtra("correctness","Yes");
                startActivity(intent);

            }
        });
        mgbTile=view.findViewById(R.id.MgbTile);
        mgbTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvgTime(fileMaghribFarz3);
                // read mgb file and pass averages to viewsalah screen
                Intent intent=new Intent(getActivity(),ViewSalah.class);
                intent.putExtra("sel_salah","Maghrib");
                intent.putExtra("sel_rakah","3");
                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
                intent.putExtra("rukuAvg",String.valueOf(total_ruku));
                intent.putExtra("qoumAvg",String.valueOf(total_qoum));
                intent.putExtra("sajdaAvg",String.valueOf(total_sajda));
                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness","Yes");
                intent.putExtra("correctness","Yes");
                startActivity(intent);

            }
        });
        ishaTile=view.findViewById(R.id.IshaTile);
        ishaTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvgTime(fileIshaFarz4);
                // read isha file and pass averages to viewsalah screen
                Intent intent=new Intent(getActivity(),ViewSalah.class);
                intent.putExtra("sel_salah","Isha");
                intent.putExtra("sel_rakah","4");
                intent.putExtra("qayamAvg",String.valueOf(total_qayam));
                intent.putExtra("rukuAvg",String.valueOf(total_qoum));
                intent.putExtra("qoumAvg",String.valueOf(0));
                intent.putExtra("sajdaAvg",String.valueOf(0));
                intent.putExtra("tashAvg",String.valueOf(total_tash));
                intent.putExtra("completeness","Yes");
                intent.putExtra("correctness","No");
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
        PostureNamesArrayList.add(new SalahPostureNames("Fajar",fjrTime));
        PostureNamesArrayList.add(new SalahPostureNames("Zuhr",zuhrTime));
        PostureNamesArrayList.add(new SalahPostureNames("Asar",asrTime));
        PostureNamesArrayList.add(new SalahPostureNames("Maghrib",mgbTime));
        PostureNamesArrayList.add(new SalahPostureNames("Isha",ishaTime));
    }
    private void getAvgTime(int [] array){


        inputStream = getResources().openRawResource(array[0]);
        reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = "";
        String line1 = "";
        try {

            while ((line = reader.readLine()) != null) {

                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split("\n");

                for (int i = 0; i < tokens.length; i++) {
                    String[] tokens2 = tokens[i].split(",");
                    token_value=tokens2[0];
                    token_name=tokens2[1];
                    if(!s1.equals(tokens2[1]) & s1=="" & t1=="") {
                        t1 = tokens2[0];
                        s1 = tokens2[1];



                    } else if (!s1.equals(tokens2[1]) & s2=="" & t2=="") {
                        t2 = tokens2[0];
                        s2 = tokens2[1];
                        split_time1=t1.split(":");
                        split_time2=t2.split(":");
                        int a=Integer.parseInt(split_time1[2]);
                        int b=Integer.parseInt(split_time2[2]);
                        if(s1.equals("Qayam")) {


                            if((b-a)>0) {
                                qayamT=b-a;
                            }
                            System.out.println("qayam : "+qayamT);
                            total_qayam+=qayamT;
                            //qayamT=0;
                        }
                        if(s1.equals("Ruku")) {
                            if((b-a)>0) {
                                rukuT = b - a;
                            }
                            System.out.println("ruku : "+rukuT);
                            total_ruku+=rukuT;
                            //rukuT=0;
                        }
                        if(s1.equals("Qoum")) {
                            if((b-a)>0) {
                                qoumT = b - a;
                            }
                            System.out.println("qoum : "+qoumT);
                            total_qoum+=qoumT;
                            //qoumT=0;

                        }
                        if(s1.equals("Sajda")) {
                            if((b-a)>0) {
                                sajdaT = b - a;
                            }
                            System.out.println("sajda : "+sajdaT);
                            total_sajda+=sajdaT;
                            //sajdaT=0;
                        }
                        if(s1.equals("Jalsa")) {
                            if((b-a)>0) {
                                jalsaT = b - a;
                            }
                            System.out.println("jalsa : "+jalsaT);
                            total_jalsa+=jalsaT;
                            //jalsaT=0;
                        }
                        if(s1.equals("Tashahud")) {
                            if((b-a)>0) {
                                tashT = b - a;
                            }
                            System.out.println("tashahud : "+tashT);
                            total_tash+=tashT;
                            //tashT=0;
                        }
                        line1=t2;
                        s1=s2;
                        t1=t2;
                        s2="";
                        t2="";

                    }

                }

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("line : "+line1);
        System.out.println("token : "+token_value);
        split_time1=line1.split(":");
        split_time2=token_value.split(":");
        int a=Integer.parseInt(split_time1[2]);
        int b=Integer.parseInt(split_time2[2]);
        tashT=b-a;
        System.out.println("tashahud : "+tashT);

        total_tash+=tashT;

        total_qayam=total_qayam/2;
        total_ruku=total_ruku/2;
        total_qoum=total_qoum/2;
        total_sajda=total_sajda/2;
        total_jalsa=total_jalsa/2;
        total_tash=total_tash/2;
        System.out.println("avg qayam: "+total_qayam);
        System.out.println("avg ruku: "+total_ruku);
        System.out.println("avg qoum: "+total_qoum);
        System.out.println("avg sajda: "+total_sajda);
        System.out.println("avg jalsa: "+total_jalsa);
        System.out.println("avg jalsa: "+total_tash);
    }
    private void SalahTimes(){
        // fajar
        int fjrSunnah=calculatTime(fileFajarSunnah2);
        int fjrFarz=calculatTime(fileFajarFarz2);
        fjrTime=fjrSunnah+fjrFarz;
        //Zuhr
        int zuhrSunnah4=calculatTime(fileZuhrSunnah4);
        int zuhrFarz4=calculatTime(fileZuhrFarz4);
        int zuhrSunnah2=calculatTime(fileZuhrSunnah2);
        int zuhrNafil2=calculatTime(fileZuhrNafil2);
        zuhrTime=zuhrSunnah4+zuhrFarz4+zuhrSunnah2+zuhrNafil2;
        //Asar
        int asarSunnah4=calculatTime(fileAsarSunnah4);
        int asarFarz4=calculatTime(fileAsarFarz4);
        asrTime=asarSunnah4+asarFarz4;
        //Maghrib
        int mgbFarz3=calculatTime(fileMaghribFarz3);
        int mgbSunnah2=calculatTime(fileMaghribSunnah2);
        int mgbNafil2=calculatTime(fileMaghribNafil2);
        mgbTime=mgbFarz3+mgbSunnah2+mgbNafil2;
        //Isha
        int ishaSunnah4=calculatTime(fileIshaSunnah4);
        int ishaFarz4=calculatTime(fileIshaFarz4);
        int ishaSunnah2=calculatTime(fileIshaSunnah2);
        int ishaNafil2=calculatTime(fileIshaNafil2);
        int ishaWitr=calculatTime(fileIshaWitr);
        ishaTime=ishaSunnah4+ishaFarz4+ishaSunnah2+ishaNafil2+ishaWitr+ishaNafil2;

    }
    private int calculatTime(int [] array){
        InputStream is;
        BufferedReader reader;
        int unitSum=0;
            is = getResources().openRawResource(array[0]);
            reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            // diffmin is returning minutes by extracting them from file
            long diffmin=filereading(reader);
            unitSum+=diffmin;
        return unitSum;

    }
    private long filereading(BufferedReader reader) {

        String firstLine="",firstLineF="",lastline="",lastlineF="";
        String firstTime[],lastTime[];
        String startTime="",endTime="";

        // first line
        try {
            while ((firstLine = reader.readLine()) != null) {
                firstLineF=firstLine;
                break;
            }
            firstTime=firstLineF.split(",");
            startTime = firstTime[0];
        } catch (IOException e1) {        }
        // last line

        try {
            while ((lastline = reader.readLine()) != null) {
                lastlineF=lastline;
            }
            lastTime=lastlineF.split(",");
            endTime = lastTime[0];
        } catch (IOException e1){        }
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(startTime);
            d2 = format.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        return diffMinutes;
    }

}