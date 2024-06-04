package com.example.emplostaff2.ui.Workforce;

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
import com.example.emplostaff2.databinding.FragmentWorkforceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class WorkforceFragment extends Fragment {

    private FragmentWorkforceBinding binding;
    private FirebaseFirestore fb;
    private Button btn_manager;
    private Button btn_staff;
    private TableLayout tabla;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkforceViewModel WorkforceViewModel =
                new ViewModelProvider(this).get(WorkforceViewModel.class);

        binding = FragmentWorkforceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fb = FirebaseFirestore.getInstance();
        btn_manager = view.findViewById(R.id.Button_manager_tl);
        btn_staff = view.findViewById(R.id.Button_staff_tl);
        tabla = view.findViewById(R.id.tl_tabla);

        CollectionReference ref = fb.collection("Users");

        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        tabla.removeAllViews();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String check_id = document.getId();
                            String[] check_id_array = document.getId().split("");
                            if (check_id_array[0].equals("M")) {
                                View registro = LayoutInflater.from(getContext()).inflate(R.layout.row_table_pn, null, false);
                                TextView nametl = registro.findViewById(R.id.name_tl);
                                TextView lastnametl = registro.findViewById(R.id.lastname_tl);
                                TextView idtl = registro.findViewById(R.id.Id_tl);
                                TextView salarytl = registro.findViewById(R.id.salary_tl);

                                String name = document.getString("Name");
                                String lastname = document.getString("LastName");
                                String base_salary = document.getString("Base Salary");
                                String penalties = document.getString("Penalties");
                                String extra_hours_ = document.getString("Extra Hours");

                                // Default values if any field is null
                                int baseSalary = (base_salary != null) ? Integer.parseInt(base_salary) : 0;
                                int penaltiesValue = (penalties != null) ? Integer.parseInt(penalties) : 0;
                                int extraHours = (extra_hours_ != null) ? Integer.parseInt(extra_hours_) * 10 : 0;

                                int total = baseSalary + extraHours - penaltiesValue;

                                nametl.setText(name);
                                lastnametl.setText(lastname);
                                idtl.setText(check_id);
                                salarytl.setText(String.valueOf(total));

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
                ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        tabla.removeAllViews();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String check_id = document.getId();
                            String[] check_id_array = document.getId().split("");
                            if (check_id_array[0].equals("S")) {
                                View registro = LayoutInflater.from(getContext()).inflate(R.layout.row_table_pn, null, false);
                                TextView nametl = registro.findViewById(R.id.name_tl);
                                TextView lastnametl = registro.findViewById(R.id.lastname_tl);
                                TextView idtl = registro.findViewById(R.id.Id_tl);
                                TextView salarytl = registro.findViewById(R.id.salary_tl);

                                String name = document.getString("Name");
                                String lastname = document.getString("LastName");
                                String base_salary = document.getString("Base Salary");
                                String penalties = document.getString("Penalties");
                                String extra_hours_ = document.getString("Extra Hours");

                                // Default values if any field is null
                                int baseSalary = (base_salary != null) ? Integer.parseInt(base_salary) : 0;
                                int penaltiesValue = (penalties != null) ? Integer.parseInt(penalties) : 0;
                                int extraHours = (extra_hours_ != null) ? Integer.parseInt(extra_hours_) * 10 : 0;

                                int total = baseSalary + extraHours - penaltiesValue;

                                nametl.setText(name);
                                lastnametl.setText(lastname);
                                idtl.setText(check_id);
                                salarytl.setText(String.valueOf(total));

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