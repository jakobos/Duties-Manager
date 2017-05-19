package com.dreamsfactory.dutiesmanager.activities;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.fragments.TaskDetailsFragment;
import com.dreamsfactory.dutiesmanager.settings.Settings;
import com.dreamsfactory.dutiesmanager.webServices.WebServiceManager;

import java.util.HashMap;
import java.util.Map;

public class FreeTaskActivity extends AppCompatActivity {

    private Task task;
    private static final String KEY_TASK = "task";

    private CheckBox checkBoxDoIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_task);

        checkBoxDoIt = (CheckBox) findViewById(R.id.checkDoIt);

        task = getIntent().getExtras().getParcelable(TaskDetailsFragment.KEY_TASK);

        TaskDetailsFragment fragment = (TaskDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentFreeTaskDetails);
        fragment.setTask(task);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(checkBoxDoIt.isChecked()){
            Map<String, String> params = new HashMap<String, String>();
            params.put("task_id", String.valueOf(task.getRemoteId()));
            params.put("owner_id", Settings.getInstance(getBaseContext()).get(Settings.USER_ID));
            WebServiceManager.getInstance(getBaseContext()).updateTask(params);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_TASK, task);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        task = savedInstanceState.getParcelable(KEY_TASK);
    }
}
