package com.example.nfc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.json.JSONException;

import java.math.BigDecimal;

public class ChoosePayment extends AppCompatActivity  {



    private String paymentAmount;
    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    String valueTotPrijs;
    Button otherPayment,nfcPayment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment);

        otherPayment = findViewById(R.id.otherPaymentMethod);
        nfcPayment = findViewById(R.id.nfcPaymentButton);





        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             valueTotPrijs = extras.getString("key");
            //The key argument here must match that used in the other activity
        }


       nfcPayment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String tottekst = valueTotPrijs;
               String tottekst2 = String.valueOf(Double.parseDouble(tottekst.substring(tottekst.indexOf(":")+1)));

               String value=tottekst2;
               Intent i = new Intent(ChoosePayment.this, password.class);
               i.putExtra("key2",value);
               startActivity(i);

           }
       });




        otherPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tottekst = valueTotPrijs;
                String tottekst2 = String.valueOf(Double.parseDouble(tottekst.substring(tottekst.indexOf(":")+1)));

                Log.d("tottekst","tottekst"+ tottekst2);

                if(tottekst2.equals("0.0")){  Toast toast = Toast.makeText(getApplicationContext(), "TOTAL PRICE HAS TO BE HIGHER THAN 0.0", Toast.LENGTH_SHORT);
                    toast.show();}
                else {
                    getPayment();
                }
                Intent intent = new Intent(ChoosePayment.this, PayPalService.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                startService(intent);



            }
        });














    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void getPayment() {
        //Getting the amount from TotalPriceText
        //String txt = totalPrice.getText().toString();
        paymentAmount = String.valueOf(Double.parseDouble(valueTotPrijs.substring(valueTotPrijs.indexOf(":")+1)));
        Log.d("PAYSTRING","PAYSTRING"+ paymentAmount);

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "EUR", "Pay Amount",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                com.paypal.android.sdk.payments.PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, com.example.nfc.PaymentConfirmation.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                        // Log.d("Success", paymentDetails);

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

    }


}
