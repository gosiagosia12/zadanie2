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

import com.mr.zadanie2.tasks.TaskListContent;

public class MainActivity extends AppCompatActivity
        implements
        TaskFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener
{
    private TaskListContent.Task currentTask;
    private final String CURRENT_TASK_KEY = "CurrentTask";
    public static final int CONTACT_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentTask = savedInstanceState.getParcelable(CURRENT_TASK_KEY);
        }
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAdd.class);
                Intent in = new Intent();
                in.putExtra("Camera", false);
                setResult(RESULT_OK, in);
                startActivityForResult(intent, CONTACT_REQUEST);
            }
        });

        FloatingActionButton fabCameraAdd = findViewById(R.id.fabCameraAdd);
        fabCameraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAdd.class);
                Intent in = new Intent();
                in.putExtra("Camera", true);
                setResult(RESULT_OK, in);
                startActivityForResult(intent, CONTACT_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            String taskTitle = data.getStringExtra("taskTitle");
            String taskDirector = data.getStringExtra("taskDirector");
      //      String taskPremiere = data.getStringExtra("taskPremiere");
            String selectedImage = data.getStringExtra("selectedImage");
            if(taskTitle.isEmpty() && taskDirector.isEmpty()/* && taskPremiere.isEmpty()*/){
                TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, getString(R.string.default_title), getString(R.string.default_director)/*, getString(R.string.default_premiere)*/, selectedImage));
            }else{
                if(taskTitle.isEmpty())
                    taskTitle = getString(R.string.default_title);
                if(taskDirector.isEmpty())
                    taskDirector = getString(R.string.default_director);
         //       if(taskPremiere.isEmpty())
         //           taskPremiere = getString(R.string.default_premiere);
                TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, taskTitle, taskDirector/*, taskPremiere*/, selectedImage));
            }
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
        else if(resultCode == RESULT_CANCELED) {}
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
