package com.example.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentConfirmation extends AppCompatActivity  {


    Button addtohistory;
    JsonObject jsonObject = new JsonObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);


        addtohistory = findViewById(R.id.addtohistory);

        //Getting Intent
        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }





    }

    private void showDetails(final JSONObject jsonDetails, final String paymentAmount) throws JSONException {
        //Views
        final TextView textViewId = (TextView) findViewById(R.id.paymentId);
        final TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        final TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" EUR");


        addtohistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    History.arrayList.add("ID : "+jsonDetails.getString("id")+"\n"+"STATUS : "+jsonDetails.getString("state")+"\n"+"TOTAL : "+paymentAmount+" EUR");
                    Intent i = new Intent(PaymentConfirmation.this,History.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }
}