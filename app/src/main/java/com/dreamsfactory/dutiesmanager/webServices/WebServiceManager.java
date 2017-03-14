package com.dreamsfactory.dutiesmanager.webServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dreamsfactory.dutiesmanager.database.DbManager;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.managers.LogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kuba on 2017-03-07.
 */

public class WebServiceManager {


    public final static String METHOD_GET_COUNTS = "";

    public final static String METHOD_GET_FRIENDS = "";
    public final static String METHOD_GET_TASKS = "";

    public final static String METHOD_CREATE_TASK = "";
    public final static String METHOD_UPDATE_TASK = "";

    public final static String METHOD_REGISTER_USER = "";
    public final static String METHOD_LOGIN_USER = "";

    public final static String METHOD_REGISTER_FLAT = "";
    public final static String METHOD_LOGIN_FLAT = "";




    private static WebServiceManager _instance;

    private Context mContext;

    private static final String TAG = WebServiceManager.class.getSimpleName();


    private static boolean isInitialized = false;


    private WebServiceManager(Context context){
        this.mContext = context;
    }
    public static synchronized WebServiceManager getInstance(Context context){
        //init();
        if(_instance == null){
            _instance = new WebServiceManager(context.getApplicationContext());
        }
        return _instance;
    }
//    private void init(){
//        if(!isInitialized){
//
//        }
//    }

    public void getCount(){

        VolleyService volleyService = new VolleyService();
        volleyService.setListener(new VolleyService.Listener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Login Response: " + response.toString());
            }

            @Override
            public void onResponse(JSONArray response) {

            }

            @Override
            public void onResponse(String response) {

            }
        });

        volleyService.makePOSTJSONObjectRequest("get_count_tag", "url", null);

    }

    public void getTasks(){
        VolleyService volleyService = new VolleyService();
        volleyService.setListener(new VolleyService.Listener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Login Response: " + response.toString());
                //hideDialog();
                try{

                    boolean error = response.getBoolean("error");

                    if(!error){

                        JSONArray objects;
                        // images found
                        // Getting Array of images
                        objects = response.getJSONArray("tasks");

                        //ArrayList<Task> tasksList = new ArrayList<>();

                        // looping through All Products
                        for (int i = 0; i < objects.length(); i++) {
                            JSONObject taskObj = objects.getJSONObject(i);
                            String taskId = taskObj.getString("tid");
                            String ownerId = taskObj.getString("oid");
                            String title = taskObj.getString("title");
                            String description = taskObj.getString("description");
                            String deadline = taskObj.getString("deadline");
                            String isDone = taskObj.getString("is_done");

                            Task task = new Task();
                            task.setTaskId(Long.valueOf(taskId));
                            task.setOwnerId(Long.valueOf(ownerId));
                            task.setTitle(title);
                            task.setDescription(description);
                            task.setDeadline(Long.valueOf(deadline));
                            task.setIsDone(Boolean.valueOf(isDone));

                            DbManager.getInstance(mContext).getTaskService().insertTask(task);
                            //tasksList.add(task);
                        }



                    }else{

                        String errorMsg = response.getString("error_msg");
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }


            }

            @Override
            public void onResponse(JSONArray response) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
        //volleyService.makePOSTJSONObjectRequest();



    }

    public void createTask(Map<String, String> params){

    }



}
