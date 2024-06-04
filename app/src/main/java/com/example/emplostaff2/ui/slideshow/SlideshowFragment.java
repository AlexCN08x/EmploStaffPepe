package com.example.emplostaff2.ui.slideshow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Calendar;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private TextView textViewDate;
    private EditText editTextExtraHours;
    private TextView textViewTotalHours;
    private TextView textViewPayment;
    private SharedViewModel sharedViewModel;
    private String hora_seleccionada;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        textViewDate = rootView.findViewById(R.id.textViewDate);
        textViewTotalHours = rootView.findViewById(R.id.textViewTotalHours);

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
        TextView hola=rootView.findViewById(R.id.textViewTotalHours);
        Button buttonSaveHours = rootView.findViewById(R.id.buttonSaveHours);
        buttonSaveHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hola.setText(hora_seleccionada);
            }
        });
        return rootView;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
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