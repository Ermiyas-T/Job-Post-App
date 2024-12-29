package com.jobpost;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        // Load the default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navHome) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if (id == R.id.navNotification) {
                    loadFragment(new NotificationFragment());
                    return true;
                } else if (id == R.id.navSetting) {
                    loadFragment(new SettingsFragment());
                    return true;
                } else if (id == R.id.navProfile) {
                    loadFragment(new ProfileFragment());
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
