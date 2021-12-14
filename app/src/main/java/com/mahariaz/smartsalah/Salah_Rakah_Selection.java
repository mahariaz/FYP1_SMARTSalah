package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;

public class Salah_Rakah_Selection extends AppCompatActivity {
    String sel_salah,sel_rakah;
    Button start;
    SearchableSpinner spinner_salah,spinner_rakah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_rakah_selection);
        //spinner_salah
        spinner_salah = (SearchableSpinner) findViewById(R.id.spinner_salah);
        ArrayAdapter<String> Adapter_salah = new ArrayAdapter<String>(Salah_Rakah_Selection.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.salah));
        Adapter_salah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_salah.setAdapter(Adapter_salah);
        //spinner_rakah
        spinner_rakah = (SearchableSpinner) findViewById(R.id.spinner_rakah);
        ArrayAdapter<String> Adapter_rakah = new ArrayAdapter<String>(Salah_Rakah_Selection.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.rakah));
        Adapter_rakah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rakah.setAdapter(Adapter_rakah);


        start=findViewById(R.id.start_salah_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel_salah = spinner_salah.getSelectedItem().toString();
                sel_rakah = spinner_rakah.getSelectedItem().toString();
                Intent intent=new Intent(Salah_Rakah_Selection.this,SalahProgress.class);
                intent.putExtra("sel_salah",sel_salah);
                intent.putExtra("sel_rakah",sel_rakah);
                startActivity(intent);
            }
        });
    }
}