package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class nuclear_energie_chart extends AppCompatActivity {

    DatabaseReference dbref;
    //  private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    Date datum = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    LineGraphSeries<DataPoint> series, series2;
    GraphView graphView;
    Calendar cal = Calendar.getInstance();
    int unroundedMinutes = cal.get(Calendar.MINUTE);
    int mod = unroundedMinutes % 15;
    String yWaarden;
    List<String> yList = new ArrayList<String>();
    Button koopEnergy;
    Button current,week,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuclear_energie_chart);

        koopEnergy = findViewById(R.id.koopEnergy);

        current = findViewById(R.id.currentGraphButton);
        week = findViewById(R.id.weekGraphButton);
        year = findViewById(R.id.yearGraphButton);

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nuclear_energie_chart.this, nuclear_energie_chart.class);
                startActivity(i);
            }
        });


        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nuclear_energie_chart.this, NuclearEnergyWeek.class);
                startActivity(i);
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nuclear_energie_chart.this, NuclearEnergyYear.class);
                startActivity(i);
            }
        });


        koopEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(nuclear_energie_chart.this,PaymentMenu.class);
                startActivity(i);
            }
        });


        dbref = FirebaseDatabase.getInstance().getReference().child(df.format(datum)).child("generation");


        Log.d("LALA", "LALA" + dbref);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                ArrayList<Date> dates = new ArrayList<Date>();
                cal.set(Calendar.HOUR_OF_DAY, 0);

                for (int i =0;i<95;i++){
                    dates.add(cal.getTime());
                    cal.add(Calendar.MINUTE, 15);
                }


                Log.d("YVAL", "Y" + yWaarden);

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                        new DataPoint(dates.get(0), Double.parseDouble(dataSnapshot.child(String.valueOf(8)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(1), Double.parseDouble(dataSnapshot.child(String.valueOf(9)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(2), Double.parseDouble(dataSnapshot.child(String.valueOf(10)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(3), Double.parseDouble(dataSnapshot.child(String.valueOf(11)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(4), Double.parseDouble(dataSnapshot.child(String.valueOf(12)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(5), Double.parseDouble(dataSnapshot.child(String.valueOf(13)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(6), Double.parseDouble(dataSnapshot.child(String.valueOf(14)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(7), Double.parseDouble(dataSnapshot.child(String.valueOf(15)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(8), Double.parseDouble(dataSnapshot.child(String.valueOf(16)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(9), Double.parseDouble(dataSnapshot.child(String.valueOf(17)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(10), Double.parseDouble(dataSnapshot.child(String.valueOf(18)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(11), Double.parseDouble(dataSnapshot.child(String.valueOf(19)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(12), Double.parseDouble(dataSnapshot.child(String.valueOf(20)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(13), Double.parseDouble(dataSnapshot.child(String.valueOf(21)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(14), Double.parseDouble(dataSnapshot.child(String.valueOf(22)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(15), Double.parseDouble(dataSnapshot.child(String.valueOf(23)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(16), Double.parseDouble(dataSnapshot.child(String.valueOf(24)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(17), Double.parseDouble(dataSnapshot.child(String.valueOf(25)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(18), Double.parseDouble(dataSnapshot.child(String.valueOf(26)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(19), Double.parseDouble(dataSnapshot.child(String.valueOf(27)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(20), Double.parseDouble(dataSnapshot.child(String.valueOf(28)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(21), Double.parseDouble(dataSnapshot.child(String.valueOf(29)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(22), Double.parseDouble(dataSnapshot.child(String.valueOf(30)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(23), Double.parseDouble(dataSnapshot.child(String.valueOf(31)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(24), Double.parseDouble(dataSnapshot.child(String.valueOf(32)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(25), Double.parseDouble(dataSnapshot.child(String.valueOf(33)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(26), Double.parseDouble(dataSnapshot.child(String.valueOf(34)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(27), Double.parseDouble(dataSnapshot.child(String.valueOf(35)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(28), Double.parseDouble(dataSnapshot.child(String.valueOf(36)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(29), Double.parseDouble(dataSnapshot.child(String.valueOf(37)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(30), Double.parseDouble(dataSnapshot.child(String.valueOf(38)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(31), Double.parseDouble(dataSnapshot.child(String.valueOf(39)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(32), Double.parseDouble(dataSnapshot.child(String.valueOf(40)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(33), Double.parseDouble(dataSnapshot.child(String.valueOf(41)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(34), Double.parseDouble(dataSnapshot.child(String.valueOf(42)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(35), Double.parseDouble(dataSnapshot.child(String.valueOf(43)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(36), Double.parseDouble(dataSnapshot.child(String.valueOf(44)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(37), Double.parseDouble(dataSnapshot.child(String.valueOf(45)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(38), Double.parseDouble(dataSnapshot.child(String.valueOf(46)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(39), Double.parseDouble(dataSnapshot.child(String.valueOf(47)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(40), Double.parseDouble(dataSnapshot.child(String.valueOf(48)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(41), Double.parseDouble(dataSnapshot.child(String.valueOf(49)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(42), Double.parseDouble(dataSnapshot.child(String.valueOf(50)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(43), Double.parseDouble(dataSnapshot.child(String.valueOf(51)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(44), Double.parseDouble(dataSnapshot.child(String.valueOf(52)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(45), Double.parseDouble(dataSnapshot.child(String.valueOf(53)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(46), Double.parseDouble(dataSnapshot.child(String.valueOf(54)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(47), Double.parseDouble(dataSnapshot.child(String.valueOf(55)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(48), Double.parseDouble(dataSnapshot.child(String.valueOf(56)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(49), Double.parseDouble(dataSnapshot.child(String.valueOf(57)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(50), Double.parseDouble(dataSnapshot.child(String.valueOf(58)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(51), Double.parseDouble(dataSnapshot.child(String.valueOf(59)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(52), Double.parseDouble(dataSnapshot.child(String.valueOf(60)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(53), Double.parseDouble(dataSnapshot.child(String.valueOf(61)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(54), Double.parseDouble(dataSnapshot.child(String.valueOf(62)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(55), Double.parseDouble(dataSnapshot.child(String.valueOf(63)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(56), Double.parseDouble(dataSnapshot.child(String.valueOf(64)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(57), Double.parseDouble(dataSnapshot.child(String.valueOf(65)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(58), Double.parseDouble(dataSnapshot.child(String.valueOf(66)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(59), Double.parseDouble(dataSnapshot.child(String.valueOf(67)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(60), Double.parseDouble(dataSnapshot.child(String.valueOf(68)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(61), Double.parseDouble(dataSnapshot.child(String.valueOf(69)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(62), Double.parseDouble(dataSnapshot.child(String.valueOf(70)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(63), Double.parseDouble(dataSnapshot.child(String.valueOf(71)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(64), Double.parseDouble(dataSnapshot.child(String.valueOf(72)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(65), Double.parseDouble(dataSnapshot.child(String.valueOf(73)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(66), Double.parseDouble(dataSnapshot.child(String.valueOf(74)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(67), Double.parseDouble(dataSnapshot.child(String.valueOf(75)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(68), Double.parseDouble(dataSnapshot.child(String.valueOf(76)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(69), Double.parseDouble(dataSnapshot.child(String.valueOf(77)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(70), Double.parseDouble(dataSnapshot.child(String.valueOf(78)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(71), Double.parseDouble(dataSnapshot.child(String.valueOf(79)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(72), Double.parseDouble(dataSnapshot.child(String.valueOf(80)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(73), Double.parseDouble(dataSnapshot.child(String.valueOf(81)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(74), Double.parseDouble(dataSnapshot.child(String.valueOf(82)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(75), Double.parseDouble(dataSnapshot.child(String.valueOf(83)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(76), Double.parseDouble(dataSnapshot.child(String.valueOf(84)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(77), Double.parseDouble(dataSnapshot.child(String.valueOf(85)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(78), Double.parseDouble(dataSnapshot.child(String.valueOf(86)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(79), Double.parseDouble(dataSnapshot.child(String.valueOf(87)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(80), Double.parseDouble(dataSnapshot.child(String.valueOf(88)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(81), Double.parseDouble(dataSnapshot.child(String.valueOf(89)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(82), Double.parseDouble(dataSnapshot.child(String.valueOf(90)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(83), Double.parseDouble(dataSnapshot.child(String.valueOf(91)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(84), Double.parseDouble(dataSnapshot.child(String.valueOf(92)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(85), Double.parseDouble(dataSnapshot.child(String.valueOf(93)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(86), Double.parseDouble(dataSnapshot.child(String.valueOf(94)).child("data").child("nuclear").getValue().toString())),
                        new DataPoint(dates.get(87), Double.parseDouble(dataSnapshot.child(String.valueOf(95)).child("data").child("nuclear").getValue().toString())),


                });


                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {

                        String msg = "X:" + new Time((long) dataPoint.getX()) + "\nY:" + dataPoint.getY();
                        Log.d("XVAL", "XVAL" + new Date((long) dataPoint.getX()));
                        Toast.makeText(nuclear_energie_chart.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


                //series2.setColor(Color.RED);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setColor(Color.rgb(242, 210, 0));
                series.setDrawBackground(true);
                series.setBackgroundColor(Color.argb(60, 240, 222, 108));

                graphView.setTitle("Nuclear Energy");

                graphView.getViewport().setScrollable(true); // enables horizontal scrolling
                graphView.getViewport().setScrollableY(true); // enables vertical scrolling
                graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
                graphView.getViewport().setScalableY(true); // enables vertical zooming and scrolling

                // graphView.getGridLabelRenderer().setNumHorizontalLabels(10);

                graphView.getViewport().setMinX(0);
                graphView.getViewport().setMaxX(8.64e+7);
                graphView.addSeries(series);

//              graphView.addSeries(series2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }

        });

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        graphView = (GraphView) findViewById(R.id.graphview);


        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double val, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat("HH:mm");
                    return formatter.format(val);
                } else {
                    return super.formatLabel(val, isValueX)+ "kWh ";
                }
            }

        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);
        BottomNavigationView topNaviChart = findViewById(R.id.bottom_graphNavi);

        topNaviChart.setSelectedItemId(R.id.chartAll);


        topNaviChart.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.chartNuclear:
                        return true;
                    case R.id.chartZon:
                        startActivity(new Intent(getApplicationContext(), water_energie_chart.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chartGas:
                        startActivity(new Intent(getApplicationContext(), gas_energie_chart.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chartWind:
                        startActivity(new Intent(getApplicationContext(), wind_energie_chart.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chartAll:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.charts);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.charts:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), History.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chartMeritOrder:
                        startActivity(new Intent(getApplicationContext(), MeritOrder.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.wallet:
                        startActivity(new Intent(getApplicationContext(), Wallet.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }

        });

    }


}
