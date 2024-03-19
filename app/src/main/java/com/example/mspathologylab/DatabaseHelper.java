package com.example.mspathologylab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mspathology.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_REGISTRATION_ENTRIES =
            "CREATE TABLE " + RegistrationContract.RegistrationEntry.TABLE_NAME + " (" +
                    RegistrationContract.RegistrationEntry._ID + " INTEGER PRIMARY KEY," +
                    RegistrationContract.RegistrationEntry.COLUMN_NAME + " TEXT," +
                    RegistrationContract.RegistrationEntry.COLUMN_EMAIL + " TEXT," +
                    RegistrationContract.RegistrationEntry.COLUMN_PHONE_NUMBER + " TEXT," +
                    RegistrationContract.RegistrationEntry.COLUMN_PASSWORD + " TEXT," +
                    RegistrationContract.RegistrationEntry.COLUMN_CITY + " TEXT," +
                    RegistrationContract.RegistrationEntry.COLUMN_ZIP_CODE + " TEXT," +
                    RegistrationContract.RegistrationEntry.COLUMN_IMAGE + " BLOB)";


    private static final String SQL_CREATE_BOOKING_ENTRIES =
            "CREATE TABLE " + BookingContract.BookingEntry.TABLE_NAME + " (" +
                    BookingContract.BookingEntry._ID + " INTEGER PRIMARY KEY," +
                    BookingContract.BookingEntry.COLUMN_NAME + " TEXT," +
                    BookingContract.BookingEntry.COLUMN_EMAIL + " TEXT," +
                    BookingContract.BookingEntry.COLUMN_PHONE_NUMBER + " TEXT," +
                    BookingContract.BookingEntry.COLUMN_HEALTHCHECKUP + " TEXT)";

    private static final String SQL_CREATE_ORDER_ENTRIES =
            "CREATE TABLE " + ProceedContract.ProceedEntry.TABLE_NAME + " (" +
                    ProceedContract.ProceedEntry._ID + " INTEGER PRIMARY KEY," +
                    ProceedContract.ProceedEntry.COLUMN_NAME + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_AGE + " INTEGER," +
                    ProceedContract.ProceedEntry.COLUMN_BLOOD_GROUP + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_TEST_DATE + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_EMAIL + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_CONTACT + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_ADDRESS + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_ZIP_CODE + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_TEST_INFO + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_TOTAL_PRICE + " TEXT," +
                    ProceedContract.ProceedEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_CREATE_PROBLEM_ENTRIES =
            "CREATE TABLE " + ProblemContract.ProblemEntry.TABLE_NAME + " (" +
                    ProblemContract.ProblemEntry._ID + " INTEGER PRIMARY KEY," +
                    ProblemContract.ProblemEntry.COLUMN_NAME + " TEXT," +
                    ProblemContract.ProblemEntry.COLUMN_EMAIL + " TEXT," +
                    ProblemContract.ProblemEntry.COLUMN_PROBLEM + " TEXT)";



    private static final String SQL_DELETE_REGISTRATION_ENTRIES =
            "DROP TABLE IF EXISTS " + RegistrationContract.RegistrationEntry.TABLE_NAME;

    private static final String SQL_DELETE_BOOKING_ENTRIES =
            "DROP TABLE IF EXISTS " + BookingContract.BookingEntry.TABLE_NAME;

    private static final String SQL_DELETE_ORDER_ENTRIES =
            "DROP TABLE IF EXISTS " + ProceedContract.ProceedEntry.TABLE_NAME;

    private static final String SQL_DELETE_PROBLEM_ENTRIES =
            "DROP TABLE IF EXISTS " + ProblemContract.ProblemEntry.TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_REGISTRATION_ENTRIES);
        db.execSQL(SQL_CREATE_BOOKING_ENTRIES);
        db.execSQL(SQL_CREATE_ORDER_ENTRIES);
        db.execSQL(SQL_CREATE_PROBLEM_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_REGISTRATION_ENTRIES);
        db.execSQL(SQL_DELETE_BOOKING_ENTRIES);
        db.execSQL(SQL_DELETE_ORDER_ENTRIES);
        db.execSQL(SQL_DELETE_PROBLEM_ENTRIES);
        onCreate(db);
    }



    public List<ShowOrder> getAllorders() {
        List<ShowOrder> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ProceedContract.ProceedEntry.TABLE_NAME, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String patientName = cursor.getString(cursor.getColumnIndexOrThrow(ProceedContract.ProceedEntry.COLUMN_NAME));
                        String contact = cursor.getString(cursor.getColumnIndexOrThrow(ProceedContract.ProceedEntry.COLUMN_CONTACT));
                        String drawn = cursor.getString(cursor.getColumnIndexOrThrow(ProceedContract.ProceedEntry.COLUMN_TEST_DATE));
                        String email = cursor.getString(cursor.getColumnIndexOrThrow(ProceedContract.ProceedEntry.COLUMN_EMAIL));
                        String ordertime = cursor.getString(cursor.getColumnIndexOrThrow(ProceedContract.ProceedEntry.COLUMN_TIMESTAMP));
                        String test = cursor.getString(cursor.getColumnIndexOrThrow(ProceedContract.ProceedEntry.COLUMN_TEST_INFO));
                        ShowOrder order = new ShowOrder(patientName, contact, drawn, email, ordertime, test);

                        orderList.add(order);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return orderList;
    }


    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {ProceedContract.ProceedEntry.COLUMN_EMAIL};
            String selection = ProceedContract.ProceedEntry.COLUMN_EMAIL + " = ?";
            String[] selectionArgs = {email};

            cursor = db.query(
                    ProceedContract.ProceedEntry.TABLE_NAME,
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






    public List<HomeVisit> getAllvisits() {
        List<HomeVisit> homeVisitList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BookingContract.BookingEntry.TABLE_NAME, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String patientName = cursor.getString(cursor.getColumnIndexOrThrow(BookingContract.BookingEntry.COLUMN_NAME));
                        String contact = cursor.getString(cursor.getColumnIndexOrThrow(BookingContract.BookingEntry.COLUMN_PHONE_NUMBER));
                        String email = cursor.getString(cursor.getColumnIndexOrThrow(BookingContract.BookingEntry.COLUMN_EMAIL));
                        String test = cursor.getString(cursor.getColumnIndexOrThrow(BookingContract.BookingEntry.COLUMN_HEALTHCHECKUP));
                        HomeVisit order = new HomeVisit(patientName, contact,  email, test);

                        homeVisitList.add(order);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return homeVisitList;
    }

    }





