package com.example.emplostaff2.ui.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.emplostaff2.R;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> messageList;

    public ChatAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.textViewMessage.setText(message.getText());
        holder.textViewSender.setText(message.getSender());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessage;
        TextView textViewSender;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.text_view_message);
            textViewSender = itemView.findViewById(R.id.text_view_sender);
        }
    }
}
