package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class WhatHeight extends AppCompatActivity {
    SearchableSpinner spinner_ft,spinner_in;
    Button cont;
    String ft,in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_height);
        //spinner_ft
        spinner_ft = (SearchableSpinner) findViewById(R.id.spinner_ft);
        ArrayAdapter<String> Adapter_ft = new ArrayAdapter<String>(WhatHeight.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ft));
        Adapter_ft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ft.setAdapter(Adapter_ft);
        //spinner_in
        spinner_in = (SearchableSpinner) findViewById(R.id.spinner_in);
        ArrayAdapter<String> Adapter_in = new ArrayAdapter<String>(WhatHeight.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.in));
        Adapter_in.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_in.setAdapter(Adapter_in);

        spinner_ft.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                 ft = spinner_ft.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected() {

            }
        });
        spinner_in.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                 in = spinner_in.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected() {

            }
        });

        cont=findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.height=ft+in;
                Intent intent=new Intent(WhatHeight.this,WhatDp.class);
                startActivity(intent);
            }
        });

    }
}