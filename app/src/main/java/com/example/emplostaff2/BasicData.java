package com.example.emplostaff2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BasicData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_basic_data);
        TextView renadomtext=findViewById(R.id.random_text);
        TextView name=findViewById(R.id.name_bd);
        TextView lastname=findViewById(R.id.lastname_bd);
        TextView nif=findViewById(R.id.nif_bd);
        TextView birthdate=findViewById(R.id.birthdate_bd);
        TextView role=findViewById(R.id.role_bd);


    }
}