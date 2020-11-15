package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MeritOrder extends AppCompatActivity {

    DatabaseReference firebaseAuth;
    Date datum = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Double uitkomstGas=0.0;
    Double uitkomstWater=0.0;
    Double uitkomstNucl=0.0;
    Double uitkomstWind=0.0;
    BarChart meritOrderChart;
    float waterfl,nuclfl,gasfl,windfl;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    ArrayList<BarEntry> dataVals1 = new ArrayList<>();
    ArrayList<BarEntry> dataVals2 = new ArrayList<>();
    ArrayList<BarEntry> dataVals3 = new ArrayList<>();
    ArrayList<BarEntry> dataVals4 = new ArrayList<>();
    Date whateverDateYouWant = new Date();
    Calendar calendar = Calendar.getInstance();
    Format formatter = new SimpleDateFormat("HH:mm:ss");
    ArrayList<String> dbtijd = new ArrayList<>();
    Double water;
    Double gas;
    Double wind;
    Double nuclear;
    SwipeRefreshLayout swipeRefreshLayout;
    NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 0;
    String NoteChannel = "0";
    Boolean notifBool=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merit_order);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.chartMeritOrder);
        meritOrderChart = findViewById(R.id.BarGraphMeritOrder);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        if(notifBool == true){notification();}
                        Intent i = new Intent(MeritOrder.this,MeritOrder.class);
                        startActivity(i);
                    }
                },1000);
            }
        });



        calendar.setTime(whateverDateYouWant);
        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 15;
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (15-mod));

        firebaseAuth = FirebaseDatabase.getInstance().getReference().child(df.format(datum)).child("generation");

        firebaseAuth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(int i=8;i<=95;i++) {

                    dbtijd.add(dataSnapshot.child(String.valueOf(i)).child("data").child("date").getValue().toString());

                }


                for(int i=0;i<=87;i++) {

                    if(dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")).equals(formatter.format(calendar.getTime()))){
                        water = (Double) dataSnapshot.child(String.valueOf(i+8)).child("data").child("water").getValue();
                        gas = (Double) dataSnapshot.child(String.valueOf(i+8)).child("data").child("naturalGas").getValue();
                        nuclear = (Double) dataSnapshot.child(String.valueOf(i+8)).child("data").child("nuclear").getValue();
                        wind = (Double) dataSnapshot.child(String.valueOf(i+8)).child("data").child("wind").getValue();
                        Log.d("covid20","covid20" + "jejeje" + water);
                        Log.d("covid20","covid20" + "jejeje" + dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")) + formatter.format(calendar.getTime()));
                    }
                    //Log.d("dbdingens","dbdingens "+ dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")));
                }



                 uitkomstGas = gas;
                 uitkomstWater = water;
                 uitkomstWind = wind;
                 uitkomstNucl = nuclear;



             //   for(int i=8;i<=95;i++) {

               //     gas = (Double) dataSnapshot.child(String.valueOf(i)).child("data").child("naturalGas").getValue();
               //     somGas += gas;

               //     water = (Double) dataSnapshot.child(String.valueOf(i)).child("data").child("water").getValue();
               //     somWater += water;

               //     wind = (Double) dataSnapshot.child(String.valueOf(i)).child("data").child("wind").getValue();
               //     somWind += wind;

               //     nucl = (Double) dataSnapshot.child(String.valueOf(i)).child("data").child("nuclear").getValue();
               //     somNucl += nucl;
              //  }

               // uitkomstGas = somGas/88;
               // uitkomstWater = somWater/88;
               // uitkomstWind = somWind/88;
               // uitkomstNucl = somNucl/88;

               // Log.d("uitkomstNUCL",""+String.format("%.2f", uitkomstNucl));
               // Log.d("uitkomstGAS",""+String.format("%.2f", uitkomstGas));
               // Log.d("uitkomstWATER",""+String.format("%.2f", uitkomstWater));
               // Log.d("uitkomstWINF",""+String.format("%.2f", uitkomstWind));



                if(uitkomstWater < uitkomstWind && uitkomstWater < uitkomstNucl && uitkomstWater < uitkomstGas && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstNucl
                   && uitkomstGas < uitkomstNucl) {
                    dataVals1.add(new BarEntry(0, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(1, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(2, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(3, uitkomstNucl.intValue()));
                }
                else if(uitkomstWater < uitkomstGas && uitkomstWater < uitkomstNucl && uitkomstWater < uitkomstWind && uitkomstWind< uitkomstNucl && uitkomstWind < uitkomstGas
                        && uitkomstNucl < uitkomstGas){
                    dataVals1.add(new BarEntry(0, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(1, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(3, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(2, uitkomstNucl.intValue()));
                }
                else if(uitkomstWater < uitkomstGas && uitkomstWater < uitkomstNucl && uitkomstWater < uitkomstWind && uitkomstNucl< uitkomstWind && uitkomstNucl < uitkomstGas
                        && uitkomstWind < uitkomstGas){
                    dataVals1.add(new BarEntry(0, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(2, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(3, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(1, uitkomstNucl.intValue()));
                }
                else if(uitkomstWater < uitkomstGas && uitkomstWater < uitkomstNucl && uitkomstWater < uitkomstWind && uitkomstGas< uitkomstNucl && uitkomstGas < uitkomstWind
                        && uitkomstWind < uitkomstNucl){
                    dataVals1.add(new BarEntry(0, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(2, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(1, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(3, uitkomstNucl.intValue()));
                }
                else if(uitkomstWater < uitkomstGas && uitkomstWater < uitkomstNucl && uitkomstWater < uitkomstWind && uitkomstGas< uitkomstNucl && uitkomstGas < uitkomstWind
                        && uitkomstNucl < uitkomstWind){
                    dataVals1.add(new BarEntry(0, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(3, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(1, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(2, uitkomstNucl.intValue()));
                }
                else if(uitkomstWater < uitkomstGas && uitkomstWater < uitkomstNucl && uitkomstWater < uitkomstWind && uitkomstNucl< uitkomstWind && uitkomstNucl < uitkomstGas
                        && uitkomstGas < uitkomstWind){
                    dataVals1.add(new BarEntry(0, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(3, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(2, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(1, uitkomstNucl.intValue()));
                }




                else if(uitkomstNucl < uitkomstWater && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWind && uitkomstWater < uitkomstWind && uitkomstWater < uitkomstGas
                        && uitkomstGas < uitkomstWind){
                    dataVals1.add(new BarEntry(1, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(3, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(2, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(0, uitkomstNucl.intValue()));
                }
                else if(uitkomstNucl < uitkomstWater && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWind && uitkomstWater < uitkomstWind && uitkomstWater < uitkomstGas
                        && uitkomstWind < uitkomstGas){
                    dataVals1.add(new BarEntry(1, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(2, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(3, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(0, uitkomstNucl.intValue()));
                }
                else if(uitkomstWind < uitkomstWater && uitkomstWind< uitkomstGas && uitkomstWind < uitkomstNucl && uitkomstWater < uitkomstGas && uitkomstWater < uitkomstNucl
                        && uitkomstGas < uitkomstNucl){
                    dataVals1.add(new BarEntry(1, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(0, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(2, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(3, uitkomstNucl.intValue()));
                }
                else if(uitkomstWind < uitkomstWater && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstNucl && uitkomstWater < uitkomstGas && uitkomstWater < uitkomstNucl
                        && uitkomstNucl < uitkomstGas){
                    dataVals1.add(new BarEntry(1, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(0, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(3, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(2, uitkomstNucl.intValue()));
                }
                else if(uitkomstGas < uitkomstWater && uitkomstGas < uitkomstNucl && uitkomstGas < uitkomstWind && uitkomstWater < uitkomstNucl && uitkomstWater < uitkomstWind
                        && uitkomstNucl < uitkomstWind){
                    dataVals1.add(new BarEntry(1, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(3, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(0, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(2, uitkomstNucl.intValue()));
                }
                else if(uitkomstGas < uitkomstWater && uitkomstGas < uitkomstNucl && uitkomstGas < uitkomstWind && uitkomstWater < uitkomstWind && uitkomstWater < uitkomstNucl
                        && uitkomstWind < uitkomstNucl){
                    dataVals1.add(new BarEntry(1, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(2, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(0, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(3, uitkomstNucl.intValue()));
                }




                else if(uitkomstNucl < uitkomstWater && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWind && uitkomstGas < uitkomstWind && uitkomstGas < uitkomstWater
                        && uitkomstWater < uitkomstWind){
                    dataVals1.add(new BarEntry(2, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(3, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(1, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(0, uitkomstNucl.intValue()));
                }
                else if(uitkomstNucl < uitkomstWater && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWind && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstWater
                        && uitkomstWater < uitkomstGas){
                    dataVals1.add(new BarEntry(2, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(1, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(3, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(0, uitkomstNucl.intValue()));
                }
                else if(uitkomstGas < uitkomstWater && uitkomstGas < uitkomstWind && uitkomstGas < uitkomstNucl && uitkomstWind < uitkomstWater && uitkomstWind < uitkomstNucl
                        && uitkomstWater < uitkomstNucl){
                    dataVals1.add(new BarEntry(2, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(1, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(0, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(3, uitkomstNucl.intValue()));
                }
                else if(uitkomstGas < uitkomstWater && uitkomstGas < uitkomstNucl && uitkomstGas < uitkomstWind && uitkomstNucl < uitkomstWind && uitkomstNucl < uitkomstWater
                        && uitkomstWater < uitkomstWind){
                    dataVals1.add(new BarEntry(2, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(3, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(0, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(1, uitkomstNucl.intValue()));
                }
                else if(uitkomstWind < uitkomstWater && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstNucl && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWater
                        && uitkomstWater < uitkomstGas){
                    dataVals1.add(new BarEntry(2, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(0, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(3, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(1, uitkomstNucl.intValue()));
                }
                else if(uitkomstWind < uitkomstWater && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstNucl && uitkomstGas < uitkomstNucl && uitkomstGas < uitkomstWater
                        && uitkomstWater < uitkomstNucl){
                    dataVals1.add(new BarEntry(2, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(0, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(1, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(3, uitkomstNucl.intValue()));
                }





                else if(uitkomstGas < uitkomstWater && uitkomstGas < uitkomstNucl && uitkomstGas < uitkomstWind && uitkomstNucl < uitkomstWind && uitkomstNucl < uitkomstWater
                        && uitkomstWind < uitkomstWater){
                    dataVals1.add(new BarEntry(3, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(2, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(0, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(1, uitkomstNucl.intValue()));
                }
                else if(uitkomstNucl < uitkomstWater && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWind && uitkomstGas < uitkomstWind && uitkomstGas < uitkomstWater
                        && uitkomstWind < uitkomstWater){
                    dataVals1.add(new BarEntry(3, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(2, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(1, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(0, uitkomstNucl.intValue()));
                }
                else if(uitkomstNucl < uitkomstWater && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWind && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstWater
                        && uitkomstGas < uitkomstWater){
                    dataVals1.add(new BarEntry(3, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(1, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(2, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(0, uitkomstNucl.intValue()));
                }
                else if(uitkomstGas < uitkomstWater && uitkomstGas < uitkomstNucl && uitkomstGas < uitkomstWind && uitkomstWind < uitkomstNucl && uitkomstWind < uitkomstWater
                        && uitkomstNucl < uitkomstWater){
                    dataVals1.add(new BarEntry(3, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(1, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(0, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(2, uitkomstNucl.intValue()));
                }
                else if(uitkomstWind < uitkomstWater && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstNucl && uitkomstGas < uitkomstNucl && uitkomstGas < uitkomstWater
                        && uitkomstNucl < uitkomstWater){
                    dataVals1.add(new BarEntry(3, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(0, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(1, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(2, uitkomstNucl.intValue()));
                }
                else if(uitkomstWind < uitkomstWater && uitkomstWind < uitkomstGas && uitkomstWind < uitkomstNucl && uitkomstNucl < uitkomstGas && uitkomstNucl < uitkomstWater
                        && uitkomstGas < uitkomstWater){
                    dataVals1.add(new BarEntry(3, uitkomstWater.intValue()));
                    dataVals2.add(new BarEntry(0, uitkomstWind.intValue()));
                    dataVals3.add(new BarEntry(2, uitkomstGas.intValue()));
                    dataVals4.add(new BarEntry(1, uitkomstNucl.intValue()));
                }



                BarDataSet barDataSet1 = new BarDataSet(dataVals1,"WATER");
                barDataSet1.setColor(Color.rgb(115, 250, 245));
                BarDataSet barDataSet2 = new BarDataSet(dataVals2,"WIND");
                barDataSet2.setColor(Color.rgb(45, 59, 92));
                BarDataSet barDataSet3 = new BarDataSet(dataVals3,"GAS");
                barDataSet3.setColor(Color.rgb(128, 84, 38));
                BarDataSet barDataSet4 = new BarDataSet(dataVals4,"NUCLEAR");
                barDataSet4.setColor(Color.rgb(242, 210, 0));

                BarData barData = new BarData();
                barData.addDataSet(barDataSet1);
                barData.addDataSet(barDataSet2);
                barData.addDataSet(barDataSet3);
                barData.addDataSet(barDataSet4);

                meritOrderChart.setData(barData);
                meritOrderChart.invalidate();




                if (Double.parseDouble(String.valueOf(waterfl)) >= Double.parseDouble(String.valueOf(water))) {
                    notifBool = true;
                    notification();
                }
                if (Double.parseDouble(String.valueOf(gasfl)) >= Double.parseDouble(String.valueOf(gas))) {
                    notifBool = true;
                    notification();
                }
                if (Double.parseDouble(String.valueOf(windfl)) >= Double.parseDouble(String.valueOf(wind))) {
                    notifBool = true;
                    notification();
                }
                if (Double.parseDouble(String.valueOf(nuclfl)) >= Double.parseDouble(String.valueOf(nuclear))) {
                    notifBool = true;
                    notification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.chartMeritOrder:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.charts:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), History.class));
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


        getObjectFromPreferences(fAuth.getCurrentUser().getUid());
        Log.d("waterfl",""+waterfl);

    }



    public void createNotificationChannel(){
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel noti = new NotificationChannel(NoteChannel,"note",NotificationManager.IMPORTANCE_HIGH);
            noti.enableLights(true);
            noti.setLightColor(Color.RED);
            noti.enableVibration(true);
            noti.setDescription("NOTIFICATION");
            notificationManager.createNotificationChannel(noti);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, NoteChannel)
                .setContentTitle("NFC BEURS WARNING !")
                .setContentText("ENERGIE WAARDEN ZITTEN LAGER DAN DE INGEGEVEN WAARDEN !")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return notifyBuilder;
    }

    public void notification(){
        createNotificationChannel();
        getNotificationBuilder();
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }



    public void saveObjectToPreferences(String key) {
        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("waterval", waterfl);
        editor.putFloat("windval", windfl);
        editor.putFloat("gasval", gasfl);
        editor.putFloat("nuclearval", nuclfl);

        editor.apply();
    }


    public void getObjectFromPreferences(String key) {
        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        waterfl = prefs.getFloat("waterval", MODE_PRIVATE);
        gasfl = prefs.getFloat("gasval", MODE_PRIVATE);
        windfl = prefs.getFloat("windval", MODE_PRIVATE);
        nuclfl = prefs.getFloat("nuclearval", MODE_PRIVATE);




    }



    @Override
    protected void onPause(){
        super.onPause();
        saveObjectToPreferences(fAuth.getCurrentUser().getUid());
    }


}
