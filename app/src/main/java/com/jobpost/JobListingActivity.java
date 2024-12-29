package com.jobpost;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class JobListingActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private JobAdapter jobAdapter;
    private EditText searchBar;
    private Button searchButton;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);

        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);
        gridView = findViewById(R.id.gridView);

        databaseHelper = new DatabaseHelper(this);
        loadJobsFromDatabase();

        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString().trim();
            searchJobs(query);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJobsFromDatabase();
    }

    private void loadJobsFromDatabase() {
        List<Job> jobList = databaseHelper.getAllJobs();
        jobAdapter = new JobAdapter(this, jobList);
        gridView.setAdapter(jobAdapter);
    }

    private void searchJobs(String query) {
        List<Job> filteredJobs = databaseHelper.getAllJobs();
        filteredJobs.removeIf(job -> !job.getTitle().toLowerCase().contains(query.toLowerCase()));
        jobAdapter = new JobAdapter(this, filteredJobs);
        gridView.setAdapter(jobAdapter);
    }
}
