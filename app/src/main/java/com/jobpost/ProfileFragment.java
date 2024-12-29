package com.jobpost;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private ImageView profileImageView;
    private Button changePhotoButton, updateButton;
    private int currentImageIndex = 1; // Start with image1.png
    private final int MAX_IMAGE_COUNT = 5;
    private EditText editTextEmail, editTextPassword, editTextPhone, editTextAddress, editTextAge;
    private DatabaseHelper databaseHelper;
    private String loggedInEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = view.findViewById(R.id.profileImageView);
        changePhotoButton = view.findViewById(R.id.changePhotoButton);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextAge = view.findViewById(R.id.editTextAge);
        updateButton = view.findViewById(R.id.updateButton);

        databaseHelper = new DatabaseHelper(getContext());

        // Retrieve logged in user's email from SharedPreferences
        loggedInEmail = getLoggedInEmail();

        if (loggedInEmail != null) {
            populateProfileData(loggedInEmail);
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }

        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfileImage();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        // Initially load the first image
        loadProfileImage(currentImageIndex);

        return view;
    }

    private String getLoggedInEmail() {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.jobpost.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        return preferences.getString("loggedInEmail", null);
    }

    private void populateProfileData(String email) {
        Cursor cursor = databaseHelper.getUserDataByEmail(email);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int emailIndex = cursor.getColumnIndex(DatabaseHelper.COL_EMAIL);
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COL_PASSWORD);
                int phoneIndex = cursor.getColumnIndex(DatabaseHelper.COL_PHONE);
                int addressIndex = cursor.getColumnIndex(DatabaseHelper.COL_ADDRESS);
                int ageIndex = cursor.getColumnIndex(DatabaseHelper.COL_AGE);

                if (emailIndex != -1) {
                    editTextEmail.setText(cursor.getString(emailIndex));
                }
                if (passwordIndex != -1) {
                    editTextPassword.setText(cursor.getString(passwordIndex));
                }
                if (phoneIndex != -1) {
                    editTextPhone.setText(cursor.getString(phoneIndex));
                }
                if (addressIndex != -1) {
                    editTextAddress.setText(cursor.getString(addressIndex));
                }
                if (ageIndex != -1) {
                    editTextAge.setText(String.valueOf(cursor.getInt(ageIndex)));
                }

            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Error populating profile data: " + e.getMessage());
                Toast.makeText(getContext(), "Error populating profile data", Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
        } else {
            Toast.makeText(getContext(), "Failed to load profile data", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserProfile() {
        String newEmail = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();

        if (newEmail.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        boolean success = databaseHelper.updateUser(loggedInEmail, newEmail, password, phone, address, age);

        if (success) {
            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
            loggedInEmail = newEmail; // Update loggedInEmail if email was changed
            saveLoggedInEmail(newEmail); // Save updated email to SharedPreferences
        } else {
            Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLoggedInEmail(String email) {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.jobpost.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loggedInEmail", email);
        editor.apply();
    }

    private void changeProfileImage() {
        currentImageIndex = (currentImageIndex % MAX_IMAGE_COUNT) + 1; // Cycle through 1 to MAX_IMAGE_COUNT
        loadProfileImage(currentImageIndex);
    }

    private void loadProfileImage(int imageIndex) {
        int resourceId = getResources().getIdentifier("image" + imageIndex, "raw", requireActivity().getPackageName());

        if (resourceId != 0) {
            profileImageView.setImageResource(resourceId);
        } else {
            Toast.makeText(getContext(), "Failed to load profile image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
