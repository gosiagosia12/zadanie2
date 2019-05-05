package com.mr.zadanie2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.mr.zadanie2.tasks.TaskListContent;



/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment/* implements View.OnClickListener */{


    public TaskInfoFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }


    public void displayTask(TaskListContent.Task task){
        FragmentActivity activity = getActivity();
        (activity.findViewById(R.id.displayFragment)).setVisibility(View.VISIBLE);
        TextView taskInfoTitle = activity.findViewById(R.id.taskInfoTitle);
        TextView taskInfoDirector = activity.findViewById(R.id.taskInfoDirector);
        TextView taskInfoPremiere = activity.findViewById(R.id.taskInfoPremiere);
        final ImageView taskInfoImage = activity.findViewById(R.id.taskInfoImage);

        taskInfoTitle.setText("Tytuł: " + task.title);
        taskInfoDirector.setText("Reżyser: " + task.director);
        taskInfoPremiere.setText("Data premiery: " + task.premiere);
        if(task.picPath != null && !task.picPath.isEmpty()) {
            if (task.picPath.contains("avatar")) {
                Drawable taskDrawable;
                switch (task.picPath) {
                    case "avatar 1":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_1);
                        break;
                    case "avatar 2":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_2);
                        break;
                    case "avatar 3":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_3);
                        break;
                    case "avatar 4":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_4);
                        break;
                    case "avatar 5":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_5);
                        break;
                    case "avatar 6":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_6);
                        break;
                    case "avatar 7":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_7);
                        break;
                    case "avatar 8":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_8);
                        break;
                    case "avatar 9":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_9);
                        break;
                    case "avatar 10":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_10);
                        break;
                    case "avatar 11":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_11);
                        break;
                    case "avatar 12":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_12);
                        break;
                    case "avatar 14":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_14);
                        break;
                    case "avatar 15":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_15);
                        break;
                    case "avatar 16":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_16);
                        break;
                    default:
                        taskDrawable = activity.getResources().getDrawable(R.drawable.avatar_3);
                }
                taskInfoImage.setImageDrawable(taskDrawable);
            }else{
                Bitmap image = BitmapFactory.decodeFile(task.picPath);
                taskInfoImage.setImageBitmap(image);
            }

        }else{
            taskInfoImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.avatar_3));
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        Intent intent = getActivity().getIntent();
        if(intent != null){
            TaskListContent.Task receivedTask = intent.getParcelableExtra(MainActivity.taskExtra);
            if(receivedTask != null){
                displayTask(receivedTask) ;
            }
        }
    }
}
