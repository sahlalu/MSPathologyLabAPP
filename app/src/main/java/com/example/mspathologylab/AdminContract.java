package com.example.mspathologylab;
import android.provider.BaseColumns;

public class AdminContract {

    public static class AdminEntry implements BaseColumns {
        public static final String TABLE_NAME = "admin";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ROLE = "role";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_CONTACT = "contact";
    }
}

