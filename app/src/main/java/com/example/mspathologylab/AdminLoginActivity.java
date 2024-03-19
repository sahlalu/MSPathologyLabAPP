package com.example.mspathologylab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button btnLogin;
    private TextView loginErrorMessage;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "admin_login_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loginErrorMessage = findViewById(R.id.loginErrorMessage);

        HashMap<String, String> userCredentials = new HashMap<>();
        userCredentials.put("user1", "password1");
        userCredentials.put("user2", "password2");

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        // Check if user is already logged in
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            // Redirect to the homepage if user is already logged in
            startActivity(new Intent(AdminLoginActivity.this, AdminHomePage.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (validateLogin(username, password)) {
                    // Redirect to admin home page on successful login
                    redirectToAdminHomePage();

                } else if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                    Intent adminHomeIntent = new Intent(AdminLoginActivity.this, AdminHomePage.class);
                    startActivity(adminHomeIntent);
                    finish();

                } else {
                    // Display error message and reset fields on failed login
                    displayErrorMessage("Invalid username or password");
                    resetFields();
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // Check local SQLite database
        if (validateLoginFromDatabase(username, password)) {
            return true;
        }

        // Check HashMa

        return false;
    }

    private boolean validateLoginFromDatabase(String username, String password) {
        AdminDbHelper adminDbHelper = new AdminDbHelper(this);
        SQLiteDatabase db = adminDbHelper.getReadableDatabase();

        String[] projection = {
                AdminContract.AdminEntry.COLUMN_USERNAME,
                AdminContract.AdminEntry.COLUMN_PASSWORD
        };

        String selection = AdminContract.AdminEntry.COLUMN_USERNAME + " = ? AND " +
                AdminContract.AdminEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                AdminContract.AdminEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean loginSuccess = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return loginSuccess;
    }

    /*private boolean validateLoginFromHashMap(String username, String password) {
        HashMap<String, String> userCredentials = new HashMap<>();
        userCredentials.put("user1", "password1");
        userCredentials.put("user2", "password2");
        // Add more users as needed

        // Check if username exists in HashMap and if the password matches
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }*/

    private void redirectToAdminHomePage() {
        AdminDbHelper adminDbHelper = new AdminDbHelper(this);
        SQLiteDatabase db = adminDbHelper.getReadableDatabase();

        String[] projection = {
                AdminContract.AdminEntry.COLUMN_NAME,
                AdminContract.AdminEntry.COLUMN_ROLE
        };

        String selection = AdminContract.AdminEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { editTextUsername.getText().toString().trim() };

        Cursor cursor = db.query(
                AdminContract.AdminEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );



        if (cursor.moveToFirst()) {
            String adminName = cursor.getString(cursor.getColumnIndexOrThrow(AdminContract.AdminEntry.COLUMN_NAME));
            String adminRole = cursor.getString(cursor.getColumnIndexOrThrow(AdminContract.AdminEntry.COLUMN_ROLE));

            // Store admin's name and role in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putString("loggedInAdminName", adminName);
            editor.putString("loggedInAdminRole", adminRole);
            editor.apply();



            // Redirect to the admin homepage
            Intent adminHomeIntent = new Intent(this, AdminHomePage.class);
            startActivity(adminHomeIntent);
            finish();
        }


        cursor.close();
        db.close();
    }

    private void displayErrorMessage(String message) {
        loginErrorMessage.setText(message);
        loginErrorMessage.setVisibility(View.VISIBLE);
    }

    private void resetFields() {
        editTextUsername.setText("");
        editTextPassword.setText("");
    }
}