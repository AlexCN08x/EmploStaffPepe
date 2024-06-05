package com.example.emplostaff2;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.Year;

public class ScriptDoneExtraHours {
    public static void script(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        CollectionReference cref= fb.collection("ExtraHours");
        cref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document:task.getResult()){
                    String day = document.getString("Day");
                        String dayy = day.substring(0, 2);
                        String month = day.substring(3, 5);
                        String year = day.substring(6, 10);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDate today = LocalDate.now();
                            String today_day = String.valueOf(today.getDayOfMonth());
                            if (Integer.valueOf(today_day)<10){
                                today_day="0"+today_day;
                            }
                            String today_month = String.valueOf(today.getMonthValue());
                            if (Integer.valueOf(today_month)<10){
                                today_month="0"+today_month;
                            }
                            String today_year = String.valueOf(today.getYear());

                            if ( today_year.equals(year) && Integer.valueOf(month)<Integer.valueOf(today_month)) {
                                updateSingleField("ExtraHours", document.getId(), "State", "Done");
                            }
                            if (today_year.equals(year) && today_month.equals(month) && Integer.valueOf(dayy)<Integer.valueOf(today_day)) {
                                updateSingleField("ExtraHours", document.getId(), "State", "Done");
                            }
                            if (Integer.valueOf(year)<Integer.valueOf(today_year)) {
                                updateSingleField("ExtraHours", document.getId(), "State", "Done");
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
