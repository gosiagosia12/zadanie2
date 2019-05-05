package com.mr.zadanie2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mr.zadanie2.tasks.TaskListContent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        TaskFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener
{
    private TaskListContent.Task currentTask;
    private final String CURRENT_TASK_KEY = "CurrentTask";
    public static final int CONTACT_REQUEST = 1;
    private final String TASKS_JSON_FILE = "tasks.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentTask = savedInstanceState.getParcelable(CURRENT_TASK_KEY);
        }
        restoreFromJson();
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAdd.class);
                intent.putExtra("Camera", true);
                startActivityForResult(intent, CONTACT_REQUEST);
            }
        });

        FloatingActionButton fabCameraAdd = findViewById(R.id.fabCameraAdd);
        fabCameraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAdd.class);
                startActivityForResult(intent, CONTACT_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            String taskTitle = data.getStringExtra("taskTitle");
            String taskDirector = data.getStringExtra("taskDirector");
            String taskPremiere = data.getStringExtra("taskPremiere");
            String selectedImage = data.getStringExtra("selectedImage");
            String picPath = data.getStringExtra("picPath");

            if(picPath == null){
                if(taskTitle.isEmpty() && taskDirector.isEmpty() && taskPremiere.isEmpty()){
                    TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, getString(R.string.default_title), getString(R.string.default_director), getString(R.string.default_premiere), selectedImage));
                }else{
                    if(taskTitle.isEmpty())
                        taskTitle = getString(R.string.default_title);
                    if(taskDirector.isEmpty())
                        taskDirector = getString(R.string.default_director);
                    if(taskPremiere.isEmpty())
                        taskPremiere = getString(R.string.default_premiere);
                    TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, taskTitle, taskDirector, taskPremiere, selectedImage));
                }
            }else
            if(taskTitle.isEmpty() && taskDirector.isEmpty() && taskPremiere.isEmpty()){
                TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, getString(R.string.default_title), getString(R.string.default_director), getString(R.string.default_premiere), picPath));
            }else{
                if(taskTitle.isEmpty())
                    taskTitle = getString(R.string.default_title);
                if(taskDirector.isEmpty())
                    taskDirector = getString(R.string.default_director);
                       if(taskPremiere.isEmpty())
                           taskPremiere = getString(R.string.default_premiere);
                TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, taskTitle, taskDirector, taskPremiere, picPath));
            }
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
        else if(resultCode == RESULT_CANCELED) {}
    }


    private void saveTasksToJson(){
        Gson gson = new Gson();
        String listJson = gson.toJson(TaskListContent.ITEMS);
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(TASKS_JSON_FILE, MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void restoreFromJson(){
        FileInputStream inputStream;
        int DEFAULT_BUFFER_SIZE = 10000;
        Gson gson = new Gson();
        String readJson;

        try {
            inputStream = openFileInput(TASKS_JSON_FILE);
            FileReader reader = new FileReader(inputStream.getFD());
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int n;
            StringBuilder builder = new StringBuilder();
            while ((n = reader.read(buf)) >= 0) {
                String tmp = String.valueOf(buf);
                String substring = (n < DEFAULT_BUFFER_SIZE) ? tmp.substring(0, n) : tmp;
                builder.append(substring);
            }
            reader.close();
            readJson = builder.toString();
            Type collectionType = new TypeToken<List<TaskListContent.Task>>() {
            }.getType();
            List<TaskListContent.Task> o = gson.fromJson(readJson, collectionType);
            if (o != null) {
                TaskListContent.clearList();
                for (TaskListContent.Task task : o) {
                    TaskListContent.addItem(task);
                }
            }
        }catch(FileNotFoundException e){
                e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void onDestroy(){
        saveTasksToJson();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
        Toast.makeText(this, getString(R.string.item_selected_msg) + position, Toast.LENGTH_SHORT).show();
        currentTask = task;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayTaskInFragment(task);
        }else{
            startSecondActivity(task, position);
        }
    }//}


    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(this, getString(R.string.long_click_msg) + position, Toast.LENGTH_SHORT).show();
        showDeleteDialog();
        currentItemPosition = position;
    }

    public static final String taskExtra = "taskExtra";

    private void startSecondActivity(TaskListContent.Task task, int position){
        Intent intent = new Intent(this, TaskInfoActivity.class);
        intent.putExtra(taskExtra, task);
        startActivity(intent);
    }

    private void displayTaskInFragment(TaskListContent.Task task){
        TaskInfoFragment taskInfoFragment = ((TaskInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(taskInfoFragment != null){
            taskInfoFragment.displayTask(task);
        }
    }

    private int currentItemPosition = -1;
    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size())
            TaskListContent.removeItem(currentItemPosition);
        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addButton);
        if(v != null){
            Snackbar.make(v, getString(R.string.delete_cancel_msg), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_msg), new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    }).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        if(currentTask != null)
            outState.putParcelable(CURRENT_TASK_KEY, currentTask);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            if(currentTask != null)
                displayTaskInFragment(currentTask);
        }
    }
}
