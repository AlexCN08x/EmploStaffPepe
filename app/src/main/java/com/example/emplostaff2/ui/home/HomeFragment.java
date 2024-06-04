package com.example.emplostaff2.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.emplostaff2.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the CalendarView
        CalendarView calendarView = binding.calendarView;
        TextView textViewFecha = binding.variableCobro;

        // Set a date change listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Ensure month is correctly formatted (month + 1 since month is 0-based)
                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = null;

                try {
                    date = format.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date != null) {
                    String formattedDate = format.format(date);
                    textViewFecha.setText(formattedDate);

                    // Check for specific events on the selected date
                    if (formattedDate.equals("29/05/2024")) {
                        // Show your event for this date
                        // You can customize this to show different events for different dates
                        showEventDialog("Vacaciones", "Vacaciones de M-000002");
                    }
                }
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = null;

                try {
                    date = format.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date != null) {
                    String formattedDate = format.format(date);
                    textViewFecha.setText("");

                    Log.d("HomeFragment", "Selected date: " + formattedDate); // Log the selected date

                    // Check for specific events on the selected date
                    if (formattedDate.equals("16/06/2024")) {
                        // Show your event for this date
                        Log.d("HomeFragment", "Event found for date: " + formattedDate); // Log if the event matches
                        showEventDialog("Vacaciones", "Vacaciones de M-000002");
                    } else if (formattedDate.equals("23/06/2024")) {
                        Log.d("HomeFragment", "Event found for date: " + formattedDate); // Log if the event matches
                        showEventDialog("Vacaciones", "Vacaciones de S-000002");
                    } else if (formattedDate.equals("30/06/2024")) {
                    Log.d("HomeFragment", "Event found for date: " + formattedDate); // Log if the event matches
                    showEventDialog("Vacaciones", "Vacaciones de B-000002");
                }
                }
            }
        });

        return root;
    }

    private void showEventDialog(String title, String description) {
        // Implement a method to show a dialog with the event details
        // For example, using AlertDialog
        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

