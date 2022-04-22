package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SalahProgress extends AppCompatActivity {
    String url1 = "https://mlint.herokuapp.com/val";
    String url2 = "https://mlint.herokuapp.com/reasoner";
    String fileNumber;
    private Toolbar mTopToolbar;
    TextView posName;
    List<String> pics = new ArrayList<>(Arrays.asList(" ","Qayam", "Ruku", "Qouma", "Sajda", "Tashahud"));

    ArrayList<String> postureName2 = new ArrayList<String>();
    String sel_salah,sel_rakah,sel_unit="";
    ImageView posturepic;
    String whichScreen="SalahProgress";
    int qayamAvg,rukuAvg,qoumAvg,sajdaAvg,jalsaAvg,tashAvg,salahUnitTime;
    String possMissed,extraPosture,extraRakah,salahStatus,rakahMissed;
    ArrayList<String> pos=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_progress);
        posName=findViewById(R.id.posName);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject1 = new JSONObject(response);
                                        System.out.println(jsonObject1);
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
                                                    else if(pos.get(i).equalsIgnoreCase("Sajda")){
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

//
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

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("num", fileNumber);

                            return params;
                        }

                    };
                    RequestQueue queue = Volley.newRequestQueue(SalahProgress.this);
                    queue.add(stringRequest);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        salahUnitTime=calculateUnitTime();
        postureName2.addAll(pics);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        get_intents();
        posturepic=findViewById(R.id.posturepic);

        Button view_salah=findViewById(R.id.view_salah_btn);
        Button end_salah=findViewById(R.id.end_salah_btn);
        end_salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                System.out.println(jsonObject);
                                                conversion1(getApplicationContext(), jsonObject);

                                                //
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
                });                // run reasoner
                getAvgTime();
                // json-obj=> file/parse
                // read the file and get avg times of postures and othr variables
                // pass intents to ViewSalah from SalahProgress




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
                intent.putExtra("possMised",possMissed);
                intent.putExtra("salahStatus",salahStatus);
                intent.putExtra("whichScreen","SalahProgress");
                intent.putExtra("qayamAvg",String.valueOf(qayamAvg));
                intent.putExtra("rukuAvg",String.valueOf(rukuAvg));
                intent.putExtra("qoumAvg",String.valueOf(qoumAvg));
                intent.putExtra("sajdaAvg",String.valueOf(sajdaAvg));
                intent.putExtra("jalsaAvg",String.valueOf(jalsaAvg));
                intent.putExtra("tashAvg",String.valueOf(tashAvg));
                intent.putExtra("salahUnitTime",String.valueOf(salahUnitTime));
                intent.putExtra("extraPosture",extraPosture);
                intent.putExtra("extraRakah",extraRakah);
                intent.putExtra("date1","2022-04-22");

                startActivity(intent);

            }
        });


    }
    @Override
    protected void onStart()
    {
        super.onStart();
    }





    private void get_intents() {
        Intent intent=getIntent();
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
        sel_unit=intent.getStringExtra("sel_unit");
        fileNumber=intent.getStringExtra("fileNumber");
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

        String hFileName;
        try {
            JSONObject hJsonObject = new JSONObject(jsonObject.toString());
            JSONArray hJsonArray = hJsonObject.toJSONArray(hJsonObject.names());

            String hCsvString = CDL.toString(hJsonArray);

            hFileName = "Temp1.csv";
            FileOutputStream hFileOutputStream = hContext.openFileOutput(
                    hFileName,
                    Context.MODE_PRIVATE
            );
            hFileOutputStream.write(hCsvString.getBytes());


        } catch (Exception e) {

        }
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

    }
    public void conversion1(Context hContext, JSONObject jsonObject) {
        String hFileName;
        try {
            JSONObject hJsonObject = new JSONObject(jsonObject.toString());
            JSONArray hJsonArray = hJsonObject.toJSONArray(hJsonObject.names());

            String hCsvString = CDL.toString(hJsonArray);

            hFileName = "Temp2.csv";
            FileOutputStream hFileOutputStream = hContext.openFileOutput(
                    hFileName,
                    Context.MODE_PRIVATE
            );
            hFileOutputStream.write(hCsvString.getBytes());


        } catch (Exception e) {

        }
        getReasonerData();
    }
    private void getReasonerData() {
        String path="/data/data/com.mahariaz.smartsalah/files/Temp2.csv";
        File myFile = new File(path);
        String line="";
        String extrapos="", posmiss="", rakahcount="", rakahextra="",status="";
        int num=0;
        try {
            FileInputStream inputStream = new FileInputStream(myFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                if (num != 0) {
                    String[] tokens = line.split("\n");
                    for (int i = 0; i < tokens.length; i++) {
                        String[] tokens2 = tokens[i].split(",");
                        extrapos=tokens2[0];
                        posmiss=tokens2[1];
                        rakahcount=tokens2[2];
                        rakahextra=tokens2[3];
                        status=tokens2[4];
                        System.out.println("Rakah Number: "+rakahcount);
                        System.out.println("Status: "+status);
                        System.out.println("Extra Posture: "+extrapos);
                        System.out.println("Posture Miss: "+posmiss);
                        System.out.println("Rakah Extra: "+ rakahextra);
                        extraPosture+=extrapos+"IN"+rakahcount;
                        possMissed+=posmiss+"IN"+rakahcount;
                        extraRakah=rakahextra;
                        if (sel_rakah.equalsIgnoreCase("4") && sel_unit.equalsIgnoreCase("Sunnah")){
                            rakahMissed="3";
                        }else{
                            rakahMissed="None";
                        }
                        if(extrapos.equalsIgnoreCase("None")&&
                                posmiss.equalsIgnoreCase("None") &&
                                rakahextra.equalsIgnoreCase("None") &&
                                rakahMissed.equalsIgnoreCase("None")){
                            salahStatus="Complete";
                        }else{
                            salahStatus="Error";
                        }





                    }
                }
                num++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        save_data();
    }
    private void getAvgTime() {
        System.out.println("INSIDE GETAVGTIME");
        String[] split_time1;
        String[] split_time2;

        int qayamT = 0, rukuT = 0, qoumT = 0, sajdaT = 0, jalsaT = 0, tashT = 0;
        String token_value = "";
        String token_name = "";
        int total_qayam = 0, total_ruku = 0, total_qoum = 0, total_sajda = 0, total_jalsa = 0, total_tash = 0;
        String path="/data/data/com.mahariaz.smartsalah/files/Temp1.csv";
        System.out.println("PATHHHHH"+path);
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
                System.out.println("ACTUAL CODE");
                // Split the line into different tokens (using the comma as a separator).
                if (num!=0) {
                    String[] tokens = line.split("\n");
                    for (int i = 0; i < tokens.length; i++) {
                        String[] tokens2 = tokens[i].split(",");
                        token_value = tokens2[3];
                        token_name = tokens2[1];

                        if (pos1=="" && tme1=="") {
                            pos1=token_name;
                            tme1=token_value;
                        } else {
                            pos2=token_name;
                            tme2=token_value;
                            split_time1 = tme1.split(":");
                            split_time2 = tme2.split(":");
                            int a = Integer.parseInt(split_time1[2]);
                            int b = Integer.parseInt(split_time2[2]);

                            if(pos1.equals("Qayam")) {
                                if((b-a)>0) {
                                    qayamT=b-a;
                                }
                                total_qayam+=qayamT;

//                                System.out.println("Qayam time: "+ qayamT);
                            } else if(pos1.equals("Ruku")) {
                                if((b-a)>0) {
                                    rukuT=b-a;
                                }
                                total_ruku+=rukuT;
//                                System.out.println("Ruku time: "+ rukuT);
                            }
                            else if(pos1.equals("Qoum")) {
                                if((b-a)>0) {
                                    qoumT=b-a;
                                }
                                total_qoum+=qoumT;
//                                System.out.println("Qoum time: "+ qoumT);
                            }
                            else if(pos1.equals("Sajda")) {
                                if((b-a)>0) {
                                    sajdaT=b-a;
                                }
                                total_sajda+=sajdaT;
//                                System.out.println("Sajda time: "+ sajdaT);
                            }
                            else if(pos1.equals("Jalsa")) {
                                if((b-a)>0) {
                                    jalsaT=b-a;
                                }
                                total_jalsa+=jalsaT;
//                                System.out.println("Jalsa time: "+ jalsaT);
                            }
                            else if(pos1.equals("Tashahud")) {
                                if((b-a)>0) {
                                    tashT=b-a;
                                }
                                total_tash+=tashT;
//                                System.out.println("Tashahud time: "+ tashT);
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
    }


    private int calculateUnitTime() {
        System.out.println("INSIDE UNIT TIME ");
        String path="/data/data/com.mahariaz.smartsalah/files/Temp1.csv";
        System.out.println("PATHHHHH"+path);
        File myFile = new File(path);
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
        // first line
        int i=0;
        try {
            while ((firstLine = reader.readLine()) != null) {

                if (i==2){
                    break;
                }
                firstLineF=firstLine;

                i++;
            }
            firstTime=firstLineF.split(",");
            startTime = firstTime[3];
            System.out.println("Fisrt time : "+startTime);
        } catch (IOException e1) {        }
        // last line

        try {
            while ((lastline = reader.readLine()) != null) {
                lastlineF=lastline;
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


        Log.d("Testing ", databaseReference.toString());
        if (sel_salah.equalsIgnoreCase("Zuhr")){
            PModel zuhrPModel = new PModel(sel_salah, Integer.parseInt(sel_rakah), possMissed, rakahMissed, salahStatus, qayamAvg, rukuAvg, qoumAvg, sajdaAvg, jalsaAvg,tashAvg,sel_unit,salahUnitTime,extraPosture,extraRakah,"2022-04-22");
            FirebasePrayer firebasePrayer = new FirebasePrayer(zuhrPModel, zuhrPModel, zuhrPModel, zuhrPModel, zuhrPModel);
            FirebaseUser firebaseUser = new FirebaseUser("21", "", "mahariaz@gmail.com", "female", "4.10","mahnooor" ,firebasePrayer);
            //databaseReference.child(firebaseUser.getUsername()).setValue(firebaseUser);
            databaseReference.child("user123").child("firebasePrayer").child(sel_salah).push().setValue(zuhrPModel);

        }



        Log.d("Testing ", "User inserted");

    }



}