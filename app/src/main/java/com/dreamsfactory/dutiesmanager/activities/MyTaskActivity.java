package com.dreamsfactory.dutiesmanager.activities;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.fragments.TaskDetailsFragment;

public class MyTaskActivity extends AppCompatActivity {

    private Task task;
    private static final String KEY_TASK = "task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);

        task = getIntent().getExtras().getParcelable(TaskDetailsFragment.KEY_TASK);

        TaskDetailsFragment fragment = (TaskDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMyTaskDetails);
        fragment.setTask(task);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_TASK, task);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        task = (Task) savedInstanceState.getParcelable(KEY_TASK);
    }
}
