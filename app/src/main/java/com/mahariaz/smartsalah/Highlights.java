package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

public class Highlights extends AppCompatActivity {

    final DBAdapter db=new DBAdapter(this);
    String t4,t5,t6,t7,t8,t9;
    String h4,h5,h6,h7,h8,h9;
    String f4,f5,f6,f7,f8,f9;
    int two_rakah=0,temp1=0,c1=0;
    int three_rakah=0,temp2=0,c2=0;
    int four_rakah=0,temp3=0,c3=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlights);
        db.openDB();
        Cursor c = db.getAllValues();
        while (c.moveToNext()) {
            String stored_rakah = c.getString(3);
            String get_salah=c.getString(2);

            if (stored_rakah.equalsIgnoreCase("2")) {
                t4 = c.getString(5);
                t5 = c.getString(6);
                t6 = c.getString(7);
                t7 = c.getString(8);
                t8 = c.getString(9);
                t9 = c.getString(10);
                temp1+=Integer.parseInt(t4)+Integer.parseInt(t5)+Integer.parseInt(t6)+
                        Integer.parseInt(t7)+Integer.parseInt(t8)+Integer.parseInt(t9);
                c1+=1;

            }
            if (stored_rakah.equalsIgnoreCase("3")) {
                h4 = c.getString(5);
                h5 = c.getString(6);
                h6 = c.getString(7);
                h7 = c.getString(8);
                h8 = c.getString(9);
                h9 = c.getString(10);
                temp2+=Integer.parseInt(h4)+Integer.parseInt(h5)+Integer.parseInt(h6)+
                        Integer.parseInt(h7)+Integer.parseInt(h8)+Integer.parseInt(h9);
                c2+=1;

            }
            if (stored_rakah.equalsIgnoreCase("4")) {
                f4 = c.getString(5);
                f5 = c.getString(6);
                f6 = c.getString(7);
                f7 = c.getString(8);
                f8 = c.getString(9);
                f9 = c.getString(10);
                temp3+=Integer.parseInt(f4)+Integer.parseInt(f5)+Integer.parseInt(f6)+
                        Integer.parseInt(f7)+Integer.parseInt(f8)+Integer.parseInt(f9);
                c3+=1;

            }

        }
        two_rakah=temp1/c1;
        System.out.println("twoooo"+two_rakah);
        three_rakah=temp2/c2;
        System.out.println("threeee"+three_rakah);
        four_rakah=temp3/c3;
        System.out.println("fourrrr"+four_rakah);

    }
}