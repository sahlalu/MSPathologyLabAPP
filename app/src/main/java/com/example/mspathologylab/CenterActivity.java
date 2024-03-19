package com.example.mspathologylab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CenterActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Center> centerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        recyclerView = findViewById(R.id.recyclerView);

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        CenterAdapter centerAdapter = new CenterAdapter(centerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(centerAdapter);
    }


    private void initData() {
        centerList = new ArrayList<>();
        centerList.add(new Center("Shivam Pathology Center", "9632587412", "Open Till 8.00 PM", "Gandhi Road, Building 12, Kalyan West"));
        centerList.add(new Center("Siddhi Diagnostic Centre", "9632587413", "Open Till 8.00 PM", "Shivaji Path, Building 25, Dombivli East"));
        centerList.add(new Center("Vivek Path Labs", "9632587414", "Open Till 8.00 PM", "Rajendra Nagar, Building 8, Thane West"));
        centerList.add(new Center("Sai Krupa Pathology Lab", "9632587415", "Open Till 8.00 PM", "Krishna Society, Building 15, Karjat"));
        centerList.add(new Center("Aarogyam Diagnostics", "9632587416", "Open Till 8.00 PM", "Jai Hind Colony, Building 30, Mumbai Central"));
        centerList.add(new Center("Nirman Path Labs", "9632587417", "Open Till 8.00 PM", "Navrangpura Road, Building 5, Navi Mumbai"));
        centerList.add(new Center("Om Sai Pathology Services", "9632587418", "Open Till 8.00 PM", "Gokhale Road, Building 18, Kalyan East"));
        centerList.add(new Center("Arogya Diagnostic Centre", "9632587419", "Open Till 8.00 PM", "Ramwadi Street, Building 22, Dombivli West"));
        centerList.add(new Center("Healthy Life Path Lab", "9632587420", "Open Till 8.00 PM", "Ganesh Nagar, Building 7, Thane East"));
        centerList.add(new Center("Krishna Pathology Clinic", "9632587421", "Open Till 8.00 PM", "Surya Nagar, Building 10, Karjat West"));
        centerList.add(new Center("Pristine Diagnostics", "9632587422", "Open Till 8.00 PM", "Ratna Park, Building 21, Mumbai East"));
        centerList.add(new Center("Aarogya Pathology Services", "9632587423", "Open Till 8.00 PM", "Pratham Marg, Building 4, Navi Mumbai South"));
    }

}