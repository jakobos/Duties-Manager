package com.dreamsfactory.dutiesmanager.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailsFragment extends Fragment {

    private Task task;

    private TextView title;
    private TextView time;
    private TextView ownerText;
    private TextView ownerName;
    private TextView description;

    public static final String KEY_TASK = "task";


    public TaskDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        title = (TextView) view.findViewById(R.id.taskTitle);
        time = (TextView) view.findViewById(R.id.taskTime);
        ownerText = (TextView) view.findViewById(R.id.taskOwnerText);
        ownerName = (TextView) view.findViewById(R.id.taskOwnerName);
        description = (TextView) view.findViewById(R.id.taskDescription);


        return view;
    }

    public void setTask(Task task){
        this.task = task;
        setData();

    }
    private void setData(){
        title.setText(task.getTitle());
        time.setText(task.getCountdown());
        if(task.getOwnerId() > 0){
            ownerText.setVisibility(View.VISIBLE);
            ownerName.setVisibility(View.VISIBLE);
            //set owner name
        }
        description.setText(task.getDescription());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // outState.putParcelable(KEY_TASK, task);
    }


}
