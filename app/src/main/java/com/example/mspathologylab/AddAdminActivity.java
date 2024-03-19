package com.example.mspathologylab;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddAdminActivity extends AppCompatActivity {
    private EditText adminName, adminRole, adminEmail, adminContact, adminUsername, adminPassword;

    private TextView validationMessage;

    private AdminDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        dbHelper = new AdminDbHelper(this);
        adminName = findViewById(R.id.adminName);
        adminRole = findViewById(R.id.adminRole);
        adminEmail = findViewById(R.id.adminEmail);
        adminContact = findViewById(R.id.adminContact);
        adminUsername = findViewById(R.id.adminUsername);
        adminPassword = findViewById(R.id.adminPassword);
        validationMessage = findViewById(R.id.validationMessage);


        Button registerButton = findViewById(R.id.btnAddAdmin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    performRegistration();
                }
            }
        });
    }

    private boolean validateFields() {
        validationMessage.setVisibility(View.GONE);

        if (TextUtils.isEmpty(adminName.getText())) {
            setValidationMessage("Name cannot be empty");
            return false;
        }

        if (TextUtils.isEmpty(adminRole.getText())) {
            setValidationMessage("Role cannot be empty");
            return false;
        }

        if (TextUtils.isEmpty(adminUsername.getText())) {
            setValidationMessage("Username cannot be empty");
            return false;
        }

        if (TextUtils.isEmpty(adminPassword.getText())) {
            setValidationMessage("Password cannot be empty");
            return false;
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(adminEmail.getText()).matches()) {
            setValidationMessage("Enter a valid email address");
            return false;
        }

        String phoneNumber = adminContact.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10) {
            setValidationMessage("Phone number should be 10 digits");
            return false;
        }






        return true;
    }
    private void performRegistration() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AdminContract.AdminEntry.COLUMN_NAME, adminName.getText().toString());
        values.put(AdminContract.AdminEntry.COLUMN_ROLE, adminRole.getText().toString()); // Example value for role
        values.put(AdminContract.AdminEntry.COLUMN_EMAIL, adminEmail.getText().toString());
        values.put(AdminContract.AdminEntry.COLUMN_USERNAME, adminUsername.getText().toString());
        values.put(AdminContract.AdminEntry.COLUMN_PASSWORD, adminPassword.getText().toString());
        values.put(AdminContract.AdminEntry.COLUMN_CONTACT, adminContact.getText().toString());



        long newRowId = db.insert(AdminContract.AdminEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            resetFields();
            Intent adminHomeIntent = new Intent(this, AdminHomePage.class);
            startActivity(adminHomeIntent);
            finish();
            Toast.makeText(this, "Admin Registration Successfull", Toast.LENGTH_SHORT).show();
        } else {
            resetFields();
            Toast.makeText(this, "Admin Registration Failed", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    private void setValidationMessage(String message) {
        validationMessage.setVisibility(View.VISIBLE);
        validationMessage.setText(message);
    }

    private void resetFields() {
        adminName.setText("");
        adminRole.setText("");
        adminEmail.setText("");
        adminUsername.setText("");
        adminPassword.setText("");
        adminContact.setText("");
    }

    }
