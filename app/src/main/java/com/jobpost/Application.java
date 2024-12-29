// MyApplicationsActivity.java
package com.jobpost;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Application extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ApplicationAdapter applicationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_application);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences preferences = getSharedPreferences("com.jobpost.PREFERENCE_FILE_KEY", MODE_PRIVATE);
        String email = preferences.getString("loggedInEmail", null);

        if (email != null) {
            List<Job> appliedJobs = dbHelper.getApplicationsByEmail(email);

            ListView listView = findViewById(R.id.applications_list);
            applicationAdapter = new ApplicationAdapter(this, appliedJobs);
            listView.setAdapter(applicationAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Job job = appliedJobs.get(position);
                    dbHelper.deleteApplication(job.getTitle());
                    appliedJobs.remove(position);
                    applicationAdapter.notifyDataSetChanged();
                    Toast.makeText(Application.this, "Application deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
