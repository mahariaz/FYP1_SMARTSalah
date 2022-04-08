package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class Calender extends AppCompatActivity {
    Boolean rakah_nd_salah_selected=false;
    CalendarView calendar;
    TextView date_view;
    String Date,sel_salah,sel_rakah;
    CircleImageView fajar_circle,zuhr_circle,asr_circle,maghreb_circle,isha_circle;
    CircleImageView r1_circle,r2_circle,r3_circle,r4_circle;
    private Button stats;
    private Toolbar mTopToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        stats=findViewById(R.id.stats);
        fajar_circle=findViewById(R.id.fajar_circle);
        zuhr_circle=findViewById(R.id.zuhr_circle);
        asr_circle=findViewById(R.id.asr_circle);
        maghreb_circle=findViewById(R.id.maghreb_circle);
        isha_circle=findViewById(R.id.isha_circle);
        r1_circle=findViewById(R.id.r1_circle);
        r2_circle=findViewById(R.id.r2_circle);
        r3_circle=findViewById(R.id.r3_circle);
        r4_circle=findViewById(R.id.r4_circle);
        fajar_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah="Fajar";
                fajar_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                zuhr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                asr_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                maghreb_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                isha_circle.setColorFilter(0,PorterDuff.Mode.SRC_ATOP);
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
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
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);

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
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
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
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
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
                r1_circle.setColorFilter(0, PorterDuff.Mode.SRC_ATOP);
                r2_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r3_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
                r4_circle.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r1_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rakah_nd_salah_selected=true;
                sel_rakah="1";
                r1_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r2_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rakah_nd_salah_selected=true;
                sel_rakah="2";
                r2_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r3_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rakah_nd_salah_selected=true;
                sel_rakah="3";
                r3_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r4_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rakah_nd_salah_selected=true;

                sel_rakah="4";
                r4_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });
        calendar = (CalendarView)
                findViewById(R.id.calendar);
        date_view = (TextView)
                findViewById(R.id.date_view);
        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,int year,int month,int dayOfMonth){
                Date= year + "-"+ (month + 1) + "-" + dayOfMonth;
                // set this date in TextView for Display
                date_view.setText(Date);
                if (rakah_nd_salah_selected){
                    stats.setEnabled(true);
                }
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Calender.this,Salah_stats_history.class);
                intent.putExtra("date",Date);
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
            Intent intent=new Intent(Calender.this,Home.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}