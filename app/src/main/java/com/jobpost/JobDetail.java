// JobDetail.java
package com.jobpost;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class JobDetail extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        dbHelper = new DatabaseHelper(this);

        TextView title = findViewById(R.id.job_title);
        TextView description = findViewById(R.id.job_keywords);
        TextView location = findViewById(R.id.job_location);
        TextView type = findViewById(R.id.job_type);
        TextView salary = findViewById(R.id.job_salary);
        Button applyButton = findViewById(R.id.apply_button);

        // Get data from intent
        String titleText = getIntent().getStringExtra("title");
        String descriptionText = getIntent().getStringExtra("description");
        String locationText = getIntent().getStringExtra("location");
        String typeText = getIntent().getStringExtra("type");
        String salaryText = getIntent().getStringExtra("salary");

        // Set data to views
        title.setText(titleText);
        description.setText(descriptionText);
        location.setText(locationText);
        type.setText(typeText);
        salary.setText(salaryText);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("com.jobpost.PREFERENCE_FILE_KEY", MODE_PRIVATE);
                String email = preferences.getString("loggedInEmail", null);

                if (email != null) {
                    ContentValues values = new ContentValues();
                    values.put("email", email);
                    values.put("title", titleText);
                    values.put("description", descriptionText);
                    values.put("location", locationText);
                    values.put("type", typeText);
                    values.put("salary", salaryText);

                    long result = dbHelper.addApplication(values);
                    if (result != -1) {
                        Toast.makeText(JobDetail.this, "Applied successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JobDetail.this, Application.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(JobDetail.this, "Failed to apply", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JobDetail.this, "Please log in first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
