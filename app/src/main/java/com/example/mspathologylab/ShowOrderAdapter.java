package com.example.mspathologylab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowOrderAdapter extends RecyclerView.Adapter<ShowOrderAdapter.ShowOrderVH> {

    private static final String TAG = "ShowOrderAdapter";
    List<ShowOrder> showorderList;

    public ShowOrderAdapter(List<ShowOrder> showorderList) {
        this.showorderList = showorderList;
    }

    @NonNull
    @Override
    public ShowOrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_order_row, parent, false);
        return new ShowOrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowOrderVH holder, int position) {

        ShowOrder showOrder = showorderList.get(position);
        holder.titleTextView.setText(showOrder.getPatientName());
        holder.contactTextView.setText(showOrder.getContact());
        holder.drawnTextView.setText(showOrder.getDrawn());
        holder.emailTextView.setText(showOrder.getEmail());
        holder.orderTextView.setText(showOrder.getOrderTime());
        holder.testTextView.setText(showOrder.getTests());

        boolean isExpanded = showorderList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return showorderList.size();
    }

    class ShowOrderVH extends RecyclerView.ViewHolder {

        private static final String TAG = "ShowOrderVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, contactTextView, drawnTextView, emailTextView, orderTextView, testTextView;

        public ShowOrderVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            contactTextView = itemView.findViewById(R.id.contactTextView);
            drawnTextView = itemView.findViewById(R.id.drawnTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            orderTextView = itemView.findViewById(R.id.orderTextView);
            testTextView = itemView.findViewById(R.id.testTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ShowOrder showOrder = showorderList.get(position);
                        showOrder.setExpanded(!showOrder.isExpanded());
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }
}
