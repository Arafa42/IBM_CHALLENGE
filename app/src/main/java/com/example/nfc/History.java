package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    ListView listView;
    public static ArrayList<String> arrayList = new ArrayList<>();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String[] items = new String[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listviewPaymentHistory);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.history);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.history:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.charts:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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


        getObjectFromPreferences(fAuth.getCurrentUser().getUid());
    }


    public void saveObjectToPreferences(String key) {
        SharedPreferences prefs = getSharedPreferences(key,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for(int i=0;i<arrayList.size();i++){

            editor.putString(String.valueOf(i), String.valueOf(arrayList.get(i)));
        }

        editor.apply();
    }


    public void getObjectFromPreferences(String key) {
        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        for(int i=0;i<arrayList.size();i++){
           items[i] = prefs.getString(String.valueOf(i), String.valueOf(MODE_PRIVATE));


        }


    }


    @Override
    protected void onPause(){
        super.onPause();
        saveObjectToPreferences(fAuth.getCurrentUser().getUid());
    }


}
