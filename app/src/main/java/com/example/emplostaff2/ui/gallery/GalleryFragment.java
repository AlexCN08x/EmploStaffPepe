package com.example.emplostaff2.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.emplostaff2.MainActivity;
import com.example.emplostaff2.databinding.FragmentGalleryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private static Integer Penalties_p = 0;
    private GalleryViewModel GalleryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel GalleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView expected_salary = binding.textView4;
        TextView expected_base_salary = binding.textView6;
        TextView penalties = binding.textView8;
        TextView extra_hours = binding.textView10;
        TextView pay_day = binding.textView12;

        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference ref = fb.collection("Users").document(MainActivity.Id);
        CollectionReference cref = fb.collection("Penalties");
        cref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Penalties_p = 0;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getId().substring(0, 8).equals(MainActivity.Id)) {
                        Penalties_p = Penalties_p + Integer.valueOf(document.getString("Sanction"));
                    }
                }
            }
        });

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String base_salary = documentSnapshot.getString("Base Salary");
                String extra_hours_ = documentSnapshot.getString("Extra Hours");
                String payment__day = documentSnapshot.getString("Payment Day");

                // Retrieve hours and payment from SharedPreferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
                int extraHours = sharedPreferences.getInt("extraHours", 0);
                int totalPayment = sharedPreferences.getInt("totalPayment", 0);

                Integer horasextra = extraHours * 10;
                expected_base_salary.setText(base_salary + "€");
                penalties.setText("- " + Penalties_p);
                extra_hours.setText(horasextra + "€");
                pay_day.setText(payment__day);
                Integer total = Integer.valueOf(base_salary) + totalPayment - Penalties_p;
                expected_salary.setText(total + "€");
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
