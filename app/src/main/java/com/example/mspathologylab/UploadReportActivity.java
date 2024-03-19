package com.example.mspathologylab;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadReportActivity extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;
    private EditText emailEditText;
    private String selectedFilePath;
    private AdminDbHelper dbHelper;

    private TextView selectedFileNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_report);

        emailEditText = findViewById(R.id.emailEditText);
        Button browseButton = findViewById(R.id.browseButton);
        Button submitButton = findViewById(R.id.submitButton);
        selectedFileNameTextView = findViewById(R.id.selectedFileNameTextView);

        dbHelper = new AdminDbHelper(this);

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (!email.isEmpty() && selectedFilePath != null) {
                    saveToDatabase(email, selectedFilePath);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a file and enter email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            String fileName = getFileName(fileUri); // Get the file name from the URI
            try {
                InputStream inputStream = getContentResolver().openInputStream(fileUri);
                if (inputStream != null) {
                    selectedFilePath = readPDFFileContent(inputStream);
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Display the file name
            selectedFileNameTextView.setVisibility(View.VISIBLE);
            selectedFileNameTextView.setText("Selected File: " + fileName);
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        result = cursor.getString(columnIndex);
                    }
                }
            } finally {
                cursor.close();
            }
        }

        if (result == null) {
            result = uri.getLastPathSegment();
        }

        return result;
    }



    private String readPDFFileContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(b)) != -1) {
            bos.write(b, 0, bytesRead);
        }
        byte[] pdfData = bos.toByteArray();
        inputStream.close();
        bos.close();
        return Base64.encodeToString(pdfData, Base64.DEFAULT);
    }


    private void saveToDatabase(String email, String fileContent) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UploadContract.UploadEntry.COLUMN_EMAIL, email);
        values.put(UploadContract.UploadEntry.COLUMN_FILE_CONTENT, fileContent); // Change to file content

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UploadContract.UploadEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(getApplicationContext(), "Report uploaded successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UploadReportActivity.this, AdminHomePage.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to upload report", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
