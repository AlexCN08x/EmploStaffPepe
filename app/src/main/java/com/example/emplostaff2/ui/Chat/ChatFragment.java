package com.example.emplostaff2.ui.Chat;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emplostaff2.MainActivity;
import com.example.emplostaff2.R;
import com.example.emplostaff2.ui.Chat.ChatAdapter;
import com.example.emplostaff2.ui.Chat.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentChange;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ChatFragment extends Fragment {

    private FirebaseFirestore db;
    private CollectionReference messagesRef;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        db = FirebaseFirestore.getInstance();
        messagesRef = db.collection("messages");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        recyclerView.setAdapter(chatAdapter);

        EditText editTextMessage = view.findViewById(R.id.edit_text_message);
        Button buttonSend = view.findViewById(R.id.button_send);

        buttonSend.setOnClickListener(v -> {
            String messageText = editTextMessage.getText().toString();
            if (!messageText.isEmpty()) {
                sendMessage(messageText);
                editTextMessage.setText("");
            }
        });

        messagesRef.orderBy("timestamp").addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }

            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    Message message = dc.getDocument().toObject(Message.class);
                    messageList.add(message);
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                }
            }
        });

        return view;
    }

    private void sendMessage(String text) {

        String sender = MainActivity.nombre+" "+MainActivity.apellido+" "+"("+MainActivity.Id.substring(0,1)+")";
        long timestamp = System.currentTimeMillis();
        Message message = new Message(text, sender, timestamp);
        messagesRef.add(message);
    }
}