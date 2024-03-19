package com.example.mspathologylab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeVisitActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // Declare recyclerView
    private DatabaseHelper dbHelper; // Declare dbHelper
    private List<HomeVisit> homeVisitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visit);
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        HomeVisitAdapter HomeVisitAdapter = new HomeVisitAdapter(homeVisitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(HomeVisitAdapter);
    }

    private void initData() {
        homeVisitList = dbHelper.getAllvisits();
    }
}