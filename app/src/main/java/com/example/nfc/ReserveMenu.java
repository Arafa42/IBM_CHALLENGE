package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReserveMenu extends AppCompatActivity {

    TextView waterCurrentPrice,nuclearCurrentPrice,windCurrentPrice,gasCurrentPrice;
    TextView totalNuclearPrice,totalWaterPrice,totalGasPrice,totalWindPrice;
    TextView totalPrice;
    EditText nuclearVal,gasVal,waterVal,windVal;

    Date whateverDateYouWant = new Date();
    Calendar calendar = Calendar.getInstance();
    Format formatter = new SimpleDateFormat("HH:mm:ss");
    Date datum = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    DatabaseReference firebaseAuth;
    ArrayList<String> dbtijd = new ArrayList<>();
    String water,gas,wind,nuclear;
    Double GasDouble=0.0,WaterDouble=0.0,WindDouble=0.0,NuclearDouble=0.0;
    Double total=0.0;
    Button buyButton;


    private String paymentAmount;


    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final int dayOfYear = LocalDate.now().getDayOfYear();
        Log.d("DADA","DADA"+ dayOfYear);

        buyButton = findViewById(R.id.buyButton);


        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tottekst = totalPrice.getText().toString();
                String tottekst2 = String.valueOf(Double.parseDouble(tottekst.substring(tottekst.indexOf(":") + 1)));

                String valuetot = totalPrice.getText().toString();
                String valuewater = totalWaterPrice.getText().toString();
                String valuewind = totalWindPrice.getText().toString();
                String valuegas = totalGasPrice.getText().toString();
                String valuenucl = totalNuclearPrice.getText().toString();

                Intent i = new Intent(ReserveMenu.this, ReservationConfirmation.class);
                i.putExtra("total", valuetot);
                i.putExtra("watertot", valuewater);
                i.putExtra("gastot", valuegas);
                i.putExtra("windtot", valuewind);
                i.putExtra("nucltot", valuenucl);

                if (tottekst2.equals("0.0")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "TOTAL PRICE HAS TO BE HIGHER THAN 0.0", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    startActivity(i);
                }

            }
        });


        waterCurrentPrice = findViewById(R.id.waterCurrentPrice);
        gasCurrentPrice = findViewById(R.id.gasCurrentPrice);
        nuclearCurrentPrice = findViewById(R.id.nuclearCurrentPrice);
        windCurrentPrice = findViewById(R.id.windCurrentPrice);

        totalGasPrice = findViewById(R.id.totalGasPrice);
        totalWaterPrice = findViewById(R.id.totalWaterPrice);
        totalWindPrice = findViewById(R.id.totalWindPrice);
        totalNuclearPrice = findViewById(R.id.totalNuclearPrice);

        gasVal = findViewById(R.id.gasEditVal);
        waterVal = findViewById(R.id.waterEditVal);
        nuclearVal = findViewById(R.id.nuclearEditVal);
        windVal = findViewById(R.id.windEditVal);

        totalPrice = findViewById(R.id.totalPrice);


        calendar.setTime(whateverDateYouWant);
        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 15;
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (15 - mod));
        //Log.d("calala","calala " + formatter.format(calendar.getTime()));


        firebaseAuth = FirebaseDatabase.getInstance().getReference();

        firebaseAuth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                wind = dataSnapshot.child("yearAheadWind").child(String.valueOf(dayOfYear)).child("day").getValue().toString();
                water = dataSnapshot.child("yearAheadWater").child(String.valueOf(dayOfYear)).child("day").getValue().toString();
                gas = dataSnapshot.child("yearAheadGas").child(String.valueOf(dayOfYear)).child("day").getValue().toString();
                nuclear = dataSnapshot.child("yearAheadNuclear").child(String.valueOf(dayOfYear)).child("day").getValue().toString();



                waterCurrentPrice.setText(String.format("%.2f",Double.parseDouble(water)));
                gasCurrentPrice.setText(String.format("%.2f",Double.parseDouble(gas)));
                windCurrentPrice.setText(String.format("%.2f",Double.parseDouble(wind)));
                nuclearCurrentPrice.setText(String.format("%.2f",Double.parseDouble(nuclear)));


                Log.d("gasprijs", "gasprice  " + GasDouble);


                gasVal.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (s != null && s.length() > 0) {
                            if (Double.parseDouble(String.valueOf(s)) <= Double.parseDouble(gas)) {
                                GasDouble = Double.parseDouble(String.valueOf(s)) * Double.parseDouble(gas);
                                totalGasPrice.setText(String.format(Locale.CANADA, "%.2f", GasDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + gas + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                GasDouble = 0.0 * Double.parseDouble(gas);
                                totalGasPrice.setText(String.format(Locale.CANADA, "%.2f", GasDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                            }
                        } else {
                            GasDouble = 0.0 * Double.parseDouble(gas);
                            totalGasPrice.setText(String.format(Locale.CANADA, "%.2f", GasDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                        }
                    }
                });

                windVal.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s != null && s.length() > 0) {
                            if (Double.parseDouble(String.valueOf(s)) <= Double.parseDouble(wind)) {
                                WindDouble = Double.parseDouble(String.valueOf(s)) * Double.parseDouble(wind);
                                totalWindPrice.setText(String.format(Locale.CANADA, "%.2f", WindDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + wind + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                WindDouble = 0.0 * Double.parseDouble(wind);
                                totalWindPrice.setText(String.format(Locale.CANADA, "%.2f", WindDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                            }
                        } else {
                            WindDouble = 0.0 * Double.parseDouble(wind);
                            totalWindPrice.setText(String.format(Locale.CANADA, "%.2f", WindDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                        }
                    }
                });

                waterVal.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s != null && s.length() > 0) {
                            if (Double.parseDouble(String.valueOf(s)) <= Double.parseDouble(water)) {
                                WaterDouble = Double.parseDouble(String.valueOf(s)) * Double.parseDouble(water);
                                totalWaterPrice.setText(String.format(Locale.CANADA, "%.2f", WaterDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + water + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                WaterDouble = 0.0 * Double.parseDouble(water);
                                totalWaterPrice.setText(String.format(Locale.CANADA, "%.2f", WaterDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                            }
                        } else {
                            WaterDouble = 0.0 * Double.parseDouble(water);
                            totalWaterPrice.setText(String.format(Locale.CANADA, "%.2f", WaterDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                        }
                    }
                });

                nuclearVal.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s != null && s.length() > 0) {
                            if (Double.parseDouble(String.valueOf(s)) <= Double.parseDouble(nuclear)) {
                                NuclearDouble = Double.parseDouble(String.valueOf(s)) * Double.parseDouble(nuclear);
                                totalNuclearPrice.setText(String.format(Locale.CANADA, "%.2f", NuclearDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + nuclear + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                NuclearDouble = 0.0 * Double.parseDouble(nuclear);
                                totalNuclearPrice.setText(String.format(Locale.CANADA, "%.2f", NuclearDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));


                            }
                        } else {
                            NuclearDouble = 0.0 * Double.parseDouble(nuclear);
                            totalNuclearPrice.setText(String.format(Locale.CANADA, "%.2f", NuclearDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;

                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA, "%.2f", total));
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    }




