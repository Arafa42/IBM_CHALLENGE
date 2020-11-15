package com.example.nfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ReservationConfirmation extends AppCompatActivity {

    Button addtohistory;
    TextView total;
    TextView gas;
    TextView wind;
    TextView water;
    TextView nucl;

    String valtot,valwater,valgas,valwind,valnucl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation);

        addtohistory = findViewById(R.id.addtohistory);
        total = findViewById(R.id.totalAmount);
        wind = findViewById(R.id.windAmount);
        gas = findViewById(R.id.gasAmount);
        water = findViewById(R.id.waterAmount);
        nucl = findViewById(R.id.nuclAmount);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valtot = extras.getString("total");
            valwind = extras.getString("windtot");
            valgas = extras.getString("gastot");
            valwater = extras.getString("watertot");
            valnucl = extras.getString("nucltot");
            //The key argument here must match that used in the other activity
        }


        gas.setText(valgas);
        nucl.setText(valnucl);
        water.setText(valwater);
        wind.setText(valwind);
        total.setText(valtot);



        addtohistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String[] TO_EMAILS = {"arafayon@gmail.com"};
                String[] CC = {"arafayon@gmail.com"};
                String[] BCC = {"arafayon@gmail.com"};

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,TO_EMAILS);
                intent.putExtra(Intent.EXTRA_CC,TO_EMAILS);
                intent.putExtra(Intent.EXTRA_BCC,TO_EMAILS);
                //intent.putExtra(Intent.EXTRA_TEXT,total.getText().toString());


                intent.putExtra(Intent.EXTRA_SUBJECT,"ENERGIE BESTELLING");
                intent.putExtra(Intent.EXTRA_TEXT,"Water : "+water.getText().toString()+"\n"+"Gas : "+gas.getText().toString()+"\n"+"Wind : "+wind.getText().toString()+"\n"+"Nucl : "+nucl.getText().toString()+"\n"+"Total : "+total.getText().toString());

                startActivity(intent);
            }
        });


    }


}