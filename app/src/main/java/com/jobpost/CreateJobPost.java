package com.jobpost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateJobPost extends AppCompatActivity {

    private EditText etJobTitle, etJobDescription, etJobLocation, etJobType, etJobSalary;
    private Button btnPostJob;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_post);

        etJobTitle = findViewById(R.id.etJobTitle);
        etJobDescription = findViewById(R.id.etJobDescription);
        etJobLocation = findViewById(R.id.etJobLocation);
        etJobType = findViewById(R.id.etJobType);
        etJobSalary = findViewById(R.id.etJobSalary);
        btnPostJob = findViewById(R.id.btnPostJob);

        databaseHelper = new DatabaseHelper(this);

        btnPostJob.setOnClickListener(v -> {
            String title = etJobTitle.getText().toString().trim();
            String description = etJobDescription.getText().toString().trim();
            String location = etJobLocation.getText().toString().trim();
            String type = etJobType.getText().toString().trim();
            String salary = etJobSalary.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || salary.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Job job = new Job(title, description, location, type, salary);
                databaseHelper.addJob(job);

                // Save the notification data in SharedPreferences
                SharedPreferences prefs = getSharedPreferences("com.jobpost.PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String notificationMessage = "JOB POST: " + title + " - " + description;
                editor.putString("lastJobPosted", notificationMessage);
                editor.apply();

                Toast.makeText(this, "Job posted successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            }
        });
    }
}
