package com.example.mspathologylab;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextNumber, editTextPassword, editTextConfirmPassword, editTextCity, editTextZipCode;
    private TextView validationMessage;
    private DatabaseHelper dbHelper;

    private static final int FIXED_IMAGE_RESOURCE = R.drawable.userlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextCity = findViewById(R.id.editTextCity);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        validationMessage = findViewById(R.id.validationMessage);

        Button registerButton = findViewById(R.id.btnRegister);
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

        if (TextUtils.isEmpty(editTextName.getText())) {
            setValidationMessage("Name cannot be empty");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText()).matches()) {
            setValidationMessage("Enter a valid email address");
            return false;
        }

        String phoneNumber = editTextNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10) {
            setValidationMessage("Phone number should be 10 digits");
            return false;
        }

        String password = editTextPassword.getText().toString();
        if (!isPasswordValid(password)) {
            setValidationMessage("Password should contain uppercase letter, lowercase letter, special character, and a number");
            return false;
        }

        String confirmPassword = editTextConfirmPassword.getText().toString();
        if (!confirmPassword.equals(password)) {
            setValidationMessage("Passwords do not match");
            return false;
        }

        if (TextUtils.isEmpty(editTextCity.getText()) || TextUtils.isEmpty(editTextZipCode.getText())) {
            setValidationMessage("City and Zip Code are required");
            return false;
        }

        return true;
    }

    private void performRegistration() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RegistrationContract.RegistrationEntry.COLUMN_NAME, editTextName.getText().toString());
        values.put(RegistrationContract.RegistrationEntry.COLUMN_EMAIL, editTextEmail.getText().toString());
        values.put(RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER, editTextNumber.getText().toString());
        values.put(RegistrationContract.RegistrationEntry.COLUMN_PASSWORD, editTextPassword.getText().toString());
        values.put(RegistrationContract.RegistrationEntry.COLUMN_CITY, editTextCity.getText().toString());
        values.put(RegistrationContract.RegistrationEntry.COLUMN_ZIP_CODE, editTextZipCode.getText().toString());

        byte[] imageByteArray = convertImageToByteArray(FIXED_IMAGE_RESOURCE);
        values.put(RegistrationContract.RegistrationEntry.COLUMN_IMAGE, imageByteArray);


        long newRowId = db.insert(RegistrationContract.RegistrationEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            showToastAndRedirect("Registration successful. Redirecting to homepage.");
        } else {
            showToastAndResetForm("Registration failed. Please try again.");
        }

        db.close();
    }

    private void showToastAndRedirect(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToastAndResetForm(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Reset form values
        editTextName.setText("");
        editTextEmail.setText("");
        editTextNumber.setText("");
        editTextPassword.setText("");
        editTextConfirmPassword.setText("");
        editTextCity.setText("");
        editTextZipCode.setText("");
    }

    private void setValidationMessage(String message) {
        validationMessage.setVisibility(View.VISIBLE);
        validationMessage.setText(message);
    }

    private boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        return pattern.matcher(password).matches();

    }

    private byte[] convertImageToByteArray(int imageResource) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResource);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}