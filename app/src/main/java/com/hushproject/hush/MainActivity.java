package com.hushproject.hush;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create SharedPreferences
        SharedPreferences locPrefs = getSharedPreferences("LocPrefs", MODE_PRIVATE);

        ArrayList<UserLocations> locations = new ArrayList<>();

        ArrayList<String> locationKeys = new ArrayList<>();

        //Get all location keys.
        Map<String, ?> keys = locPrefs.getAll();
        for(Map.Entry<String, ?> entries : keys.entrySet()) {
            locationKeys.add(entries.getKey());
        }

        //Convert all json strings back into UserLocations.
        for(int i = 0; i < locationKeys.size(); i++) {
            //find object using key.
            String savedLoc = locPrefs.getString(locationKeys.get(i), "");
            //convert json string back to object.
            UserLocations savedLocation = gson.fromJson(savedLoc, UserLocations.class);
            //add recovered object to locations ArrayList.
            locations.add(savedLocation);
        }

        startService();

        //LocationViewAdapter
        RecyclerView.Adapter locationAdapter = new LocationViewAdapter(locations);
        RecyclerView locationView = findViewById(R.id.locationViewer);
        locationView.setAdapter(locationAdapter);
        locationView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService();
    }

    public void startService() {

        Intent foregroundServiceIntent = new Intent(this, ForegroundService.class);
        startService(foregroundServiceIntent);
    }

    public void stopService() {
        Intent foregroundServiceIntent = new Intent(this, ForegroundService.class);
        stopService(foregroundServiceIntent);
    }

    public void add(View view) {
        Intent openAdd = new Intent(this, AddActivity.class);
        openAdd.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openAdd);
    }
}
