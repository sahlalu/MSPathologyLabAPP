package com.example.mspathologylab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> cartItems;
    private Context context;



    public CartAdapter(Context context, List<Object> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;

        // Determine which type of ViewHolder to inflate based on viewType
        if (viewType == TYPE_CARD_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
            return new CardViewHolder(view);
        } else if (viewType == TYPE_SVG_CARD_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.svg_cart_item_layout, parent, false);
            return new SVGCardViewHolder(view);
        } else if (viewType == TYPE_ITEM_TEST) {
            view = LayoutInflater.from(context).inflate(R.layout.test_cart_item_layout, parent, false);
            return new TestCardViewHolder(view);
        }

        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = cartItems.get(position);

        // Bind data based on the type of item
        if (holder instanceof CardViewHolder) {
            ((CardViewHolder) holder).bind((CardItem) item);
        } else if (holder instanceof SVGCardViewHolder) {
            ((SVGCardViewHolder) holder).bind((SVGCardItem) item);
        }else if (holder instanceof TestCardViewHolder) {
            ((TestCardViewHolder) holder).bind((TestCardItem) item);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = cartItems.get(position);
        if (item instanceof CardItem) {
            return TYPE_CARD_ITEM;
        } else if (item instanceof SVGCardItem) {
            return TYPE_SVG_CARD_ITEM;
        }else if (item instanceof TestCardItem) {
            return TYPE_ITEM_TEST;
        }
        return super.getItemViewType(position);
    }


    // Constants for view types
    private static final int TYPE_CARD_ITEM = 0;
    private static final int TYPE_SVG_CARD_ITEM = 1;
    private static final int TYPE_ITEM_TEST = 2;




    // ViewHolder for CardItem
    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView titleTextView, subtitleTextView, priceTextView;
        Button removeButton;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

        public void bind(CardItem item) {
            // Bind data to views
            itemImageView.setImageResource(item.getImageResource());
            titleTextView.setText(item.getTitle());
            priceTextView.setText(item.getPrice());
            subtitleTextView.setText(item.getSubtitle());

            // Remove button click listener
            removeButton.setOnClickListener(v -> removeItem(getAdapterPosition()));
        }
    }

    // ViewHolder for SVGCardItem
    public class SVGCardViewHolder extends RecyclerView.ViewHolder {
        ImageView svgItemImageView;
        TextView svgTitleTextView, svgPriceTextView;
        Button svgRemoveButton;

        public SVGCardViewHolder(@NonNull View itemView) {
            super(itemView);
            svgItemImageView = itemView.findViewById(R.id.svgItemImageView);
            svgTitleTextView = itemView.findViewById(R.id.svgTitleTextView);
            svgPriceTextView = itemView.findViewById(R.id.svgPriceTextView);
            svgRemoveButton = itemView.findViewById(R.id.svgRemoveButton);
        }

        public void bind(SVGCardItem item) {
            // Bind data to views for SVGCardItem
            svgItemImageView.setImageResource(item.getIconResource());
            svgTitleTextView.setText(item.getTestName());
            svgPriceTextView.setText(item.getPrice());

            // Remove button click listener
            svgRemoveButton.setOnClickListener(v -> removeItem(getAdapterPosition()));
        }
    }

    public class TestCardViewHolder extends RecyclerView.ViewHolder {
        ImageView svgItemImageView;
        TextView svgTitleTextView, svgPriceTextView;
        Button svgRemoveButton;

        public TestCardViewHolder(@NonNull View itemView) {
            super(itemView);
            svgItemImageView = itemView.findViewById(R.id.svgItemImageView);
            svgTitleTextView = itemView.findViewById(R.id.svgTitleTextView);
            svgPriceTextView = itemView.findViewById(R.id.svgPriceTextView);
            svgRemoveButton = itemView.findViewById(R.id.svgRemoveButton);
        }

        public void bind(TestCardItem item) {
            // Bind data to views for SVGCardItem
            svgItemImageView.setImageResource(item.getIconResource());
            svgTitleTextView.setText(item.getTestName());
            svgPriceTextView.setText(item.getPrice());

            // Remove button click listener
            svgRemoveButton.setOnClickListener(v -> removeItem(getAdapterPosition()));
        }
    }





    // Method to remove item from the list
    private void removeItem(int position) {
        cartItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartItems.size());
        CartManager.removeItem(position);
        CartManager2.removeItem(position);
        CartManager3.removeItem(position);

    }
}

