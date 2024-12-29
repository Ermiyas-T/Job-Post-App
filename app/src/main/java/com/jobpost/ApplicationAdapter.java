package com.jobpost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ApplicationAdapter extends ArrayAdapter<Job> {

    private Context context;
    private List<Job> jobs;
    private DatabaseHelper dbHelper;

    public ApplicationAdapter(Context context, List<Job> jobs) {
        super(context, R.layout.item_application, jobs);
        this.context = context;
        this.jobs = jobs;
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_application, parent, false);
        }

        TextView textTitle = convertView.findViewById(R.id.textTitle);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        final Job job = jobs.get(position);
        textTitle.setText(job.getTitle());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the application from database and list
                dbHelper.deleteApplication(job.getTitle());
                jobs.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Application deleted", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
