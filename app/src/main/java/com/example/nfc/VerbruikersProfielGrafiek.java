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

public class VerbruikersProfielGrafiek extends AppCompatActivity {

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
    Button koopEnergy;
    List<String> yList = new ArrayList<String>();
    Button current,week,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbruikers_profiel_grafiek);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        graphView = (GraphView) findViewById(R.id.graphview);






                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                        new DataPoint(VerbruikersProfiel.uploadData.get(0).getX(), VerbruikersProfiel.uploadData.get(0).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(1).getX(), VerbruikersProfiel.uploadData.get(1).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(2).getX(), VerbruikersProfiel.uploadData.get(2).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(3).getX(), VerbruikersProfiel.uploadData.get(3).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(4).getX(), VerbruikersProfiel.uploadData.get(4).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(5).getX(), VerbruikersProfiel.uploadData.get(5).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(6).getX(), VerbruikersProfiel.uploadData.get(6).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(7).getX(), VerbruikersProfiel.uploadData.get(7).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(8).getX(), VerbruikersProfiel.uploadData.get(8).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(9).getX(), VerbruikersProfiel.uploadData.get(9).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(10).getX(), VerbruikersProfiel.uploadData.get(10).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(11).getX(), VerbruikersProfiel.uploadData.get(11).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(12).getX(), VerbruikersProfiel.uploadData.get(12).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(13).getX(), VerbruikersProfiel.uploadData.get(13).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(14).getX(), VerbruikersProfiel.uploadData.get(14).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(15).getX(), VerbruikersProfiel.uploadData.get(15).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(16).getX(), VerbruikersProfiel.uploadData.get(16).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(17).getX(), VerbruikersProfiel.uploadData.get(17).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(18).getX(), VerbruikersProfiel.uploadData.get(18).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(19).getX(), VerbruikersProfiel.uploadData.get(19).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(20).getX(), VerbruikersProfiel.uploadData.get(20).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(21).getX(), VerbruikersProfiel.uploadData.get(21).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(22).getX(), VerbruikersProfiel.uploadData.get(22).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(23).getX(), VerbruikersProfiel.uploadData.get(23).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(24).getX(), VerbruikersProfiel.uploadData.get(24).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(25).getX(), VerbruikersProfiel.uploadData.get(25).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(26).getX(), VerbruikersProfiel.uploadData.get(26).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(27).getX(), VerbruikersProfiel.uploadData.get(27).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(28).getX(), VerbruikersProfiel.uploadData.get(28).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(29).getX(), VerbruikersProfiel.uploadData.get(29).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(30).getX(), VerbruikersProfiel.uploadData.get(30).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(31).getX(), VerbruikersProfiel.uploadData.get(31).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(32).getX(), VerbruikersProfiel.uploadData.get(32).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(33).getX(), VerbruikersProfiel.uploadData.get(33).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(34).getX(), VerbruikersProfiel.uploadData.get(34).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(35).getX(), VerbruikersProfiel.uploadData.get(35).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(36).getX(), VerbruikersProfiel.uploadData.get(36).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(37).getX(), VerbruikersProfiel.uploadData.get(37).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(38).getX(), VerbruikersProfiel.uploadData.get(38).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(39).getX(), VerbruikersProfiel.uploadData.get(39).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(40).getX(), VerbruikersProfiel.uploadData.get(40).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(41).getX(), VerbruikersProfiel.uploadData.get(41).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(42).getX(), VerbruikersProfiel.uploadData.get(42).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(43).getX(), VerbruikersProfiel.uploadData.get(43).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(44).getX(), VerbruikersProfiel.uploadData.get(44).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(45).getX(), VerbruikersProfiel.uploadData.get(45).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(46).getX(), VerbruikersProfiel.uploadData.get(46).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(47).getX(), VerbruikersProfiel.uploadData.get(47).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(48).getX(), VerbruikersProfiel.uploadData.get(48).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(49).getX(), VerbruikersProfiel.uploadData.get(49).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(50).getX(), VerbruikersProfiel.uploadData.get(50).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(51).getX(), VerbruikersProfiel.uploadData.get(51).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(52).getX(), VerbruikersProfiel.uploadData.get(52).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(53).getX(), VerbruikersProfiel.uploadData.get(53).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(54).getX(), VerbruikersProfiel.uploadData.get(54).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(55).getX(), VerbruikersProfiel.uploadData.get(55).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(56).getX(), VerbruikersProfiel.uploadData.get(56).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(57).getX(), VerbruikersProfiel.uploadData.get(57).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(58).getX(), VerbruikersProfiel.uploadData.get(58).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(59).getX(), VerbruikersProfiel.uploadData.get(59).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(60).getX(), VerbruikersProfiel.uploadData.get(60).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(61).getX(), VerbruikersProfiel.uploadData.get(61).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(62).getX(), VerbruikersProfiel.uploadData.get(62).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(63).getX(), VerbruikersProfiel.uploadData.get(63).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(64).getX(), VerbruikersProfiel.uploadData.get(64).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(65).getX(), VerbruikersProfiel.uploadData.get(65).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(66).getX(), VerbruikersProfiel.uploadData.get(66).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(67).getX(), VerbruikersProfiel.uploadData.get(67).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(68).getX(), VerbruikersProfiel.uploadData.get(68).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(69).getX(), VerbruikersProfiel.uploadData.get(69).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(70).getX(), VerbruikersProfiel.uploadData.get(70).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(71).getX(), VerbruikersProfiel.uploadData.get(71).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(72).getX(), VerbruikersProfiel.uploadData.get(72).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(73).getX(), VerbruikersProfiel.uploadData.get(73).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(74).getX(), VerbruikersProfiel.uploadData.get(74).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(75).getX(), VerbruikersProfiel.uploadData.get(75).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(76).getX(), VerbruikersProfiel.uploadData.get(76).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(77).getX(), VerbruikersProfiel.uploadData.get(77).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(78).getX(), VerbruikersProfiel.uploadData.get(78).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(79).getX(), VerbruikersProfiel.uploadData.get(79).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(80).getX(), VerbruikersProfiel.uploadData.get(80).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(81).getX(), VerbruikersProfiel.uploadData.get(81).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(82).getX(), VerbruikersProfiel.uploadData.get(82).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(83).getX(), VerbruikersProfiel.uploadData.get(83).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(84).getX(), VerbruikersProfiel.uploadData.get(84).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(85).getX(), VerbruikersProfiel.uploadData.get(85).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(86).getX(), VerbruikersProfiel.uploadData.get(86).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(87).getX(), VerbruikersProfiel.uploadData.get(87).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(88).getX(), VerbruikersProfiel.uploadData.get(88).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(89).getX(), VerbruikersProfiel.uploadData.get(89).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(90).getX(), VerbruikersProfiel.uploadData.get(90).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(91).getX(), VerbruikersProfiel.uploadData.get(91).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(92).getX(), VerbruikersProfiel.uploadData.get(92).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(93).getX(), VerbruikersProfiel.uploadData.get(93).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(94).getX(), VerbruikersProfiel.uploadData.get(94).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(95).getX(), VerbruikersProfiel.uploadData.get(95).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(96).getX(), VerbruikersProfiel.uploadData.get(96).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(97).getX(), VerbruikersProfiel.uploadData.get(97).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(98).getX(), VerbruikersProfiel.uploadData.get(98).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(99).getX(), VerbruikersProfiel.uploadData.get(99).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(100).getX(), VerbruikersProfiel.uploadData.get(100).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(101).getX(), VerbruikersProfiel.uploadData.get(101).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(102).getX(), VerbruikersProfiel.uploadData.get(102).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(103).getX(), VerbruikersProfiel.uploadData.get(103).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(104).getX(), VerbruikersProfiel.uploadData.get(104).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(105).getX(), VerbruikersProfiel.uploadData.get(105).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(106).getX(), VerbruikersProfiel.uploadData.get(106).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(107).getX(), VerbruikersProfiel.uploadData.get(107).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(108).getX(), VerbruikersProfiel.uploadData.get(108).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(109).getX(), VerbruikersProfiel.uploadData.get(109).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(110).getX(), VerbruikersProfiel.uploadData.get(110).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(111).getX(), VerbruikersProfiel.uploadData.get(111).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(112).getX(), VerbruikersProfiel.uploadData.get(112).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(113).getX(), VerbruikersProfiel.uploadData.get(113).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(114).getX(), VerbruikersProfiel.uploadData.get(114).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(115).getX(), VerbruikersProfiel.uploadData.get(115).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(116).getX(), VerbruikersProfiel.uploadData.get(116).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(117).getX(), VerbruikersProfiel.uploadData.get(117).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(118).getX(), VerbruikersProfiel.uploadData.get(118).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(119).getX(), VerbruikersProfiel.uploadData.get(119).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(120).getX(), VerbruikersProfiel.uploadData.get(120).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(121).getX(), VerbruikersProfiel.uploadData.get(121).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(122).getX(), VerbruikersProfiel.uploadData.get(122).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(123).getX(), VerbruikersProfiel.uploadData.get(123).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(124).getX(), VerbruikersProfiel.uploadData.get(124).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(125).getX(), VerbruikersProfiel.uploadData.get(125).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(126).getX(), VerbruikersProfiel.uploadData.get(126).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(127).getX(), VerbruikersProfiel.uploadData.get(127).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(128).getX(), VerbruikersProfiel.uploadData.get(128).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(129).getX(), VerbruikersProfiel.uploadData.get(129).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(130).getX(), VerbruikersProfiel.uploadData.get(130).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(131).getX(), VerbruikersProfiel.uploadData.get(131).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(132).getX(), VerbruikersProfiel.uploadData.get(132).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(133).getX(), VerbruikersProfiel.uploadData.get(133).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(134).getX(), VerbruikersProfiel.uploadData.get(134).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(135).getX(), VerbruikersProfiel.uploadData.get(135).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(136).getX(), VerbruikersProfiel.uploadData.get(136).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(137).getX(), VerbruikersProfiel.uploadData.get(137).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(138).getX(), VerbruikersProfiel.uploadData.get(138).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(139).getX(), VerbruikersProfiel.uploadData.get(139).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(140).getX(), VerbruikersProfiel.uploadData.get(140).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(141).getX(), VerbruikersProfiel.uploadData.get(141).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(142).getX(), VerbruikersProfiel.uploadData.get(142).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(143).getX(), VerbruikersProfiel.uploadData.get(143).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(144).getX(), VerbruikersProfiel.uploadData.get(144).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(145).getX(), VerbruikersProfiel.uploadData.get(145).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(146).getX(), VerbruikersProfiel.uploadData.get(146).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(147).getX(), VerbruikersProfiel.uploadData.get(147).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(148).getX(), VerbruikersProfiel.uploadData.get(148).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(149).getX(), VerbruikersProfiel.uploadData.get(149).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(150).getX(), VerbruikersProfiel.uploadData.get(150).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(151).getX(), VerbruikersProfiel.uploadData.get(151).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(152).getX(), VerbruikersProfiel.uploadData.get(152).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(153).getX(), VerbruikersProfiel.uploadData.get(153).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(154).getX(), VerbruikersProfiel.uploadData.get(154).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(155).getX(), VerbruikersProfiel.uploadData.get(155).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(156).getX(), VerbruikersProfiel.uploadData.get(156).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(157).getX(), VerbruikersProfiel.uploadData.get(157).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(158).getX(), VerbruikersProfiel.uploadData.get(158).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(159).getX(), VerbruikersProfiel.uploadData.get(159).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(160).getX(), VerbruikersProfiel.uploadData.get(160).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(161).getX(), VerbruikersProfiel.uploadData.get(161).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(162).getX(), VerbruikersProfiel.uploadData.get(162).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(163).getX(), VerbruikersProfiel.uploadData.get(163).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(164).getX(), VerbruikersProfiel.uploadData.get(164).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(165).getX(), VerbruikersProfiel.uploadData.get(165).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(166).getX(), VerbruikersProfiel.uploadData.get(166).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(167).getX(), VerbruikersProfiel.uploadData.get(167).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(168).getX(), VerbruikersProfiel.uploadData.get(168).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(169).getX(), VerbruikersProfiel.uploadData.get(169).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(170).getX(), VerbruikersProfiel.uploadData.get(170).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(171).getX(), VerbruikersProfiel.uploadData.get(171).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(172).getX(), VerbruikersProfiel.uploadData.get(172).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(173).getX(), VerbruikersProfiel.uploadData.get(173).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(174).getX(), VerbruikersProfiel.uploadData.get(174).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(175).getX(), VerbruikersProfiel.uploadData.get(175).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(176).getX(), VerbruikersProfiel.uploadData.get(176).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(177).getX(), VerbruikersProfiel.uploadData.get(177).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(178).getX(), VerbruikersProfiel.uploadData.get(178).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(179).getX(), VerbruikersProfiel.uploadData.get(179).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(180).getX(), VerbruikersProfiel.uploadData.get(180).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(181).getX(), VerbruikersProfiel.uploadData.get(181).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(182).getX(), VerbruikersProfiel.uploadData.get(182).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(183).getX(), VerbruikersProfiel.uploadData.get(183).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(184).getX(), VerbruikersProfiel.uploadData.get(184).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(185).getX(), VerbruikersProfiel.uploadData.get(185).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(186).getX(), VerbruikersProfiel.uploadData.get(186).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(187).getX(), VerbruikersProfiel.uploadData.get(187).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(188).getX(), VerbruikersProfiel.uploadData.get(188).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(189).getX(), VerbruikersProfiel.uploadData.get(189).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(190).getX(), VerbruikersProfiel.uploadData.get(190).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(191).getX(), VerbruikersProfiel.uploadData.get(191).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(192).getX(), VerbruikersProfiel.uploadData.get(192).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(193).getX(), VerbruikersProfiel.uploadData.get(193).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(194).getX(), VerbruikersProfiel.uploadData.get(194).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(195).getX(), VerbruikersProfiel.uploadData.get(195).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(196).getX(), VerbruikersProfiel.uploadData.get(196).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(197).getX(), VerbruikersProfiel.uploadData.get(197).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(198).getX(), VerbruikersProfiel.uploadData.get(198).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(199).getX(), VerbruikersProfiel.uploadData.get(199).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(200).getX(), VerbruikersProfiel.uploadData.get(200).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(201).getX(), VerbruikersProfiel.uploadData.get(201).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(202).getX(), VerbruikersProfiel.uploadData.get(202).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(203).getX(), VerbruikersProfiel.uploadData.get(203).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(204).getX(), VerbruikersProfiel.uploadData.get(204).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(205).getX(), VerbruikersProfiel.uploadData.get(205).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(206).getX(), VerbruikersProfiel.uploadData.get(206).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(207).getX(), VerbruikersProfiel.uploadData.get(207).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(208).getX(), VerbruikersProfiel.uploadData.get(208).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(209).getX(), VerbruikersProfiel.uploadData.get(209).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(210).getX(), VerbruikersProfiel.uploadData.get(210).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(211).getX(), VerbruikersProfiel.uploadData.get(211).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(212).getX(), VerbruikersProfiel.uploadData.get(212).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(213).getX(), VerbruikersProfiel.uploadData.get(213).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(214).getX(), VerbruikersProfiel.uploadData.get(214).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(215).getX(), VerbruikersProfiel.uploadData.get(215).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(216).getX(), VerbruikersProfiel.uploadData.get(216).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(217).getX(), VerbruikersProfiel.uploadData.get(217).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(218).getX(), VerbruikersProfiel.uploadData.get(218).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(219).getX(), VerbruikersProfiel.uploadData.get(219).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(220).getX(), VerbruikersProfiel.uploadData.get(220).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(221).getX(), VerbruikersProfiel.uploadData.get(221).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(222).getX(), VerbruikersProfiel.uploadData.get(222).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(223).getX(), VerbruikersProfiel.uploadData.get(223).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(224).getX(), VerbruikersProfiel.uploadData.get(224).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(225).getX(), VerbruikersProfiel.uploadData.get(225).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(226).getX(), VerbruikersProfiel.uploadData.get(226).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(227).getX(), VerbruikersProfiel.uploadData.get(227).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(228).getX(), VerbruikersProfiel.uploadData.get(228).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(229).getX(), VerbruikersProfiel.uploadData.get(229).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(230).getX(), VerbruikersProfiel.uploadData.get(230).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(231).getX(), VerbruikersProfiel.uploadData.get(231).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(232).getX(), VerbruikersProfiel.uploadData.get(232).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(233).getX(), VerbruikersProfiel.uploadData.get(233).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(234).getX(), VerbruikersProfiel.uploadData.get(234).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(235).getX(), VerbruikersProfiel.uploadData.get(235).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(236).getX(), VerbruikersProfiel.uploadData.get(236).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(237).getX(), VerbruikersProfiel.uploadData.get(237).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(238).getX(), VerbruikersProfiel.uploadData.get(238).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(239).getX(), VerbruikersProfiel.uploadData.get(239).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(240).getX(), VerbruikersProfiel.uploadData.get(240).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(241).getX(), VerbruikersProfiel.uploadData.get(241).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(242).getX(), VerbruikersProfiel.uploadData.get(242).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(243).getX(), VerbruikersProfiel.uploadData.get(243).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(244).getX(), VerbruikersProfiel.uploadData.get(244).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(245).getX(), VerbruikersProfiel.uploadData.get(245).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(246).getX(), VerbruikersProfiel.uploadData.get(246).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(247).getX(), VerbruikersProfiel.uploadData.get(247).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(248).getX(), VerbruikersProfiel.uploadData.get(248).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(249).getX(), VerbruikersProfiel.uploadData.get(249).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(250).getX(), VerbruikersProfiel.uploadData.get(250).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(251).getX(), VerbruikersProfiel.uploadData.get(251).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(252).getX(), VerbruikersProfiel.uploadData.get(252).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(253).getX(), VerbruikersProfiel.uploadData.get(253).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(254).getX(), VerbruikersProfiel.uploadData.get(254).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(255).getX(), VerbruikersProfiel.uploadData.get(255).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(256).getX(), VerbruikersProfiel.uploadData.get(256).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(257).getX(), VerbruikersProfiel.uploadData.get(257).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(258).getX(), VerbruikersProfiel.uploadData.get(258).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(259).getX(), VerbruikersProfiel.uploadData.get(259).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(260).getX(), VerbruikersProfiel.uploadData.get(260).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(261).getX(), VerbruikersProfiel.uploadData.get(261).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(262).getX(), VerbruikersProfiel.uploadData.get(262).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(263).getX(), VerbruikersProfiel.uploadData.get(263).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(264).getX(), VerbruikersProfiel.uploadData.get(264).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(265).getX(), VerbruikersProfiel.uploadData.get(265).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(266).getX(), VerbruikersProfiel.uploadData.get(266).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(267).getX(), VerbruikersProfiel.uploadData.get(267).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(268).getX(), VerbruikersProfiel.uploadData.get(268).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(269).getX(), VerbruikersProfiel.uploadData.get(269).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(270).getX(), VerbruikersProfiel.uploadData.get(270).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(271).getX(), VerbruikersProfiel.uploadData.get(271).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(272).getX(), VerbruikersProfiel.uploadData.get(272).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(273).getX(), VerbruikersProfiel.uploadData.get(273).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(274).getX(), VerbruikersProfiel.uploadData.get(274).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(275).getX(), VerbruikersProfiel.uploadData.get(275).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(276).getX(), VerbruikersProfiel.uploadData.get(276).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(277).getX(), VerbruikersProfiel.uploadData.get(277).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(278).getX(), VerbruikersProfiel.uploadData.get(278).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(279).getX(), VerbruikersProfiel.uploadData.get(279).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(280).getX(), VerbruikersProfiel.uploadData.get(280).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(281).getX(), VerbruikersProfiel.uploadData.get(281).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(282).getX(), VerbruikersProfiel.uploadData.get(282).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(283).getX(), VerbruikersProfiel.uploadData.get(283).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(284).getX(), VerbruikersProfiel.uploadData.get(284).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(285).getX(), VerbruikersProfiel.uploadData.get(285).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(286).getX(), VerbruikersProfiel.uploadData.get(286).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(287).getX(), VerbruikersProfiel.uploadData.get(287).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(288).getX(), VerbruikersProfiel.uploadData.get(288).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(289).getX(), VerbruikersProfiel.uploadData.get(289).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(290).getX(), VerbruikersProfiel.uploadData.get(290).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(291).getX(), VerbruikersProfiel.uploadData.get(291).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(292).getX(), VerbruikersProfiel.uploadData.get(292).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(293).getX(), VerbruikersProfiel.uploadData.get(293).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(294).getX(), VerbruikersProfiel.uploadData.get(294).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(295).getX(), VerbruikersProfiel.uploadData.get(295).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(296).getX(), VerbruikersProfiel.uploadData.get(296).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(297).getX(), VerbruikersProfiel.uploadData.get(297).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(298).getX(), VerbruikersProfiel.uploadData.get(298).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(299).getX(), VerbruikersProfiel.uploadData.get(299).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(300).getX(), VerbruikersProfiel.uploadData.get(300).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(301).getX(), VerbruikersProfiel.uploadData.get(301).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(302).getX(), VerbruikersProfiel.uploadData.get(302).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(303).getX(), VerbruikersProfiel.uploadData.get(303).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(304).getX(), VerbruikersProfiel.uploadData.get(304).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(305).getX(), VerbruikersProfiel.uploadData.get(305).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(306).getX(), VerbruikersProfiel.uploadData.get(306).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(307).getX(), VerbruikersProfiel.uploadData.get(307).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(308).getX(), VerbruikersProfiel.uploadData.get(308).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(309).getX(), VerbruikersProfiel.uploadData.get(309).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(310).getX(), VerbruikersProfiel.uploadData.get(310).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(311).getX(), VerbruikersProfiel.uploadData.get(311).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(312).getX(), VerbruikersProfiel.uploadData.get(312).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(313).getX(), VerbruikersProfiel.uploadData.get(313).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(314).getX(), VerbruikersProfiel.uploadData.get(314).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(315).getX(), VerbruikersProfiel.uploadData.get(315).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(316).getX(), VerbruikersProfiel.uploadData.get(316).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(317).getX(), VerbruikersProfiel.uploadData.get(317).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(318).getX(), VerbruikersProfiel.uploadData.get(318).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(319).getX(), VerbruikersProfiel.uploadData.get(319).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(320).getX(), VerbruikersProfiel.uploadData.get(320).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(321).getX(), VerbruikersProfiel.uploadData.get(321).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(322).getX(), VerbruikersProfiel.uploadData.get(322).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(323).getX(), VerbruikersProfiel.uploadData.get(323).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(324).getX(), VerbruikersProfiel.uploadData.get(324).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(325).getX(), VerbruikersProfiel.uploadData.get(325).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(326).getX(), VerbruikersProfiel.uploadData.get(326).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(327).getX(), VerbruikersProfiel.uploadData.get(327).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(328).getX(), VerbruikersProfiel.uploadData.get(328).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(329).getX(), VerbruikersProfiel.uploadData.get(329).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(330).getX(), VerbruikersProfiel.uploadData.get(330).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(331).getX(), VerbruikersProfiel.uploadData.get(331).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(332).getX(), VerbruikersProfiel.uploadData.get(332).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(333).getX(), VerbruikersProfiel.uploadData.get(333).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(334).getX(), VerbruikersProfiel.uploadData.get(334).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(335).getX(), VerbruikersProfiel.uploadData.get(335).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(336).getX(), VerbruikersProfiel.uploadData.get(336).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(337).getX(), VerbruikersProfiel.uploadData.get(337).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(338).getX(), VerbruikersProfiel.uploadData.get(338).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(339).getX(), VerbruikersProfiel.uploadData.get(339).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(340).getX(), VerbruikersProfiel.uploadData.get(340).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(341).getX(), VerbruikersProfiel.uploadData.get(341).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(342).getX(), VerbruikersProfiel.uploadData.get(342).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(343).getX(), VerbruikersProfiel.uploadData.get(343).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(344).getX(), VerbruikersProfiel.uploadData.get(344).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(345).getX(), VerbruikersProfiel.uploadData.get(345).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(346).getX(), VerbruikersProfiel.uploadData.get(346).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(347).getX(), VerbruikersProfiel.uploadData.get(347).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(348).getX(), VerbruikersProfiel.uploadData.get(348).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(349).getX(), VerbruikersProfiel.uploadData.get(349).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(350).getX(), VerbruikersProfiel.uploadData.get(350).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(351).getX(), VerbruikersProfiel.uploadData.get(351).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(352).getX(), VerbruikersProfiel.uploadData.get(352).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(353).getX(), VerbruikersProfiel.uploadData.get(353).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(354).getX(), VerbruikersProfiel.uploadData.get(354).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(355).getX(), VerbruikersProfiel.uploadData.get(355).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(356).getX(), VerbruikersProfiel.uploadData.get(356).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(357).getX(), VerbruikersProfiel.uploadData.get(357).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(358).getX(), VerbruikersProfiel.uploadData.get(358).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(359).getX(), VerbruikersProfiel.uploadData.get(359).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(360).getX(), VerbruikersProfiel.uploadData.get(360).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(361).getX(), VerbruikersProfiel.uploadData.get(361).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(362).getX(), VerbruikersProfiel.uploadData.get(362).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(363).getX(), VerbruikersProfiel.uploadData.get(363).getY()),
                        new DataPoint(VerbruikersProfiel.uploadData.get(364).getX(), VerbruikersProfiel.uploadData.get(364).getY()),

                });


                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {

                        String msg = "X:" + dataPoint.getX() + "\nY:" + dataPoint.getY();
                        Log.d("XVAL", "XVAL" + new Date((long) dataPoint.getX()));
                        Toast.makeText(VerbruikersProfielGrafiek.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


                //series2.setColor(Color.RED);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setColor(Color.rgb(128, 84, 38));
                series.setDrawBackground(true);
                series.setBackgroundColor(Color.argb(60, 223, 187, 149));

                graphView.setTitle("Verbruikersprofiel");


                graphView.getViewport().setScrollable(true); // enables horizontal scrolling
                graphView.getViewport().setScrollableY(true); // enables vertical scrolling
                graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
                graphView.getViewport().setScalableY(true); // enables vertical zooming and scrolling

                // graphView.getGridLabelRenderer().setNumHorizontalLabels(10);

                graphView.getViewport().setMinX(0);
                graphView.getViewport().setMaxX(8.64e+7);
                graphView.addSeries(series);




        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double val, boolean isValueX) {
                if (isValueX) {
                    //Format formatter = new SimpleDateFormat("HH:mm");
                    return String.valueOf(val);
                } else {
                    return super.formatLabel(val, isValueX)+ "kWh ";
                }
            }

        });




    }


}
