package com.example.emplostaff2.ui.Help;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.emplostaff2.R;
import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {
    private List<FAQItem> faqList;

    public FAQAdapter(List<FAQItem> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_help, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        FAQItem faqItem = faqList.get(position);
        holder.question.setText(faqItem.getQuestion());
        holder.answer.setText(faqItem.getAnswer());

        holder.itemView.setOnClickListener(v -> {
            if (holder.answer.getVisibility() == View.GONE) {
                holder.answer.setVisibility(View.VISIBLE);
            } else {
                holder.answer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public static class FAQViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView answer;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
        }
    }
}
