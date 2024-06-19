package com.omprakash.todolist;

import android.net.Uri;
import android.provider.BaseColumns;

public final class TaskContract {

    // Prevents instantiation of the contract class.
    private TaskContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.todolist";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TASKS = "tasks";

    public static final class TaskEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "task";
        public static final String COLUMN_TASK_COMPLETED = "completed";
    }
}
