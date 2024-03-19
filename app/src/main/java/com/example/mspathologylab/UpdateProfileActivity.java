package com.example.mspathologylab;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateProfileActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 100;

    private EditText editTextName, editTextEmail, editTextPhone, editTextCity, editTextZipCode;
    private ImageView imageViewProfile;
    private boolean isNewImageSelected = false;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Initialize EditText fields
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextCity = findViewById(R.id.editTextCity);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        imageViewProfile = findViewById(R.id.imageViewProfile);

        Button buttonSelectImage = findViewById(R.id.buttonEditPicture);
        Button buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);

        // Set click listener for selecting image
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Set click listener for updating profile
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        // Call method to retrieve user data from the database
        retrieveUserDataFromDatabase();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData(); // Assign value to class-level variable
            imageViewProfile.setImageURI(selectedImageUri);
            isNewImageSelected = true; // Set flag to indicate a new image is selected
        }
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
                RegistrationContract.RegistrationEntry.COLUMN_EMAIL,
                RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER,
                RegistrationContract.RegistrationEntry.COLUMN_CITY,
                RegistrationContract.RegistrationEntry.COLUMN_ZIP_CODE,
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
            String email = cursor.getString(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_CITY));
            String zipCode = cursor.getString(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_ZIP_CODE));
            byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(RegistrationContract.RegistrationEntry.COLUMN_IMAGE)); // Retrieve image data

            if (imageData != null && imageData.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                imageViewProfile.setImageBitmap(bitmap);
            }

            // Populate EditText fields with the retrieved data
            editTextName.setText(name);
            editTextEmail.setText(email);
            editTextPhone.setText(phone);
            editTextCity.setText(city);
            editTextZipCode.setText(zipCode);
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }

    private void updateProfile() {
        // Get updated values from EditText fields
        String newName = editTextName.getText().toString().trim();
        String newEmail = editTextEmail.getText().toString().trim();
        String newPhone = editTextPhone.getText().toString().trim();
        String newCity = editTextCity.getText().toString().trim();
        String newZipCode = editTextZipCode.getText().toString().trim();

        // Update the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store updated values
        ContentValues values = new ContentValues();
        values.put(RegistrationContract.RegistrationEntry.COLUMN_NAME, newName);
        values.put(RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER, newPhone);
        values.put(RegistrationContract.RegistrationEntry.COLUMN_CITY, newCity);
        values.put(RegistrationContract.RegistrationEntry.COLUMN_ZIP_CODE, newZipCode);

        // Update the profile image only if a new image is selected
        if (isNewImageSelected && selectedImageUri != null) {
            // Convert the selected image URI to a byte array
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] newImageData = outputStream.toByteArray();
                values.put(RegistrationContract.RegistrationEntry.COLUMN_IMAGE, newImageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Define the selection and selectionArgs for updating the database
        String selection = RegistrationContract.RegistrationEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { newEmail };

        // Update the row
        int rowsUpdated = db.update(
                RegistrationContract.RegistrationEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        // Check if the update was successful
        if (rowsUpdated > 0) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }

        // Close the database
        db.close();
    }
}
