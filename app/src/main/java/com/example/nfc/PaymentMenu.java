package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Empty;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class PaymentMenu extends AppCompatActivity {

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
    DatabaseReference firebaseAuth2;
    ArrayList<String> dbtijd = new ArrayList<>();
    String water,gas,wind,nuclear;
    Double GasDouble=0.0,WaterDouble=0.0,WindDouble=0.0,NuclearDouble=0.0;
    Double gs=0.0,wnd=0.0,ncl=0.0,wtr=0.0;
    Double total=0.0,total2=0.0;
    Button buyButton;
    TextView vpVal;
    String vpvl="0";


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
        setContentView(R.layout.activity_payment_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buyButton = findViewById(R.id.buyButton);
        vpVal = findViewById(R.id.vpVal);
        final int dayOfYear = LocalDate.now().getDayOfYear();

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tottekst = totalPrice.getText().toString();
                String tottekst2 = String.valueOf(Double.parseDouble(tottekst.substring(tottekst.indexOf(":")+1)));

                String value= totalPrice.getText().toString();
                Intent i = new Intent(PaymentMenu.this, ChoosePayment.class);
                i.putExtra("key",value);

                if(tottekst2.equals("0.0")){  Toast toast = Toast.makeText(getApplicationContext(), "TOTAL PRICE HAS TO BE HIGHER THAN 0.0", Toast.LENGTH_SHORT);
                    toast.show();}
                else {
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
        totalNuclearPrice= findViewById(R.id.totalNuclearPrice);

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
        calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (15-mod));
        //Log.d("calala","calala " + formatter.format(calendar.getTime()));





        firebaseAuth = FirebaseDatabase.getInstance().getReference();

        firebaseAuth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(int i=8;i<=95;i++) {

                    dbtijd.add(dataSnapshot.child(df.format(datum)).child("generation").child(String.valueOf(i)).child("data").child("date").getValue().toString());

                }


                for(int i=0;i<=87;i++) {

                    if(dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")).equals(formatter.format(calendar.getTime()))){
                         water =  dataSnapshot.child(df.format(datum)).child("generation").child(String.valueOf(i+8)).child("data").child("water").getValue().toString();
                         gas =  dataSnapshot.child(df.format(datum)).child("generation").child(String.valueOf(i+8)).child("data").child("naturalGas").getValue().toString();
                         nuclear =  dataSnapshot.child(df.format(datum)).child("generation").child(String.valueOf(i+8)).child("data").child("nuclear").getValue().toString();
                         wind =  dataSnapshot.child(df.format(datum)).child("generation").child(String.valueOf(i+8)).child("data").child("wind").getValue().toString();
                         vpvl = dataSnapshot.child("Verbruikersprofiel").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("y").child(String.valueOf(dayOfYear-1)).getValue().toString();
                        Log.d("covid20","covid20" + "jejeje" + water);
                        Log.d("covid20","covid20" + "jejeje" + dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")) + formatter.format(calendar.getTime()));
                    }
                 //Log.d("dbdingens","dbdingens "+ dbtijd.get(i).substring(dbtijd.get(i).indexOf("T") + 1, dbtijd.get(i).indexOf("Z")));
                }

                waterCurrentPrice.setText(water);
                gasCurrentPrice.setText(gas);
                windCurrentPrice.setText(wind);
                nuclearCurrentPrice.setText(nuclear);
                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)));



                Log.d("gasprijs","gasprice  " + GasDouble );


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
                                gs = Double.parseDouble(String.valueOf(s));
                                totalGasPrice.setText(String.format(Locale.CANADA,"%.2f",GasDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + gas + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                GasDouble = 0.0 * Double.parseDouble(gas);
                                gs = 0.0;
                                totalGasPrice.setText(String.format(Locale.CANADA,"%.2f",GasDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
                            }
                        }
                        else{
                            GasDouble = 0.0 * Double.parseDouble(gas);
                            gs = 0.0;
                            totalGasPrice.setText(String.format(Locale.CANADA,"%.2f",GasDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                            total2 = gs + wnd + ncl + wtr;
                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));}
                            vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
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
                                wnd = Double.parseDouble(String.valueOf(s));
                                totalWindPrice.setText(String.format(Locale.CANADA,"%.2f",WindDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + wind + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                WindDouble = 0.0 * Double.parseDouble(wind);
                                wnd = 0.0;
                                totalWindPrice.setText(String.format(Locale.CANADA,"%.2f",WindDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
                            }
                        }
                        else{  WindDouble = 0.0 * Double.parseDouble(wind);
                            wnd = 0.0;
                            totalWindPrice.setText(String.format(Locale.CANADA,"%.2f",WindDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                            total2 = gs + wnd + ncl + wtr;
                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                            vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
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
                                wtr = Double.parseDouble(String.valueOf(s));
                                totalWaterPrice.setText(String.format(Locale.CANADA,"%.2f",WaterDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + water + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                WaterDouble = 0.0 * Double.parseDouble(water);
                                wtr = 0.0;
                                totalWaterPrice.setText(String.format(Locale.CANADA,"%.2f",WaterDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
                            }
                        }
                        else{
                            WaterDouble = 0.0 * Double.parseDouble(water);
                            wtr = 0.0;
                            totalWaterPrice.setText(String.format(Locale.CANADA,"%.2f",WaterDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                            total2 = gs + wnd + ncl + wtr;
                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                            vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
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
                                ncl = Double.parseDouble(String.valueOf(s));
                                totalNuclearPrice.setText(String.format(Locale.CANADA,"%.2f", NuclearDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "THERE IS ONLY " + nuclear + "kWh OF ENERGY AVAILABLE", Toast.LENGTH_SHORT);
                                toast.show();
                                NuclearDouble = 0.0 * Double.parseDouble(nuclear);
                                ncl = 0.0;
                                totalNuclearPrice.setText( String.format(Locale.CANADA,"%.2f", NuclearDouble));
                                total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                                total2 = gs + wnd + ncl + wtr;
                                totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                                vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));


                            }
                        }
                        else{
                            NuclearDouble = 0.0 * Double.parseDouble(nuclear);
                            ncl = 0.0;
                            totalNuclearPrice.setText(String.format(Locale.CANADA,"%.2f", NuclearDouble));
                            total = NuclearDouble + GasDouble + WaterDouble + WindDouble;
                            total2 = gs + wnd + ncl + wtr;
                            totalPrice.setText("TOTAL : " + String.format(Locale.CANADA,"%.2f", total));
                            vpVal.setText("VP VALUE TODAY : " + String.format("%.2f", Double.parseDouble(vpvl)-Double.parseDouble(String.valueOf(total2))));
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
