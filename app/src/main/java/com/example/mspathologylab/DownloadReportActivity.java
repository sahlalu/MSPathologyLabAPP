package com.example.mspathologylab;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DownloadReportActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button downloadButton;
    private ProgressBar progressBar;
    private TextView textShopped, textOrderConfirmed, textOutOfDelivery, textDelivered, logtv4, logtv5, logtv6;

    private ImageView track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_report);

        emailEditText = findViewById(R.id.emailEditText);
        downloadButton = findViewById(R.id.downloadButton);
        progressBar = findViewById(R.id.progressBar);
        textShopped = findViewById(R.id.textShopped);
        textOrderConfirmed = findViewById(R.id.textOrderConfirmed);
        textOutOfDelivery = findViewById(R.id.textOutOfDelivery);
        textDelivered = findViewById(R.id.textDelivered);
        logtv4 = findViewById(R.id.logtv4);
        logtv5 = findViewById(R.id.logtv5);
        logtv6 = findViewById(R.id.logtv6);
        track = findViewById(R.id.track);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                fadeInView(progressBar);
                track.setVisibility(View.VISIBLE);
                fadeInView(track);
                checkAndDownloadReport(email);
            }
        });
    }

    private void checkAndDownloadReport(final String email) {
        final AdminDbHelper dbHelper = new AdminDbHelper(this);
        final DatabaseHelper dbHelper2 = new DatabaseHelper(this);
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setProgress(0);

        // Simulated delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean reportExists = dbHelper.checkReportExists(email);
                boolean emailExists = dbHelper2.checkEmailExists(email);
                if (reportExists) {
                    downloadReport(email);
                } else if(emailExists){
                    progressBar.setProgress(66);
                    textOutOfDelivery.setVisibility(View.VISIBLE);
                    fadeInView(textOutOfDelivery);
                    logtv5.setVisibility(View.GONE);
                    logtv6.setVisibility(View.VISIBLE);
                    logtv4.setVisibility(View.GONE);
                }else {
                    progressBar.setProgress(0);
                    fadeInView(logtv4);
                    textOutOfDelivery.setVisibility(View.GONE);
                    textShopped.setVisibility(View.GONE);
                    textOrderConfirmed.setVisibility(View.GONE);
                    textDelivered.setVisibility(View.GONE);
                    logtv4.setVisibility(View.VISIBLE);
                    logtv5.setVisibility(View.GONE);
                    logtv6.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    track.setVisibility(View.GONE);
                }
            }
        }, 4000);

        // Smooth visibility transition
        textOrderConfirmed.setVisibility(View.VISIBLE);
        fadeInView(textOrderConfirmed);

        // Simulated delay for other texts
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textShopped.setVisibility(View.VISIBLE);
                logtv4.setVisibility(View.GONE);
                fadeInView(textShopped);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logtv4.setVisibility(View.GONE);
                textOutOfDelivery.setVisibility(View.VISIBLE);
                fadeInView(textOutOfDelivery);
            }
        }, 3000);
    }

    // Smooth visibility transition animation
    private void fadeInView(View view) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);
        view.startAnimation(fadeIn);
    }

    // Simulated method to download report file
    private void downloadReport(String email) {
        final AdminDbHelper dbHelper = new AdminDbHelper(this);
        File reportFile = dbHelper.getReportFromDatabase(email);

        if (reportFile != null) {
            Toast.makeText(this, "Report downloaded for " + email, Toast.LENGTH_SHORT).show();
            // TODO: Implement logic to open the downloaded report file using an appropriate PDF viewer
            progressBar.setProgress(100);
            textDelivered.setVisibility(View.VISIBLE);
            fadeInView(textDelivered);
            logtv5.setVisibility(View.VISIBLE);
            logtv6.setVisibility(View.GONE);
            logtv4.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Error downloading report for " + email, Toast.LENGTH_SHORT).show();
        }
    }
}

