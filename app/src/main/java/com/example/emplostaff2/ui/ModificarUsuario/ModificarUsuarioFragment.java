package com.example.emplostaff2.ui.ModificarUsuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.emplostaff2.R;
import com.example.emplostaff2.ValidadorNIF;
import com.example.emplostaff2.databinding.FragmentModificarUsuarioBinding;
import com.example.emplostaff2.ui.ModificarUsuario.ModificarUsuarioViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ModificarUsuarioFragment extends Fragment {

    private FragmentModificarUsuarioBinding binding;

    public static String password;
    public static String horas_extra;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ModificarUsuarioViewModel ModificarUsuarioViewModel =
                new ViewModelProvider(this).get(ModificarUsuarioViewModel.class);

        binding = FragmentModificarUsuarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tituloModificarUsuario;
        ModificarUsuarioViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        Button btn_get= binding.getMu;
        Button btn_submit=binding.submitMu;
        TextView tw=binding.tituloCrearUsuario3;
        EditText user=binding.userMu;
        EditText name=binding.nameMu;
        EditText lastname=binding.lastnameMu;
        EditText nif=binding.nifMu;
        EditText salary=binding.salaryMu;
        EditText birthdate=binding.birthdateMu;
        EditText paymentday=binding.nifMu2;
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref=fb.collection("Users").document(user.getText().toString());
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String Name=documentSnapshot.getString("Name");
                        String LastName=documentSnapshot.getString("LastName");
                        String NIF=documentSnapshot.getString("NIF");
                        String Salary=documentSnapshot.getString("Base Salary");
                        String Birthdate=documentSnapshot.getString("Birthdate");
                        String Payment_day=documentSnapshot.getString("Payment Day");
                        String passwordd=documentSnapshot.getString("Password");
                        String horas_extraaa=documentSnapshot.getString("Extra Hours");
                        password=passwordd;
                        horas_extra=horas_extraaa;
                        if (Name!=null){
                            name.setText(Name);
                        }if (LastName!=null){
                            lastname.setText(LastName);
                        }if (NIF!=null){
                            nif.setText(NIF);
                        }
                        if (Salary!=null){
                            salary.setText(Salary);
                        }if (Birthdate!=null){
                            birthdate.setText(Birthdate);
                        }if (Payment_day!=null){
                            paymentday.setText(Payment_day);
                        }
                    }
                });
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidadorNIF.validador(nif.getText().toString())) {
                    DocumentReference ref = fb.collection("Users").document(user.getText().toString());
                    Map<String, Object> new_modify_user = new HashMap<>();
                    new_modify_user.put("Name", name.getText().toString());
                    new_modify_user.put("LastName", lastname.getText().toString());
                    new_modify_user.put("NIF", nif.getText().toString());
                    new_modify_user.put("Base Salary", salary.getText().toString());
                    new_modify_user.put("Birthdate", birthdate.getText().toString());
                    new_modify_user.put("Payment Day", paymentday.getText().toString());
                    new_modify_user.put("Password", password);
                    new_modify_user.put("Extra Hours", horas_extra);
                    ref.set(new_modify_user);
                    tw.setText("Modifications were successfully in User: " + user.getText().toString());
                }
                else {
                    tw.setText("invalid NIF");
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
