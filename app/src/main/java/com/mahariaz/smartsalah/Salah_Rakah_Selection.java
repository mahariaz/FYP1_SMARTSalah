package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class Salah_Rakah_Selection extends AppCompatActivity {
    String sel_salah="",sel_rakah="";
    Button start;
    CircleImageView fajar_circle,zuhr_circle,asr_circle,maghreb_circle,isha_circle,nafl_circle;
    CircleImageView r1_circle,r2_circle,r3_circle,r4_circle;
    Boolean isSalahSelected=false;
    String mlURL="https://mlint.herokuapp.com/val";

    private Toolbar mTopToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_rakah_selection);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        fajar_circle=findViewById(R.id.fajar_circle);
        zuhr_circle=findViewById(R.id.zuhr_circle);
        asr_circle=findViewById(R.id.asr_circle);
        maghreb_circle=findViewById(R.id.maghreb_circle);
        isha_circle=findViewById(R.id.isha_circle);
        nafl_circle=findViewById(R.id.nafl_circle);
        r1_circle=findViewById(R.id.r1_circle);
        r2_circle=findViewById(R.id.r2_circle);
        r3_circle=findViewById(R.id.r3_circle);
        r4_circle=findViewById(R.id.r4_circle);


        fajar_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah="Fajar";
                fajar_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                zuhr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                asr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                maghreb_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                isha_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                nafl_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setEnabled(false);
                r4_circle.setEnabled(false);
                r1_circle.setEnabled(false);
                r2_circle.setEnabled(true);
                isSalahSelected=true;
                start.setEnabled(false);
            }
        });
        zuhr_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah="Zuhr";
                fajar_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                zuhr_circle.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
                asr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                maghreb_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                isha_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                nafl_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setEnabled(false);
                r1_circle.setEnabled(false);
                r2_circle.setEnabled(true);
                r4_circle.setEnabled(true);
                isSalahSelected=true;
                start.setEnabled(false);
            }
        });
        asr_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah="Asr";
                fajar_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                zuhr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                asr_circle.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
                maghreb_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                isha_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                nafl_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setEnabled(true);
                r3_circle.setEnabled(false);
                r2_circle.setEnabled(false);
                r1_circle.setEnabled(false);
                isSalahSelected=true;
                start.setEnabled(false);
            }
        });
        maghreb_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah="Maghreb";
                fajar_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                zuhr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                asr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                maghreb_circle.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
                isha_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                nafl_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setEnabled(false);
                r1_circle.setEnabled(false);
                r3_circle.setEnabled(true);
                r2_circle.setEnabled(true);
                isSalahSelected=true;
                start.setEnabled(false);
            }
        });
        isha_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah="Isha";
                fajar_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                zuhr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                asr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                maghreb_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                isha_circle.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
                nafl_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r1_circle.setEnabled(false);
                r2_circle.setEnabled(true);
                r4_circle.setEnabled(true);
                r3_circle.setEnabled(true);
                isSalahSelected=true;
                start.setEnabled(false);
            }
        });
        nafl_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah="Nafl";
                fajar_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                zuhr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                asr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                maghreb_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                isha_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                nafl_circle.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r1_circle.setEnabled(true);
                r2_circle.setEnabled(true);
                r3_circle.setEnabled(true);
                r4_circle.setEnabled(true);
                isSalahSelected=true;
                start.setEnabled(false);

            }
        });
        r1_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sel_rakah="1";
                r1_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                if (isSalahSelected){
                    start.setEnabled(true);
                }
                // color set to 0 for other buttons
                r2_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r2_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_rakah="2";
                r2_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                if (isSalahSelected){
                    start.setEnabled(true);
                }
                // color set to 0 for other buttons
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r3_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_rakah="3";
                r3_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                if (isSalahSelected){
                    start.setEnabled(true);
                }
                // color set to 0 for other buttons
                r2_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r4_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sel_rakah="4";
                r4_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                if (isSalahSelected){
                    start.setEnabled(true);
                }
                // color set to 0 for other buttons
                r2_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
            }
        });



        start=findViewById(R.id.start_salah_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create object of MyAsyncTasks class and execute it
//                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
//                myAsyncTasks.execute();
                String data = DataRepo.getData();
                Log.d("maha : tag",data);


                Intent intent=new Intent(Salah_Rakah_Selection.this,SalahProgress.class);
                intent.putExtra("sel_salah",sel_salah);
                intent.putExtra("sel_rakah",sel_rakah);


                startActivity(intent);
            }
        });
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
            Intent intent=new Intent(Salah_Rakah_Selection.this,Home.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    public class MyAsyncTasks extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... params ) {
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, mlURL,
//                                    new Response.Listener<String>() {
//                                        @Override
//                                        public void onResponse(String response) {
//
//                                            try {
//                                                JSONObject jsonObject = new JSONObject(response);
//                                                String data = jsonObject.getString("posture");
//                                                // result.setText(data)
//                                                System.out.println(data);
//
////
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//
//                                        }
//                                    },
//                                    new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            Toast.makeText(Salah_Rakah_Selection.this, "ERROR!!!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
////
//            return null;
//        }
//    }





}