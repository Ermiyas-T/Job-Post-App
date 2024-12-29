package com.jobpost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NotificationFragment extends Fragment {

    private TextView tvNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_fragement, container, false);

        tvNotification = view.findViewById(R.id.tvNotification);

        // Load the notification data from SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("com.jobpost.PREFS", Context.MODE_PRIVATE);
        String lastJobPosted = prefs.getString("lastJobPosted", "No new notifications");
        tvNotification.setText(lastJobPosted);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update the notification data when the fragment is resumed
        SharedPreferences prefs = getActivity().getSharedPreferences("com.jobpost.PREFS", Context.MODE_PRIVATE);
        String lastJobPosted = prefs.getString("lastJobPosted", "No new notifications");
        tvNotification.setText(lastJobPosted);
    }
}

