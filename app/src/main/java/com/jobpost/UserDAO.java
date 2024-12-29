package com.jobpost;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;


public class UserDAO {
    private final SQLiteDatabase db;
    private final DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public User authenticateUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Cursor cursor = null;
        User user = null;

        try {
            cursor = db.rawQuery(query, new String[]{email, password});

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int emailIndex = cursor.getColumnIndex("email");
                int nameIndex = cursor.getColumnIndex("name");
                int ageIndex = cursor.getColumnIndex("age");
                int addressIndex = cursor.getColumnIndex("address");
                int phoneIndex = cursor.getColumnIndex("phone");

                if (idIndex != -1 && emailIndex != -1 && nameIndex != -1 && ageIndex != -1 && addressIndex != -1 && phoneIndex != -1) {
                    user = new User(cursor.getString(nameIndex), cursor.getString(ageIndex), cursor.getInt(addressIndex), cursor.getString(phoneIndex), cursor.getInt(idIndex));
                    user.setId(cursor.getInt(idIndex));
                    user.setEmail(cursor.getString(emailIndex));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    public void close() {
        dbHelper.close();
    }
}
