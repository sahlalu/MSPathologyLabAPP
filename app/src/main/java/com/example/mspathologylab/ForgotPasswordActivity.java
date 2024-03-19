package com.example.mspathologylab;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber, editTextOtp, editTextNewPassword, editTextConfirmNewPassword;
    private Button btnSendOtp, btnSubmitOtp, btnChangePassword;
    private TextView tvVerificationMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextOtp = findViewById(R.id.editTextOtp);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnSubmitOtp = findViewById(R.id.btnSubmitOtp);
        tvVerificationMessage = findViewById(R.id.tvVerificationMessage);

        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmNewPassword = findViewById(R.id.editTextConfirmNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();

                if (isPhoneNumberExists(phoneNumber)) {
                    // Phone number exists, proceed to send OTP
                    showOtpFields();
                } else {
                    // Phone number not found in the database
                    tvVerificationMessage.setText("Number not found");
                    tvVerificationMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSubmitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = editTextOtp.getText().toString().trim();

                if (verifyOtp(enteredOtp)) {
                    // OTP verification successful
                    showNewPasswordFields();
                } else {
                    // OTP verification failed
                    tvVerificationMessage.setText("Incorrect OTP. Please try again.");
                    tvVerificationMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateNewPassword()) {
                    String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                    String newPassword = editTextNewPassword.getText().toString().trim();
                    updatePasswordInDatabase(phoneNumber, newPassword);
                }
            }
        });
    }

    private boolean isPhoneNumberExists(String phoneNumber) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER
        };

        String selection = RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(
                RegistrationContract.RegistrationEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean phoneNumberExists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return phoneNumberExists;
    }

    private boolean verifyOtp(String enteredOtp) {
        // For demonstration purposes, assuming the correct OTP is "123456"
        return enteredOtp.equals("123456");
    }

    private boolean validateNewPassword() {
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmNewPassword = editTextConfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword) || newPassword.length() < 8) {
            setValidationMessage("Password must be at least 8 characters");
            return false;
        }

        if (!newPassword.matches(".*\\d.*")) {
            setValidationMessage("Password must contain at least one digit");
            return false;
        }

        if (!newPassword.matches(".*[A-Z].*")) {
            setValidationMessage("Password must contain at least one uppercase letter");
            return false;
        }

        if (!newPassword.matches(".*[a-z].*")) {
            setValidationMessage("Password must contain at least one lowercase letter");
            return false;
        }

        if (!newPassword.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) {
            setValidationMessage("Password must contain at least one special character");
            return false;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            setValidationMessage("Passwords do not match");
            return false;
        }

        return true;
    }

    private void updatePasswordInDatabase(String phoneNumber, String newPassword) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RegistrationContract.RegistrationEntry.COLUMN_PASSWORD, newPassword);

        String whereClause = RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER + " = ?";
        String[] whereArgs = {phoneNumber};

        int rowsAffected = db.update(
                RegistrationContract.RegistrationEntry.TABLE_NAME,
                values,
                whereClause,
                whereArgs
        );

        if (rowsAffected > 0) {
            showToastAndRedirect("Password changed successfully. Redirecting to login page.");
        } else {
            showToastAndResetFields("Failed to change password. Please try again.");
        }

        db.close();
    }

    private void showOtpFields() {
        editTextOtp.setVisibility(View.VISIBLE);
        btnSubmitOtp.setVisibility(View.VISIBLE);
    }

    private void showNewPasswordFields() {
        editTextNewPassword.setVisibility(View.VISIBLE);
        editTextConfirmNewPassword.setVisibility(View.VISIBLE);
        btnChangePassword.setVisibility(View.VISIBLE);
    }

    private void showToastAndRedirect(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // TODO: Add code to redirect to the login page (e.g., start a new LoginActivity)
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

        // Finish the current activity to prevent going back to it from the login page
        finish();
    }

    private void showToastAndResetFields(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Reset password fields
        editTextNewPassword.setText("");
        editTextConfirmNewPassword.setText("");
    }

    private void setValidationMessage(String message) {
        tvVerificationMessage.setText(message);
        tvVerificationMessage.setVisibility(View.VISIBLE);
    }
}