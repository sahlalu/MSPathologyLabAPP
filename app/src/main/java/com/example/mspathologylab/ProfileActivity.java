package com.example.mspathologylab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView userEmailTextView, textView;
    private Button logoutButton, button3, button5, button2, button4, button;

    private ImageView imageView3;

    // SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, CenterActivity.class));
            }
        });

        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, FAQActivity.class));
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, DownloadReportActivity.class));
            }
        });

        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProblemActivity.class));
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
            }
        });

        // Initialize views
        userNameTextView = findViewById(R.id.textView);
        userEmailTextView = findViewById(R.id.textView2);
        imageView3 = findViewById(R.id.imageView3);
        textView = findViewById(R.id.textView);
        logoutButton = findViewById(R.id.Logout);

        // Retrieve user's name and email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_login_pref", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("loggedInUserEmail", "");


        userEmailTextView.setText(userEmail);

        // Set onClickListener for logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

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
                // Handle profile action
                return true;
            }
            return false;
        });

        retrieveUserDataFromDatabase();
    }

    private void logoutUser() {
        // Clear user session in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_login_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect user to login page
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent user from going back to the profile screen
    }


    private void retrieveUserDataFromDatabase() {
        // Get the user ID of the logged-in user from SharedPreferences or any other storage
        SharedPreferences sharedPreferences = getSharedPreferences("user_login_pref", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("loggedInUserEmail", "");

        // Open the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                RegistrationContract.RegistrationEntry.COLUMN_NAME,
                RegistrationContract.RegistrationEntry.COLUMN_IMAGE
        };


        String selection = RegistrationContract.RegistrationEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { userEmail };

        // Query the database
        Cursor cursor = db.query(
                RegistrationContract.RegistrationEntry.TABLE_NAME,   // The table to query
                projection,                           // The array of columns to return (pass null to get all)
                selection,                            // The columns for the WHERE clause
                selectionArgs,                        // The values for the WHERE clause
                null,
                null,
                null
        );

        // Check if there is data available
        if (cursor.moveToFirst()) {
            // Retrieve data from the cursor
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_NAME));
            byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_IMAGE)); // Retrieve image data


            if (imageData != null && imageData.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                imageView3.setImageBitmap(bitmap);
            }

            textView.setText(name);

        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }
}
