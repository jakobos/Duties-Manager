package com.dreamsfactory.dutiesmanager.webServices;

import android.content.Context;
import android.nfc.tech.IsoDep;
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


    public final static String METHOD_GET_COUNTS = "http://192.168.8.101/duties_manager_api/get_counts.php";

    public final static String METHOD_GET_FRIENDS = "";
    public final static String METHOD_GET_TASKS = "";

    public final static String METHOD_CREATE_TASK = "http://192.168.8.101/duties_manager_api/create_task.php";
    public final static String METHOD_UPDATE_TASK = "http://192.168.8.101/duties_manager_api/update_task.php";

    public final static String METHOD_REGISTER_USER = "";
    public final static String METHOD_LOGIN_USER = "";

    public final static String METHOD_REGISTER_FLAT = "";
    public final static String METHOD_LOGIN_FLAT = "";

    public final static String RESPONSE_ERROR = "error";
    public final static String RESPONSE_ERROR_MSG = "error_msg";

    public final static String RESPONSE_USER_ID = "uid";
    public final static String RESPONSE_USER_NAME = "name";
    public final static String RESPONSE_USER_EMAIL = "email";
    public final static String RESPONSE_USER = "user";

    public final static String RESPONSE_CREATED_AT = "created_at";
    public final static String RESPONSE_UPDATED_AT = "updated_at";

    public final static String RESPONSE_TASK = "task";
    public final static String RESPONSE_TASKS = "tasks";
    public final static String RESPONSE_TASK_ID = "tid";
    public final static String RESPONSE_OWNER_ID = "oid";
    public final static String RESPONSE_TASK_TITLE = "title";
    public final static String RESPONSE_TASK_DESCRIPTION = "description";
    public final static String RESPONSE_TASK_DEADLINE = "deadline";
    public final static String RESPONSE_TASK_IS_DONE = "is_done";

    public final static String RESPONSE_FLAT_ID = "fid";
    public final static String RESPONSE_FLAT_ADDRESS = "address";
    public final static String RESPONSE_FLAT = "flat";

    public final static String RESPONSE_LAST_SYNC_FRIEND = "last_sync_friend";
    public final static String RESPONSE_LAST_SYNC_TASK = "last_sync_task";



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

    public void getCount(Map<String, String> params){

        VolleyService volleyService = new VolleyService();
        volleyService.setListener(new VolleyService.Listener() {
            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(JSONArray response) {

            }

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
            }
        });

        volleyService.makePOSTStringRequest("get_count_tag", METHOD_GET_COUNTS, params);

    }

    public void getTasks(){
        VolleyService volleyService = new VolleyService();
        volleyService.setListener(new VolleyService.Listener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Login Response: " + response.toString());
                //hideDialog();
                try{

                    boolean error = response.getBoolean(RESPONSE_ERROR);

                    if(!error){

                        JSONArray objects;
                        // images found
                        // Getting Array of images
                        objects = response.getJSONArray(RESPONSE_TASKS);

                        //ArrayList<Task> tasksList = new ArrayList<>();

                        // looping through All Products
                        for (int i = 0; i < objects.length(); i++) {
                            JSONObject taskObj = objects.getJSONObject(i);

                            String taskId = taskObj.getString(RESPONSE_TASK_ID);
                            String ownerId = taskObj.getString(RESPONSE_OWNER_ID);
                            String title = taskObj.getString(RESPONSE_TASK_TITLE);
                            String description = taskObj.getString(RESPONSE_TASK_DESCRIPTION);
                            String deadline = taskObj.getString(RESPONSE_TASK_DEADLINE);
                            String isDone = taskObj.getString(RESPONSE_TASK_IS_DONE);

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

                        String errorMsg = response.getString(RESPONSE_ERROR_MSG);
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

    public void createTask(Map<String,String> params){
        Log.d(TAG, "createTask()");
        VolleyService volleyService = new VolleyService();
        volleyService.setListener(new VolleyService.Listener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Login Response: " + response.toString());
                //hideDialog();



            }

            @Override
            public void onResponse(JSONArray response) {

            }

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);

                try{
                    JSONObject JSONresponse = new JSONObject(response);
                    boolean error = JSONresponse.getBoolean("error");

                    if(!error){

                        JSONObject taskObj;

                        taskObj = JSONresponse.getJSONObject(RESPONSE_TASK);

                        String taskId = taskObj.getString(RESPONSE_TASK_ID);
                        String ownerId = taskObj.getString(RESPONSE_OWNER_ID);
                        String title = taskObj.getString(RESPONSE_TASK_TITLE);
                        String description = taskObj.getString(RESPONSE_TASK_DESCRIPTION);
                        String deadline = taskObj.getString(RESPONSE_TASK_DEADLINE);
                        String isDone = taskObj.getString(RESPONSE_TASK_IS_DONE);

                        Task task = new Task();
                        task.setTaskId(Long.valueOf(taskId));
                        task.setOwnerId(Long.valueOf(ownerId));
                        task.setTitle(title);
                        task.setDescription(description);
                        task.setDeadline(Long.valueOf(deadline));
                        task.setIsDone(Boolean.valueOf(isDone));

                        DbManager.getInstance(mContext).getTaskService().insertTask(task);



                    }else{

                        String errorMsg = JSONresponse.getString(RESPONSE_ERROR_MSG);
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
        });
        //volleyService.makePOSTJSONObjectRequest("tag_create_task", METHOD_CREATE_TASK, params);
        volleyService.makePOSTStringRequest("tag_create_task", METHOD_CREATE_TASK, params);
    }

    public void updateTask(Map<String, String> params){
        Log.d(TAG, "updateTask()");
        VolleyService volleyService = new VolleyService();
        volleyService.setListener(new VolleyService.Listener() {
            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onResponse(JSONArray response) {

            }

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);

                try{
                    JSONObject JSONresponse = new JSONObject(response);
                    boolean error = JSONresponse.getBoolean("error");

                    if(!error){

                        JSONObject taskObj;

                        taskObj = JSONresponse.getJSONObject(RESPONSE_TASK);

                        String taskId = taskObj.getString(RESPONSE_TASK_ID);
                        String ownerId = taskObj.getString(RESPONSE_OWNER_ID);
                        String title = taskObj.getString(RESPONSE_TASK_TITLE);
                        String description = taskObj.getString(RESPONSE_TASK_DESCRIPTION);
                        String deadline = taskObj.getString(RESPONSE_TASK_DEADLINE);
                        String isDone = taskObj.getString(RESPONSE_TASK_IS_DONE);

                        Task task = new Task();
                        task.setTaskId(Long.valueOf(taskId));
                        task.setOwnerId(Long.valueOf(ownerId));
                        task.setTitle(title);
                        task.setDescription(description);
                        task.setDeadline(Long.valueOf(deadline));
                        task.setIsDone(Boolean.valueOf(isDone));

                        DbManager.getInstance(mContext).getTaskService().updateTask(task);



                    }else{

                        String errorMsg = JSONresponse.getString(RESPONSE_ERROR_MSG);
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
        });
        //volleyService.makePOSTJSONObjectRequest("tag_create_task", METHOD_CREATE_TASK, params);
        volleyService.makePOSTStringRequest("tag_update_task", METHOD_UPDATE_TASK, params);


    }



}
