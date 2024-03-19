package com.example.mspathologylab;


import android.provider.BaseColumns;

public class ProblemContract {

    public static class ProblemEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_problem";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PROBLEM = "problem";

    }
}


