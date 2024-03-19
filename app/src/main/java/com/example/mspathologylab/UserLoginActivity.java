package com.example.mspathologylab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserLoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView forgotPasswordLink, loginErrorMessage, newuser;

    // SharedPreferences
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user_login_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        loginErrorMessage = findViewById(R.id.loginErrorMessage);
        newuser = findViewById(R.id.newuser);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to ForgotPasswordActivity
                startActivity(new Intent(UserLoginActivity.this, RegisterActivity.class));
            }
        });


        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to ForgotPasswordActivity
                startActivity(new Intent(UserLoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Check if user is already logged in
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            // Redirect to the homepage if user is already logged in
            startActivity(new Intent(UserLoginActivity.this, UserHomePage.class));
            finish();
        }
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            loginErrorMessage.setText("Please fill in all fields");
            loginErrorMessage.setVisibility(View.VISIBLE);
            return;
        }

        boolean loginSuccessful = performLoginVerification(email, password);

        if (loginSuccessful) {
            // Retrieve user's name and email from the database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] projection = {
                    RegistrationContract.RegistrationEntry.COLUMN_NAME,
                    RegistrationContract.RegistrationEntry.COLUMN_EMAIL
            };

            String selection = RegistrationContract.RegistrationEntry.COLUMN_EMAIL + " = ?";
            String[] selectionArgs = {email};

            Cursor cursor = db.query(
                    RegistrationContract.RegistrationEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            // If cursor has data, move to the first row and get the user's name and email
            if (cursor.moveToFirst()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_NAME));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_EMAIL));


                // Store user's name and email in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_IS_LOGGED_IN, true);
                editor.putString("loggedInUserName", userName);
                editor.putString("loggedInUserEmail", userEmail);
                editor.apply();

                // Redirect to the homepage on successful login
                Intent intent = new Intent(UserLoginActivity.this, UserHomePage.class);
                startActivity(intent);
                finish();
            } else {
                loginErrorMessage.setText("LOGIN FAILED, PLEASE TRY AGAIN");
                loginErrorMessage.setVisibility(View.VISIBLE);
                resetFields();
            }

            cursor.close();
            db.close();
        } else {
            loginErrorMessage.setText("LOGIN FAILED, PLEASE TRY AGAIN");
            loginErrorMessage.setVisibility(View.VISIBLE);
            resetFields();
        }
    }

    private boolean performLoginVerification(String email, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                RegistrationContract.RegistrationEntry.COLUMN_EMAIL,
                RegistrationContract.RegistrationEntry.COLUMN_PASSWORD
        };

        String selection = RegistrationContract.RegistrationEntry.COLUMN_EMAIL + " = ? AND " +
                RegistrationContract.RegistrationEntry.COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                RegistrationContract.RegistrationEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean loginSuccessful = cursor.getCount() > 0;



        cursor.close();
        db.close();

        return loginSuccessful;
    }

    private void resetFields() {
        editTextEmail.setText("");
        editTextPassword.setText("");
    }
}
