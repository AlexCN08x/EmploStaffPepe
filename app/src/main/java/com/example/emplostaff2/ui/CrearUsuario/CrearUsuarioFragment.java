package com.example.emplostaff2.ui.CrearUsuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.emplostaff2.ValidadorNIF;
import com.example.emplostaff2.databinding.FragmentCrearUsuarioBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CrearUsuarioFragment extends Fragment {

    private FragmentCrearUsuarioBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CrearUsuarioViewModel crearUsuarioViewModel =
                new ViewModelProvider(this).get(CrearUsuarioViewModel.class);

        binding = FragmentCrearUsuarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tituloCrearUsuario;
        crearUsuarioViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        Button btn_aceptar=binding.submitMu;
        RadioButton button_boos=binding.ButtonBoss;
        RadioButton button_manager=binding.ButtonManager;
        RadioButton button_staff=binding.ButtonStaff;
        TextView tw=binding.tituloCrearUsuario2;
        EditText name=binding.nameMu;
        EditText lastname=binding.lastnameMu;
        EditText nif=binding.salaryMu;
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference ref=fb.collection("Users");
                if (button_boos.isChecked()){
                    ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (ValidadorNIF.validador(nif.getText().toString()) == true) {
                                int ultimo_id = 1;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getId().substring(0, 1).equals("B")) {
                                        String[] list_string = document.getId().split("");
                                        if (Integer.valueOf(list_string[7]) >= ultimo_id) {
                                            ultimo_id = Integer.valueOf(list_string[7]) + 1;
                                        }
                                    }
                                }
                                if (!nif.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()) {
                                    String id = "B-00000" + String.valueOf(ultimo_id);
                                    String password = "admin";
                                    Map<String, Object> newUser = new HashMap<>();
                                    newUser.put("Password", password);
                                    Integer longe=nif.getText().toString().length();
                                    if (nif.getText().toString().substring(8,9).equals("0") || nif.getText().toString().substring(8,9).equals("1")  || nif.getText().toString().substring(8,9).equals("2")  || nif.getText().toString().substring(8,9).equals("3")  || nif.getText().toString().substring(8,9).equals("4")  || nif.getText().toString().substring(8,9).equals("5")  || nif.getText().toString().substring(8,9).equals("6")  || nif.getText().toString().substring(8,9).equals("7")  || nif.getText().toString().substring(8,9).equals("8")  || nif.getText().toString().substring(8,9).equals("9")){
                                        tw.setText("Invalid NIF");
                                    }else {
                                        newUser.put("NIF", nif.getText().toString());
                                    }
                                    newUser.put("Name", name.getText().toString());
                                    newUser.put("LastName", lastname.getText().toString());
                                    newUser.put("Base Salary", "0");
                                    newUser.put("Penalties", "0");
                                    newUser.put("Extra Hours", "0");
                                    DocumentReference newuserref = ref.document(id);
                                    newuserref.set(newUser);
                                    DocumentReference derf = fb.collection("Users").document(id);
                                    derf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            tw.setText("User created successfully");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            tw.setText("User creation failed");
                                        }
                                    });
                                } else {
                                    tw.setText("You must write in all the fields");
                                }
                            }else {
                                tw.setText("Invalid NIF");
                            }
                        }
                    });
                }
                if(button_manager.isChecked()){
                    ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (ValidadorNIF.validador(nif.getText().toString()) == true) {
                                int ultimo_id = 1;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getId().substring(0, 1).equals("M")) {
                                        String[] list_string = document.getId().split("");
                                        if (Integer.valueOf(list_string[7]) >= ultimo_id) {
                                            ultimo_id = Integer.valueOf(list_string[7]) + 1;
                                        }
                                    }
                                }
                                if (!nif.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()) {
                                    String id = "M-00000" + String.valueOf(ultimo_id);
                                    String password = "admin";
                                    Map<String, Object> newUser = new HashMap<>();
                                    newUser.put("Password", password);
                                    newUser.put("NIF", nif.getText().toString());
                                    newUser.put("Name", name.getText().toString());
                                    newUser.put("LastName", lastname.getText().toString());
                                    newUser.put("Base Salary", "0");
                                    newUser.put("Penalties", "0");
                                    newUser.put("Extra Hours", "0");
                                    DocumentReference newuserref = ref.document(id);
                                    newuserref.set(newUser);
                                    DocumentReference derf = fb.collection("Users").document(id);
                                    derf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            tw.setText("User created successfully");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            tw.setText("User creation failed");
                                        }
                                    });
                                } else {
                                    tw.setText("You must write in all the fields");
                                }
                            }else {
                                tw.setText("Invalid NIF");
                            }
                        }
                    });
                }
                if (button_staff.isChecked()){
                    ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (ValidadorNIF.validador(nif.getText().toString()) == true) {
                                int ultimo_id = 1;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getId().substring(0, 1).equals("S")) {
                                        String[] list_string = document.getId().split("");
                                        if (Integer.valueOf(list_string[7]) >= ultimo_id) {
                                            ultimo_id = Integer.valueOf(list_string[7]) + 1;
                                        }
                                    }
                                }
                                if (!nif.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()) {
                                    String id = "S-00000" + String.valueOf(ultimo_id);
                                    String password = "admin";
                                    Map<String, Object> newUser = new HashMap<>();
                                    newUser.put("Password", password);
                                    newUser.put("NIF", nif.getText().toString());
                                    newUser.put("Name", name.getText().toString());
                                    newUser.put("LastName", lastname.getText().toString());
                                    newUser.put("Base Salary", "0");
                                    newUser.put("Extra Hours", "0");
                                    DocumentReference newuserref = ref.document(id);
                                    newuserref.set(newUser);
                                    DocumentReference derf = fb.collection("Users").document(id);
                                    derf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            tw.setText("User created successfully");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            tw.setText("User creation failed");
                                        }
                                    });
                                } else {
                                    tw.setText("You must write in all the fields");
                                }
                            }else {
                                tw.setText("Invalid NIF");
                            }

                        }
                    });
                }else{

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
