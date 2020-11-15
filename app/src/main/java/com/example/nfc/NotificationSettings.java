package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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

public class NotificationSettings extends AppCompatActivity {

    //public static boolean notifBool=false;
    EditText waterval,gasval,windval,nuclearval;
    DatabaseReference firebaseAuth;
    Date whateverDateYouWant = new Date();
    ArrayList<String> dbtijd = new ArrayList<>();
    Format formatter = new SimpleDateFormat("HH:mm:ss");
    Date datum = new Date();
    String water,gas,wind,nuclear;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    float waterfl,nuclfl,gasfl,windfl;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        waterval = findViewById(R.id.WaterNotification);
        gasval = findViewById(R.id.GasNotification);
        windval = findViewById(R.id.WindNotification);
        nuclearval = findViewById(R.id.NuclearNotification);

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

                for (int i = 8; i <= 95; i++) {
                    dbtijd.add(dataSnapshot.child(String.valueOf(i)).child("data").child("date").getValue().toString());
                }


                for (int i = 0; i <= 87; i++) {
                    if (dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")).equals(formatter.format(calendar.getTime()))) {
                        water = dataSnapshot.child(String.valueOf(i + 8)).child("data").child("water").getValue().toString();
                        gas = dataSnapshot.child(String.valueOf(i + 8)).child("data").child("naturalGas").getValue().toString();
                        nuclear = dataSnapshot.child(String.valueOf(i + 8)).child("data").child("nuclear").getValue().toString();
                        wind = dataSnapshot.child(String.valueOf(i + 8)).child("data").child("wind").getValue().toString();
                        Log.d("covid20", "covid20" + "jejeje" + water);
                        Log.d("covid20", "covid20" + "jejeje" + dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")) + formatter.format(calendar.getTime()));
                    }
                    //Log.d("dbdingens","dbdingens "+ dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")));
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        getObjectFromPreferences(fAuth.getCurrentUser().getUid());



    }




    public void saveObjectToPreferences(String key) {
        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("waterval", Float.parseFloat(waterval.getText().toString()));
        editor.putFloat("windval", Float.parseFloat(windval.getText().toString()));
        editor.putFloat("gasval", Float.parseFloat(gasval.getText().toString()));
        editor.putFloat("nuclearval", Float.parseFloat(nuclearval.getText().toString()));

        editor.apply();
    }


    public void getObjectFromPreferences(String key) {
        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        waterfl = prefs.getFloat("waterval", MODE_PRIVATE);
        gasfl = prefs.getFloat("gasval", MODE_PRIVATE);
        windfl = prefs.getFloat("windval", MODE_PRIVATE);
        nuclfl = prefs.getFloat("nuclearval", MODE_PRIVATE);

        waterval.setText(String.valueOf(waterfl));
        gasval.setText(String.valueOf(gasfl));
        windval.setText(String.valueOf(windfl));
        nuclearval.setText(String.valueOf(nuclfl));



    }



    @Override
    protected void onPause(){
        super.onPause();
        saveObjectToPreferences(fAuth.getCurrentUser().getUid());
    }

}
