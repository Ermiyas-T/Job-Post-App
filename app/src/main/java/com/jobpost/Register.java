package com.jobpost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Register extends AppCompatActivity {

    EditText email, password, phone, address, age;
    Button register;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase dbwriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        phone = findViewById(R.id.register_phone);
        address = findViewById(R.id.register_address);
        age = findViewById(R.id.register_age);
        register = findViewById(R.id.btn_register);

        databaseHelper = new DatabaseHelper(this);
        dbwriter = databaseHelper.getWritableDatabase();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String phoneStr = phone.getText().toString().trim();
                String addressStr = address.getText().toString().trim();
                String ageStr = age.getText().toString().trim();

                if (emailStr.isEmpty() || passwordStr.isEmpty() || phoneStr.isEmpty() || addressStr.isEmpty() || ageStr.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int phoneInt = Integer.parseInt(phoneStr);
                        int ageInt = Integer.parseInt(ageStr);

                        ContentValues values = new ContentValues();
                        values.put("email", emailStr);
                        values.put("password", passwordStr);
                        values.put("phone", phoneInt);
                        values.put("address", addressStr);
                        values.put("age", ageInt);

                        long result = dbwriter.insert("users", null, values);

                        if (result != -1) {
                            Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, LogInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Invalid Registration", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(Register.this, "Phone and Age must be numeric", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbwriter != null) {
            dbwriter.close();
        }
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
