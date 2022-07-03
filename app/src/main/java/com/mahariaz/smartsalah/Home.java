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
import android.widget.LinearLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
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



    private NavigationView nvDrawer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView prayedSalahTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        page = findViewById(R.id.viewPager) ;
        tabLayout = findViewById(R.id.tabLayout);
        prayedSalahTv=findViewById(R.id.prayedSalah);

        listItems = new ArrayList<>() ;
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item1,"Slider 1 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item2,"Slider 2 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item3,"Slider 3 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item4,"Slider 4 Title"));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item5,"Slider 5 Title"));
        The_Slide_items_Pager_Adapter itemsPager_adapter = new The_Slide_items_Pager_Adapter(this, listItems);
        page.setAdapter(itemsPager_adapter);
        tabLayout.setupWithViewPager(page,true);

        pieChartFarz = findViewById(R.id.farzPie);
        pieChartSunnah = findViewById(R.id.sunnahPie);
        weekPie = findViewById(R.id.weekPie);
        setupPieChartFarz();
        setupPieChartSunnah();
        weekStatusPieChart();




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
        CardView trackSalah=findViewById(R.id.trackSalah);
        trackSalah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Salah_Rakah_Selection.class);
                startActivity(intent);
            }
        });
        LinearLayout weekLL=findViewById(R.id.weekLL);
        weekLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Highlights.class);
                startActivity(intent);
            }
        });
        CardView supp=findViewById(R.id.supp);
        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Supplications.class);
                startActivity(intent);
            }
        });
        CardView hist=findViewById(R.id.hist);
        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Calender.class);
                startActivity(intent);
            }
        });
        getPrayedPrayers();



    }

    private void setupPieChartFarz() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(17, "Total Farz"));
        entries.add(new PieEntry(15, "Prayed"));
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#95b8d1"));
        colors.add(Color.parseColor("#b8e0d2"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        pieChartFarz.setData(data);
        pieChartFarz.invalidate();
        pieChartFarz.animateY(1400, Easing.EaseInOutQuad);
        pieChartFarz.setDrawHoleEnabled(true);
        pieChartFarz.getDescription().setEnabled(false);
        pieChartFarz.setDrawSliceText(false);
        Legend legend = pieChartFarz.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12);
        legend.setFormSize(15);
        legend.setFormToTextSpace(2);



    }
    private void setupPieChartSunnah() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(12, "Total Sunnah"));
        entries.add(new PieEntry(5, "Prayed"));
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#95b8d1"));
        colors.add(Color.parseColor("#b8e0d2"));
        Legend legend = pieChartSunnah.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12);
        legend.setFormSize(15);
        legend.setFormToTextSpace(2);
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
        entries.add(new PieEntry(0.3f, "Error"));
        entries.add(new PieEntry(0.7f, "Missed"));
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#B8D8BE"));
        colors.add(Color.parseColor("#d1cfe2"));
        colors.add(Color.parseColor("#f6ac69"));
        Legend legend = weekPie.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12);
        legend.setFormSize(15);
        legend.setFormToTextSpace(2);

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(weekPie));
        data.setValueTextSize(15f);
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
    private void getPrayedPrayers(){
        // step 1: getting todays date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currDateTemp=dateFormat.format(new Date());
        String dateTokens[]=currDateTemp.split("-");
        String currDate=dateTokens[2]+"-"+dateTokens[1]+"-"+dateTokens[0];
        System.out.println("currDate : "+currDate);
        String salahNames[]={"Fajr","Zuhr","Asr","Maghrib","Isha"};
        ArrayList<String> uniqueSalah=new ArrayList<>();
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserBio");
        databaseReference.child(shared.username).child("firebasePrayer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println("inside snapshot");
                    System.out.println("cccccccccc"+snapshot.getKey()+ds.getChildrenCount() + "");
                    String name = ds.getKey();
                    System.out.println("childNm : "+name);

                    DatabaseReference databaseReference2;
                    databaseReference2 = firebaseDatabase.getReference("UserBio");
                    databaseReference2.child(shared.username).child("firebasePrayer").child(name).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds2 : snapshot.getChildren()) {
                                        String fetchedDate=ds2.child("currDate").getValue(String.class);
                                        String fetchedName=ds2.child("salahName").getValue(String.class);
                                        String fetchedTime=ds2.child("salahTimelinessStatus").getValue(String.class);
                                        System.out.println("feted date : "+fetchedDate);
                                        if(fetchedDate.equalsIgnoreCase(currDate) && fetchedTime.equalsIgnoreCase("OnTime")){
                                            uniqueSalah.add(fetchedName);
                                        }
                                        System.out.println("uni : "+uniqueSalah);
                                        Set<String> set = new HashSet<>(uniqueSalah);
                                        uniqueSalah.clear();
                                        uniqueSalah.addAll(set);
                                        System.out.println("uniDupRmv : "+uniqueSalah);
                                        String s=String.valueOf(uniqueSalah.size());
                                        prayedSalahTv.setText(s);


                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("FAILED");

            }
        });


    }


}


