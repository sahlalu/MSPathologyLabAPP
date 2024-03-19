package com.example.mspathologylab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessfulActivity extends AppCompatActivity {

    private Button success_message_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);

        success_message_btn = findViewById(R.id.success_message_btn);

        success_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessfulActivity.this, UserHomePage.class));
            }
        });


    }
}