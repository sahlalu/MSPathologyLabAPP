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

public class ProblemActivity extends AppCompatActivity {

    private EditText name, email, problem;
    private TextView validationMessage;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        dbHelper = new DatabaseHelper(this);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        problem = findViewById(R.id.problem);
        validationMessage = findViewById(R.id.validationMessage);

        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
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

        if (TextUtils.isEmpty(name.getText())) {
            setValidationMessage("Name cannot be empty");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            setValidationMessage("Enter a valid email address");
            return false;
        }

        if (TextUtils.isEmpty(problem.getText())) {
            setValidationMessage("Name cannot be empty");
            return false;
        }

        return true;
    }

    private void performRegistration() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProblemContract.ProblemEntry.COLUMN_NAME, name.getText().toString());
        values.put(ProblemContract.ProblemEntry.COLUMN_EMAIL, email.getText().toString());
        values.put(ProblemContract.ProblemEntry.COLUMN_PROBLEM, problem.getText().toString());

        long newRowId = db.insert(ProblemContract.ProblemEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            showToastAndRedirect("Your Request Submitted Successfully");
        } else {
            showToastAndResetForm("Request Submition Failed ! Please try again");
        }

        db.close();
    }

    private void showToastAndRedirect(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProblemActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToastAndResetForm(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Reset form values
        name.setText("");
        email.setText("");
        problem.setText("");

    }

    private void setValidationMessage(String message) {
        validationMessage.setVisibility(View.VISIBLE);
        validationMessage.setText(message);
    }

}





