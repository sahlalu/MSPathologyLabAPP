package com.example.mspathologylab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter {

    private List<CardItem> mData;
    private Context mContext;

    public CardPagerAdapter(Context context, List<CardItem> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_item_layout, container, false);

        CardItem item = mData.get(position);

        ImageView imageView = view.findViewById(R.id.cardImageView);
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView subtitleTextView = view.findViewById(R.id.subtitleTextView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);
        Button addToCartButton = view.findViewById(R.id.addToCartButton);

        imageView.setImageResource(item.getImageResource());
        titleTextView.setText(item.getTitle());
        subtitleTextView.setText(item.getSubtitle());
        priceTextView.setText(item.getPrice());

        addToCartButton.setOnClickListener(v -> {
            addToCart(item);
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void addToCart(CardItem item) {
        // Add the item to the cart
        CartManager.addToCart(item);
        String toastMessage = mContext.getString(R.string.added_to_cart_message);
        Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT).show();
    }
}

