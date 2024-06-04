package com.example.emplostaff2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static String Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText user=findViewById(R.id.name_mu);
        EditText password=findViewById(R.id.password_reset);
        Button btnLogin=findViewById(R.id.submit_mu);
        Button btnReset=findViewById(R.id.login_button2);
        TextView tw=findViewById(R.id.text_view);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref=db.collection("Users").document(user.getText().toString());
                ScriptDeletePenalties30Days.script();
                ScriptUpdatePaymentDay.script();
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String passwors_fb=documentSnapshot.getString("Password");
                        String[] user_desglosado=user.getText().toString().split("");
                        if (user_desglosado[0].equals("S") || user_desglosado[0].equals("M") || user_desglosado[0].equals("B")) {
                            if (user_desglosado.length==8 && user_desglosado[1].equals("-")) {
                                if (passwors_fb.equals(password.getText().toString())) {
                                    Id = user.getText().toString();
                                    if (user.getText().toString().substring(0, 1).equals("B")) {
                                        Intent intent = new Intent(MainActivity.this, BossDesplegable.class);
                                        startActivity(intent);
                                    }
                                    if (user.getText().toString().substring(0, 1).equals("S")) {
                                        Intent intent = new Intent(MainActivity.this, StaffDesplegable.class);
                                        startActivity(intent);
                                    }
                                    if (user.getText().toString().substring(0, 1).equals("M")) {
                                        Intent intent = new Intent(MainActivity.this, ManagerDesplegable.class);
                                        startActivity(intent);
                                    }
                                } else {
                                    tw.setText("The password is incorrect");
                                }
                            }else {tw.setText("The user is incorrect");}
                        }else{tw.setText("The user is incorrect");}
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref=db.collection("Users").document(user.getText().toString());
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String passwors_fb=documentSnapshot.getString("Password");
                        Id=user.getText().toString();
                        if (passwors_fb.equals(password.getText().toString()) && passwors_fb.equals("admin") ){
                            Intent intent= new Intent(MainActivity.this, ResetPassword.class);
                            startActivity(intent);}
                        else{
                            tw.setText("You cant reset the password");

                        }
                    }
                });
            }
        });
    }

    }




