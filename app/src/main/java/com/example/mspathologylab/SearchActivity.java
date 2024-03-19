package com.example.mspathologylab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ViewPager svgViewPager1, svgViewPager2, svgViewPager3, svgViewPager4, svgViewPager5, svgViewPager6, svgViewPager7, svgViewPager8,
            svgViewPager9, svgViewPager10, svgViewPager11, svgViewPager12, svgViewPager13, svgViewPager14, svgViewPager15, svgViewPager16, svgViewPager17, svgViewPager18,
            svgViewPager19, svgViewPager20, svgViewPager21, svgViewPager22, svgViewPager23, svgViewPager24, svgViewPager25, svgViewPager26, svgViewPager27, svgViewPager28,
            svgViewPager29, svgViewPager30;

    private int testDrawableId = R.drawable.test;

    private ConstraintLayout imageslidershoping;

    private static final int NUM_TEST_ITEMS = 30;
    private ViewPager[] svgViewPagers = new ViewPager[NUM_TEST_ITEMS];




    private List<TestCardItem> allTestCardItems;
    private List<TestCardItem> filteredTestCardItems;
    private TestCardPagerAdapter testCardPagerAdapter;

    private ViewPager svgViewPager;
    private SearchView searchEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        allTestCardItems = new ArrayList<>();
        filteredTestCardItems = new ArrayList<>();

        addAllTestCardItems();
        imageslidershoping = findViewById(R.id.imageslidershoping);
        svgViewPager = findViewById(R.id.viewpager);
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTestItems(newText);
                // Show or hide ViewPager and noResultsTextView based on search results
                if (filteredTestCardItems.isEmpty()) {
                    svgViewPager.setVisibility(View.GONE);
                    imageslidershoping.setVisibility(View.VISIBLE);
                } else {
                    svgViewPager.setVisibility(View.VISIBLE);
                    imageslidershoping.setVisibility(View.GONE);
                }
                return true;
            }

        });






        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_search);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), UserHomePage.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_search) {
                /*startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();*/
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



        svgViewPager1 = findViewById(R.id.svgCardViewPager1);
        svgViewPager2 = findViewById(R.id.svgCardViewPager2);
        svgViewPager3 = findViewById(R.id.svgCardViewPager3);
        svgViewPager4 = findViewById(R.id.svgCardViewPager4);
        svgViewPager5 = findViewById(R.id.svgCardViewPager5);
        svgViewPager6 = findViewById(R.id.svgCardViewPager6);
        svgViewPager7 = findViewById(R.id.svgCardViewPager7);
        svgViewPager8 = findViewById(R.id.svgCardViewPager8);
        svgViewPager9 = findViewById(R.id.svgCardViewPager9);
        svgViewPager10 = findViewById(R.id.svgCardViewPager10);
        svgViewPager11 = findViewById(R.id.svgCardViewPager11);
        svgViewPager12 = findViewById(R.id.svgCardViewPager12);
        svgViewPager13 = findViewById(R.id.svgCardViewPager13);
        svgViewPager14 = findViewById(R.id.svgCardViewPager14);
        svgViewPager15 = findViewById(R.id.svgCardViewPager15);
        svgViewPager16 = findViewById(R.id.svgCardViewPager16);
        svgViewPager17 = findViewById(R.id.svgCardViewPager17);
        svgViewPager18 = findViewById(R.id.svgCardViewPager18);
        svgViewPager19 = findViewById(R.id.svgCardViewPager19);
        svgViewPager20 = findViewById(R.id.svgCardViewPager20);
        svgViewPager21 = findViewById(R.id.svgCardViewPager21);
        svgViewPager22 = findViewById(R.id.svgCardViewPager22);
        svgViewPager23 = findViewById(R.id.svgCardViewPager23);
        svgViewPager24 = findViewById(R.id.svgCardViewPager24);
        svgViewPager25 = findViewById(R.id.svgCardViewPager25);
        svgViewPager26 = findViewById(R.id.svgCardViewPager26);
        svgViewPager27 = findViewById(R.id.svgCardViewPager27);
        svgViewPager28 = findViewById(R.id.svgCardViewPager28);
        svgViewPager29 = findViewById(R.id.svgCardViewPager29);
        svgViewPager30 = findViewById(R.id.svgCardViewPager30);




        // Populate card data and set adapters for each ViewPager
        List<TestCardItem> TestCardItems1 = new ArrayList<>();
        TestCardItems1.add(new TestCardItem(R.drawable.test, "Lipid Profile\n-Mini Test", "Rs.800"));

        List<TestCardItem> TestCardItems2 = new ArrayList<>();
        TestCardItems2.add(new TestCardItem(R.drawable.test, "Liver Function Test-1\n(Maxi)", "Rs.1500"));


        List<TestCardItem> TestCardItems3 = new ArrayList<>();
        TestCardItems3.add(new TestCardItem(R.drawable.test, "Prothrombin Time\n(PT)", "Rs.420"));

        List<TestCardItem> TestCardItems4 = new ArrayList<>();
        TestCardItems4.add(new TestCardItem(R.drawable.test, "Renal(Kidney)\nFunction Tests", "Rs.1180"));

        List<TestCardItem> TestCardItems5 = new ArrayList<>();
        TestCardItems5.add(new TestCardItem(R.drawable.test, "SGPT ALT\nSerum", "Rs.250"));

        List<TestCardItem> TestCardItems6 = new ArrayList<>();
        TestCardItems6.add(new TestCardItem(R.drawable.test, "Thyroid Panel-1\n(T3/T4/TSH)", "Rs.600"));


        List<TestCardItem> TestCardItems7 = new ArrayList<>();
        TestCardItems7.add(new TestCardItem(R.drawable.test, "TSH(Ultrasensitive)\nECLIA Serum", "Rs.400"));

        List<TestCardItem> TestCardItems8 = new ArrayList<>();
        TestCardItems8.add(new TestCardItem(R.drawable.test, "Urea Test\nSerum", "Rs.230"));

        List<TestCardItem> TestCardItems9 = new ArrayList<>();
        TestCardItems9.add(new TestCardItem(R.drawable.test, "Uric Acid\nSerum", "Rs.250"));

        List<TestCardItem> TestCardItems10 = new ArrayList<>();
        TestCardItems10.add(new TestCardItem(R.drawable.test, "Vitamin B12\nCyanocobalamin", "Rs.1200"));

        List<TestCardItem> TestCardItems11 = new ArrayList<>();
        TestCardItems11.add(new TestCardItem(R.drawable.test, "Complete Blood Count\n(CBC)", "Rs.350"));

        List<TestCardItem> TestCardItems12 = new ArrayList<>();
        TestCardItems12.add(new TestCardItem(R.drawable.test, "Electrolyte Panel\n(Sodium/Potassium)", "Rs.480"));

        List<TestCardItem> TestCardItems13 = new ArrayList<>();
        TestCardItems13.add(new TestCardItem(R.drawable.test, "Fasting Blood Sugar\n(FBS)", "Rs.180"));

        List<TestCardItem> TestCardItems14 = new ArrayList<>();
        TestCardItems14.add(new TestCardItem(R.drawable.test, "HbA1c\n(Glycated Hemoglobin)", "Rs.320"));

        List<TestCardItem> TestCardItems15 = new ArrayList<>();
        TestCardItems15.add(new TestCardItem(R.drawable.test, "Hepatitis B Surface\nAntigen(HBsAg)", "Rs.550"));

        List<TestCardItem> TestCardItems16 = new ArrayList<>();
        TestCardItems16.add(new TestCardItem(R.drawable.test, "Hepatitis C Antibody\nAnti-HCV", "Rs.680"));

        List<TestCardItem> TestCardItems17 = new ArrayList<>();
        TestCardItems17.add(new TestCardItem(R.drawable.test, "HIV Screening\nELISA", "Rs.800"));

        List<TestCardItem> TestCardItems18 = new ArrayList<>();
        TestCardItems18.add(new TestCardItem(R.drawable.test, "Lipase Test\nSerum", "Rs.420"));

        List<TestCardItem> TestCardItems19 = new ArrayList<>();
        TestCardItems19.add(new TestCardItem(R.drawable.test, "Pregnancy Test\nHCG", "Rs.150"));

        List<TestCardItem> TestCardItems20 = new ArrayList<>();
        TestCardItems20.add(new TestCardItem(R.drawable.test, "Rapid Malaria Test\n(RDT)", "Rs.250"));

        List<TestCardItem> TestCardItems21 = new ArrayList<>();
        TestCardItems21.add(new TestCardItem(R.drawable.test, "C-Reactive Protein\n(CRP) Test", "Rs.300"));

        List<TestCardItem> TestCardItems22 = new ArrayList<>();
        TestCardItems22.add(new TestCardItem(R.drawable.test, "Calcium Blood Test\n(CBC Test)", "Rs.200"));

        List<TestCardItem> TestCardItems23 = new ArrayList<>();
        TestCardItems23.add(new TestCardItem(R.drawable.test, "Magnesium Blood Test\n", "Rs.220"));

        List<TestCardItem> TestCardItems24 = new ArrayList<>();
        TestCardItems24.add(new TestCardItem(R.drawable.test, "Creatinine Clearance Test", "Rs.400"));

        List<TestCardItem> TestCardItems25 = new ArrayList<>();
        TestCardItems25.add(new TestCardItem(R.drawable.test, "Lactate Dehydrogenase\n(LDH) Test", "Rs.350"));

        List<TestCardItem> TestCardItems26 = new ArrayList<>();
        TestCardItems26.add(new TestCardItem(R.drawable.test, "Iron Blood Test\n", "Rs.250"));

        List<TestCardItem> TestCardItems27 = new ArrayList<>();
        TestCardItems27.add(new TestCardItem(R.drawable.test, "Ferritin Test\n", "Rs.280"));

        List<TestCardItem> TestCardItems28 = new ArrayList<>();
        TestCardItems28.add(new TestCardItem(R.drawable.test, "Troponin Test\n", "Rs.500"));

        List<TestCardItem> TestCardItems29 = new ArrayList<>();
        TestCardItems29.add(new TestCardItem(R.drawable.test, "D-Dimer Test\n", "Rs.380"));

        List<TestCardItem> TestCardItems30 = new ArrayList<>();
        TestCardItems30.add(new TestCardItem(R.drawable.test, "Alpha-Fetoprotein\n(AFP) Test", "Rs.420"));


        TestCardPagerAdapter TestAdapter1 = new TestCardPagerAdapter(this, TestCardItems1);
        svgViewPager1.setAdapter(TestAdapter1);

        TestCardPagerAdapter TestAdapter2 = new TestCardPagerAdapter(this, TestCardItems2);
        svgViewPager2.setAdapter(TestAdapter2);

        TestCardPagerAdapter TestAdapter3 = new TestCardPagerAdapter(this, TestCardItems3);
        svgViewPager3.setAdapter(TestAdapter3);

        TestCardPagerAdapter TestAdapter4 = new TestCardPagerAdapter(this, TestCardItems4);
        svgViewPager4.setAdapter(TestAdapter4);

        TestCardPagerAdapter TestAdapter5 = new TestCardPagerAdapter(this, TestCardItems5);
        svgViewPager5.setAdapter(TestAdapter5);

        TestCardPagerAdapter TestAdapter6 = new TestCardPagerAdapter(this, TestCardItems6);
        svgViewPager6.setAdapter(TestAdapter6);

        TestCardPagerAdapter TestAdapter7 = new TestCardPagerAdapter(this, TestCardItems7);
        svgViewPager7.setAdapter(TestAdapter7);

        TestCardPagerAdapter TestAdapter8 = new TestCardPagerAdapter(this, TestCardItems8);
        svgViewPager8.setAdapter(TestAdapter8);

        TestCardPagerAdapter TestAdapter9 = new TestCardPagerAdapter(this, TestCardItems9);
        svgViewPager9.setAdapter(TestAdapter9);

        TestCardPagerAdapter TestAdapter10 = new TestCardPagerAdapter(this, TestCardItems10);
        svgViewPager10.setAdapter(TestAdapter10);

        TestCardPagerAdapter TestAdapter11 = new TestCardPagerAdapter(this, TestCardItems11);
        svgViewPager11.setAdapter(TestAdapter11);

        TestCardPagerAdapter TestAdapter12 = new TestCardPagerAdapter(this, TestCardItems12);
        svgViewPager12.setAdapter(TestAdapter12);

        TestCardPagerAdapter TestAdapter13 = new TestCardPagerAdapter(this, TestCardItems13);
        svgViewPager13.setAdapter(TestAdapter13);

        TestCardPagerAdapter TestAdapter14 = new TestCardPagerAdapter(this, TestCardItems14);
        svgViewPager14.setAdapter(TestAdapter14);

        TestCardPagerAdapter TestAdapter15 = new TestCardPagerAdapter(this, TestCardItems15);
        svgViewPager15.setAdapter(TestAdapter15);

        TestCardPagerAdapter TestAdapter16 = new TestCardPagerAdapter(this, TestCardItems16);
        svgViewPager16.setAdapter(TestAdapter16);

        TestCardPagerAdapter TestAdapter17 = new TestCardPagerAdapter(this, TestCardItems17);
        svgViewPager17.setAdapter(TestAdapter17);

        TestCardPagerAdapter TestAdapter18 = new TestCardPagerAdapter(this, TestCardItems18);
        svgViewPager18.setAdapter(TestAdapter18);

        TestCardPagerAdapter TestAdapter19 = new TestCardPagerAdapter(this, TestCardItems19);
        svgViewPager19.setAdapter(TestAdapter19);

        TestCardPagerAdapter TestAdapter20 = new TestCardPagerAdapter(this, TestCardItems20);
        svgViewPager20.setAdapter(TestAdapter20);

        TestCardPagerAdapter TestAdapter21 = new TestCardPagerAdapter(this, TestCardItems21);
        svgViewPager21.setAdapter(TestAdapter21);

        TestCardPagerAdapter TestAdapter22 = new TestCardPagerAdapter(this, TestCardItems22);
        svgViewPager22.setAdapter(TestAdapter22);

        TestCardPagerAdapter TestAdapter23 = new TestCardPagerAdapter(this, TestCardItems23);
        svgViewPager23.setAdapter(TestAdapter23);

        TestCardPagerAdapter TestAdapter24 = new TestCardPagerAdapter(this, TestCardItems24);
        svgViewPager24.setAdapter(TestAdapter24);

        TestCardPagerAdapter TestAdapter25 = new TestCardPagerAdapter(this, TestCardItems25);
        svgViewPager25.setAdapter(TestAdapter25);

        TestCardPagerAdapter TestAdapter26 = new TestCardPagerAdapter(this, TestCardItems26);
        svgViewPager26.setAdapter(TestAdapter26);

        TestCardPagerAdapter TestAdapter27 = new TestCardPagerAdapter(this, TestCardItems27);
        svgViewPager27.setAdapter(TestAdapter27);

        TestCardPagerAdapter TestAdapter28 = new TestCardPagerAdapter(this, TestCardItems28);
        svgViewPager28.setAdapter(TestAdapter28);

        TestCardPagerAdapter TestAdapter29 = new TestCardPagerAdapter(this, TestCardItems29);
        svgViewPager29.setAdapter(TestAdapter29);

        TestCardPagerAdapter TestAdapter30 = new TestCardPagerAdapter(this, TestCardItems30);
        svgViewPager30.setAdapter(TestAdapter30);



    }

    private void filterTestItems(String query) {
        filteredTestCardItems.clear();
        for (TestCardItem item : allTestCardItems) {
            if (item.getTestName().toLowerCase().contains(query.toLowerCase())) {
                filteredTestCardItems.add(item);
            }
        }
        // Update the adapter of the main ViewPager with the filtered test items
        testCardPagerAdapter = new TestCardPagerAdapter(this, filteredTestCardItems);
        svgViewPager.setAdapter(testCardPagerAdapter);
    }

    private void addAllTestCardItems() {
        String[] testNames = {
                "Lipid Profile\n-Mini Test",
                "Liver Function Test-1\n(Maxi)",
                "Prothrombin Time\n(PT)",
                "Renal(Kidney)\nFunction Tests",
                "SGPT ALT\nSerum",
                "Thyroid Panel-1\n(T3/T4/TSH)",
                "TSH(Ultrasensitive)\nECLIA Serum",
                "Urea Test\nSerum",
                "Uric Acid\nSerum",
                "Vitamin B12\nCyanocobalamin",
                "Complete Blood Count\n(CBC)",
                "Electrolyte Panel\n(Sodium/Potassium)",
                "Fasting Blood Sugar\n(FBS)",
                "HbA1c\n(Glycated Hemoglobin)",
                "Hepatitis B Surface\nAntigen(HBsAg)",
                "Hepatitis C Antibody\nAnti-HCV",
                "HIV Screening\nELISA",
                "Lipase Test\nSerum",
                "Pregnancy Test\nHCG",
                "Rapid Malaria Test\n(RDT)",
                "C-Reactive Protein\n(CRP) Test",
                "Calcium Blood Test\n(CBC Test)",
                "Magnesium Blood Test\n",
                "Creatinine Clearance Test",
                "Lactate Dehydrogenase\n(LDH) Test",
                "Iron Blood Test\n",
                "Ferritin Test\n",
                "Troponin Test\n",
                "D-Dimer Test\n",
                "Alpha-Fetoprotein\n(AFP) Test"
                // Add more test names here...
        };

        int[] testPrices = {
                800, 1500, 420, 1180, 250, 600, 400, 230, 250, 1200,
                350, 480, 180, 320, 550, 680, 800, 420, 150, 250,
                300, 200, 220, 400, 350, 250, 280, 500, 380, 420
                // Add more test prices here...
        };

        for (int i = 0; i < NUM_TEST_ITEMS; i++) {
            allTestCardItems.add(new TestCardItem(testDrawableId, testNames[i], "Rs." + testPrices[i]));
        }
    }

}

















