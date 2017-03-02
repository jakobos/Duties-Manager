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

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    private static final int DATE_DIALOG_ID = 999;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Calendar calendar;
    private int day, month, year;

    private EditText title;
    private EditText description;
    private Button deadlineBtn;
    private FloatingActionButton fab;

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
                //save task and update server
            }
        });
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DATE_DIALOG_ID){
            return new DatePickerDialog(getApplicationContext(), mDateListener, year, month, day);
        }
        return null;
    }
}
