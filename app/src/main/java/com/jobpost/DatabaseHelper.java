package com.jobpost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "User.db";
    public static final String TABLE_USER = "users";
    private static final String TABLE_APPLICATIONS = "applications";
    private static final int DATABASE_VERSION = 2;

    public static final String COL_ID = "id";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_PHONE = "phone";
    public static final String COL_ADDRESS = "address";
    public static final String COL_AGE = "age";
    private static final String TABLE_JOBS = "jobs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_SALARY = "salary";
    //-----------------------------------------------------
    private static final String COLUMN_APPLICATION_EMAIL = "email";
    private static final String COLUMN_APPLICATION_TITLE = "title";
    private static final String COLUMN_APPLICATION_DESCRIPTION = "description";
    private static final String COLUMN_APPLICATION_LOCATION = "location";
    private static final String COLUMN_APPLICATION_TYPE = "type";
    private static final String COLUMN_APPLICATION_SALARY = "salary";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_USER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_PHONE + " INTEGER, " +
                COL_ADDRESS + " TEXT, " +
                COL_AGE + " INTEGER)";
        db.execSQL(sql);
        String CREATE_JOBS_TABLE = "CREATE TABLE " + TABLE_JOBS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_SALARY + " TEXT" + ")";
        db.execSQL(CREATE_JOBS_TABLE);
        //------------------------------------
        String CREATE_APPLICATIONS_TABLE = "CREATE TABLE " + TABLE_APPLICATIONS + "("
                + COLUMN_APPLICATION_EMAIL + " TEXT,"
                + COLUMN_APPLICATION_TITLE + " TEXT,"
                + COLUMN_APPLICATION_DESCRIPTION + " TEXT,"
                + COLUMN_APPLICATION_LOCATION + " TEXT,"
                + COLUMN_APPLICATION_TYPE + " TEXT,"
                + COLUMN_APPLICATION_SALARY + " TEXT" + ")";
        db.execSQL(CREATE_APPLICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
        onCreate(db);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_PHONE, user.getPhone());
        values.put(COL_ADDRESS, user.getAddress());
        values.put(COL_AGE, user.getAge());

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }
    public void addJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, job.getTitle());
        values.put(COLUMN_DESCRIPTION, job.getDescription());
        values.put(COLUMN_LOCATION, job.getLocation());
        values.put(COLUMN_TYPE, job.getType());
        values.put(COLUMN_SALARY, job.getSalary());

        db.insert(TABLE_JOBS, null, values);
        db.close();
    }
    public List<Job> getAllJobs() {
        List<Job> jobList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_JOBS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    String title = getColumnString(cursor, COLUMN_TITLE);
                    String description = getColumnString(cursor, COLUMN_DESCRIPTION);
                    String location = getColumnString(cursor, COLUMN_LOCATION);
                    String type = getColumnString(cursor, COLUMN_TYPE);
                    String salary = getColumnString(cursor, COLUMN_SALARY);

                    Job job = new Job(title, description, location, type, salary);
                    jobList.add(job);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return jobList;
    }

    private String getColumnString(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            throw new IllegalArgumentException("Column " + columnName + " not found in the cursor");
        }
        return cursor.getString(index);
    }

    public Cursor getUserDataByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_EMAIL + " = ?", new String[]{email});
    }

    public boolean isUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COL_EMAIL + " = ?", new String[]{email}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?", new String[]{email, password}, null, null, null);

        boolean isAuthenticated = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return isAuthenticated;
    }

    public boolean updateUser(String oldEmail, String newEmail, String password, String phone, String address, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, newEmail);
        values.put(COL_PASSWORD, password);
        values.put(COL_PHONE, phone);
        values.put(COL_ADDRESS, address);
        values.put(COL_AGE, age);

        // Update the user data based on old email
        int rowsAffected = db.update(TABLE_USER, values, COL_EMAIL + " = ?", new String[]{oldEmail});
        db.close();

        return rowsAffected > 0;
    }

    public long addApplication(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_APPLICATIONS, null, values);
    }

    public List<Job> getApplicationsByEmail(String email) {
        List<Job> jobs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPLICATIONS, null, COLUMN_APPLICATION_EMAIL + "=?",
                new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex(COLUMN_APPLICATION_TITLE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_APPLICATION_DESCRIPTION);
                int locationIndex = cursor.getColumnIndex(COLUMN_APPLICATION_LOCATION);
                int typeIndex = cursor.getColumnIndex(COLUMN_APPLICATION_TYPE);
                int salaryIndex = cursor.getColumnIndex(COLUMN_APPLICATION_SALARY);

                if (titleIndex >= 0 && descriptionIndex >= 0 && locationIndex >= 0 &&
                        typeIndex >= 0 && salaryIndex >= 0) {
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    String location = cursor.getString(locationIndex);
                    String type = cursor.getString(typeIndex);
                    String salary = cursor.getString(salaryIndex);
                    jobs.add(new Job(title, description, location, type, salary));
                } else {
                    // Handle the case where one or more column indices are invalid
                    Log.e("DatabaseHelper", "One or more column indices are invalid");
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return jobs;
    }

    public int deleteApplication(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_APPLICATIONS, COLUMN_APPLICATION_TITLE + "=?",
                new String[]{title});
    }

    public boolean deleteJob(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_JOBS, COLUMN_TITLE + "=?", new String[]{title});
        db.close();
        return rowsDeleted > 0;
    }


    public boolean deleteApplication(String email, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_APPLICATIONS, COLUMN_APPLICATION_EMAIL + "=? AND " + COLUMN_APPLICATION_TITLE + "=?", new String[]{email, title});
        db.close();
        return rowsDeleted > 0;
    }

    // Method to get applications by email
    public List<JobApplication> getApplicationsWithDetails() {
        List<JobApplication> applications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPLICATIONS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int emailIndex = cursor.getColumnIndex(COLUMN_APPLICATION_EMAIL);
                int titleIndex = cursor.getColumnIndex(COLUMN_APPLICATION_TITLE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_APPLICATION_DESCRIPTION);
                int locationIndex = cursor.getColumnIndex(COLUMN_APPLICATION_LOCATION);
                int typeIndex = cursor.getColumnIndex(COLUMN_APPLICATION_TYPE);
                int salaryIndex = cursor.getColumnIndex(COLUMN_APPLICATION_SALARY);

                if (emailIndex >= 0 && titleIndex >= 0 && descriptionIndex >= 0 &&
                        locationIndex >= 0 && typeIndex >= 0 && salaryIndex >= 0) {

                    String email = cursor.getString(emailIndex);
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    String location = cursor.getString(locationIndex);
                    String type = cursor.getString(typeIndex);
                    String salary = cursor.getString(salaryIndex);

                    Cursor userCursor = db.query(TABLE_USER, null, COL_EMAIL + "=?", new String[]{email}, null, null, null);
                    if (userCursor != null && userCursor.moveToFirst()) {
                        int phoneIndex = userCursor.getColumnIndex(COL_PHONE);
                        int addressIndex = userCursor.getColumnIndex(COL_ADDRESS);
                        int ageIndex = userCursor.getColumnIndex(COL_AGE);

                        if (phoneIndex >= 0 && addressIndex >= 0 && ageIndex >= 0) {
                            int phone = userCursor.getInt(phoneIndex);
                            String address = userCursor.getString(addressIndex);
                            int age = userCursor.getInt(ageIndex);

                            applications.add(new JobApplication(email, title, description, location, type, salary, phone, address, age));
                        }
                    }
                    if (userCursor != null) {
                        userCursor.close();
                    }
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return applications;
    }



}
