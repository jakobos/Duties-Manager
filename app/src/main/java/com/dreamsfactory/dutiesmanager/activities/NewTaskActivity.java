package com.dreamsfactory.dutiesmanager.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.app.AppController;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.managers.LogManager;
import com.dreamsfactory.dutiesmanager.settings.Settings;
import com.dreamsfactory.dutiesmanager.webServices.WebServiceManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewTaskActivity extends AppCompatActivity {

    private static final int DATE_DIALOG_ID = 999;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Calendar calendar;
    private int day, month, year;

    private EditText title;
    private EditText description;
    private Button deadlineBtn;
    private FloatingActionButton fab;

    private TextView deadlineTextView;

    private long deadline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        title = (EditText) findViewById(R.id.newTaskTitleEditText);
        description = (EditText) findViewById(R.id.newTaskDescriptionEditText);
        deadlineBtn = (Button) findViewById(R.id.setDeadlineButton);
        deadlineTextView = (TextView) findViewById(R.id.newTaskDeadlineTextView);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        deadline = Calendar.getInstance().getTimeInMillis();
        deadlineTextView.setText((new Date(deadline).toString()));

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                deadline = cal.getTimeInMillis();
                deadlineTextView.setText((new Date(deadline).toString()));
            }
        };



    }

    @Override
    protected void onStart() {
        super.onStart();

        deadlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogManager.logInfo("onClick() FAB");

                String taskTitle = title.getText().toString().trim();
                String taskDescription = description.getText().toString().trim();

                if(!taskTitle.isEmpty() && !taskDescription.isEmpty() && deadline > calendar.getTimeInMillis()){

                    title.setText("");
                    description.setText("");


                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title", taskTitle);
                    params.put("description", taskDescription);
                    params.put("deadline", String.valueOf(deadline));
                    params.put("flat_id", Settings.getInstance(getBaseContext()).get(Settings.FLAT_ID));
                    createTask(params);


                }

            }
        });


    }
    private void createTask(Map<String, String> params){
        WebServiceManager.getInstance(this).createTask(this, params);
    }



    @Override
    protected void onStop() {
        super.onStop();
        deadlineBtn.setOnClickListener(null);
        fab.setOnClickListener(null);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(this, mDateListener, year, month, day);
        }
        return null;
    }


}
