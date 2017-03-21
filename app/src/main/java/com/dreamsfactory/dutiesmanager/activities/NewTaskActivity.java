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

        fab = (FloatingActionButton) findViewById(R.id.fab);


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

                //if(title.getText().toString().isEmpty()||description.getText().toString().isEmpty()||deadline<calendar.getTimeInMillis()){

                    // JSONObject params = new JSONObject();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title", title.getText().toString());
                    params.put("description", description.getText().toString());
                    params.put("deadline", String.valueOf(deadline));
                    params.put("flat_id", Settings.getInstance(getBaseContext()).get(Settings.FLAT_ID));

                    //testPOST();
                    WebServiceManager.getInstance(getBaseContext()).createTask(params);

                    //for tests only

//                    Map<String, String> params2 = new HashMap<String, String>();
//                    params.put("flat_id", "10");
//                    params.put("last_sync_friend", "0");
//                    params.put("last_sync_task", "0");
//                    WebServiceManager.getInstance(getBaseContext()).getCount(params2);

                    //save task and update server
                    finish();


                //}

            }
        });


    }

    private void testPOST() {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = WebServiceManager.METHOD_CREATE_TASK;


        StringRequest req = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        LogManager.logInfo("RESPONSE: "+response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR::", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", "Androidhive");
                params.put("description", "abc@androidhive.info");
                params.put("deadline", "11111");
                params.put("flat_id", "3");

                return params;
            }

        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_obj);

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
