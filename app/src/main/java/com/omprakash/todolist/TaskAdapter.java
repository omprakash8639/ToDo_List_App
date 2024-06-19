package com.omprakash.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private Cursor cursor;

    public TaskAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        @SuppressLint("Range") String task = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_NAME));
        @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(TaskContract.TaskEntry._ID));

        holder.textViewTask.setText(task);
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).deleteTask(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;
        CheckBox checkBoxComplete;
        Button buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            checkBoxComplete = itemView.findViewById(R.id.checkBoxComplete);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
