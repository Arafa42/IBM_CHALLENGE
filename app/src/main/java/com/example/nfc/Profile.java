package com.example.nfc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class Profile extends AppCompatActivity {

    ImageView imageView;
    TextView firstName, lastName, email, phone,verifyMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button logout,resendCode;
    GoogleSignInClient  mGoogleSignInClient;
    ImageButton settings;
    Button verbruikersProfiel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        verbruikersProfiel = findViewById(R.id.vpKnop);


        verbruikersProfiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,VerbruikersProfiel.class);
                startActivity(i);
            }
        });



        settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,Settings.class);
                startActivity(i);
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        imageView = findViewById(R.id.imageView);
        phone = findViewById(R.id.profilePhone);
        firstName = findViewById(R.id.profileFirstName);
        lastName = findViewById(R.id.profileLastName);
        email = findViewById(R.id.profileEmail);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        logout = findViewById(R.id.logout);
        resendCode = findViewById(R.id.buttonVerify);
        verifyMsg = findViewById(R.id.verifyMsg);

        final FirebaseUser user = fAuth.getCurrentUser();




        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            firstName.setText(personGivenName);
            lastName.setText(personFamilyName);
            email.setText(personEmail);

            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }










        if(Login.googleIngelogd == false) {
                if (!user.isEmailVerified()) {
                    verifyMsg.setVisibility(View.VISIBLE);
                    resendCode.setVisibility(View.VISIBLE);
                    resendCode.setEnabled(true);

                    resendCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(v.getContext(), "Verification mail has been sent", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Verification mail not sent : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });

                }
            }

        if(Login.googleIngelogd == false) {
            DocumentReference documentReference = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    phone.setText(documentSnapshot.getString("Phone"));
                    firstName.setText(documentSnapshot.getString("FirstName"));
                    lastName.setText(documentSnapshot.getString("LastName"));
                    email.setText(documentSnapshot.getString("Email"));
                }
            });
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.profile);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        return true;
                    case R.id.charts:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), History.class));
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


    //    if(!Login.googleIngelogd) {
    //    logout.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View v) {
    //            fAuth.signOut();//logout
    //            Intent i = new Intent(Profile.this, Login.class);
    //            startActivity(i);
    //            finish();
    //            }
    //    });}
    //    else{

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            signOut();


                    //}

                }
            });

     //   }



    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(Profile.this, Login.class);
                        startActivity(i);
                        Login.googleIngelogd = false;
                        finish();
                    }
                });
    }



}
