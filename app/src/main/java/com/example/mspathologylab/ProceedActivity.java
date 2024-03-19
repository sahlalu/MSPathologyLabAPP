package com.example.mspathologylab;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ProceedActivity extends AppCompatActivity implements PaymentResultListener {

    private TextView testInfoTextView, totalPriceTextView;
    private Button payBtn;

    private DatabaseHelper dbHelper;
    private int amount;

    private Spinner bloodGroupSpinner;

    private EditText patientName, ageEditText, DateEditText, contactNumberEditText, fullAddressEditText, zipCodeEditText, emailEditText;
    EditText selectDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed);

        selectDateEditText = findViewById(R.id.selectDateEditText);

        selectDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Spinner bloodgroupSpinner = findViewById(R.id.bloodGroupSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroupSpinner.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);
        patientName = findViewById(R.id.patientName);
        ageEditText = findViewById(R.id.ageEditText);
        bloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
        DateEditText = findViewById(R.id.selectDateEditText);
        contactNumberEditText = findViewById(R.id.contactNumberEditText);
        fullAddressEditText = findViewById(R.id.fullAddressEditText);
        zipCodeEditText = findViewById(R.id.zipCodeEditText);
        testInfoTextView = findViewById(R.id.testInfoTextView);
        emailEditText=findViewById(R.id.emailEditText);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);




        testInfoTextView = findViewById(R.id.testInfoTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        Intent intent = getIntent();
        if (intent != null) {
            String totalPrice = intent.getStringExtra("totalPrice");
            if (totalPrice != null) {
                totalPriceTextView.setText("Total Price: Rs." + totalPrice);
                amount = Math.round(Float.parseFloat(totalPrice) * 100);
            }
        }

        payBtn = findViewById(R.id.proceed);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    startPayment();
                } else {
                    Toast.makeText(ProceedActivity.this, "Please fill in all the required information", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Retrieve test names and prices from intent extras
        String testInfo = getIntent().getStringExtra("testInfo");

        // Display test names and prices in the TextView
        testInfoTextView.setText(testInfo);
    }

    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date to the EditText
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        selectDateEditText.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        // Set minimum date to today's date
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_DIXPA1YoUWrhfC");
        checkout.setImage(R.drawable.logo);
        JSONObject object = new JSONObject();
        try {
            object.put("name", "MS Pathology App");
            object.put("description", "Test payment");
            object.put("theme.color", "");
            object.put("currency", "INR");
            object.put("amount", amount);
            object.put("prefill.contact", "7710814279");
            object.put("prefill.email", "sahlalu19@gmail.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        checkout.open(this, object);
    }

    @Override
    public void onPaymentSuccess(String s) {
        performRegistration();
        Intent intent = new Intent(ProceedActivity.this, SuccessfulActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPaymentError(int i, String s) {
        // Implement the behavior when payment fails
        // For example, you can show an error message or retry payment
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private boolean isSpinnerDefault(Spinner spinner) {
        return spinner.getSelectedItemPosition() == 0;
    }

    private boolean isFormValid() {
        if (isEditTextEmpty(patientName)) {
            patientName.setError("Patient name is required");
            return false;
        }
        if (isEditTextEmpty(ageEditText)) {
            ageEditText.setError("Age is required");
            return false;
        }
        if (isSpinnerDefault(bloodGroupSpinner)) {
            // Assuming the first item in the spinner is a hint
            ((TextView)bloodGroupSpinner.getSelectedView()).setError("Please select blood group");
            return false;
        }
        if (isEditTextEmpty(DateEditText)) {
            DateEditText.setError("Date is required");
            return false;
        }
        if (isEditTextEmpty(contactNumberEditText)) {
            contactNumberEditText.setError("Contact number is required");
            return false;
        }
        if (isEditTextEmpty(fullAddressEditText)) {
            fullAddressEditText.setError("Address is required");
            return false;
        }
        if (isEditTextEmpty(zipCodeEditText)) {
            zipCodeEditText.setError("Zip code is required");
            return false;
        }
        return true;
    }


    private void performRegistration() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProceedContract.ProceedEntry.COLUMN_NAME, patientName.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_AGE, ageEditText.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_BLOOD_GROUP, bloodGroupSpinner.getSelectedItem().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_TEST_DATE, DateEditText.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_EMAIL, emailEditText.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_CONTACT, contactNumberEditText.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_ADDRESS, fullAddressEditText.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_ZIP_CODE, zipCodeEditText.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_TEST_INFO, testInfoTextView.getText().toString());
        values.put(ProceedContract.ProceedEntry.COLUMN_TOTAL_PRICE, totalPriceTextView.getText().toString());


        long newRowId = db.insert(ProceedContract.ProceedEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Order Booked Successfully : " , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Booking Order Failed " , Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
