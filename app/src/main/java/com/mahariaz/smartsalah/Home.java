package com.mahariaz.smartsalah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.onesignal.OneSignal;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
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

public class Home extends AppCompatActivity {
    private List<The_Slide_Items_Model_Class> listItems;
    private ViewPager page;
    private TabLayout tabLayout;
    private PieChart pieChartFarz;
    private PieChart pieChartSunnah;
    private PieChart weekPie;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private static final String ONESIGNAL_APP_ID = "a827ee7e-6beb-4dd5-9c21-f281bed4c4c7";

    private NavigationView nvDrawer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


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
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.hadith1,"Slider 5 Title"));
        The_Slide_items_Pager_Adapter itemsPager_adapter = new The_Slide_items_Pager_Adapter(this, listItems);
        page.setAdapter(itemsPager_adapter);
        tabLayout.setupWithViewPager(page,true);

        pieChartFarz = findViewById(R.id.farzPie);
        pieChartSunnah = findViewById(R.id.sunnahPie);
        weekPie = findViewById(R.id.weekPie);
        setupPieChartFarz();
        setupPieChartSunnah();
        weekStatusPieChart();




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

        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(),2000,3000);
        tabLayout.setupWithViewPager(page,true);

        CardView todayStats=findViewById(R.id.todayStats);
        todayStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Highlights.class);
                startActivity(intent);
            }
        });


    }

    private void setupPieChartFarz() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(17, "Total Farz"));
        entries.add(new PieEntry(15, "Prayed"));
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        pieChartFarz.setData(data);
        pieChartFarz.invalidate();
        pieChartFarz.animateY(1400, Easing.EaseInOutQuad);
        pieChartFarz.setDrawHoleEnabled(true);
        pieChartFarz.getDescription().setEnabled(false);
        pieChartFarz.setDrawSliceText(false);



    }
    private void setupPieChartSunnah() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(12, "Total Sunnah"));
        entries.add(new PieEntry(5, "Prayed"));
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        pieChartSunnah.setData(data);
        pieChartSunnah.invalidate();
        pieChartSunnah.animateY(1400, Easing.EaseInOutQuad);
        pieChartSunnah.setDrawHoleEnabled(true);
        pieChartSunnah.getDescription().setEnabled(false);
        pieChartSunnah.setDrawSliceText(false);
    }
    private void weekStatusPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.3f, "Complete"));
        entries.add(new PieEntry(0.3f, "Correct"));
        entries.add(new PieEntry(0.7f, "Missed"));
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(weekPie));
        weekPie.setData(data);
        weekPie.invalidate();
        weekPie.animateY(1400, Easing.EaseInOutQuad);
        weekPie.setDrawHoleEnabled(false);
        weekPie.getDescription().setEnabled(false);
        weekPie.setDrawSliceText(false);
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


