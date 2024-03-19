package com.example.mspathologylab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqVH> {

    private static final String TAG = "FaqAdapter";
    List<Faq> faqList;

    public FaqAdapter(List<Faq> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FaqVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_row, parent, false);
        return new FaqVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqVH holder, int position) {

        Faq faq = faqList.get(position);
        holder.titleTextView.setText(faq.getQuestion());
        holder.answerTextView.setText(faq.getAnswer());

        boolean isExpanded = faqList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    class FaqVH extends RecyclerView.ViewHolder {

        private static final String TAG = "FaqVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, answerTextView;

        public FaqVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);


            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Faq faq = faqList.get(getAdapterPosition());
                    faq.setExpanded(!faq.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }
}

