package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Supplications extends AppCompatActivity implements RecyclerViewAdapterDuas.OnTileListner {
    private RecyclerView rec_view;
    RecyclerView.LayoutManager lm;
    RecyclerViewAdapterDuas rec_view_adp;
    ArrayList<String> duaTopic = new ArrayList<String>();
    ArrayList<Integer> tileColor = new ArrayList<Integer>();
    ArrayList<Uri> uri_img = new ArrayList<Uri>();
    List<String> topic = Arrays.asList("Salah","Morning","Evening",
            "Ailment","Istikhara","Forgiveness");
    List<Integer> color = Arrays.asList(R.color.blue,R.color.aqua,R.color.algae_green,
            R.color.air_force_blue,R.color.banana_mania,R.color.alice_blue);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplications);
        // getting the list of URIs of png Images
        List<Uri> img_uri_list=list_uri();
        // populating the ArrayList with temporary lists
        duaTopic.addAll(topic);
        uri_img.addAll(img_uri_list);
        tileColor.addAll(color);
        // setting of recyclerView
        rec_view=findViewById(R.id.rec_view);
        lm=new GridLayoutManager(this,2);
        rec_view.setLayoutManager(lm);
        rec_view_adp=new RecyclerViewAdapterDuas(duaTopic,tileColor,uri_img,getApplicationContext(),this);
        rec_view.setAdapter(rec_view_adp);
        rec_view.setHasFixedSize(true);
    }
    /* Get the URI of png image and then
insert it into list and return that list
 */
    public List<Uri> list_uri(){
        Uri uri_i1= get_uri(R.drawable.sun);
        Uri uri_i2= get_uri(R.drawable.moon);
        Uri uri_i3= get_uri(R.drawable.flower);
        Uri uri_i4= get_uri(R.drawable.flower);
        Uri uri_i5= get_uri(R.drawable.flower);
        Uri uri_i6= get_uri(R.drawable.flower);
        List<Uri> uri_list = Arrays.asList(uri_i1,uri_i2,uri_i3,uri_i4
                ,uri_i5,uri_i6);
        return uri_list;
    }
    // Typical code for changing PNG  into URI
    public Uri get_uri(int id){
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(id)
                + '/' + getResources().getResourceTypeName(id) + '/' + getResources().getResourceEntryName(id) );

        return uri;

    }

    @Override
    public void onTileClick(int position) {
        if (duaTopic.get(position).equalsIgnoreCase(duaTopic.get(0))){
            Intent intent=new Intent(this,salah_duas.class);
            startActivity(intent);
        }

    }
}