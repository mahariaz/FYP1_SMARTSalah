package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class SalahProgress extends AppCompatActivity {
    String url = "https://deploy-flask-app.herokuapp.com/predict";
    private Toolbar mTopToolbar;
    TextView posName;
    List<String> pics = new ArrayList<>(Arrays.asList(" ","Qayam", "Ruku", "Qouma", "Sajda", "Tashahud"));

    ArrayList<String> postureName2 = new ArrayList<String>();
    String sel_salah,sel_rakah,rakah_performed,user_name,timestamp;
    TextView rakah1,rakah2,rakah3,rakah4;
    ProgressBar bar1,bar2,bar3,bar4;
    ImageView posturepic;
    boolean is_bar1_filled=false,is_bar2_filled=false,is_bar3_filled=false,is_bar4_filled=false;
    boolean one_fill=false,two_fill=false,three_fill=false,four_fill=false;
    int qayamAvg=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_progress);
        postureName2.addAll(pics);
        posName=findViewById(R.id.posName);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        get_intents();
        get_bar_ids();
         posturepic=findViewById(R.id.posturepic);
         // changing the pictures
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Handler handler1 = new Handler();
//                for (int i=0;i<postureName2.size();i++){
//
//                    if(postureName2.get(i).equalsIgnoreCase("Qayam")){
//                        handler1.postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.qayampic));
//                            }
//                        }, 1000 * i);
//                    }
//                    else if(postureName2.get(i).equalsIgnoreCase("Ruku")){
//                        handler1.postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.rukupic));
//                            }
//                        }, 1000 * i);
//                    }
//                    else if(postureName2.get(i).equalsIgnoreCase("Qoum")){
//                        handler1.postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.qoumapic));
//                            }
//                        }, 1000 * i);
//                    }
//                    else if(postureName2.get(i).equalsIgnoreCase("Sajda")){
//                        handler1.postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                posturepic.setImageDrawable(getResources().getDrawable(R.drawable.sajdapic));
//                            }
//                        }, 1000 * i);
//                    }
//
//                }
//            }
//        };
//        final Handler handler = new Handler();
//        handler.postDelayed(runnable, 5000);

//        fill_bar1(bar1);
        Button view_salah=findViewById(R.id.view_salah_btn);
        Button end_salah=findViewById(R.id.end_salah_btn);
        end_salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_salah.setEnabled(true);
            }
        });
        view_salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalahProgress.this,ViewSalah.class);
                intent.putExtra("sel_salah",sel_salah);
                intent.putExtra("sel_rakah",sel_rakah);
                intent.putExtra("qayamAvg",String.valueOf(qayamAvg));
                intent.putExtra("rukuAvg",String.valueOf(qayamAvg));
                intent.putExtra("qoumAvg",String.valueOf(qayamAvg));
                intent.putExtra("sajdaAvg",String.valueOf(qayamAvg));
                intent.putExtra("tashAvg",String.valueOf(qayamAvg));
                startActivity(intent);

            }
        });


    }
    @Override
    protected void onStart()
    {
        super.onStart();
    }




    private void fill_bar1(ProgressBar bar) {
        rakah1.setVisibility(View.VISIBLE);
        bar1.setVisibility(View.VISIBLE);
        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=5;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                    is_bar1_filled=true;
                    if (sel_rakah.equalsIgnoreCase("2") ||
                            sel_rakah.equalsIgnoreCase("3") ||
                            sel_rakah.equalsIgnoreCase("4"))
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                bar1.setVisibility(View.GONE);
                                rakah1.setVisibility(View.GONE);
                                // Stuff that updates the UI

                            }
                        });

                        fill_bar2(bar2);

                }

            }
        };
        t.schedule(tt,0,100);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hide_bar(bar1,rakah1);
    }
    private void fill_bar2(ProgressBar bar) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                rakah2.setVisibility(View.VISIBLE);
                bar2.setVisibility(View.VISIBLE);

            }
        });

        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=5;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                    if (sel_rakah.equalsIgnoreCase("3") ||
                            sel_rakah.equalsIgnoreCase("4")){
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                bar2.setVisibility(View.GONE);
                                rakah2.setVisibility(View.GONE);
                                // Stuff that updates the UI

                            }
                        });
                        fill_bar3(bar3);
                    }
                }

            }
        };
        t.schedule(tt,0,100);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hide_bar(bar2,rakah2);
    }
    private void fill_bar3(ProgressBar bar) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                rakah3.setVisibility(View.VISIBLE);
                bar3.setVisibility(View.VISIBLE);

            }
        });
        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=5;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                    if (sel_rakah.equalsIgnoreCase("4")){
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                bar3.setVisibility(View.GONE);
                                rakah3.setVisibility(View.GONE);
                                // Stuff that updates the UI

                            }
                        });
                        fill_bar4(bar4);



                    }
                }

            }
        };
        t.schedule(tt,0,100);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hide_bar(bar3,rakah3);
    }



    private void fill_bar4(ProgressBar bar) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                rakah4.setVisibility(View.VISIBLE);
                bar4.setVisibility(View.VISIBLE);

            }
        });
        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            int counter=0;
            @Override
            public void run() {
                counter+=5;
                bar.setProgress(counter);
                if(counter==100){
                    t.cancel();
                }

            }
        };
        t.schedule(tt,0,100);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hide_bar(bar4,rakah4);
    }
    private void hide_bar(ProgressBar bar,TextView tv) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                bar.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                // Stuff that updates the UI

            }
        });
    }

    private void get_intents() {
        Intent intent=getIntent();
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
        shared.curr_rakah=sel_rakah;
        shared.curr_salah=sel_salah;
        System.out.println("sel_rakah : "+shared.curr_rakah+"  sel_salaah : "+shared.curr_salah);

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

}