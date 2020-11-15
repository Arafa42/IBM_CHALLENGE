package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GasEnergyYear extends AppCompatActivity {

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
    Button bestelEnergy;
    Button current,week,year;
    TextView currDay;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_energy_year);

        currDay = findViewById(R.id.currDay);

        final int dayOfYear = LocalDate.now().getDayOfYear();

        currDay.setText("DAY : " + dayOfYear);


        current = findViewById(R.id.currentGraphButton);
        week = findViewById(R.id.weekGraphButton);
        year = findViewById(R.id.yearGraphButton);


        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GasEnergyYear.this, gas_energie_chart.class);
                startActivity(i);
            }
        });


        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GasEnergyYear.this, GasEnergyWeek.class);
                startActivity(i);
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GasEnergyYear.this, GasEnergyYear.class);
                startActivity(i);
            }
        });

        bestelEnergy = findViewById(R.id.koopEnergy);


        bestelEnergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GasEnergyYear.this, ReserveMenu.class);
                startActivity(i);
            }
        });


        dbref = FirebaseDatabase.getInstance().getReference().child("yearAheadGas");


        Log.d("LALA", "LALA" + dbref);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Date> dates = new ArrayList<Date>();
                cal.set(Calendar.HOUR_OF_DAY, 0);

                for (int i = 0; i < 95; i++) {
                    dates.add(cal.getTime());
                    cal.add(Calendar.MINUTE, 15);
                }

                Log.d("YVAL", "Y" + yWaarden);

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                        new DataPoint(0, Double.parseDouble(dataSnapshot.child(String.valueOf(0)).child("day").getValue().toString())),
                        new DataPoint(1, Double.parseDouble(dataSnapshot.child(String.valueOf(1)).child("day").getValue().toString())),
                        new DataPoint(2, Double.parseDouble(dataSnapshot.child(String.valueOf(2)).child("day").getValue().toString())),
                        new DataPoint(3, Double.parseDouble(dataSnapshot.child(String.valueOf(3)).child("day").getValue().toString())),
                        new DataPoint(4, Double.parseDouble(dataSnapshot.child(String.valueOf(4)).child("day").getValue().toString())),
                        new DataPoint(5, Double.parseDouble(dataSnapshot.child(String.valueOf(5)).child("day").getValue().toString())),
                        new DataPoint(6, Double.parseDouble(dataSnapshot.child(String.valueOf(6)).child("day").getValue().toString())),
                        new DataPoint(7, Double.parseDouble(dataSnapshot.child(String.valueOf(7)).child("day").getValue().toString())),
                        new DataPoint(8, Double.parseDouble(dataSnapshot.child(String.valueOf(8)).child("day").getValue().toString())),
                        new DataPoint(9, Double.parseDouble(dataSnapshot.child(String.valueOf(9)).child("day").getValue().toString())),
                        new DataPoint(10, Double.parseDouble(dataSnapshot.child(String.valueOf(10)).child("day").getValue().toString())),
                        new DataPoint(11, Double.parseDouble(dataSnapshot.child(String.valueOf(11)).child("day").getValue().toString())),
                        new DataPoint(12, Double.parseDouble(dataSnapshot.child(String.valueOf(12)).child("day").getValue().toString())),
                        new DataPoint(13, Double.parseDouble(dataSnapshot.child(String.valueOf(13)).child("day").getValue().toString())),
                        new DataPoint(14, Double.parseDouble(dataSnapshot.child(String.valueOf(14)).child("day").getValue().toString())),
                        new DataPoint(15, Double.parseDouble(dataSnapshot.child(String.valueOf(15)).child("day").getValue().toString())),
                        new DataPoint(16, Double.parseDouble(dataSnapshot.child(String.valueOf(16)).child("day").getValue().toString())),
                        new DataPoint(17, Double.parseDouble(dataSnapshot.child(String.valueOf(17)).child("day").getValue().toString())),
                        new DataPoint(18, Double.parseDouble(dataSnapshot.child(String.valueOf(18)).child("day").getValue().toString())),
                        new DataPoint(19, Double.parseDouble(dataSnapshot.child(String.valueOf(19)).child("day").getValue().toString())),
                        new DataPoint(20, Double.parseDouble(dataSnapshot.child(String.valueOf(20)).child("day").getValue().toString())),
                        new DataPoint(21, Double.parseDouble(dataSnapshot.child(String.valueOf(21)).child("day").getValue().toString())),
                        new DataPoint(22, Double.parseDouble(dataSnapshot.child(String.valueOf(22)).child("day").getValue().toString())),
                        new DataPoint(23, Double.parseDouble(dataSnapshot.child(String.valueOf(23)).child("day").getValue().toString())),
                        new DataPoint(24, Double.parseDouble(dataSnapshot.child(String.valueOf(24)).child("day").getValue().toString())),
                        new DataPoint(25, Double.parseDouble(dataSnapshot.child(String.valueOf(25)).child("day").getValue().toString())),
                        new DataPoint(26, Double.parseDouble(dataSnapshot.child(String.valueOf(26)).child("day").getValue().toString())),
                        new DataPoint(27, Double.parseDouble(dataSnapshot.child(String.valueOf(27)).child("day").getValue().toString())),
                        new DataPoint(28, Double.parseDouble(dataSnapshot.child(String.valueOf(28)).child("day").getValue().toString())),
                        new DataPoint(29, Double.parseDouble(dataSnapshot.child(String.valueOf(29)).child("day").getValue().toString())),
                        new DataPoint(30, Double.parseDouble(dataSnapshot.child(String.valueOf(30)).child("day").getValue().toString())),
                        new DataPoint(31, Double.parseDouble(dataSnapshot.child(String.valueOf(31)).child("day").getValue().toString())),
                        new DataPoint(32, Double.parseDouble(dataSnapshot.child(String.valueOf(32)).child("day").getValue().toString())),
                        new DataPoint(33, Double.parseDouble(dataSnapshot.child(String.valueOf(33)).child("day").getValue().toString())),
                        new DataPoint(34, Double.parseDouble(dataSnapshot.child(String.valueOf(34)).child("day").getValue().toString())),
                        new DataPoint(35, Double.parseDouble(dataSnapshot.child(String.valueOf(35)).child("day").getValue().toString())),
                        new DataPoint(36, Double.parseDouble(dataSnapshot.child(String.valueOf(36)).child("day").getValue().toString())),
                        new DataPoint(37, Double.parseDouble(dataSnapshot.child(String.valueOf(37)).child("day").getValue().toString())),
                        new DataPoint(38, Double.parseDouble(dataSnapshot.child(String.valueOf(38)).child("day").getValue().toString())),
                        new DataPoint(39, Double.parseDouble(dataSnapshot.child(String.valueOf(39)).child("day").getValue().toString())),
                        new DataPoint(40, Double.parseDouble(dataSnapshot.child(String.valueOf(40)).child("day").getValue().toString())),
                        new DataPoint(41, Double.parseDouble(dataSnapshot.child(String.valueOf(41)).child("day").getValue().toString())),
                        new DataPoint(42, Double.parseDouble(dataSnapshot.child(String.valueOf(42)).child("day").getValue().toString())),
                        new DataPoint(43, Double.parseDouble(dataSnapshot.child(String.valueOf(43)).child("day").getValue().toString())),
                        new DataPoint(44, Double.parseDouble(dataSnapshot.child(String.valueOf(44)).child("day").getValue().toString())),
                        new DataPoint(45, Double.parseDouble(dataSnapshot.child(String.valueOf(45)).child("day").getValue().toString())),
                        new DataPoint(46, Double.parseDouble(dataSnapshot.child(String.valueOf(46)).child("day").getValue().toString())),
                        new DataPoint(47, Double.parseDouble(dataSnapshot.child(String.valueOf(47)).child("day").getValue().toString())),
                        new DataPoint(48, Double.parseDouble(dataSnapshot.child(String.valueOf(48)).child("day").getValue().toString())),
                        new DataPoint(49, Double.parseDouble(dataSnapshot.child(String.valueOf(49)).child("day").getValue().toString())),
                        new DataPoint(50, Double.parseDouble(dataSnapshot.child(String.valueOf(50)).child("day").getValue().toString())),
                        new DataPoint(51, Double.parseDouble(dataSnapshot.child(String.valueOf(51)).child("day").getValue().toString())),
                        new DataPoint(52, Double.parseDouble(dataSnapshot.child(String.valueOf(52)).child("day").getValue().toString())),
                        new DataPoint(53, Double.parseDouble(dataSnapshot.child(String.valueOf(53)).child("day").getValue().toString())),
                        new DataPoint(54, Double.parseDouble(dataSnapshot.child(String.valueOf(54)).child("day").getValue().toString())),
                        new DataPoint(55, Double.parseDouble(dataSnapshot.child(String.valueOf(55)).child("day").getValue().toString())),
                        new DataPoint(56, Double.parseDouble(dataSnapshot.child(String.valueOf(56)).child("day").getValue().toString())),
                        new DataPoint(57, Double.parseDouble(dataSnapshot.child(String.valueOf(57)).child("day").getValue().toString())),
                        new DataPoint(58, Double.parseDouble(dataSnapshot.child(String.valueOf(58)).child("day").getValue().toString())),
                        new DataPoint(59, Double.parseDouble(dataSnapshot.child(String.valueOf(59)).child("day").getValue().toString())),
                        new DataPoint(60, Double.parseDouble(dataSnapshot.child(String.valueOf(60)).child("day").getValue().toString())),
                        new DataPoint(61, Double.parseDouble(dataSnapshot.child(String.valueOf(61)).child("day").getValue().toString())),
                        new DataPoint(62, Double.parseDouble(dataSnapshot.child(String.valueOf(62)).child("day").getValue().toString())),
                        new DataPoint(63, Double.parseDouble(dataSnapshot.child(String.valueOf(63)).child("day").getValue().toString())),
                        new DataPoint(64, Double.parseDouble(dataSnapshot.child(String.valueOf(64)).child("day").getValue().toString())),
                        new DataPoint(65, Double.parseDouble(dataSnapshot.child(String.valueOf(65)).child("day").getValue().toString())),
                        new DataPoint(66, Double.parseDouble(dataSnapshot.child(String.valueOf(66)).child("day").getValue().toString())),
                        new DataPoint(67, Double.parseDouble(dataSnapshot.child(String.valueOf(67)).child("day").getValue().toString())),
                        new DataPoint(68, Double.parseDouble(dataSnapshot.child(String.valueOf(68)).child("day").getValue().toString())),
                        new DataPoint(69, Double.parseDouble(dataSnapshot.child(String.valueOf(69)).child("day").getValue().toString())),
                        new DataPoint(70, Double.parseDouble(dataSnapshot.child(String.valueOf(70)).child("day").getValue().toString())),
                        new DataPoint(71, Double.parseDouble(dataSnapshot.child(String.valueOf(71)).child("day").getValue().toString())),
                        new DataPoint(72, Double.parseDouble(dataSnapshot.child(String.valueOf(72)).child("day").getValue().toString())),
                        new DataPoint(73, Double.parseDouble(dataSnapshot.child(String.valueOf(73)).child("day").getValue().toString())),
                        new DataPoint(74, Double.parseDouble(dataSnapshot.child(String.valueOf(74)).child("day").getValue().toString())),
                        new DataPoint(75, Double.parseDouble(dataSnapshot.child(String.valueOf(75)).child("day").getValue().toString())),
                        new DataPoint(76, Double.parseDouble(dataSnapshot.child(String.valueOf(76)).child("day").getValue().toString())),
                        new DataPoint(77, Double.parseDouble(dataSnapshot.child(String.valueOf(77)).child("day").getValue().toString())),
                        new DataPoint(78, Double.parseDouble(dataSnapshot.child(String.valueOf(78)).child("day").getValue().toString())),
                        new DataPoint(79, Double.parseDouble(dataSnapshot.child(String.valueOf(79)).child("day").getValue().toString())),
                        new DataPoint(80, Double.parseDouble(dataSnapshot.child(String.valueOf(80)).child("day").getValue().toString())),
                        new DataPoint(81, Double.parseDouble(dataSnapshot.child(String.valueOf(81)).child("day").getValue().toString())),
                        new DataPoint(82, Double.parseDouble(dataSnapshot.child(String.valueOf(82)).child("day").getValue().toString())),
                        new DataPoint(83, Double.parseDouble(dataSnapshot.child(String.valueOf(83)).child("day").getValue().toString())),
                        new DataPoint(84, Double.parseDouble(dataSnapshot.child(String.valueOf(84)).child("day").getValue().toString())),
                        new DataPoint(85, Double.parseDouble(dataSnapshot.child(String.valueOf(85)).child("day").getValue().toString())),
                        new DataPoint(86, Double.parseDouble(dataSnapshot.child(String.valueOf(86)).child("day").getValue().toString())),
                        new DataPoint(87, Double.parseDouble(dataSnapshot.child(String.valueOf(87)).child("day").getValue().toString())),
                        new DataPoint(88, Double.parseDouble(dataSnapshot.child(String.valueOf(88)).child("day").getValue().toString())),
                        new DataPoint(89, Double.parseDouble(dataSnapshot.child(String.valueOf(89)).child("day").getValue().toString())),
                        new DataPoint(90, Double.parseDouble(dataSnapshot.child(String.valueOf(90)).child("day").getValue().toString())),
                        new DataPoint(91, Double.parseDouble(dataSnapshot.child(String.valueOf(91)).child("day").getValue().toString())),
                        new DataPoint(92, Double.parseDouble(dataSnapshot.child(String.valueOf(92)).child("day").getValue().toString())),
                        new DataPoint(93, Double.parseDouble(dataSnapshot.child(String.valueOf(93)).child("day").getValue().toString())),
                        new DataPoint(94, Double.parseDouble(dataSnapshot.child(String.valueOf(94)).child("day").getValue().toString())),
                        new DataPoint(95, Double.parseDouble(dataSnapshot.child(String.valueOf(95)).child("day").getValue().toString())),
                        new DataPoint(96, Double.parseDouble(dataSnapshot.child(String.valueOf(96)).child("day").getValue().toString())),
                        new DataPoint(97, Double.parseDouble(dataSnapshot.child(String.valueOf(97)).child("day").getValue().toString())),
                        new DataPoint(98, Double.parseDouble(dataSnapshot.child(String.valueOf(98)).child("day").getValue().toString())),
                        new DataPoint(99, Double.parseDouble(dataSnapshot.child(String.valueOf(99)).child("day").getValue().toString())),
                        new DataPoint(100, Double.parseDouble(dataSnapshot.child(String.valueOf(100)).child("day").getValue().toString())),
                        new DataPoint(101, Double.parseDouble(dataSnapshot.child(String.valueOf(101)).child("day").getValue().toString())),
                        new DataPoint(102, Double.parseDouble(dataSnapshot.child(String.valueOf(102)).child("day").getValue().toString())),
                        new DataPoint(103, Double.parseDouble(dataSnapshot.child(String.valueOf(103)).child("day").getValue().toString())),
                        new DataPoint(104, Double.parseDouble(dataSnapshot.child(String.valueOf(104)).child("day").getValue().toString())),
                        new DataPoint(105, Double.parseDouble(dataSnapshot.child(String.valueOf(105)).child("day").getValue().toString())),
                        new DataPoint(106, Double.parseDouble(dataSnapshot.child(String.valueOf(106)).child("day").getValue().toString())),
                        new DataPoint(107, Double.parseDouble(dataSnapshot.child(String.valueOf(107)).child("day").getValue().toString())),
                        new DataPoint(108, Double.parseDouble(dataSnapshot.child(String.valueOf(108)).child("day").getValue().toString())),
                        new DataPoint(109, Double.parseDouble(dataSnapshot.child(String.valueOf(109)).child("day").getValue().toString())),
                        new DataPoint(110, Double.parseDouble(dataSnapshot.child(String.valueOf(110)).child("day").getValue().toString())),
                        new DataPoint(111, Double.parseDouble(dataSnapshot.child(String.valueOf(111)).child("day").getValue().toString())),
                        new DataPoint(112, Double.parseDouble(dataSnapshot.child(String.valueOf(112)).child("day").getValue().toString())),
                        new DataPoint(113, Double.parseDouble(dataSnapshot.child(String.valueOf(113)).child("day").getValue().toString())),
                        new DataPoint(114, Double.parseDouble(dataSnapshot.child(String.valueOf(114)).child("day").getValue().toString())),
                        new DataPoint(115, Double.parseDouble(dataSnapshot.child(String.valueOf(115)).child("day").getValue().toString())),
                        new DataPoint(116, Double.parseDouble(dataSnapshot.child(String.valueOf(116)).child("day").getValue().toString())),
                        new DataPoint(117, Double.parseDouble(dataSnapshot.child(String.valueOf(117)).child("day").getValue().toString())),
                        new DataPoint(118, Double.parseDouble(dataSnapshot.child(String.valueOf(118)).child("day").getValue().toString())),
                        new DataPoint(119, Double.parseDouble(dataSnapshot.child(String.valueOf(119)).child("day").getValue().toString())),
                        new DataPoint(120, Double.parseDouble(dataSnapshot.child(String.valueOf(120)).child("day").getValue().toString())),
                        new DataPoint(121, Double.parseDouble(dataSnapshot.child(String.valueOf(121)).child("day").getValue().toString())),
                        new DataPoint(122, Double.parseDouble(dataSnapshot.child(String.valueOf(122)).child("day").getValue().toString())),
                        new DataPoint(123, Double.parseDouble(dataSnapshot.child(String.valueOf(123)).child("day").getValue().toString())),
                        new DataPoint(124, Double.parseDouble(dataSnapshot.child(String.valueOf(124)).child("day").getValue().toString())),
                        new DataPoint(125, Double.parseDouble(dataSnapshot.child(String.valueOf(125)).child("day").getValue().toString())),
                        new DataPoint(126, Double.parseDouble(dataSnapshot.child(String.valueOf(126)).child("day").getValue().toString())),
                        new DataPoint(127, Double.parseDouble(dataSnapshot.child(String.valueOf(127)).child("day").getValue().toString())),
                        new DataPoint(128, Double.parseDouble(dataSnapshot.child(String.valueOf(128)).child("day").getValue().toString())),
                        new DataPoint(129, Double.parseDouble(dataSnapshot.child(String.valueOf(129)).child("day").getValue().toString())),
                        new DataPoint(130, Double.parseDouble(dataSnapshot.child(String.valueOf(130)).child("day").getValue().toString())),
                        new DataPoint(131, Double.parseDouble(dataSnapshot.child(String.valueOf(131)).child("day").getValue().toString())),
                        new DataPoint(132, Double.parseDouble(dataSnapshot.child(String.valueOf(132)).child("day").getValue().toString())),
                        new DataPoint(133, Double.parseDouble(dataSnapshot.child(String.valueOf(133)).child("day").getValue().toString())),
                        new DataPoint(134, Double.parseDouble(dataSnapshot.child(String.valueOf(134)).child("day").getValue().toString())),
                        new DataPoint(135, Double.parseDouble(dataSnapshot.child(String.valueOf(135)).child("day").getValue().toString())),
                        new DataPoint(136, Double.parseDouble(dataSnapshot.child(String.valueOf(136)).child("day").getValue().toString())),
                        new DataPoint(137, Double.parseDouble(dataSnapshot.child(String.valueOf(137)).child("day").getValue().toString())),
                        new DataPoint(138, Double.parseDouble(dataSnapshot.child(String.valueOf(138)).child("day").getValue().toString())),
                        new DataPoint(139, Double.parseDouble(dataSnapshot.child(String.valueOf(139)).child("day").getValue().toString())),
                        new DataPoint(140, Double.parseDouble(dataSnapshot.child(String.valueOf(140)).child("day").getValue().toString())),
                        new DataPoint(141, Double.parseDouble(dataSnapshot.child(String.valueOf(141)).child("day").getValue().toString())),
                        new DataPoint(142, Double.parseDouble(dataSnapshot.child(String.valueOf(142)).child("day").getValue().toString())),
                        new DataPoint(143, Double.parseDouble(dataSnapshot.child(String.valueOf(143)).child("day").getValue().toString())),
                        new DataPoint(144, Double.parseDouble(dataSnapshot.child(String.valueOf(144)).child("day").getValue().toString())),
                        new DataPoint(145, Double.parseDouble(dataSnapshot.child(String.valueOf(145)).child("day").getValue().toString())),
                        new DataPoint(146, Double.parseDouble(dataSnapshot.child(String.valueOf(146)).child("day").getValue().toString())),
                        new DataPoint(147, Double.parseDouble(dataSnapshot.child(String.valueOf(147)).child("day").getValue().toString())),
                        new DataPoint(148, Double.parseDouble(dataSnapshot.child(String.valueOf(148)).child("day").getValue().toString())),
                        new DataPoint(149, Double.parseDouble(dataSnapshot.child(String.valueOf(149)).child("day").getValue().toString())),
                        new DataPoint(150, Double.parseDouble(dataSnapshot.child(String.valueOf(150)).child("day").getValue().toString())),
                        new DataPoint(151, Double.parseDouble(dataSnapshot.child(String.valueOf(151)).child("day").getValue().toString())),
                        new DataPoint(152, Double.parseDouble(dataSnapshot.child(String.valueOf(152)).child("day").getValue().toString())),
                        new DataPoint(153, Double.parseDouble(dataSnapshot.child(String.valueOf(153)).child("day").getValue().toString())),
                        new DataPoint(154, Double.parseDouble(dataSnapshot.child(String.valueOf(154)).child("day").getValue().toString())),
                        new DataPoint(155, Double.parseDouble(dataSnapshot.child(String.valueOf(155)).child("day").getValue().toString())),
                        new DataPoint(156, Double.parseDouble(dataSnapshot.child(String.valueOf(156)).child("day").getValue().toString())),
                        new DataPoint(157, Double.parseDouble(dataSnapshot.child(String.valueOf(157)).child("day").getValue().toString())),
                        new DataPoint(158, Double.parseDouble(dataSnapshot.child(String.valueOf(158)).child("day").getValue().toString())),
                        new DataPoint(159, Double.parseDouble(dataSnapshot.child(String.valueOf(159)).child("day").getValue().toString())),
                        new DataPoint(160, Double.parseDouble(dataSnapshot.child(String.valueOf(160)).child("day").getValue().toString())),
                        new DataPoint(161, Double.parseDouble(dataSnapshot.child(String.valueOf(161)).child("day").getValue().toString())),
                        new DataPoint(162, Double.parseDouble(dataSnapshot.child(String.valueOf(162)).child("day").getValue().toString())),
                        new DataPoint(163, Double.parseDouble(dataSnapshot.child(String.valueOf(163)).child("day").getValue().toString())),
                        new DataPoint(164, Double.parseDouble(dataSnapshot.child(String.valueOf(164)).child("day").getValue().toString())),
                        new DataPoint(165, Double.parseDouble(dataSnapshot.child(String.valueOf(165)).child("day").getValue().toString())),
                        new DataPoint(166, Double.parseDouble(dataSnapshot.child(String.valueOf(166)).child("day").getValue().toString())),
                        new DataPoint(167, Double.parseDouble(dataSnapshot.child(String.valueOf(167)).child("day").getValue().toString())),
                        new DataPoint(168, Double.parseDouble(dataSnapshot.child(String.valueOf(168)).child("day").getValue().toString())),
                        new DataPoint(169, Double.parseDouble(dataSnapshot.child(String.valueOf(169)).child("day").getValue().toString())),
                        new DataPoint(170, Double.parseDouble(dataSnapshot.child(String.valueOf(170)).child("day").getValue().toString())),
                        new DataPoint(171, Double.parseDouble(dataSnapshot.child(String.valueOf(171)).child("day").getValue().toString())),
                        new DataPoint(172, Double.parseDouble(dataSnapshot.child(String.valueOf(172)).child("day").getValue().toString())),
                        new DataPoint(173, Double.parseDouble(dataSnapshot.child(String.valueOf(173)).child("day").getValue().toString())),
                        new DataPoint(174, Double.parseDouble(dataSnapshot.child(String.valueOf(174)).child("day").getValue().toString())),
                        new DataPoint(175, Double.parseDouble(dataSnapshot.child(String.valueOf(175)).child("day").getValue().toString())),
                        new DataPoint(176, Double.parseDouble(dataSnapshot.child(String.valueOf(176)).child("day").getValue().toString())),
                        new DataPoint(177, Double.parseDouble(dataSnapshot.child(String.valueOf(177)).child("day").getValue().toString())),
                        new DataPoint(178, Double.parseDouble(dataSnapshot.child(String.valueOf(178)).child("day").getValue().toString())),
                        new DataPoint(179, Double.parseDouble(dataSnapshot.child(String.valueOf(179)).child("day").getValue().toString())),
                        new DataPoint(180, Double.parseDouble(dataSnapshot.child(String.valueOf(180)).child("day").getValue().toString())),
                        new DataPoint(181, Double.parseDouble(dataSnapshot.child(String.valueOf(181)).child("day").getValue().toString())),
                        new DataPoint(182, Double.parseDouble(dataSnapshot.child(String.valueOf(182)).child("day").getValue().toString())),
                        new DataPoint(183, Double.parseDouble(dataSnapshot.child(String.valueOf(183)).child("day").getValue().toString())),
                        new DataPoint(184, Double.parseDouble(dataSnapshot.child(String.valueOf(184)).child("day").getValue().toString())),
                        new DataPoint(185, Double.parseDouble(dataSnapshot.child(String.valueOf(185)).child("day").getValue().toString())),
                        new DataPoint(186, Double.parseDouble(dataSnapshot.child(String.valueOf(186)).child("day").getValue().toString())),
                        new DataPoint(187, Double.parseDouble(dataSnapshot.child(String.valueOf(187)).child("day").getValue().toString())),
                        new DataPoint(188, Double.parseDouble(dataSnapshot.child(String.valueOf(188)).child("day").getValue().toString())),
                        new DataPoint(189, Double.parseDouble(dataSnapshot.child(String.valueOf(189)).child("day").getValue().toString())),
                        new DataPoint(190, Double.parseDouble(dataSnapshot.child(String.valueOf(190)).child("day").getValue().toString())),
                        new DataPoint(191, Double.parseDouble(dataSnapshot.child(String.valueOf(191)).child("day").getValue().toString())),
                        new DataPoint(192, Double.parseDouble(dataSnapshot.child(String.valueOf(192)).child("day").getValue().toString())),
                        new DataPoint(193, Double.parseDouble(dataSnapshot.child(String.valueOf(193)).child("day").getValue().toString())),
                        new DataPoint(194, Double.parseDouble(dataSnapshot.child(String.valueOf(194)).child("day").getValue().toString())),
                        new DataPoint(195, Double.parseDouble(dataSnapshot.child(String.valueOf(195)).child("day").getValue().toString())),
                        new DataPoint(196, Double.parseDouble(dataSnapshot.child(String.valueOf(196)).child("day").getValue().toString())),
                        new DataPoint(197, Double.parseDouble(dataSnapshot.child(String.valueOf(197)).child("day").getValue().toString())),
                        new DataPoint(198, Double.parseDouble(dataSnapshot.child(String.valueOf(198)).child("day").getValue().toString())),
                        new DataPoint(199, Double.parseDouble(dataSnapshot.child(String.valueOf(199)).child("day").getValue().toString())),
                        new DataPoint(200, Double.parseDouble(dataSnapshot.child(String.valueOf(200)).child("day").getValue().toString())),
                        new DataPoint(201, Double.parseDouble(dataSnapshot.child(String.valueOf(201)).child("day").getValue().toString())),
                        new DataPoint(202, Double.parseDouble(dataSnapshot.child(String.valueOf(202)).child("day").getValue().toString())),
                        new DataPoint(203, Double.parseDouble(dataSnapshot.child(String.valueOf(203)).child("day").getValue().toString())),
                        new DataPoint(204, Double.parseDouble(dataSnapshot.child(String.valueOf(204)).child("day").getValue().toString())),
                        new DataPoint(205, Double.parseDouble(dataSnapshot.child(String.valueOf(205)).child("day").getValue().toString())),
                        new DataPoint(206, Double.parseDouble(dataSnapshot.child(String.valueOf(206)).child("day").getValue().toString())),
                        new DataPoint(207, Double.parseDouble(dataSnapshot.child(String.valueOf(207)).child("day").getValue().toString())),
                        new DataPoint(208, Double.parseDouble(dataSnapshot.child(String.valueOf(208)).child("day").getValue().toString())),
                        new DataPoint(209, Double.parseDouble(dataSnapshot.child(String.valueOf(209)).child("day").getValue().toString())),
                        new DataPoint(210, Double.parseDouble(dataSnapshot.child(String.valueOf(210)).child("day").getValue().toString())),
                        new DataPoint(211, Double.parseDouble(dataSnapshot.child(String.valueOf(211)).child("day").getValue().toString())),
                        new DataPoint(212, Double.parseDouble(dataSnapshot.child(String.valueOf(212)).child("day").getValue().toString())),
                        new DataPoint(213, Double.parseDouble(dataSnapshot.child(String.valueOf(213)).child("day").getValue().toString())),
                        new DataPoint(214, Double.parseDouble(dataSnapshot.child(String.valueOf(214)).child("day").getValue().toString())),
                        new DataPoint(215, Double.parseDouble(dataSnapshot.child(String.valueOf(215)).child("day").getValue().toString())),
                        new DataPoint(216, Double.parseDouble(dataSnapshot.child(String.valueOf(216)).child("day").getValue().toString())),
                        new DataPoint(217, Double.parseDouble(dataSnapshot.child(String.valueOf(217)).child("day").getValue().toString())),
                        new DataPoint(218, Double.parseDouble(dataSnapshot.child(String.valueOf(218)).child("day").getValue().toString())),
                        new DataPoint(219, Double.parseDouble(dataSnapshot.child(String.valueOf(219)).child("day").getValue().toString())),
                        new DataPoint(220, Double.parseDouble(dataSnapshot.child(String.valueOf(220)).child("day").getValue().toString())),
                        new DataPoint(221, Double.parseDouble(dataSnapshot.child(String.valueOf(221)).child("day").getValue().toString())),
                        new DataPoint(222, Double.parseDouble(dataSnapshot.child(String.valueOf(222)).child("day").getValue().toString())),
                        new DataPoint(223, Double.parseDouble(dataSnapshot.child(String.valueOf(223)).child("day").getValue().toString())),
                        new DataPoint(224, Double.parseDouble(dataSnapshot.child(String.valueOf(224)).child("day").getValue().toString())),
                        new DataPoint(225, Double.parseDouble(dataSnapshot.child(String.valueOf(225)).child("day").getValue().toString())),
                        new DataPoint(226, Double.parseDouble(dataSnapshot.child(String.valueOf(226)).child("day").getValue().toString())),
                        new DataPoint(227, Double.parseDouble(dataSnapshot.child(String.valueOf(227)).child("day").getValue().toString())),
                        new DataPoint(228, Double.parseDouble(dataSnapshot.child(String.valueOf(228)).child("day").getValue().toString())),
                        new DataPoint(229, Double.parseDouble(dataSnapshot.child(String.valueOf(229)).child("day").getValue().toString())),
                        new DataPoint(230, Double.parseDouble(dataSnapshot.child(String.valueOf(230)).child("day").getValue().toString())),
                        new DataPoint(231, Double.parseDouble(dataSnapshot.child(String.valueOf(231)).child("day").getValue().toString())),
                        new DataPoint(232, Double.parseDouble(dataSnapshot.child(String.valueOf(232)).child("day").getValue().toString())),
                        new DataPoint(233, Double.parseDouble(dataSnapshot.child(String.valueOf(233)).child("day").getValue().toString())),
                        new DataPoint(234, Double.parseDouble(dataSnapshot.child(String.valueOf(234)).child("day").getValue().toString())),
                        new DataPoint(235, Double.parseDouble(dataSnapshot.child(String.valueOf(235)).child("day").getValue().toString())),
                        new DataPoint(236, Double.parseDouble(dataSnapshot.child(String.valueOf(236)).child("day").getValue().toString())),
                        new DataPoint(237, Double.parseDouble(dataSnapshot.child(String.valueOf(237)).child("day").getValue().toString())),
                        new DataPoint(238, Double.parseDouble(dataSnapshot.child(String.valueOf(238)).child("day").getValue().toString())),
                        new DataPoint(239, Double.parseDouble(dataSnapshot.child(String.valueOf(239)).child("day").getValue().toString())),
                        new DataPoint(240, Double.parseDouble(dataSnapshot.child(String.valueOf(240)).child("day").getValue().toString())),
                        new DataPoint(241, Double.parseDouble(dataSnapshot.child(String.valueOf(241)).child("day").getValue().toString())),
                        new DataPoint(242, Double.parseDouble(dataSnapshot.child(String.valueOf(242)).child("day").getValue().toString())),
                        new DataPoint(243, Double.parseDouble(dataSnapshot.child(String.valueOf(243)).child("day").getValue().toString())),
                        new DataPoint(244, Double.parseDouble(dataSnapshot.child(String.valueOf(244)).child("day").getValue().toString())),
                        new DataPoint(245, Double.parseDouble(dataSnapshot.child(String.valueOf(245)).child("day").getValue().toString())),
                        new DataPoint(246, Double.parseDouble(dataSnapshot.child(String.valueOf(246)).child("day").getValue().toString())),
                        new DataPoint(247, Double.parseDouble(dataSnapshot.child(String.valueOf(247)).child("day").getValue().toString())),
                        new DataPoint(248, Double.parseDouble(dataSnapshot.child(String.valueOf(248)).child("day").getValue().toString())),
                        new DataPoint(249, Double.parseDouble(dataSnapshot.child(String.valueOf(249)).child("day").getValue().toString())),
                        new DataPoint(250, Double.parseDouble(dataSnapshot.child(String.valueOf(250)).child("day").getValue().toString())),
                        new DataPoint(251, Double.parseDouble(dataSnapshot.child(String.valueOf(251)).child("day").getValue().toString())),
                        new DataPoint(252, Double.parseDouble(dataSnapshot.child(String.valueOf(252)).child("day").getValue().toString())),
                        new DataPoint(253, Double.parseDouble(dataSnapshot.child(String.valueOf(253)).child("day").getValue().toString())),
                        new DataPoint(254, Double.parseDouble(dataSnapshot.child(String.valueOf(254)).child("day").getValue().toString())),
                        new DataPoint(255, Double.parseDouble(dataSnapshot.child(String.valueOf(255)).child("day").getValue().toString())),
                        new DataPoint(256, Double.parseDouble(dataSnapshot.child(String.valueOf(256)).child("day").getValue().toString())),
                        new DataPoint(257, Double.parseDouble(dataSnapshot.child(String.valueOf(257)).child("day").getValue().toString())),
                        new DataPoint(258, Double.parseDouble(dataSnapshot.child(String.valueOf(258)).child("day").getValue().toString())),
                        new DataPoint(259, Double.parseDouble(dataSnapshot.child(String.valueOf(259)).child("day").getValue().toString())),
                        new DataPoint(260, Double.parseDouble(dataSnapshot.child(String.valueOf(260)).child("day").getValue().toString())),
                        new DataPoint(261, Double.parseDouble(dataSnapshot.child(String.valueOf(261)).child("day").getValue().toString())),
                        new DataPoint(262, Double.parseDouble(dataSnapshot.child(String.valueOf(262)).child("day").getValue().toString())),
                        new DataPoint(263, Double.parseDouble(dataSnapshot.child(String.valueOf(263)).child("day").getValue().toString())),
                        new DataPoint(264, Double.parseDouble(dataSnapshot.child(String.valueOf(264)).child("day").getValue().toString())),
                        new DataPoint(265, Double.parseDouble(dataSnapshot.child(String.valueOf(265)).child("day").getValue().toString())),
                        new DataPoint(266, Double.parseDouble(dataSnapshot.child(String.valueOf(266)).child("day").getValue().toString())),
                        new DataPoint(267, Double.parseDouble(dataSnapshot.child(String.valueOf(267)).child("day").getValue().toString())),
                        new DataPoint(268, Double.parseDouble(dataSnapshot.child(String.valueOf(268)).child("day").getValue().toString())),
                        new DataPoint(269, Double.parseDouble(dataSnapshot.child(String.valueOf(269)).child("day").getValue().toString())),
                        new DataPoint(270, Double.parseDouble(dataSnapshot.child(String.valueOf(270)).child("day").getValue().toString())),
                        new DataPoint(271, Double.parseDouble(dataSnapshot.child(String.valueOf(271)).child("day").getValue().toString())),
                        new DataPoint(272, Double.parseDouble(dataSnapshot.child(String.valueOf(272)).child("day").getValue().toString())),
                        new DataPoint(273, Double.parseDouble(dataSnapshot.child(String.valueOf(273)).child("day").getValue().toString())),
                        new DataPoint(274, Double.parseDouble(dataSnapshot.child(String.valueOf(274)).child("day").getValue().toString())),
                        new DataPoint(275, Double.parseDouble(dataSnapshot.child(String.valueOf(275)).child("day").getValue().toString())),
                        new DataPoint(276, Double.parseDouble(dataSnapshot.child(String.valueOf(276)).child("day").getValue().toString())),
                        new DataPoint(277, Double.parseDouble(dataSnapshot.child(String.valueOf(277)).child("day").getValue().toString())),
                        new DataPoint(278, Double.parseDouble(dataSnapshot.child(String.valueOf(278)).child("day").getValue().toString())),
                        new DataPoint(279, Double.parseDouble(dataSnapshot.child(String.valueOf(279)).child("day").getValue().toString())),
                        new DataPoint(280, Double.parseDouble(dataSnapshot.child(String.valueOf(280)).child("day").getValue().toString())),
                        new DataPoint(281, Double.parseDouble(dataSnapshot.child(String.valueOf(281)).child("day").getValue().toString())),
                        new DataPoint(282, Double.parseDouble(dataSnapshot.child(String.valueOf(282)).child("day").getValue().toString())),
                        new DataPoint(283, Double.parseDouble(dataSnapshot.child(String.valueOf(283)).child("day").getValue().toString())),
                        new DataPoint(284, Double.parseDouble(dataSnapshot.child(String.valueOf(284)).child("day").getValue().toString())),
                        new DataPoint(285, Double.parseDouble(dataSnapshot.child(String.valueOf(285)).child("day").getValue().toString())),
                        new DataPoint(286, Double.parseDouble(dataSnapshot.child(String.valueOf(286)).child("day").getValue().toString())),
                        new DataPoint(287, Double.parseDouble(dataSnapshot.child(String.valueOf(287)).child("day").getValue().toString())),
                        new DataPoint(288, Double.parseDouble(dataSnapshot.child(String.valueOf(288)).child("day").getValue().toString())),
                        new DataPoint(289, Double.parseDouble(dataSnapshot.child(String.valueOf(289)).child("day").getValue().toString())),
                        new DataPoint(290, Double.parseDouble(dataSnapshot.child(String.valueOf(290)).child("day").getValue().toString())),
                        new DataPoint(291, Double.parseDouble(dataSnapshot.child(String.valueOf(291)).child("day").getValue().toString())),
                        new DataPoint(292, Double.parseDouble(dataSnapshot.child(String.valueOf(292)).child("day").getValue().toString())),
                        new DataPoint(293, Double.parseDouble(dataSnapshot.child(String.valueOf(293)).child("day").getValue().toString())),
                        new DataPoint(294, Double.parseDouble(dataSnapshot.child(String.valueOf(294)).child("day").getValue().toString())),
                        new DataPoint(295, Double.parseDouble(dataSnapshot.child(String.valueOf(295)).child("day").getValue().toString())),
                        new DataPoint(296, Double.parseDouble(dataSnapshot.child(String.valueOf(296)).child("day").getValue().toString())),
                        new DataPoint(297, Double.parseDouble(dataSnapshot.child(String.valueOf(297)).child("day").getValue().toString())),
                        new DataPoint(298, Double.parseDouble(dataSnapshot.child(String.valueOf(298)).child("day").getValue().toString())),
                        new DataPoint(299, Double.parseDouble(dataSnapshot.child(String.valueOf(299)).child("day").getValue().toString())),
                        new DataPoint(300, Double.parseDouble(dataSnapshot.child(String.valueOf(300)).child("day").getValue().toString())),
                        new DataPoint(301, Double.parseDouble(dataSnapshot.child(String.valueOf(301)).child("day").getValue().toString())),
                        new DataPoint(302, Double.parseDouble(dataSnapshot.child(String.valueOf(302)).child("day").getValue().toString())),
                        new DataPoint(303, Double.parseDouble(dataSnapshot.child(String.valueOf(303)).child("day").getValue().toString())),
                        new DataPoint(304, Double.parseDouble(dataSnapshot.child(String.valueOf(304)).child("day").getValue().toString())),
                        new DataPoint(305, Double.parseDouble(dataSnapshot.child(String.valueOf(305)).child("day").getValue().toString())),
                        new DataPoint(306, Double.parseDouble(dataSnapshot.child(String.valueOf(306)).child("day").getValue().toString())),
                        new DataPoint(307, Double.parseDouble(dataSnapshot.child(String.valueOf(307)).child("day").getValue().toString())),
                        new DataPoint(308, Double.parseDouble(dataSnapshot.child(String.valueOf(308)).child("day").getValue().toString())),
                        new DataPoint(309, Double.parseDouble(dataSnapshot.child(String.valueOf(309)).child("day").getValue().toString())),
                        new DataPoint(310, Double.parseDouble(dataSnapshot.child(String.valueOf(310)).child("day").getValue().toString())),
                        new DataPoint(311, Double.parseDouble(dataSnapshot.child(String.valueOf(311)).child("day").getValue().toString())),
                        new DataPoint(312, Double.parseDouble(dataSnapshot.child(String.valueOf(312)).child("day").getValue().toString())),
                        new DataPoint(313, Double.parseDouble(dataSnapshot.child(String.valueOf(313)).child("day").getValue().toString())),
                        new DataPoint(314, Double.parseDouble(dataSnapshot.child(String.valueOf(314)).child("day").getValue().toString())),
                        new DataPoint(315, Double.parseDouble(dataSnapshot.child(String.valueOf(315)).child("day").getValue().toString())),
                        new DataPoint(316, Double.parseDouble(dataSnapshot.child(String.valueOf(316)).child("day").getValue().toString())),
                        new DataPoint(317, Double.parseDouble(dataSnapshot.child(String.valueOf(317)).child("day").getValue().toString())),
                        new DataPoint(318, Double.parseDouble(dataSnapshot.child(String.valueOf(318)).child("day").getValue().toString())),
                        new DataPoint(319, Double.parseDouble(dataSnapshot.child(String.valueOf(319)).child("day").getValue().toString())),
                        new DataPoint(320, Double.parseDouble(dataSnapshot.child(String.valueOf(320)).child("day").getValue().toString())),
                        new DataPoint(321, Double.parseDouble(dataSnapshot.child(String.valueOf(321)).child("day").getValue().toString())),
                        new DataPoint(322, Double.parseDouble(dataSnapshot.child(String.valueOf(322)).child("day").getValue().toString())),
                        new DataPoint(323, Double.parseDouble(dataSnapshot.child(String.valueOf(323)).child("day").getValue().toString())),
                        new DataPoint(324, Double.parseDouble(dataSnapshot.child(String.valueOf(324)).child("day").getValue().toString())),
                        new DataPoint(325, Double.parseDouble(dataSnapshot.child(String.valueOf(325)).child("day").getValue().toString())),
                        new DataPoint(326, Double.parseDouble(dataSnapshot.child(String.valueOf(326)).child("day").getValue().toString())),
                        new DataPoint(327, Double.parseDouble(dataSnapshot.child(String.valueOf(327)).child("day").getValue().toString())),
                        new DataPoint(328, Double.parseDouble(dataSnapshot.child(String.valueOf(328)).child("day").getValue().toString())),
                        new DataPoint(329, Double.parseDouble(dataSnapshot.child(String.valueOf(329)).child("day").getValue().toString())),
                        new DataPoint(330, Double.parseDouble(dataSnapshot.child(String.valueOf(330)).child("day").getValue().toString())),
                        new DataPoint(331, Double.parseDouble(dataSnapshot.child(String.valueOf(331)).child("day").getValue().toString())),
                        new DataPoint(332, Double.parseDouble(dataSnapshot.child(String.valueOf(332)).child("day").getValue().toString())),
                        new DataPoint(333, Double.parseDouble(dataSnapshot.child(String.valueOf(333)).child("day").getValue().toString())),
                        new DataPoint(334, Double.parseDouble(dataSnapshot.child(String.valueOf(334)).child("day").getValue().toString())),
                        new DataPoint(335, Double.parseDouble(dataSnapshot.child(String.valueOf(335)).child("day").getValue().toString())),
                        new DataPoint(336, Double.parseDouble(dataSnapshot.child(String.valueOf(336)).child("day").getValue().toString())),
                        new DataPoint(337, Double.parseDouble(dataSnapshot.child(String.valueOf(337)).child("day").getValue().toString())),
                        new DataPoint(338, Double.parseDouble(dataSnapshot.child(String.valueOf(338)).child("day").getValue().toString())),
                        new DataPoint(339, Double.parseDouble(dataSnapshot.child(String.valueOf(339)).child("day").getValue().toString())),
                        new DataPoint(340, Double.parseDouble(dataSnapshot.child(String.valueOf(340)).child("day").getValue().toString())),
                        new DataPoint(341, Double.parseDouble(dataSnapshot.child(String.valueOf(341)).child("day").getValue().toString())),
                        new DataPoint(342, Double.parseDouble(dataSnapshot.child(String.valueOf(342)).child("day").getValue().toString())),
                        new DataPoint(343, Double.parseDouble(dataSnapshot.child(String.valueOf(343)).child("day").getValue().toString())),
                        new DataPoint(344, Double.parseDouble(dataSnapshot.child(String.valueOf(344)).child("day").getValue().toString())),
                        new DataPoint(345, Double.parseDouble(dataSnapshot.child(String.valueOf(345)).child("day").getValue().toString())),
                        new DataPoint(346, Double.parseDouble(dataSnapshot.child(String.valueOf(346)).child("day").getValue().toString())),
                        new DataPoint(347, Double.parseDouble(dataSnapshot.child(String.valueOf(347)).child("day").getValue().toString())),
                        new DataPoint(348, Double.parseDouble(dataSnapshot.child(String.valueOf(348)).child("day").getValue().toString())),
                        new DataPoint(349, Double.parseDouble(dataSnapshot.child(String.valueOf(349)).child("day").getValue().toString())),
                        new DataPoint(350, Double.parseDouble(dataSnapshot.child(String.valueOf(350)).child("day").getValue().toString())),
                        new DataPoint(351, Double.parseDouble(dataSnapshot.child(String.valueOf(351)).child("day").getValue().toString())),
                        new DataPoint(352, Double.parseDouble(dataSnapshot.child(String.valueOf(352)).child("day").getValue().toString())),
                        new DataPoint(353, Double.parseDouble(dataSnapshot.child(String.valueOf(353)).child("day").getValue().toString())),
                        new DataPoint(354, Double.parseDouble(dataSnapshot.child(String.valueOf(354)).child("day").getValue().toString())),
                        new DataPoint(355, Double.parseDouble(dataSnapshot.child(String.valueOf(355)).child("day").getValue().toString())),
                        new DataPoint(356, Double.parseDouble(dataSnapshot.child(String.valueOf(356)).child("day").getValue().toString())),
                        new DataPoint(357, Double.parseDouble(dataSnapshot.child(String.valueOf(357)).child("day").getValue().toString())),
                        new DataPoint(358, Double.parseDouble(dataSnapshot.child(String.valueOf(358)).child("day").getValue().toString())),
                        new DataPoint(359, Double.parseDouble(dataSnapshot.child(String.valueOf(359)).child("day").getValue().toString())),
                        new DataPoint(360, Double.parseDouble(dataSnapshot.child(String.valueOf(360)).child("day").getValue().toString())),
                        new DataPoint(361, Double.parseDouble(dataSnapshot.child(String.valueOf(361)).child("day").getValue().toString())),
                        new DataPoint(362, Double.parseDouble(dataSnapshot.child(String.valueOf(362)).child("day").getValue().toString())),
                        new DataPoint(363, Double.parseDouble(dataSnapshot.child(String.valueOf(363)).child("day").getValue().toString())),
                        new DataPoint(364, Double.parseDouble(dataSnapshot.child(String.valueOf(364)).child("day").getValue().toString())),
                        new DataPoint(365, Double.parseDouble(dataSnapshot.child(String.valueOf(365)).child("day").getValue().toString())),

                });


                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {

                        String msg = "X:" +  dataPoint.getX() + "\nY:" + dataPoint.getY();
                        Log.d("XVAL", "XVAL" + new Date((long) dataPoint.getX()));
                        Toast.makeText(GasEnergyYear.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


                //series2.setColor(Color.RED);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setColor(Color.rgb(128, 84, 38));
                series.setDrawBackground(true);
                series.setBackgroundColor(Color.argb(60, 223, 187, 149));

                graphView.setTitle("Gas Energy Year Ahead");


                graphView.getViewport().setScrollable(true); // enables horizontal scrolling
                graphView.getViewport().setScrollableY(true); // enables vertical scrolling
                graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
                graphView.getViewport().setScalableY(true); // enables vertical zooming and scrolling

                // graphView.getGridLabelRenderer().setNumHorizontalLabels(10);

                graphView.getViewport().setMinX(0);
                graphView.getViewport().setMaxX(365);
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
                    //Format formatter = new SimpleDateFormat("HH:mm");
                    //return formatter.format(val);
                    return String.valueOf(val);
                } else {
                    return  super.formatLabel(val, isValueX) + "kWh ";
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
                    case R.id.chartGas:
                        return true;
                    case R.id.chartWind:
                        startActivity(new Intent(getApplicationContext(), wind_energie_chart.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chartNuclear:
                        startActivity(new Intent(getApplicationContext(), nuclear_energie_chart.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chartAll:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chartZon:
                        startActivity(new Intent(getApplicationContext(), water_energie_chart.class));
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
