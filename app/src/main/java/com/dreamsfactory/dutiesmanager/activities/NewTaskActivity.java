package com.dreamsfactory.dutiesmanager.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.entities.Task;

import java.util.Calendar;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity {

    private static final int DATE_DIALOG_ID = 999;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Calendar calendar;
    private int day, month, year;

    private EditText title;
    private EditText description;
    private Button deadlineBtn;
    private FloatingActionButton fab;

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

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,23);
                cal.set(Calendar.MINUTE,59);
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH,month);
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                deadline = cal.getTimeInMillis();
            }
        };


        title = (EditText) findViewById(R.id.newTaskTitleEditText);
        description = (EditText) findViewById(R.id.newTaskDescriptionEditText);
        deadlineBtn = (Button) findViewById(R.id.setDeadlineButton);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        deadlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Task newTask = new Task();
                newTask.setTitle(title.getText().toString());
                newTask.setDescription(description.getText().toString());
                newTask.setDeadline(deadline);



                //save task and update server
                finish();
            }
        });
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DATE_DIALOG_ID){
            return new DatePickerDialog(this, mDateListener, year, month, day);
        }
        return null;
    }
}
