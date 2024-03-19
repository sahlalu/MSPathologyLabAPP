package com.example.mspathologylab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminHomePage extends AppCompatActivity {

    private TextView textview;
    private TextView role;
    CardView logout, addAdmin, showorder, uploadreport, removeAdmin, homevisitAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        textview = findViewById(R.id.textview);
        role = findViewById(R.id.role);
        logout = findViewById(R.id.logout);
        addAdmin=findViewById(R.id.addAdmin);
        showorder=findViewById(R.id.showorder);
        uploadreport = findViewById(R.id.uploadreport);
        removeAdmin = findViewById(R.id.removeAdmin);
        homevisitAdmin = findViewById(R.id.homevisitAdmin);

        // Retrieve user's name and email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("admin_login_pref", Context.MODE_PRIVATE);
        String adminName = sharedPreferences.getString("loggedInAdminName", "");
        String adminRole = sharedPreferences.getString("loggedInAdminRole", "");

        // Display user's name and email in respective TextViews
        textview.setText(adminName);
        role.setText(adminRole);

        // Set onClickListener for logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, AddAdminActivity.class));
            }
        });

        showorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, ShowOrderActivity.class));
            }
        });

        uploadreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, UploadReportActivity.class));
            }
        });

        removeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, RemoveAdminActivity.class));
            }
        });

        homevisitAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, HomeVisitActivity.class));
            }
        });



    }

    private void logoutUser() {
        // Clear user session in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("admin_login_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect user to login page
        Intent intent = new Intent(AdminHomePage.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent user from going back to the profile screen
    }
}