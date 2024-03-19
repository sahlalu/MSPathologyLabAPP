package com.example.mspathologylab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AdminDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2; // Updated version
    public static final String DATABASE_NAME = "mspathology(Admin).db";

    private static final String SQL_CREATE_ADMIN_ENTRIES =
            "CREATE TABLE " + AdminContract.AdminEntry.TABLE_NAME + " (" +
                    AdminContract.AdminEntry._ID + " INTEGER PRIMARY KEY," +
                    AdminContract.AdminEntry.COLUMN_NAME + " TEXT," +
                    AdminContract.AdminEntry.COLUMN_ROLE + " TEXT," +
                    AdminContract.AdminEntry.COLUMN_EMAIL + " TEXT," +
                    AdminContract.AdminEntry.COLUMN_USERNAME + " TEXT," +
                    AdminContract.AdminEntry.COLUMN_PASSWORD + " TEXT," +
                    AdminContract.AdminEntry.COLUMN_CONTACT + " TEXT)";

    private static final String SQL_CREATE_UPLOAD_ENTRIES =
            "CREATE TABLE " + UploadContract.UploadEntry.TABLE_NAME + " (" +
                    UploadContract.UploadEntry._ID + " INTEGER PRIMARY KEY," +
                    UploadContract.UploadEntry.COLUMN_EMAIL + " TEXT," +
                    UploadContract.UploadEntry.COLUMN_FILE_CONTENT + " BLOB)";

    private static final String SQL_DELETE_ADMIN_ENTRIES =
            "DROP TABLE IF EXISTS " + AdminContract.AdminEntry.TABLE_NAME;

    private static final String SQL_DELETE_UPLOAD_ENTRIES =
            "DROP TABLE IF EXISTS " + UploadContract.UploadEntry.TABLE_NAME;

    private Context mContext;

    public AdminDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ADMIN_ENTRIES);
        db.execSQL(SQL_CREATE_UPLOAD_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ADMIN_ENTRIES);
        db.execSQL(SQL_DELETE_UPLOAD_ENTRIES);
        onCreate(db);
    }

    private File createTempFile(byte[] reportData) {
        File tempFile = null;
        try {
            File directory = new File(mContext.getFilesDir(), "reports");
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    Log.e("AdminDbHelper", "Failed to create directory: " + directory.getAbsolutePath());
                    return null; // Return null to indicate failure
                }
            }
            tempFile = File.createTempFile("report_", ".pdf", directory);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(reportData);
            fos.close();
        } catch (IOException e) {
            Log.e("AdminDbHelper", "Error creating temp file", e);
            e.printStackTrace();
        }
        return tempFile;
    }

    public File getReportFromDatabase(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {UploadContract.UploadEntry.COLUMN_FILE_CONTENT};
            String selection = UploadContract.UploadEntry.COLUMN_EMAIL + " = ?";
            String[] selectionArgs = {email};

            cursor = db.query(
                    UploadContract.UploadEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if (cursor != null && cursor.moveToFirst()) {
                int fileContentIndex = cursor.getColumnIndex(UploadContract.UploadEntry.COLUMN_FILE_CONTENT);
                if (fileContentIndex != -1) {
                    byte[] reportData = cursor.getBlob(fileContentIndex);
                    File tempFile = createTempFile(reportData);
                    return tempFile;
                } else {
                    // Log an error message indicating that the column doesn't exist
                    Log.e("AdminDbHelper", "COLUMN_FILE_CONTENT column does not exist in the cursor.");
                    // You might also throw an exception or handle it according to your application's requirements
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return null; // Return null if no report found
    }

    public boolean checkReportExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {UploadContract.UploadEntry.COLUMN_EMAIL};
            String selection = UploadContract.UploadEntry.COLUMN_EMAIL + " = ?";
            String[] selectionArgs = {email};

            cursor = db.query(
                    UploadContract.UploadEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public ArrayList<String> getAllAdmins() {
        ArrayList<String> adminsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + AdminContract.AdminEntry.TABLE_NAME, null);
            if (cursor != null) {
                int nameIndex = cursor.getColumnIndex(AdminContract.AdminEntry.COLUMN_NAME);
                if (nameIndex != -1 && cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(nameIndex);
                        adminsList.add(name);
                    } while (cursor.moveToNext());
                } else {
                    Log.e("AdminDbHelper", "COLUMN_NAME does not exist in the cursor.");
                }
            }
        } catch (Exception e) {
            Log.e("AdminDbHelper", "Error getting all admins: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return adminsList;
    }



    public void removeAdmin(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AdminContract.AdminEntry.TABLE_NAME,
                AdminContract.AdminEntry.COLUMN_NAME + " = ?",
                new String[]{name});
        db.close();
    }

    }

