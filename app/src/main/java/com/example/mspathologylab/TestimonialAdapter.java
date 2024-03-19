package com.example.mspathologylab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class TestimonialAdapter extends PagerAdapter {
    private Context context;
    private List<Testimonial> testimonials;

    public TestimonialAdapter(Context context, List<Testimonial> testimonials) {
        this.context = context;
        this.testimonials = testimonials;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_testimonial, container, false);

        Testimonial testimonial = testimonials.get(position);

        TextView starsTextView = view.findViewById(R.id.starsTextView);
        TextView commentTextView = view.findViewById(R.id.commentTextView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        ImageView roundedImageView = view.findViewById(R.id.roundedImageView);


        starsTextView.setText(generateStarsString(testimonial.getStars()));
        commentTextView.setText(testimonial.getComment());
        nameTextView.setText(testimonial.getName());
        roundedImageView.setImageResource(testimonial.getRoundimage());

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return testimonials.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private String generateStarsString(int stars) {
        StringBuilder starsString = new StringBuilder();
        for (int i = 0; i < stars; i++) {
            starsString.append("★");
        }
        return starsString.toString();
    }
}