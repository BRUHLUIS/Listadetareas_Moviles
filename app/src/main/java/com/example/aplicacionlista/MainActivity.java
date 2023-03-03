package com.example.aplicacionlista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the tasks list and adapter
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks);

        // Set the adapter on the ListView
        ListView listViewTasks = findViewById(R.id.list_view_tasks);
        listViewTasks.setAdapter(adapter);

        // Add a click listener to the add task button
        Button buttonAddTask = findViewById(R.id.button_add_task);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.edit_text_task);
                String description = editText.getText().toString().trim();

                if (!description.isEmpty()) {
                    Task task = new Task(description);
                    tasks.add(task);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });
        Button buttonResetTasks = findViewById(R.id.button_reset_tasks);
        buttonResetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.clear();
                adapter.notifyDataSetChanged();
            }
        });

        // Add a long click listener to each item in the ListView to allow the user to delete tasks
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private static class TaskAdapter extends ArrayAdapter<Task> {

        public TaskAdapter(Context context, ArrayList<Task> tasks) {
            super(context, 0, tasks);
        }


        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
            }

            Task task = getItem(position);

            CheckBox checkBoxTask = convertView.findViewById(R.id.check_box_task);
            checkBoxTask.setText(task.getDescription());
            checkBoxTask.setChecked(task.isDone());
            checkBoxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setDone(isChecked);
                }
            });

            return convertView;
        }
    }
}