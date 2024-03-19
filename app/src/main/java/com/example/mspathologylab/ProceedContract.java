package com.example.mspathologylab;

import android.provider.BaseColumns;

public final class ProceedContract {
    private ProceedContract() {}

    public static class ProceedEntry implements BaseColumns {
        public static final String TABLE_NAME = "test";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_BLOOD_GROUP = "blood_group";
        public static final String COLUMN_TEST_DATE = "test_date";
        public static final String COLUMN_EMAIL = "email_id";
        public static final String COLUMN_CONTACT = "contact";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_ZIP_CODE = "zip_code";
        public static final String COLUMN_TEST_INFO = "test_info";
        public static final String COLUMN_TOTAL_PRICE = "total_price";
        public static  final String COLUMN_TIMESTAMP = "ordertime";
    }
}

