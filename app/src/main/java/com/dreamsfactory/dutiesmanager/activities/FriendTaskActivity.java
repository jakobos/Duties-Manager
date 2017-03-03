package com.dreamsfactory.dutiesmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.fragments.TaskDetailsFragment;

public class FriendTaskActivity extends AppCompatActivity {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_task);

        task = new Task();

        TaskDetailsFragment fragment = (TaskDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTaskDetails);
        fragment.setTask(task);
    }
}
