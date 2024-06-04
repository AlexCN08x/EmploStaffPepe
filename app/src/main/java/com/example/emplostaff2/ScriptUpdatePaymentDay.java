package com.example.emplostaff2;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;

public class ScriptUpdatePaymentDay {
    public static void script(){
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        CollectionReference dref=fb.collection("Users");
        dref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    String payday_total = "";
                    String payday = document.getString("Payment Day");
                    if (payday != null) {
                        String day = payday.substring(0, 2);
                        String month = payday.substring(3, 5);
                        String year = payday.substring(6, 10);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDate today = LocalDate.now();
                            String today_day = String.valueOf(today.getDayOfMonth());
                            String today_month = String.valueOf(today.getMonthValue());
                            String today_year = String.valueOf(today.getYear());
                            if (Integer.valueOf(today_month) < 10) {
                                today_month = "0" + today_month;
                            }
                            if (day.equals(today_day) && month.equals(today_month) && year.equals(today_year)) {
                                if (month.equals("12")) {
                                    month = "01";
                                    year = String.valueOf(Integer.valueOf(today_year) + 1);
                                    payday_total = day + "/" + month + "/" + year;
                                    updateSingleField("Users", document.getId(), "Payment Day", payday_total);
                                } else {
                                    month = String.valueOf(Integer.valueOf(today_month) + 1);
                                    if (Integer.valueOf(month) < 10) {
                                        month = "0" + month;
                                    }
                                    payday_total = day + "/" + month + "/" + year;
                                    updateSingleField("Users", document.getId(), "Payment Day", payday_total);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    private static void updateSingleField(String collectionPath, String documentId, String fieldName, Object newValue) {
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        fb.collection(collectionPath).document(documentId)
                .update(fieldName, newValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Field successfully updated
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error occurred while updating the field
                    }
                });
    }
}
