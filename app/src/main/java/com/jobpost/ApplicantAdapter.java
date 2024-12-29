// ApplicantAdapter.java
package com.jobpost;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ApplicantAdapter extends BaseAdapter {

    private Context context;
    private List<JobApplication> applicationList;

    public ApplicantAdapter(Context context, List<JobApplication> applicationList) {
        this.context = context;
        this.applicationList = applicationList;
    }

    @Override
    public int getCount() {
        return applicationList.size();
    }

    @Override
    public Object getItem(int position) {
        return applicationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.applicant_item, parent, false);
        }

        TextView applicantEmail = convertView.findViewById(R.id.applicantEmail);
        TextView applicantJobTitle = convertView.findViewById(R.id.applicantJobTitle);
        TextView applicantPhone = convertView.findViewById(R.id.applicantPhone);
        TextView applicantAddress = convertView.findViewById(R.id.applicantAddress);
        TextView applicantAge = convertView.findViewById(R.id.applicantAge);
        Button contactButton = convertView.findViewById(R.id.contactButton);

        JobApplication application = applicationList.get(position);
        applicantEmail.setText(application.getEmail());
        applicantJobTitle.setText(application.getTitle());
        applicantPhone.setText(String.valueOf(application.getPhone()));
        applicantAddress.setText(application.getAddress());
        applicantAge.setText(String.valueOf(application.getAge()));

        contactButton.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", application.getEmail(), null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Job Application Inquiry");
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        });

        return convertView;
    }
}
