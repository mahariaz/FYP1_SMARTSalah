package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class Calender extends AppCompatActivity {
    CalendarView calendar;
    TextView date_view;
    SearchableSpinner spinner_salah,spinner_rakah;
    String Date,sel_salah,sel_rakah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendar = (CalendarView)
                findViewById(R.id.calendar);
        date_view = (TextView)
                findViewById(R.id.date_view);
        //spinner_salah
        spinner_salah = (SearchableSpinner) findViewById(R.id.spinner_salah);
        ArrayAdapter<String> Adapter_salah = new ArrayAdapter<String>(Calender.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.salah));
        Adapter_salah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_salah.setAdapter(Adapter_salah);
        //spinner_rakah
        spinner_rakah = (SearchableSpinner) findViewById(R.id.spinner_rakah);
        ArrayAdapter<String> Adapter_rakah = new ArrayAdapter<String>(Calender.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.rakah));
        Adapter_rakah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rakah.setAdapter(Adapter_rakah);
        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,int year,int month,int dayOfMonth){
                Date= year + "-"+ (month + 1) + "-" + dayOfMonth;
                // set this date in TextView for Display
                date_view.setText(Date);
            }
        });
        Button stats=findViewById(R.id.stats);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah = spinner_salah.getSelectedItem().toString();
                sel_rakah = spinner_rakah.getSelectedItem().toString();
                Intent intent=new Intent(Calender.this,Salah_stats_history.class);
                intent.putExtra("date",Date);
                intent.putExtra("sel_salah",sel_salah);
                intent.putExtra("sel_rakah",sel_rakah);
                startActivity(intent);


            }
        });
    }

}