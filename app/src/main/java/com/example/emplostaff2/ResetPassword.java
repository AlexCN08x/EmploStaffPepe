package com.example.emplostaff2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {
    public static String Name;
    public static String LastName;
    public static String Birthdate;
    public static String BaseSalary;

    public static String ExtraHours;
    public static String NIF;
    public static String PaymentDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        TextView user=findViewById(R.id.name_reset);
        EditText password=findViewById(R.id.password_reset);
        Button btn_submit=findViewById(R.id.submit_mu);
        user.setText(MainActivity.Id);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref=fb.collection("Users").document(MainActivity.Id);
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name_id=documentSnapshot.getString("Name");
                        String lastname_id=documentSnapshot.getString("LastName");
                        String nif_id=documentSnapshot.getString("NIF");
                        String birthdate_id=documentSnapshot.getString("Birthdate");
                        String basesalary_id=documentSnapshot.getString("Base Salary");
                        String extrahours=documentSnapshot.getString("Extra Hours");
                        String paymentday=documentSnapshot.getString("Payment Day");
                        Name=name_id;
                        LastName=lastname_id;
                        NIF=nif_id;
                        Birthdate=birthdate_id;
                        BaseSalary=basesalary_id;
                        ExtraHours=extrahours;
                        PaymentDay=paymentday;
                    }
                });
                Map<String,Object> mapeo=new HashMap<>();
                mapeo.put("Password",password.getText().toString());
                mapeo.put("Name",Name);
                mapeo.put("LastName",LastName);
                mapeo.put("NIF",NIF);
                mapeo.put("Birthdate",Birthdate);
                mapeo.put("Base Salary",BaseSalary);
                mapeo.put("Extra Hours",ExtraHours);
                mapeo.put("Payment Day",PaymentDay);
                ref.set(mapeo);
                Intent itent=new Intent(ResetPassword.this,MainActivity.class);
                startActivity(itent);
            }
        });
    }
}