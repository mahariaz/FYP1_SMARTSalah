package com.mahariaz.smartsalah;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahariaz.smartsalah.firebase.FirebasePrayer;
import com.mahariaz.smartsalah.firebase.FirebaseUser;
import com.mahariaz.smartsalah.firebase.PModel;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SalahProgress extends AppCompatActivity {

    String url;



    int reasonerLines=0;
    //not working
    String url1 = "https://appintegration.herokuapp.com/val";
    String url2 = "https://appintegration.herokuapp.com/reasoner";
    String fileNumber;
    private Toolbar mTopToolbar;
    TextView posName;
    List<String> pics = new ArrayList<>(Arrays.asList(" ","Qayam", "Ruku", "Qouma", "Sajda", "Tashahud"));

    ArrayList<String> postureName2 = new ArrayList<String>();
    String sel_salah,sel_rakah,sel_unit="";
    ImageView posturepic;
    String whichScreen="SalahProgress";
    int qayamAvg,rukuAvg,qoumAvg,sajdaAvg,jalsaAvg,tashAvg,salahUnitTime;
    String postureExtraString="",postureMissString="",extraRakah="",salahStatus="",rakahMissed="",salahTimelinessStatus;
    ArrayList<String> pos=new ArrayList<>();
    String salahPerformedTime="";
    ProgressBar progressBar;
    Button end_salah,view_salah;
    String currDate,currTime,currTime24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_progress);
        posName=findViewById(R.id.posName);
        progressBar=findViewById(R.id.progressBar);
        view_salah=findViewById(R.id.view_salah_btn);
        end_salah=findViewById(R.id.end_salah_btn);
        // step 1: getting todays date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currDateTemp=dateFormat.format(new Date());
        String dateTokens[]=currDateTemp.split("-");
        currDate=dateTokens[2]+"-"+dateTokens[1]+"-"+dateTokens[0];
        // step 2: at what time you are performing Salah
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        currTime=timeFormat.format(new Date());
        SimpleDateFormat timeFormat2 = new SimpleDateFormat("HH:mm");
        currTime24=timeFormat2.format(new Date());


        // the lot lang value of your location

        // Original url"https://api.aladhan.com/v1/calendar?latitude=33.738045&longitude=73.084488&method=2&month=5&year=2022";
        url="https://api.aladhan.com/v1/calendar?latitude=33.738045&longitude=73.084488&method=2&month="+dateTokens[1]+"&year="+dateTokens[1]+"";



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String whichJson="Values";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.d("Response ",response.toString());

                                    try {
                                        JSONObject jsonObject1 = new JSONObject(response);

                                        System.out.println("JSONNNNN!!!!!!!!: "+jsonObject1);

                                        conversion(getApplicationContext(),jsonObject1);
                                        final Runnable runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                Handler handler1 = new Handler();
                                                for (int i=0;i<pos.size();i++){

                                                    if(pos.get(i).equalsIgnoreCase("Qayam")){
                                                        handler1.postDelayed(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.qayampic));
                                                                posName.setText("Qayam");
                                                            }
                                                        }, 1000 * i);
                                                    }
                                                    else if(pos.get(i).equalsIgnoreCase("Ruku")){
                                                        handler1.postDelayed(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.rukupic));
                                                                posName.setText("Ruku");
                                                            }
                                                        }, 1000 * i);
                                                    }
                                                    else if(pos.get(i).equalsIgnoreCase("Qoum")){
                                                        handler1.postDelayed(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.qoumapic));
                                                                posName.setText("Qouma");
                                                            }
                                                        }, 1000 * i);
                                                    }
                                                    else if(pos.get(i).equalsIgnoreCase("Sajda")){
                                                        handler1.postDelayed(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.sajdapic));
                                                                posName.setText("Sajda");

                                                            }
                                                        }, 1000 * i);
                                                    }
                                                    else if(pos.get(i).equalsIgnoreCase("Jalsa")){
                                                        handler1.postDelayed(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.tashpic));
                                                                posName.setText("Jalsa");

                                                            }
                                                        }, 1000 * i);
                                                    }
                                                    else if(pos.get(i).equalsIgnoreCase("Tashahud")){
                                                        handler1.postDelayed(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.tashpic));
                                                                posName.setText("Tashahud");

                                                            }
                                                        }, 1000 * i);
                                                    }


                                                }
                                            }
                                        };
                                        final Handler handler = new Handler();
                                        handler.postDelayed(runnable, 5000);
                                        end_salah.setEnabled(true);




                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("This is the err",error.toString());
                                    Toast.makeText(SalahProgress.this, "ERROR!!!", Toast.LENGTH_SHORT).show();
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("num", "6");

                            return params;
                        }

                    };
                    RequestQueue queue = Volley.newRequestQueue(SalahProgress.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(stringRequest);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        postureName2.addAll(pics);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        get_intents();
        posturepic=findViewById(R.id.posturepic);


        end_salah.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String whichJson="Reasoner";
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                System.out.println("Reasonerrrrr"+jsonObject);

                                                conversion1(getApplicationContext(), jsonObject);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(SalahProgress.this, "ERROR!!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }) {


                            };
                            RequestQueue queue = Volley.newRequestQueue(SalahProgress.this);
                            queue.add(stringRequest);

                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });// run reasoner

                view_salah.setEnabled(true);



            }

        });
        view_salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalahProgress.this,ViewSalah.class);
                intent.putExtra("sel_salah",sel_salah);
                intent.putExtra("sel_rakah",sel_rakah);
                intent.putExtra("sel_unit",sel_unit);
                intent.putExtra("rakahMissed",rakahMissed);
                intent.putExtra("possMissed",postureMissString);
                intent.putExtra("salahStatus",salahStatus);
                intent.putExtra("whichScreen","SalahProgress");
                intent.putExtra("qayamAvg",String.valueOf(qayamAvg));
                intent.putExtra("rukuAvg",String.valueOf(rukuAvg));
                intent.putExtra("qoumAvg",String.valueOf(qoumAvg));
                intent.putExtra("sajdaAvg",String.valueOf(sajdaAvg));
                intent.putExtra("jalsaAvg",String.valueOf(jalsaAvg));
                intent.putExtra("tashAvg",String.valueOf(tashAvg));
                intent.putExtra("salahUnitTime",String.valueOf(salahUnitTime));
                intent.putExtra("extraPosture",postureExtraString);
                intent.putExtra("extraRakah",extraRakah);
                intent.putExtra("salahTimelinessStatus",salahTimelinessStatus);

                intent.putExtra("currDate",currDate);

                startActivity(intent);

            }
        });


    }
    @Override
    protected void onStart()
    {
        super.onStart();
        // here the second async task for the time of Salah performance
        // getting timeliness
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String whichJson="Timeliness";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        System.out.println("TIMELINESS "+jsonObject);
                                        TmelinessConversion(getApplicationContext(), jsonObject);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(SalahProgress.this, "ERROR!!!", Toast.LENGTH_SHORT).show();
                                }
                            }) {


                    };
                    RequestQueue queue = Volley.newRequestQueue(SalahProgress.this);
                    queue.add(stringRequest);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }





    private void get_intents() {
        Intent intent=getIntent();
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
        sel_unit=intent.getStringExtra("sel_unit");
        //fileNumber=intent.getStringExtra("fileNumber");
        shared.curr_rakah=sel_rakah;
        shared.curr_salah=sel_salah;
        //System.out.println("sel_rakah : "+shared.curr_rakah+"  sel_salaah : "+shared.curr_salah);

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
            Intent intent=new Intent(SalahProgress.this,Home.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public List<Uri> list_uri(){
        Uri uri_i1= get_uri(R.drawable.qayampic);
        Uri uri_i2= get_uri(R.drawable.rukupic);
        List<Uri> uri_list = Arrays.asList(uri_i1,uri_i2);
        return uri_list;
    }
    // Typical code for changing PNG  into URI
    public Uri get_uri(int id){
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(id)
                + '/' + getResources().getResourceTypeName(id) + '/' + getResources().getResourceEntryName(id) );

        return uri;

    }
    public void conversion(Context hContext, JSONObject jsonObject) {
        String hFileName="Temp1.csv";


        try {
            JSONObject hJsonObject = new JSONObject(jsonObject.toString());
            JSONArray hJsonArray = hJsonObject.toJSONArray(hJsonObject.names());
            String hCsvString = CDL.toString(hJsonArray);
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(new File(getFilesDir(), "Temp1.csv"));
                fout.write(hCsvString.getBytes());
                fout.close();
                System.out.println("file written");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        getValueData();

    }
    public void TmelinessConversion(Context hContext, JSONObject jsonObject) {
        String salahPresTime24="",salahNextTime24="",salahPresTime="",salahNextTime="";
        // getting what salah you have performed
        String selSalah="",nextSalah="";
        /* Matching the naming convention of salah names
        with json object's salah names as our is different */
        if(sel_salah.equalsIgnoreCase("Fajr")){
            selSalah="Fajr";
            nextSalah="Sunrise";
        }
        if(sel_salah.equalsIgnoreCase("Zuhr")){
            selSalah="Dhuhr";
            nextSalah="Asr";
        }
        if(sel_salah.equalsIgnoreCase("Asr")){
            selSalah="Asr";
            nextSalah="Maghrib";
        }
        if(sel_salah.equalsIgnoreCase("Maghrib")){
            selSalah="Maghrib";
            nextSalah="Isha";
        }
        if(sel_salah.equalsIgnoreCase("Isha")){
            selSalah="Isha";
            nextSalah="Midnight";
        }


        try {
            JSONArray arr=jsonObject.getJSONArray("data");
            for (int i=0;i<arr.length();i++){
                JSONObject jsonObject1=arr.getJSONObject(i);
                /* jsonObject1 contains: timings,date at first two
                indexes which are json objects themselves*/

                //extracting date
                JSONObject obj1=jsonObject1.getJSONObject("date");
                /* gregorian is another jason object inside date object which
                further contains the actual date in the format 01-01-2020*/
                JSONObject obj2=obj1.getJSONObject("gregorian");
                // extracting the date which we want to compare with today's date
                String d =obj2.getString("date");
                /*this will extract that object in which today's date is written
                which means today's salah prescribed time */

                if (d.equalsIgnoreCase(currDate)){
                    JSONObject jsonObject2=jsonObject1.getJSONObject("timings");
                    /* getting prescribed time of Salah from json object 'timings'
                    for instance "Fajr":"04:06" */
                     salahPresTime24=jsonObject2.getString(selSalah);
                     salahNextTime24=jsonObject2.getString(nextSalah); // getting prescribed time of next Salah
                    /*converting Salah prescribed time and nextSalahTime
                    from 24 hr to 12 hr format */

                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final SimpleDateFormat sdfNext = new SimpleDateFormat("H:mm");
                    final Date dateObj,dateObjNext;
                    try {
                        dateObj = sdf.parse(salahPresTime24);
                        dateObjNext = sdfNext.parse(salahNextTime24);
                        salahPresTime=new SimpleDateFormat("hh:mm").format(dateObj);
                        salahNextTime=new SimpleDateFormat("hh:mm").format(dateObjNext);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // computing your salah status i-e onTime/lateTime
        // converting three string times to Date for comparison
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date currTimeCmp = sdf.parse(currTime24);
            Date salahPresTimeCmp = sdf.parse(salahPresTime24);
            Date salahNextTimeCmp = sdf.parse(salahNextTime24);
            // printing
            System.out.println("salah name : "+sel_salah);
            System.out.println("pres time : "+salahPresTime);
            System.out.println("end time: "+salahNextTimeCmp+"   "+salahNextTime24);
            System.out.println("you prayed at : "+currTimeCmp+"   "+currTime24);
            // formula: currTime < nextSalahTime and currTime > salahPresTime
            if (currTimeCmp.after(salahPresTimeCmp) && currTimeCmp.before(salahNextTimeCmp)){
                System.out.println("OnTime: "+currTime24+" "+salahPresTime24+" "+salahNextTime24);
                salahTimelinessStatus="OnTime";
            }else{
                System.out.println("Late: "+currTime24+" "+salahPresTime24+" "+salahNextTime24);
                salahTimelinessStatus="Late";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }





    }
    public void conversion1(Context hContext, JSONObject jsonObject) {
        String hFileName="Temp2.csv";
        try {
            JSONObject hJsonObject = new JSONObject(jsonObject.toString());
            JSONArray hJsonArray = hJsonObject.toJSONArray(hJsonObject.names());
            String hCsvString = CDL.toString(hJsonArray);
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(new File(getFilesDir(), hFileName));
                fout.write(hCsvString.getBytes());
                fout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
        getReasonerData(jsonObject);

    }
    public void getValueData(){
        String path="/data/data/com.mahariaz.smartsalah/files/Temp1.csv";
        File myFile = new File(path);
        String line="";
        String []pos_arr={};
        int num=0;
        try {
            FileInputStream inputStream = new FileInputStream(myFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            int inc=0;
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).

                if (num != 0) {
                    String[] tokens = line.split("\n");
                    for (int i = 0; i < tokens.length; i++) {
                        String[] tokens2 = tokens[i].split(",");
                        System.out.println("TOKEN2222 : "+tokens2[0]);
//                        pos_arr[inc]=tokens2[1];
                        pos.add(tokens2[1]);
                        System.out.println("TOKEN2222 : "+pos);

                        System.out.println("Posture: "+tokens2[1]);

                    }
                }

                num++;
                inc++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getAvgTime();



    }


    private void getReasonerData(JSONObject  jsonObject) {
        // here calculate the no of lines in temp2: reasonerFile
        String path="/data/data/com.mahariaz.smartsalah/files/Temp2.csv";
        File myFile = new File(path);
        String line="";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            reasonerLines++;
        }
        // Declarations of Rakah Missed
        ArrayList<String> saveRakahCount=new ArrayList<>();
        ArrayList<String> standardRakahCount4=new ArrayList<>();
        ArrayList<String> standardRakahCount3=new ArrayList<>();
        ArrayList<String> standardRakahCount2=new ArrayList<>();
        String [] rakahCollection4={"1","2","3","4"};
        String [] rakahCollection3={"1","2","3"};
        String [] rakahCollection2={"1","2"};
        standardRakahCount4.addAll(Arrays.asList(rakahCollection4));
        standardRakahCount3.addAll(Arrays.asList(rakahCollection3));
        standardRakahCount2.addAll(Arrays.asList(rakahCollection2));


        try {
            for (int i=1;i<reasonerLines;i++) {
                String s=String.valueOf(i);
                JSONObject obj1 = jsonObject.getJSONObject(s);
                System.out.println("obj1 " + obj1);
                String PostureExtra = obj1.getString("PostureExtra");
                String PostureMiss = obj1.getString("PostureMiss");
                String Rakah = obj1.getString("Rakah");
                saveRakahCount.add(Rakah);
                //System.out.println(PostureExtra + " " + PostureMiss + " " + Rakah + " " + RakahExtra + " " + Status);
                postureExtraString += PostureExtra + "IN" + Rakah + "-";
                postureMissString += PostureMiss + "IN" + Rakah + "-";
            }
            System.out.println("postureExtraString : "+postureExtraString);
            System.out.println("postureMissString : "+postureMissString);
            // Salah Status
            JSONObject obj2 = jsonObject.getJSONObject("1");
            // it is same for all
            salahStatus = obj2.getString("Status");
            extraRakah = obj2.getString("RakahExtra");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // checks for Rakah Missed
        if (sel_rakah.equalsIgnoreCase("4")){
            for (int j=0;j<standardRakahCount4.size();j++){
                if (!saveRakahCount.contains(standardRakahCount4.get(j))){
                    rakahMissed+=standardRakahCount4.get(j)+"AND";
                }
            }
        }
        if (sel_rakah.equalsIgnoreCase("3")){
            for (int j=0;j<standardRakahCount3.size();j++){
                if (!saveRakahCount.contains(standardRakahCount3.get(j))){
                    rakahMissed+=standardRakahCount3.get(j)+"AND";
                }
            }
        }
        if (sel_rakah.equalsIgnoreCase("2")){
            for (int j=0;j<standardRakahCount2.size();j++){
                if (!saveRakahCount.contains(standardRakahCount2.get(j))){
                    rakahMissed+=standardRakahCount2.get(j)+"AND";
                }
            }
        }
        save_data();


    }

    private void getAvgTime() {
        // calculaing average time spent on  each posture
        String[] minute;
        String[] second;

        int qayamT = 0, rukuT = 0, qoumT = 0, sajdaT = 0, jalsaT = 0, tashT = 0;
        String token_value = "";
        String token_name = "";
        /* variables for adding up individual posture times
        and then storing them as average in the following variables*/
        int total_qayam = 0, total_ruku = 0, total_qoum = 0, total_sajda = 0, total_jalsa = 0, total_tash = 0;

        /* path where the file is residing in the phone */
        String path="/data/data/com.mahariaz.smartsalah/files/Temp1.csv";

        File myFile = new File(path);
        try {
            FileInputStream inputStream = new FileInputStream(myFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            int num=0;
            String pos1="";
            String tme1="";
            String pos2="";
            String tme2="";
            while ((line = reader.readLine()) != null) {

                // Split the line into different tokens (using the comma as a separator).
                if (num!=0) {
                    String[] tokens = line.split("\n");
                    for (int i = 0; i < tokens.length; i++) {
                        String[] tokens2 = tokens[i].split(",");
                        token_value = tokens2[3]; // timestamp at this index
                        token_name = tokens2[1];  // posture at this index

                        if (pos1=="" && tme1=="") {
                            pos1=token_name;
                            tme1=token_value;
                        } else {
                            pos2=token_name;
                            tme2=token_value;
                            minute = tme1.split(":");
                            second = tme2.split(":");

                            /*converting minutes and seconds
                            from string to integer for computation*/
                            int a = Integer.parseInt(minute[2]); // minutes
                            int b = Integer.parseInt(second[2]); // seconds

                            if(pos1.equals("Qayam")) {
                                if((b-a)>0) {
                                    qayamT=b-a;
                                }
                                total_qayam+=qayamT;


                            } else if(pos1.equals("Ruku")) {
                                if((b-a)>0) {
                                    rukuT=b-a;
                                }
                                total_ruku+=rukuT;

                            }
                            else if(pos1.equals("Qoum")) {
                                if((b-a)>0) {
                                    qoumT=b-a;
                                }
                                total_qoum+=qoumT;

                            }
                            else if(pos1.equals("Sajda")) {
                                if((b-a)>0) {
                                    sajdaT=b-a;
                                }
                                total_sajda+=sajdaT;

                            }
                            else if(pos1.equals("Jalsa")) {
                                if((b-a)>0) {
                                    jalsaT=b-a;
                                }
                                total_jalsa+=jalsaT;

                            }
                            else if(pos1.equals("Tashahud")) {
                                if((b-a)>0) {
                                    tashT=b-a;
                                }
                                total_tash+=tashT;

                            }
                            pos1=pos2;
                            tme1=tme2;
                            pos2="";
                            tme2="";
                        }


                    }
                }
                num++;


            }
            int tash=total_tash+tashT;
            if (tash==0){
                tash=12;
            }else{
                tash/=2;
            }
            qayamAvg=total_qayam/Integer.parseInt(sel_rakah);
            rukuAvg=total_ruku/Integer.parseInt(sel_rakah);
            qoumAvg=total_qoum/Integer.parseInt(sel_rakah);
            sajdaAvg=total_sajda/(Integer.parseInt(sel_rakah)*2);
            jalsaAvg=total_jalsa/Integer.parseInt(sel_rakah);
            tashAvg=tash;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        salahUnitTime=calculateUnitTime();
    }


    private int calculateUnitTime() {
        /* calculating total time spent in one Salah */
        String path="/data/data/com.mahariaz.smartsalah/files/Temp1.csv";
        File myFile = new File(path);
        /* variables to extract first and last line of file
        * as it's the starting and ending time of Salah */
        String firstLine="",firstLineF="",lastline="",lastlineF="";
        String firstTime[],lastTime[];
        String startTime="",endTime="";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        // first line : starting time of Salah
        int i=0;
        try {
            while ((firstLine = reader.readLine()) != null) {

                if (i==2){
                    break;
                }
                firstLineF=firstLine;
                System.out.println("check first line : "+firstLineF);


                i++;
            }
            /* splitting file line i-e
            Zuhr,Qayam,4, 2021-11-19 12:24:14:130,Sunnah
             */

            firstTime=firstLineF.split(",");
            startTime = firstTime[3]; // timestamp at this index
            /*extracting only time from timestamp on
            which salah is performed*/
            String tempStartTime=startTime;
            String perSalahTime[]=tempStartTime.split(" ");
            salahPerformedTime=perSalahTime[1];
            System.out.println("salahPerformedTime : "+perSalahTime[1]);


        } catch (IOException e1) {        }
        // last line : end time of Salah

        try {
            while ((lastline = reader.readLine()) != null) {
                lastlineF=lastline;
                System.out.println("check last line : "+lastlineF);
            }
            lastTime=lastlineF.split(",");
            endTime = lastTime[3];
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
        int salahMin=(int)diffMinutes;
        return salahMin;
    }

    private void save_data() {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserBio");
        if (sel_salah.equalsIgnoreCase(sel_salah)){
            PModel zuhrPModel = new PModel(sel_salah, Integer.parseInt(sel_rakah),
                    postureMissString, rakahMissed, salahStatus, qayamAvg, rukuAvg,
                    qoumAvg, sajdaAvg, jalsaAvg,tashAvg,sel_unit,salahUnitTime,
                    postureExtraString,extraRakah,currDate,salahTimelinessStatus);
            databaseReference.child(shared.username).child("firebasePrayer").child(sel_salah).push().setValue(zuhrPModel);

        }
    }



}