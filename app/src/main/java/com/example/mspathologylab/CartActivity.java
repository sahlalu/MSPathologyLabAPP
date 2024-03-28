package com.example.mspathologylab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;

    private TextView logtv4;

    private ImageView empty;

    private Button proceedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        logtv4 = findViewById(R.id.logtv4);
        empty = findViewById(R.id.empty);

        recyclerView = findViewById(R.id.recyclerViewCart);

        // Combine data from both managers into a single list
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(CartManager.getCartItems());
        combinedList.addAll(CartManager2.getSVGCartItems());
        combinedList.addAll(CartManager3.getTestCartItems());


        // Initialize and set up the adapter with the combined list
        cartAdapter = new CartAdapter(this, combinedList);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (combinedList.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
            logtv4.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
            logtv4.setVisibility(View.GONE);
        }




        // Bottom NavigationView setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_cart);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle bottom navigation item clicks
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
                // Already in cart activity
                return true;
            } else if (item.getItemId() == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        proceedButton = findViewById(R.id.proceed);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToCheckout();
            }
        });

    }



    private void proceedToCheckout() {

        // Combine test names and prices in the desired format

        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(CartManager.getCartItems());
        combinedList.addAll(CartManager2.getSVGCartItems());
        combinedList.addAll(CartManager3.getTestCartItems());

        if (combinedList.isEmpty()) {
            // Cart is empty, show a message or perform any necessary action
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        } else {
            // Combine test names and prices in the desired format
            StringBuilder testInfoBuilder = new StringBuilder();
            double totalPrice = 0.0;
            for (Object item : combinedList) {
                if (item instanceof TestCardItem) {
                    TestCardItem testItem = (TestCardItem) item;
                    testInfoBuilder.append(testItem.getTestName()).append(" - ").append(testItem.getPrice()).append(" | ");
                    totalPrice += parsePrice(testItem.getPrice());
                } else if (item instanceof SVGCardItem) {
                    SVGCardItem testItem = (SVGCardItem) item;
                    testInfoBuilder.append(testItem.getTestName()).append(" - ").append(testItem.getPrice()).append(" | ");
                    totalPrice += parsePrice(testItem.getPrice());
                } else if (item instanceof CardItem) {
                    CardItem testItem = (CardItem) item;
                    testInfoBuilder.append(testItem.getTitle()).append(" - ").append(testItem.getPrice()).append(" | ");
                    totalPrice += parsePrice(testItem.getPrice());
                }
            }

            if (testInfoBuilder.length() > 0) {
                testInfoBuilder.deleteCharAt(testInfoBuilder.length() - 1);
                String testInfoString = testInfoBuilder.toString().replaceAll("\n", " ");
                testInfoBuilder = new StringBuilder(testInfoString);
            }

            // Start ProceedActivity and pass test names and prices
            Intent intent = new Intent(CartActivity.this, ProceedActivity.class);
            intent.putExtra("testInfo", testInfoBuilder.toString());
            intent.putExtra("totalPrice", String.valueOf(totalPrice));

            startActivity(intent);
        }
    }



    private double parsePrice(String price) {
        String priceWithoutCurrency = price.replace("Rs.", "").trim();
        priceWithoutCurrency = priceWithoutCurrency.replace(",", "");
        return Double.parseDouble(priceWithoutCurrency);
    }
}