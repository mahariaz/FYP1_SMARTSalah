package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class WhatDp extends AppCompatActivity {
    CircleImageView iv;
    Uri selectedImage = null;
    Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_dp);
        iv=findViewById(R.id.dp);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Selecting");
                Intent selecting = new Intent();
                selecting.setAction(Intent.ACTION_GET_CONTENT);
                selecting.setType("image/*");
                startActivityForResult(selecting, 200);
            }
        });
        cont=findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WhatDp.this,signUp.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            shared.selectedImage=selectedImage;
            iv = findViewById(R.id.dp);
            iv.setImageURI(shared.selectedImage);


        }
    }
}