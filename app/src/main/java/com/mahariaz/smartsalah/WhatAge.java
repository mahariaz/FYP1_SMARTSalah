package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class WhatAge extends AppCompatActivity {
    SearchableSpinner spinner_age;
    Button cont;
    String age_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_age);
        //spinner_salah
        spinner_age = (SearchableSpinner) findViewById(R.id.spinner_age);
        ArrayAdapter<String> Adapter_age = new ArrayAdapter<String>(WhatAge.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.age));
        Adapter_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(Adapter_age);
        spinner_age.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                 age_selected = spinner_age.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected() {

            }
        });
        cont=findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.age=age_selected;
                Intent intent=new Intent(WhatAge.this,WhatHeight.class);
                startActivity(intent);
            }
        });

    }
}