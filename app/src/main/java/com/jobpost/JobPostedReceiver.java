package com.jobpost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class JobPostedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && "com.jobpost.JOB_POSTED".equals(intent.getAction())) {
            String jobTitle = intent.getStringExtra("jobTitle");
            SharedPreferences prefs = context.getSharedPreferences("com.jobpost.PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastJobPosted", jobTitle);
            editor.apply();
        }
    }
}
