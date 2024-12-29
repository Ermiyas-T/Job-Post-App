package com.jobpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        etEmail = findViewById(R.id.login_et_email);
        etPassword = findViewById(R.id.login_et_password);
        btnLogin = findViewById(R.id.login_btn_login);
        btnRegister = findViewById(R.id.login_btn_register);

        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAuthenticated = dbHelper.authenticateUser(email, password);
                    if (isAuthenticated) {
                        saveLoggedInEmail(email);  // Save email to SharedPreferences
                        Toast.makeText(LogInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Navigate to the next activity after successful login
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void saveLoggedInEmail(String email) {
        SharedPreferences preferences = getSharedPreferences("com.jobpost.PREFERENCE_FILE_KEY", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loggedInEmail", email);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
