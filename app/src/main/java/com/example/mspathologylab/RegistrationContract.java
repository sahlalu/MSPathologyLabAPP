package com.example.mspathologylab;

import android.provider.BaseColumns;

public final class RegistrationContract {
    private RegistrationContract() {}

    public static class RegistrationEntry implements BaseColumns {
        public static final String TABLE_NAME = "registration";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_ZIP_CODE = "zip_code";
        public static final String COLUMN_IMAGE = "image";
    }
}

