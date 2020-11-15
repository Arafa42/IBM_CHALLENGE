package com.example.nfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class changePin extends AppCompatActivity {
    public static final String SHARED_PREFS="sharedPrefs";
    public static final String PASS="pass";
    private String password;
    EditText oldPass, newPass, confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        oldPass=findViewById(R.id.oldPassword);
        newPass=findViewById(R.id.newPassword);
        confirmPass=findViewById(R.id.confirmPassword);

        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        password=sharedPreferences.getString(PASS,"1234");










    }



    public void backToProfile(View view) {
        Intent intent= new Intent(changePin.this,Profile.class);
        startActivity(intent);
    }

    public  void changePassword(View view) {
        if(oldPass.getText().toString().equals(password) && newPass.getText().toString().equals(confirmPass.getText().toString())){
            password=newPass.getText().toString();

            SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();

            editor.putString(PASS,password);
            editor.apply();

            Toast.makeText(this, "Wachtwoord is aangepast", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(changePin.this, Profile.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(this, "Vul de velden correct in", Toast.LENGTH_SHORT).show();
            Log.i("password",password);
            Log.i("password", oldPass.getText().toString());
            Log.i("password", newPass.getText().toString());
            Log.i("password", confirmPass.getText().toString());

        }
    }
}
