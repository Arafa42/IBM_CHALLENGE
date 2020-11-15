package com.example.nfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMoneyConfirmation extends AppCompatActivity {


    String addedamountsharedpref,addedamountsharedpref2;
    TextView textViewId;
    TextView textViewStatus;
    TextView textViewAmount;
    FirebaseAuth fAuth;
    Button goToWallet;
    public static boolean addmoney=false;
    Float flo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_confirmation);

        fAuth = FirebaseAuth.getInstance();
        textViewId = (TextView) findViewById(R.id.paymentId);
        textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        textViewAmount = (TextView) findViewById(R.id.addedAmount);
        goToWallet = findViewById(R.id.gotowallet);


        goToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmoney=true;
                NfcReader.nfcread = false;
                Intent i = new Intent(AddMoneyConfirmation.this,Wallet.class);
                startActivity(i);
            }
        });






        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        getObjectFromPreferences(fAuth.getCurrentUser().getUid());
    }





    private void showDetails(final JSONObject jsonDetails, final String paymentAmount) throws JSONException {
        //Views

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount);




    }



    public void saveObjectToPreferences(String key) {

        if(NfcReader.nfcread){  flo = Float.parseFloat(textViewAmount.getText().toString()) + Float.parseFloat(addedamountsharedpref2);}
        else{flo = Float.parseFloat(textViewAmount.getText().toString()) + Float.parseFloat(addedamountsharedpref);}


        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("addedmula1", String.valueOf(flo));
        editor.apply();
    }


    public void getObjectFromPreferences(String key) {
        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        addedamountsharedpref = prefs.getString("addedmula", String.valueOf(MODE_PRIVATE));
        addedamountsharedpref2 = prefs.getString("addedmula2", String.valueOf(MODE_PRIVATE));
    }



    @Override
    protected void onPause(){
        super.onPause();
        saveObjectToPreferences(fAuth.getCurrentUser().getUid());
    }
}
