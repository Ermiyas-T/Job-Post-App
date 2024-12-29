// JobAdapter.java
package com.jobpost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class JobAdapter extends BaseAdapter {

    private Context context;
    private List<Job> jobList;

    public JobAdapter(Context context, List<Job> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @Override
    public int getCount() {
        return jobList.size();
    }

    @Override
    public Object getItem(int position) {
        return jobList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        }

        TextView jobTitle = convertView.findViewById(R.id.jobTitle);
        TextView jobDescription = convertView.findViewById(R.id.jobDescription);

        Job job = jobList.get(position);
        jobTitle.setText(job.getTitle());
        jobDescription.setText(job.getDescription());

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, JobDetail.class);
            intent.putExtra("title", job.getTitle());
            intent.putExtra("description", job.getDescription());
            intent.putExtra("location", job.getLocation());
            intent.putExtra("type", job.getType());
            intent.putExtra("salary", job.getSalary());
            context.startActivity(intent);
        });

        return convertView;
    }
}
