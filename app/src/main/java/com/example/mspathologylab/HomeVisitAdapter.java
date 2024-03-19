package com.example.mspathologylab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeVisitAdapter extends RecyclerView.Adapter<HomeVisitAdapter.HomeVisitVH> {

    private static final String TAG = "HomeVisitAdapter";
    List<HomeVisit> homeVisitList;

    public HomeVisitAdapter(List<HomeVisit> homeVisitList) {
        this.homeVisitList = homeVisitList;
    }

    @NonNull
    @Override
    public HomeVisitVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_visit_row, parent, false);
        return new HomeVisitVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVisitVH holder, int position) {

        HomeVisit showOrder = homeVisitList.get(position);
        holder.titleTextView.setText(showOrder.getName());
        holder.emailTextView.setText(showOrder.getEmail());
        holder.contactTextView.setText(showOrder.getContact());
        holder.testTextView.setText(showOrder.getAddress());

        boolean isExpanded = homeVisitList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return homeVisitList.size();
    }

    class HomeVisitVH extends RecyclerView.ViewHolder {

        private static final String TAG = "HomeVisitVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, contactTextView, emailTextView, testTextView;

        public HomeVisitVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            contactTextView = itemView.findViewById(R.id.contactTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            testTextView = itemView.findViewById(R.id.testTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        HomeVisit showOrder = homeVisitList.get(position);
                        showOrder.setExpanded(!showOrder.isExpanded());
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }
}
