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

public class SVGCardPagerAdapter extends PagerAdapter {

    private List<SVGCardItem> mData;
    private Context mContext;

    public SVGCardPagerAdapter(Context context, List<SVGCardItem> data) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.svg_card_item_layout, container, false);

        SVGCardItem item = mData.get(position);

        ImageView imageView = view.findViewById(R.id.svgImageView);
        TextView titleTextView = view.findViewById(R.id.testNameTextView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);
        Button addToCartButton = view.findViewById(R.id.addToCartButton);

        imageView.setImageResource(item.getIconResource());
        titleTextView.setText(item.getTestName());
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

    private void addToCart(SVGCardItem item) {
        // Add the item to the cart
        CartManager2.addToCart(item);
        String toastMessage = mContext.getString(R.string.added_to_cart_message);
        Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT).show();
    }
}

