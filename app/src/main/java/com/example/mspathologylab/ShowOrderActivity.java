package com.example.mspathologylab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView; // Declare recyclerView
    private DatabaseHelper dbHelper; // Declare dbHelper
    private List<ShowOrder> showorderList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        ShowOrderAdapter ShowOrderAdapter = new ShowOrderAdapter(showorderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ShowOrderAdapter);
    }

    private void initData() {
        showorderList = dbHelper.getAllorders();
    }
}
