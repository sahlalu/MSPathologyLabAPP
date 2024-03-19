package com.example.mspathologylab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.CenterVH> {

    private static final String TAG = "CenterAdapter";
    List<Center> centerList;

    public CenterAdapter(List<Center> centerList) {
        this.centerList = centerList;
    }

    @NonNull
    @Override
    public CenterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.center_row, parent, false);
        return new CenterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CenterVH holder, int position) {

        Center center = centerList.get(position);
        holder.titleTextView.setText(center.getCenter());
        holder.yearTextView.setText(center.getContact());
        holder.ratingTextView.setText(center.getOpentill());
        holder.plotTextView.setText(center.getAddress());

        boolean isExpanded = centerList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return centerList.size();
    }

    class CenterVH extends RecyclerView.ViewHolder {

        private static final String TAG = "CenterVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, yearTextView, ratingTextView, plotTextView;

        public CenterVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            plotTextView = itemView.findViewById(R.id.addressTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);


            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Center center = centerList.get(getAdapterPosition());
                    center.setExpanded(!center.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }
}
