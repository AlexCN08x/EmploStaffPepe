package com.example.emplostaff2.ui.slideshow;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.emplostaff2.R;
import com.example.emplostaff2.databinding.FragmentSlideshowBinding;
import com.example.emplostaff2.ui.shared.SharedViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private TextView textViewDate;
    private EditText editTextExtraHours;
    private TextView textViewTotalHours;
    private TextView textViewPayment;
    private SharedViewModel sharedViewModel;
    private String hora_seleccionada;
    private Button buttonOpenCalendar;
    private FirebaseFirestore fr;
    private String selectedDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        textViewDate = rootView.findViewById(R.id.textViewDate);

        Spinner spinner = rootView.findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dropdown_options, R.layout.custom_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Set the listener for the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hora_seleccionada=String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button buttonOpenCalendar = rootView.findViewById(R.id.buttonOpenCalendar);
        buttonOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button buttonSaveHours = rootView.findViewById(R.id.buttonSaveHours);
        EditText et=rootView.findViewById(R.id.id_pp);
        fr=FirebaseFirestore.getInstance();
        CollectionReference cred=fr.collection("ExtraHours");
        buttonSaveHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference cred=fr.collection("ExtraHours");
                cred.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Map<String, Object> newPenalty = new HashMap<>();
                        newPenalty.put("Hours",hora_seleccionada);
                        newPenalty.put("Day",selectedDate);
                        newPenalty.put("State","To Do");
                        String[] usersplit=et.getText().toString().split("");
                        String letraiuser=usersplit[0];
                        String id_pp="";
                        int ultimo_id=1;
                        for (QueryDocumentSnapshot document:task.getResult()){
                            if(document.getId().substring(0,1).equals(letraiuser) && document.getId().substring(0,8).equals(et.getText().toString())){
                                String[] list_string=document.getId().split("");
                                if (Integer.valueOf(list_string[9])>=ultimo_id){
                                    ultimo_id=Integer.valueOf(list_string[9])+1;
                                }
                            }
                        }
                        id_pp =et.getText().toString()+"-"+ultimo_id;
                        DocumentReference newupenalty = cred.document(id_pp);
                        newupenalty.set(newPenalty);
                    }
                });
            }
        });
        return rootView;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                textViewDate.setText(selectedDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}