package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Supplications extends AppCompatActivity implements RecyclerViewAdapterDuas.OnTileListner {
    private Toolbar mTopToolbar;
    private RecyclerView rec_view;
    RecyclerView.LayoutManager lm;
    RecyclerViewAdapterDuas rec_view_adp;
    ArrayList<String> duaTopic = new ArrayList<String>();
    ArrayList<String> tileColor = new ArrayList<String>();
    ArrayList<Uri> uri_img = new ArrayList<Uri>();
    List<String> topic = Arrays.asList("Salah","Morning","Evening",
            "Ailment","Job Seeking","Forgiveness");
  List<String> color = Arrays.asList("#89CFF0","#BF94E4","#D19FE8","#E2A76F","#536872","#E4717A");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplications);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
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
        Uri uri_i1= get_uri(R.drawable.pray);
        Uri uri_i2= get_uri(R.drawable.sun);
        Uri uri_i3= get_uri(R.drawable.moon);
        Uri uri_i4= get_uri(R.drawable.flower);
        Uri uri_i5= get_uri(R.drawable.jobpic);
        Uri uri_i6= get_uri(R.drawable.flower2);
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
        if (duaTopic.get(position).equalsIgnoreCase(duaTopic.get(1))){
            Intent intent=new Intent(this,morning_duas.class);
            startActivity(intent);
        }
        if (duaTopic.get(position).equalsIgnoreCase(duaTopic.get(2))){
            Intent intent=new Intent(this,morning_duas.class);
            startActivity(intent);
        }
        if (duaTopic.get(position).equalsIgnoreCase(duaTopic.get(3))){
            Intent intent=new Intent(this,illness_duas.class);
            startActivity(intent);
        }
        if (duaTopic.get(position).equalsIgnoreCase(duaTopic.get(4))){
            Intent intent=new Intent(this,job_duas.class);
            startActivity(intent);
        }
        if (duaTopic.get(position).equalsIgnoreCase(duaTopic.get(5))){
            Intent intent=new Intent(this,forgive_duas.class);
            startActivity(intent);
        }
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
            Intent intent=new Intent(Supplications.this,Home.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}