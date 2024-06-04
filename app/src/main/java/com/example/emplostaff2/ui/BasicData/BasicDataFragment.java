package com.example.emplostaff2.ui.BasicData;

import static android.os.Build.VERSION_CODES.R;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emplostaff2.MainActivity;
import com.example.emplostaff2.databinding.FragmentBasicDataBinding;
import com.example.emplostaff2.databinding.FragmentHelpBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class BasicDataFragment extends Fragment {
    private TextView textView;
    private TextView name;
    private TextView lastname;
    private TextView nif;
    private TextView birthdate;
    private TextView role;
    private Handler handler;
    private Runnable runnable;
    private String[] phrases = {
            "GREAT THINGS NEVER COME FROM COMFORT ZONES.",
            "SUCCESS IS NOT THE KEY TO HAPPINESS. HAPPINESS IS THE KEY TO SUCCESS",
            "ALONE WE CAN DO SO LITTLE; TOGETHER WE CAN DO SO MUCH",
            "THE ONLY WAY TO DO GREAT WORK IS TO LOVE WHAT YOU DO",
            "YOUR HARD WORK AND DEDICATION ARE TRULY APPRECIATED"
    };
    private int phraseIndex = 0;

    private BasicDataViewModel mViewModel;
        private FragmentBasicDataBinding binding;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            BasicDataViewModel basicDataViewModel =
                    new ViewModelProvider(this).get(BasicDataViewModel.class);

            binding = FragmentBasicDataBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            final TextView textView = binding.randomText;
            return root;
        }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = binding.randomText;
        name=binding.nameBd;
        lastname=binding.lastnameBd;
        nif=binding.nifBd;
        birthdate=binding.birthdateBd;
        role=binding.roleBd;
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        DocumentReference dref=fb.collection("Users").document(MainActivity.Id);
        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String namebd=documentSnapshot.getString("Name");
                String lastnamebd=documentSnapshot.getString("LastName");
                String nifbd=documentSnapshot.getString("NIF");
                String birthdatebd=documentSnapshot.getString("Birthdate");

                name.setText(namebd);
                lastname.setText(lastnamebd);
                nif.setText(nifbd);
                birthdate.setText(birthdatebd);
                if (MainActivity.Id.substring(0,1).equals("S")){
                    role.setText("Staff");
                }                if (MainActivity.Id.substring(0,1).equals("M")){
                    role.setText("Manager");
                }                if (MainActivity.Id.substring(0,1).equals("B")){
                    role.setText("Boss");
                }

            }
        });
        handler = new Handler(Looper.getMainLooper());

        runnable = new Runnable() {
            @Override
            public void run() {
                // Change the text of the TextView
                textView.setText(phrases[phraseIndex]);
                // Update the index to the next phrase
                phraseIndex = (phraseIndex + 1) % phrases.length;
                // Schedule the next change in 5 seconds
                handler.postDelayed(this, 5000);
            }
        };
        // Start the first change
        handler.post(runnable);
    }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }