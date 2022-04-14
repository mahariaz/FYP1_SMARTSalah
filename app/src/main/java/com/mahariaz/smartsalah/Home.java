package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.onesignal.OneSignal;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TimerTask;

public class Home extends AppCompatActivity implements RecyclerViewAdapter.OnTileListner{
    private List<The_Slide_Items_Model_Class> listItems;
    private ViewPager page;
    private TabLayout tabLayout;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private static final String ONESIGNAL_APP_ID = "a827ee7e-6beb-4dd5-9c21-f281bed4c4c7";

    private NavigationView nvDrawer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //recyclerview things
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Uri> images = new ArrayList<Uri>();
    ArrayList<String> headings = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        page = findViewById(R.id.viewPager) ;
        tabLayout = findViewById(R.id.tabLayout);
        listItems = new ArrayList<>() ;
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item1,"Slider 1 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item2,"Slider 2 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item3,"Slider 3 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item4,"Slider 4 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item5,"Slider 5 Title"));
        The_Slide_items_Pager_Adapter itemsPager_adapter = new The_Slide_items_Pager_Adapter(this, listItems);
        page.setAdapter(itemsPager_adapter);
        tabLayout.setupWithViewPager(page,true);

        List<String> heading_list = Arrays.asList("Highlights","Track Salah","Salah History",
                "Supplications");
        // getting the list of URIs of png Images
        List<Uri> imguri_list=list_uri();
        images.addAll(imguri_list);
        headings.addAll(heading_list);
        // setting of recyclerView
        recyclerView = findViewById(R.id.rec_view);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(images,headings,getApplicationContext(),this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        // Set a Toolbar to replace the ActionBar.
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        OneSignal.sendTag("User_ID","a@gmail.com");
        //notifications("Track your Salah right away!");

        // This will display an Up icon (<-), we will replace it with hamburger later
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        View headerView = nvDrawer.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.uname_tview);
        TextView userEmail = headerView.findViewById(R.id.email_tview);
        ImageView show_dp=headerView.findViewById(R.id.show_dp);
        // MY SAVIOUR CODE FOR RETRIEVING A PARTICULAR INDEX FROM FIREBASE
        show_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("UserBio").child(shared.username).child("dp");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        ImageView show_dp=findViewById(R.id.show_dp);
                        Glide.with(getApplicationContext()).load(value).into(show_dp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("FAILED");

                    }
                });

            }
        });
        // set user name and email
        userName.setText(shared.username);
        userEmail.setText(shared.email);
        /*Button go=findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Salah_Rakah_Selection.class);
                startActivity(intent);
            }
        });*/
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(),2000,3000);
        tabLayout.setupWithViewPager(page,true);


    }

    private List<Uri> list_uri() {
        Uri uri_i1= get_uri(R.drawable.analytics_img);
        Uri uri_i2= get_uri(R.drawable.salah_img);
        Uri uri_i3= get_uri(R.drawable.history_img);
        Uri uri_i4= get_uri(R.drawable.supp_img);
        Uri uri_i5= get_uri(R.drawable.clock_img);
        List<Uri> uri_list = Arrays.asList(uri_i1,uri_i2,uri_i3,uri_i4
                ,uri_i5);
        return uri_list;
    }
    // Typical code for changing PNG  into URI
    public Uri get_uri(int id){
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(id)
                + '/' + getResources().getResourceTypeName(id) + '/' + getResources().getResourceEntryName(id) );

        return uri;

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch(menuItem.getItemId()) {
            case R.id.fr_about:
                Intent intent=new Intent(Home.this,About.class);
                startActivity(intent);
                break;
            case R.id.fr_profile:
                Intent intent1=new Intent(Home.this,UserProfile.class);
                startActivity(intent1);
                break;
            case R.id.fr_settings:
                Intent intent2=new Intent(Home.this,Settings.class);
                startActivity(intent2);
            case R.id.fr_rate:
                Intent intent3=new Intent(Home.this,RateUs.class);
                startActivity(intent3);



        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void notifications(String msg)
    {



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

               // Toast.makeText(getApplicationContext(), "Inside Notification fun", Toast.LENGTH_SHORT).show();
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                   /* //This is a Simple Logic to Send Notification different Device Programmatically....
                    if (MainActivity.LoggedIn_User_Email.equals("user1@gmail.com")) {
                        send_email = "user2@gmail.com";
                    } else {
                        send_email = "user1@gmail.com";
                    }*/

                    send_email="a@gmail.com";
                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NDFkOTNiZjQtMDdhOC00NThlLTljZTEtZGJhMjE2MDdmZTM5");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"a827ee7e-6beb-4dd5-9c21-f281bed4c4c7\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \""+msg+"\"}"
                                + "}";



                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }

                        //Toast.makeText(getApplicationContext(), jsonResponse, Toast.LENGTH_SHORT).show();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onTileClick(int position) {
        System.out.println("onclick listner");
        System.out.println("POSITION : "+headings.get(position));
        System.out.println("INDEX 1 :"+headings.get(1));
        if (headings.get(position).equalsIgnoreCase(headings.get(0))){
            Intent intent=new Intent(this,Highlights.class);
            startActivity(intent);
        }
        else if (headings.get(position).equalsIgnoreCase(headings.get(1))){
            Intent intent=new Intent(this,Salah_Rakah_Selection.class);
            startActivity(intent);
        }
        else if (headings.get(position).equalsIgnoreCase(headings.get(2))){
            Intent intent=new Intent(Home.this,Calender.class);
            startActivity(intent);
        }
        else if (headings.get(position).equalsIgnoreCase(headings.get(3))){
            Intent intent=new Intent(this,Supplications.class);
            startActivity(intent);
        }
//        else if (headings.get(position).equalsIgnoreCase(headings.get(4))){
//            Intent intent=new Intent(this,SalahTimings.class);
//            startActivity(intent);
//        }
    }
    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {

            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page.getCurrentItem()< listItems.size()-1) {
                        page.setCurrentItem(page.getCurrentItem()+1);
                    }
                    else
                        page.setCurrentItem(0);
                }
            });
        }
    }
}


