package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class Salah_Rakah_Selection extends AppCompatActivity {
    String sel_salah="",sel_rakah="";
    Button start;
    CircleImageView fajar_circle,zuhr_circle,asr_circle,maghreb_circle,isha_circle,nafl_circle;
    CircleImageView r1_circle,r2_circle,r3_circle,r4_circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_rakah_selection);

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
                fajar_circle.setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
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

            }
        });
        r1_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_rakah="1";
                r1_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r2_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_rakah="2";
                r2_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r3_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_rakah="3";
                r3_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });
        r4_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_rakah="4";
                r4_circle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }
        });



        start=findViewById(R.id.start_salah_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Salah_Rakah_Selection.this,SalahProgress.class);
                intent.putExtra("sel_salah",sel_salah);
                intent.putExtra("sel_rakah",sel_rakah);

                startActivity(intent);
            }
        });
    }
}