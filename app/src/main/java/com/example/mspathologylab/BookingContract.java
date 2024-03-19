package com.example.mspathologylab;

import android.provider.BaseColumns;

public final class BookingContract {
    private BookingContract() {}

    public static class BookingEntry implements BaseColumns {
        public static final String TABLE_NAME = "bookings";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_HEALTHCHECKUP = "healthcheck";

    }
}
