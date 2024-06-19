package com.omprakash.todolist;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextTask;
    private Button buttonAddTask;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private SQLiteDatabase database;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteOpenHelper dbHelper = new com.omprakash.todolist.TaskDbHelper(this);
        database = dbHelper.getWritableDatabase();

        searchView = findViewById(R.id.searchviewtext);
        //searchView.clearFocus();

        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this, getAllTasks());
        recyclerViewTasks.setAdapter(taskAdapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskAdapter.swapCursor(getFilteredTasks(newText));
                return true;
            }
        });



    }
    private Cursor getFilteredTasks(String query) {
        return database.rawQuery("SELECT * FROM tasks WHERE task LIKE ?", new String[]{"%" + query + "%"});
    }
    private void addTask() {
        String task = editTextTask.getText().toString().trim();
        if (!TextUtils.isEmpty(task)) {
            database.execSQL("INSERT INTO tasks (task) VALUES (?)", new String[]{task});
            taskAdapter.swapCursor(getAllTasks());
            editTextTask.getText().clear();
        }
    }

    private Cursor getAllTasks() {
        return database.rawQuery("SELECT * FROM tasks", null);
    }
    public void deleteTask(long id) {
        database.delete(TaskContract.TaskEntry.TABLE_NAME, TaskContract.TaskEntry._ID + "=" + id, null);
        taskAdapter.swapCursor(getAllTasks());
    }
}
