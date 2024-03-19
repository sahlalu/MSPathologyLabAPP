package com.example.mspathologylab;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UserHomePage extends AppCompatActivity {

    RecyclerView iconRecyclerView, icon2RecyclerView ;

    ViewPager mViewPager;
    ViewPager svgViewPager1, svgViewPager2, svgViewPager3, svgViewPager4; // Declare ViewPager instances for each pair
    int[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    ImageSliderAdapter mViewPagerAdapter;
    private static final int AUTO_SLIDE_DELAY = 2000;

    private final Handler autoSlideHandler = new Handler();
    private final Runnable autoSlideRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            int totalItems = mViewPagerAdapter.getCount();

            if (currentItem < totalItems - 1) {
                mViewPager.setCurrentItem(currentItem + 1);
            } else {
                mViewPager.setCurrentItem(0);
            }

            autoSlideHandler.postDelayed(this, AUTO_SLIDE_DELAY);
        }
    };

    private DatabaseHelper dbHelper;
    private EditText editTextName, editTextEmail, editTextNumber;

    private Spinner spinner;
    private Button readmore;

    private ViewPager viewPager;
    private TestimonialAdapter testimonialAdapter;
    private List<Testimonial> testimonials;
    private int currentPage = 0;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        readmore = findViewById(R.id.readmore);
        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomePage.this, FAQActivity.class));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), UserHomePage.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_search) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_cart) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        viewPager = findViewById(R.id.testimonialViewPager);
        testimonials = generateTestimonials(); // Your list of testimonials

        testimonialAdapter = new TestimonialAdapter(this, testimonials);
        viewPager.setAdapter(testimonialAdapter);

        // Auto switch testimonials every 5 seconds
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (currentPage == testimonials.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 5000);


        iconRecyclerView = findViewById(R.id.iconRecyclerView);
        IconRecyclerAdapter iconAdapter = createIconAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        iconRecyclerView.setLayoutManager(layoutManager);
        iconRecyclerView.setAdapter(iconAdapter);

        iconAdapter.setOnItemClickListener(new IconRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click, redirect to SearchActivity
                Intent intent = new Intent(UserHomePage.this, SearchActivity.class);
                startActivity(intent);
            }
        });


        icon2RecyclerView = findViewById(R.id.icon2RecyclerView);
        Icon2RecyclerAdapter iconAdapter2 = createIconAdapter2();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        icon2RecyclerView.setLayoutManager(layoutManager2);
        icon2RecyclerView.setAdapter(iconAdapter2);

        iconAdapter2.setOnItemClickListener(new Icon2RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click, redirect to SearchActivity
                Intent intent = new Intent(UserHomePage.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mViewPager = findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ImageSliderAdapter(UserHomePage.this, images);
        mViewPager.setAdapter(mViewPagerAdapter);
        startAutoSlide();

        Spinner healthCheckupSpinner = findViewById(R.id.healthCheckupSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.health_checkup_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        healthCheckupSpinner.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);
        editTextName = findViewById(R.id.fullNameEditText);
        editTextEmail = findViewById(R.id.emailEditText);
        editTextNumber = findViewById(R.id.mobileEditText);
        spinner = findViewById(R.id.healthCheckupSpinner);


        Button registerButton = findViewById(R.id.bookHomeVisitButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });




    ViewPager viewPager = findViewById(R.id.cardViewPager);

        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem(R.drawable.truhealth_vital, "TruHealth Vital", "8 Profile | 77 Parameters", "Rs.3000"));
        cardItems.add(new CardItem(R.drawable.truhealth_vital_plus, "TruHealth Vital Plus", "8 Profile | 80 Parameters", "Rs.4500"));
        cardItems.add(new CardItem(R.drawable.truhealth_active_male, "TruHealth Active Male", "9 Profile | 94 Parameters", "Rs.5500"));
        cardItems.add(new CardItem(R.drawable.truhealth_active_female, "TruHealth Active Female", "10 Profile | 95 Parameters", "Rs.5500"));
        cardItems.add(new CardItem(R.drawable.pro_male, "TruHealth Pro-Actice Male", "11 Profile | 100 Parameters", "Rs.6500"));
        cardItems.add(new CardItem(R.drawable.pro_female, "TruHealth Pro-Actice Female", "12 Profile | 100 Parameters", "Rs.6500"));
        cardItems.add(new CardItem(R.drawable.truhealth_expert_male, "TruHealth Expert Male", "11 Profile | 106 Parameters", "Rs.7500"));
        cardItems.add(new CardItem(R.drawable.truhealth_expert_female, "TruHealth Expert Female", "12 Profile | 107 Parameters", "Rs.7500"));
        // Add more card items as needed...

        CardPagerAdapter adapter2 = new CardPagerAdapter(this, cardItems);
        viewPager.setAdapter(adapter2);

        // Initialize ViewPager instances
        svgViewPager1 = findViewById(R.id.svgCardViewPager1);
        svgViewPager2 = findViewById(R.id.svgCardViewPager2);
        svgViewPager3 = findViewById(R.id.svgCardViewPager3);
        svgViewPager4 = findViewById(R.id.svgCardViewPager4);

        // Populate card data and set adapters for each ViewPager
        List<SVGCardItem> svgCardItems1 = new ArrayList<>();
        svgCardItems1.add(new SVGCardItem(R.drawable.test, "Calcium Test \nCT Test", "Rs.260"));
        svgCardItems1.add(new SVGCardItem(R.drawable.test, "CBC (Complete Blood Count Test)", "Rs.330"));

        List<SVGCardItem> svgCardItems2 = new ArrayList<>();
        svgCardItems2.add(new SVGCardItem(R.drawable.test, "Creatinine Serum\nTest", "Rs.250"));
        svgCardItems2.add(new SVGCardItem(R.drawable.test, "C-Reactive Protien\n(CRP)", "Rs.590"));

        List<SVGCardItem> svgCardItems3 = new ArrayList<>();
        svgCardItems3.add(new SVGCardItem(R.drawable.test, "Electrolytes Serum\nTest", "Rs.550"));
        svgCardItems3.add(new SVGCardItem(R.drawable.test, "ESR Automated\nBlood", "Rs.150"));

        List<SVGCardItem> svgCardItems4 = new ArrayList<>();
        svgCardItems4.add(new SVGCardItem(R.drawable.test, "Fasting Blood Sugar\nFBS Test", "Rs.90"));
        svgCardItems4.add(new SVGCardItem(R.drawable.test, "HbA1C-Glycated Haemoglobin", "Rs.620"));

        SVGCardPagerAdapter svgAdapter1 = new SVGCardPagerAdapter(this, svgCardItems1);
        svgViewPager1.setAdapter(svgAdapter1);

        SVGCardPagerAdapter svgAdapter2 = new SVGCardPagerAdapter(this, svgCardItems2);
        svgViewPager2.setAdapter(svgAdapter2);

        SVGCardPagerAdapter svgAdapter3 = new SVGCardPagerAdapter(this, svgCardItems3);
        svgViewPager3.setAdapter(svgAdapter3);

        SVGCardPagerAdapter svgAdapter4 = new SVGCardPagerAdapter(this, svgCardItems4);
        svgViewPager4.setAdapter(svgAdapter4);
    }

    private List<Testimonial> generateTestimonials() {
        List<Testimonial> testimonials = new ArrayList<>();
        // Add your testimonials here
        testimonials.add(new Testimonial(R.drawable.man,"Morning given the samples in Dombivli branch got the report by 12.30pm it's fast and efficient delivery by mail and want to tell about the staff they are very professional and friendly definitely I will recommend my friends and family to MS Pathology  labs", "Vikram Kumar", 5));
        testimonials.add(new Testimonial(R.drawable.woman,"Delightful experience at Chinchwad branch. Ms.Prajakta is courteous, professional in her approach, & believes in service-with-smile attitude. Waiting timewas least possible,Reports wereemailed same day evening.Overall good service-oriented approach. Good job, keep up the good show. Thank you.", "Jane Smith", 5));
        testimonials.add(new Testimonial(R.drawable.man,"The Home Service was excellent. Medical Technician took samples at 7:00am and returned at 9:30 after breakfast to take further samples. The results were sent by email at 6pm. The whole experience was excellent, professional and well organised. Will certainly recommend the home service for convenience", "Bob Johnson", 4));
        return testimonials;
    }

    private void performRegistration() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookingContract.BookingEntry.COLUMN_NAME, editTextName.getText().toString());
        values.put(BookingContract.BookingEntry.COLUMN_EMAIL, editTextEmail.getText().toString());
        values.put(BookingContract.BookingEntry.COLUMN_PHONE_NUMBER, editTextNumber.getText().toString());
        values.put(BookingContract.BookingEntry.COLUMN_HEALTHCHECKUP, spinner.getSelectedItem().toString());

        long newRowId = db.insert(BookingContract.BookingEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            resetFields();
            Toast.makeText(UserHomePage.this, "We Will Back To You!", Toast.LENGTH_SHORT).show();
        } else {
            resetFields();
            Toast.makeText(UserHomePage.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void resetFields() {
        editTextName.setText("");
        editTextEmail.setText("");
        editTextNumber.setText("");
    }


    private IconRecyclerAdapter createIconAdapter() {
        // Replace with your actual icon resources and titles
        int[] iconResIds = {R.drawable.heart, R.drawable.liver, R.drawable.thyroid, R.drawable.joint, R.drawable.kidney};
        String[] titles = {"Heart", "Liver", "Thyroid" , "Joint", "Kidney"};

        return new IconRecyclerAdapter(this, iconResIds, titles);
    }

    private Icon2RecyclerAdapter createIconAdapter2() {
        // Replace with your actual icon resources and titles
        int[] iconResIds = {R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5, R.drawable.icon_6, R.drawable.icon_7, R.drawable.icon_8};
        String[] titles = {"Anemia", "Allergy", "Alcohol" , "Arthritis", "Cancer", "Diabetes" , "Fever", "Vitamin"};

        return new Icon2RecyclerAdapter(this, iconResIds, titles);
    }

    @Override
    protected void onDestroy() {
        stopAutoSlide();
        super.onDestroy();
    }

    private void startAutoSlide() {
        autoSlideHandler.postDelayed(autoSlideRunnable, AUTO_SLIDE_DELAY);
    }

    private void stopAutoSlide() {
        autoSlideHandler.removeCallbacks(autoSlideRunnable);
    }
}
