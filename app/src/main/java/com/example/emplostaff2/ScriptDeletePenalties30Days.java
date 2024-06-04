package com.example.emplostaff2;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.Date;

public class ScriptDeletePenalties30Days {
    public static void script(){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        CollectionReference cref= fb.collection("Penalties");
        cref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String creation_day=document.getString("Day");
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        LocalDate today = LocalDate.now();
                        int fecha=0;
                        if (Integer.valueOf(creation_day)>335 && today.getDayOfYear()<31){
                            int paso1=365-Integer.valueOf(creation_day);
                            int paso2=30-paso1;
                            if (paso2==today.getDayOfYear()){
                                String documentId = document.getId();
                                // Delete the document
                                deleteDocument(cref, documentId);
                            }
                        }else {
                            int total=today.getDayOfYear()-Integer.valueOf(creation_day);
                            if (total>=30){
                                String documentId = document.getId();
                                // Delete the document
                                deleteDocument(cref, documentId);
                            }

                        }
                    }
                }
            }
        });
    }
    private static void deleteDocument(CollectionReference ref, String documentId) {
        ref.document(documentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}
