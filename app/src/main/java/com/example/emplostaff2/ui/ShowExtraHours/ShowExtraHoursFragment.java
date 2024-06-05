package com.example.emplostaff2.ui.ShowExtraHours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.emplostaff2.R;
import com.example.emplostaff2.databinding.FragmentShowExtraHoursBinding;
import com.example.emplostaff2.databinding.FragmentWorkforceBinding;
import com.example.emplostaff2.ui.ShowExtraHours.ShowExtraHoursViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;

public class ShowExtraHoursFragment extends Fragment {

    private FragmentShowExtraHoursBinding binding;
    private FirebaseFirestore fb;
    private Button btn_manager;
    private Button btn_staff;
    private TableLayout tabla;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShowExtraHoursViewModel ShowExtraHoursViewModel =
                new ViewModelProvider(this).get(ShowExtraHoursViewModel.class);

        binding = FragmentShowExtraHoursBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fb=FirebaseFirestore.getInstance();
        btn_manager=view.findViewById(R.id.Button_manager_tl);
        btn_staff=view.findViewById(R.id.Button_staff_tl);
        tabla=view.findViewById(R.id.tl_tabla);

        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference cref=fb.collection("ExtraHours");
                cref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        tabla.removeAllViews();
                        for (QueryDocumentSnapshot document:task.getResult()){
                            String hour=document.getString("Hours");
                            String dayu=document.getString("Day");
                            String state=document.getString("State");
                            String id=document.getId();
                            if (state.equals("ToDo")){
                                View registro = LayoutInflater.from(getContext()).inflate(R.layout.row_table_pn, null, false);
                                TextView nametl = registro.findViewById(R.id.name_tl);
                                TextView lastnametl = registro.findViewById(R.id.lastname_tl);
                                TextView idtl = registro.findViewById(R.id.Id_tl);
                                TextView salarytl = registro.findViewById(R.id.salary_tl);
                                nametl.setText(id);
                                lastnametl.setText(dayu);
                                idtl.setText(hour);
                                salarytl.setText(String.valueOf(state));

                                tabla.addView(registro);

                            }
                        }
                    }
                });
            }
        });
        btn_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference cref=fb.collection("ExtraHours");
                cref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        tabla.removeAllViews();
                        for (QueryDocumentSnapshot document:task.getResult()){
                            String hour=document.getString("Hours");
                            String dayu=document.getString("Day");
                            String state=document.getString("State");
                            String id=document.getId();
                            if (state.equals("Done")){
                                View registro = LayoutInflater.from(getContext()).inflate(R.layout.row_table_pn, null, false);
                                TextView nametl = registro.findViewById(R.id.name_tl);
                                TextView lastnametl = registro.findViewById(R.id.lastname_tl);
                                TextView idtl = registro.findViewById(R.id.Id_tl);
                                TextView salarytl = registro.findViewById(R.id.salary_tl);
                                nametl.setText(id);
                                lastnametl.setText(dayu);
                                idtl.setText(hour);
                                salarytl.setText(String.valueOf(state));

                                tabla.addView(registro);

                            }
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}