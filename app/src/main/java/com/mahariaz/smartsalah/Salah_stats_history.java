package com.mahariaz.smartsalah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Salah_stats_history extends AppCompatActivity {
    BarChart barChart;
    private Toolbar mTopToolbar;
    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String> labelNames;
    ArrayList<SalahPostureNames> PostureNamesArrayList=new ArrayList<SalahPostureNames>();

    //db
    final DBAdapter db=new DBAdapter(this);
    String sel_salah,sel_rakah,date,rakah_per,qayam_avg="0",ruku_avg="0",qoum_avg="0",sajda_avg="0",jalsa_avg="0",tash_avg="0",get_salah="Missed/Qaza",get_rakah="0 Rakah";
    TextView salah_view,rakah_view;
    // graph
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Takbir","Qayam", "Ruku", "Qouma", "Sajda", "Tashahud"));
    List<Entry> postures;
    TextView sel_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salah_stats_history);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        get_intents();
        search_records(); // from sqlite of entered salah,rakah and date
        salah_view=findViewById(R.id.salahName);
        rakah_view=findViewById(R.id.rakahPrayed);
        // making graph
        postures = getPostureAverageTime();
        dataSets = new ArrayList<>();
        makeGraph();
        sel_date=findViewById(R.id.sel_date);
        sel_date.setText(date);

    }

    private void makeGraph() {
        LineDataSet set1;

        set1 = new LineDataSet(postures, "Postures");
        set1.setColor(Color.rgb(65, 168, 121));
        set1.setValueTextColor(Color.rgb(55, 70, 73));
        set1.setValueTextSize(10f);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawFilled(true);
        set1.setFillColor(getResources().getColor(R.color.teal_200));
        dataSets.add(set1);

//customization
        LineChart mLineGraph = findViewById(R.id.lineChart);
        mLineGraph.setTouchEnabled(true);
        mLineGraph.setDragEnabled(true);
        mLineGraph.setScaleEnabled(true);
        mLineGraph.setPinchZoom(true);
        mLineGraph.setDrawGridBackground(true);
        mLineGraph.setExtraLeftOffset(15);
        mLineGraph.setExtraRightOffset(15);
//to hide background lines
        mLineGraph.getXAxis().setDrawGridLines(true);
        mLineGraph.getAxisLeft().setDrawGridLines(true);
        mLineGraph.getAxisRight().setDrawGridLines(true);

//to hide right Y and top X border
        YAxis rightYAxis = mLineGraph.getAxisRight();
        rightYAxis.setEnabled(false);
        YAxis leftYAxis = mLineGraph.getAxisLeft();
        leftYAxis.setEnabled(true);
        XAxis topXAxis = mLineGraph.getXAxis();
        topXAxis.setEnabled(true);


        XAxis xAxis = mLineGraph.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        set1.setLineWidth(4f);
        set1.setCircleRadius(3f);
        set1.setDrawValues(false);
        set1.setCircleHoleColor(getResources().getColor(R.color.teal_200));
        set1.setCircleColor(getResources().getColor(R.color.black));

//String setter in x-Axis
        mLineGraph.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        LineData data = new LineData(dataSets);
        mLineGraph.setData(data);
        mLineGraph.animateX(2000);
        mLineGraph.invalidate();
        mLineGraph.getLegend().setEnabled(true);
        mLineGraph.getDescription().setEnabled(false);

    }
    private List<Entry> getPostureAverageTime() {
        ArrayList<Entry> posture = new ArrayList<>();

        posture.add(new Entry(1, 12));
        posture.add(new Entry(2, 2));
        posture.add(new Entry(3, 2));
        posture.add(new Entry(4, 3));
        posture.add(new Entry(5, 2));
        posture.add(new Entry(6, 12));


        return posture.subList(0, 6);
    }

    private void search_records() {
        db.openDB();
        Cursor c=db.getAllValues();
        while(c.moveToNext()){
            String get_time=c.getString(11);
            String stored_salah=c.getString(2);
            String stored_rakah=c.getString(3);
            String[] stored_date=get_time.split("/");
            if(stored_date[0].equalsIgnoreCase(date)){
                if(stored_salah.equalsIgnoreCase(sel_salah) && stored_rakah.equalsIgnoreCase(sel_rakah)){
                    get_salah=c.getString(2);
                    get_rakah=c.getString(3);
                    rakah_per=c.getString(4);
                    qayam_avg=c.getString(5);
                    ruku_avg=c.getString(6);
                    qoum_avg=c.getString(7);
                    sajda_avg=c.getString(8);
                    jalsa_avg=c.getString(9);
                    tash_avg=c.getString(10);

                }

            }





        }

    }

    private void get_intents() {
        Intent intent=getIntent();
        date=intent.getStringExtra("date");
        sel_salah=intent.getStringExtra("sel_salah");
        sel_rakah=intent.getStringExtra("sel_rakah");
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
            Intent intent=new Intent(Salah_stats_history.this,Home.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}