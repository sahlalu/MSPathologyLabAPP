package com.example.mspathologylab;
import android.provider.BaseColumns;

public class UploadContract {

    public static class UploadEntry implements BaseColumns {
        public static final String TABLE_NAME = "reports";

        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_FILE_CONTENT = "report_file";

    }
}

