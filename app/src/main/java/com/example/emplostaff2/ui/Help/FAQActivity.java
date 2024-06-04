package com.example.emplostaff2.ui.Help;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emplostaff2.R;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_help);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<FAQItem> faqList = new ArrayList<>();
        faqList.add(new FAQItem("¿Como puedo ver mi salario en la aplicacion con sus respectivos datos?", "Debe ir al apartado 'Salary' y ahi podra visualizar los datos deseados sobre el salario"));
        faqList.add(new FAQItem("De que forma podria cambiar la contraseña", "En la pagina de inicio de sesion, puede comprobar que hay un boton de 'Reset', haga click y cambie su contraseña"));
        faqList.add(new FAQItem("Otra cuestion", "Si tiene otro tipo de duda, llame al 677 776 888 para que podamos resolverla"));

        FAQAdapter adapter = new FAQAdapter(faqList);
        recyclerView.setAdapter(adapter);
    }
}
