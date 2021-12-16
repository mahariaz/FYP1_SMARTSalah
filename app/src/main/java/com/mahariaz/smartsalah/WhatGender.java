package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;

public class WhatGender extends AppCompatActivity {
    Button cont;
    CircleImageView female,male;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_gender);

        cont=findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WhatGender.this,WhatAge.class);
                startActivity(intent);
            }
        });
        female=findViewById(R.id.female);
        male=findViewById(R.id.male);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.gender="female";
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.gender="male";
            }
        });


    }
}