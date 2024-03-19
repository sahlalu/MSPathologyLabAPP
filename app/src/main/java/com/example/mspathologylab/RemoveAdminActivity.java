package com.example.mspathologylab;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RemoveAdminActivity extends AppCompatActivity {

    private AdminDbHelper dbHelper;
    private ArrayList<String> adminsList;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_admin);

        dbHelper = new AdminDbHelper(this);
        adminsList = dbHelper.getAllAdmins();

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, adminsList);
        listView.setAdapter(adapter);

        Button removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedAdmins();
            }
        });
    }

    private void removeSelectedAdmins() {
        SparseBooleanArray checkedItemPositions = ((ListView) findViewById(R.id.listView)).getCheckedItemPositions();
        for (int i = checkedItemPositions.size() - 1; i >= 0; i--) {
            if (checkedItemPositions.valueAt(i)) {
                String adminName = adapter.getItem(checkedItemPositions.keyAt(i));
                dbHelper.removeAdmin(adminName);
                adapter.remove(adminName);
            }
        }
        adapter.notifyDataSetChanged();
    }


}
