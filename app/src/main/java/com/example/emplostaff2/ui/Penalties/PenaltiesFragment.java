package com.example.emplostaff2.ui.Penalties;

import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emplostaff2.MainActivity;
import com.example.emplostaff2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PenaltiesFragment extends Fragment {

    private PenaltiesViewModel mViewModel;

    public static PenaltiesFragment newInstance() {
        return new PenaltiesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PenaltiesViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_put_penalties, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText id=view.findViewById(R.id.id_pp);
        TextView name = view.findViewById(R.id.name_pp);
        TextView lastname = view.findViewById(R.id.lastname_pp2);
        TextView nif = view.findViewById(R.id.nif_pp3);
        TextView birthdate=view.findViewById(R.id.birthdate_pp);
        EditText reason=view.findViewById(R.id.reason_pp);
        EditText penalties = view.findViewById(R.id.penalty_pp);
        Button get=view.findViewById(R.id.get_pp);
        Button submit = view.findViewById(R.id.submit_pp);

        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = fb.collection("Users").document(id.getText().toString());
                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name_fb=documentSnapshot.getString("Name");
                        String lastname_fb=documentSnapshot.getString("LastName");
                        String nif_fb=documentSnapshot.getString("NIF");
                        String birthdate_fb=documentSnapshot.getString("Birthdate");

                        name.setText(name_fb);
                        lastname.setText(lastname_fb);
                        nif.setText(nif_fb);
                        birthdate.setText(birthdate_fb);
                    }
                });
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference ref=fb.collection("Penalties");
                ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Map<String, Object> newPenalty = new HashMap<>();
                        newPenalty.put("ID", id.getText().toString());
                        newPenalty.put("Sanction", penalties.getText().toString());
                        newPenalty.put("Name", reason.getText().toString());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate today=LocalDate.now();
                            newPenalty.put("Day", String.valueOf(today.getDayOfYear()));
                        }
                        String[] usersplit=id.getText().toString().split("");
                        String letraiuser=usersplit[0];
                        String id_pp="";
                        int ultimo_id=1;
                        for (QueryDocumentSnapshot document:task.getResult()){
                            if(document.getId().substring(0,1).equals(letraiuser) && document.getId().substring(0,8).equals(id.getText().toString())){
                                String[] list_string=document.getId().split("");
                                if (Integer.valueOf(list_string[9])>=ultimo_id){
                                    ultimo_id=Integer.valueOf(list_string[9])+1;
                                }
                            }
                        }
                        id_pp =id.getText().toString()+"-"+ultimo_id;
                        DocumentReference newupenalty = ref.document(id_pp);
                        newupenalty.set(newPenalty);
                    }
                });

            }
        });

    }
}