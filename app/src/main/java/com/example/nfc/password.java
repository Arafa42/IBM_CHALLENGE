package com.example.nfc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;


import androidx.appcompat.app.AppCompatActivity;

import com.hanks.passcodeview.PasscodeView;
import com.paypal.android.sdk.payments.PayPalService;

import static com.example.nfc.changePin.PASS;
import static com.example.nfc.changePin.SHARED_PREFS;


public class password extends AppCompatActivity {
    PasscodeView passcodeView;
    private static final String TAG = "password";
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        password=sharedPreferences.getString(PASS,"1234");

        Log.d("password",password );

        Log.i(TAG,"Pincode menu");

        passcodeView=findViewById(R.id.passcodeView);
        passcodeView.setPasscodeLength(4)
                .setLocalPasscode(password)
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(getApplicationContext()
                                , "Password is wrong",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                            String value=getIntent().getStringExtra("key2");
                            Intent intent= new Intent(password.this, NfcReader.class);
                            intent.putExtra("key2",value);
                            startActivity(intent);





                    }
                });


    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }






}

