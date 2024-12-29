// MyApplicantsActivity.java
package com.jobpost;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MyApplicantsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ApplicantAdapter applicantAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applicants);

        listView = findViewById(R.id.applicants_list);

        databaseHelper = new DatabaseHelper(this);
        loadApplicantsFromDatabase();
    }

    private void loadApplicantsFromDatabase() {
        List<JobApplication> applicationList = databaseHelper.getApplicationsWithDetails();
        applicantAdapter = new ApplicantAdapter(this, applicationList);
        listView.setAdapter(applicantAdapter);
    }
}
