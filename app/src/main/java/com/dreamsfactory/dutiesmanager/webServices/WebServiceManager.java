package com.dreamsfactory.dutiesmanager.webServices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.tech.IsoDep;
import android.util.Log;
import android.widget.Toast;

import com.dreamsfactory.dutiesmanager.activities.FlatLoginActivity;
import com.dreamsfactory.dutiesmanager.activities.FlatRegisterActivity;
import com.dreamsfactory.dutiesmanager.activities.MainActivity;
import com.dreamsfactory.dutiesmanager.activities.UserLoginActivity;
import com.dreamsfactory.dutiesmanager.activities.UserRegisterActivity;
import com.dreamsfactory.dutiesmanager.database.DbManager;
import com.dreamsfactory.dutiesmanager.database.entities.Flat;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.database.entities.User;
import com.dreamsfactory.dutiesmanager.managers.LogManager;
import com.dreamsfactory.dutiesmanager.settings.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kuba on 2017-03-07.
 */

public class WebServiceManager {

    public final static String SERVER_URI = "http://192.168.8.102/duties_manager_api";

    public final static String METHOD_GET_COUNTS = "get_counts.php";

    public final static String METHOD_GET_FRIENDS = "";
    public final static String METHOD_GET_TASKS = "";

    public final static String METHOD_CREATE_TASK = "create_task.php";
    public final static String METHOD_UPDATE_TASK = "update_task.php";

    public final static String METHOD_REGISTER_USER = "register_user.php";
    public final static String METHOD_LOGIN_USER = "login_user.php";

    public final static String METHOD_REGISTER_FLAT = "";
    public final static String METHOD_LOGIN_FLAT = "login_flat.php";

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


    private WeakReference<Context> mRef;
    //private Context mContext;

    private static final String TAG = WebServiceManager.class.getSimpleName();


    private static boolean isInitialized = false;


    private WebServiceManager(Context context){
        mRef = new WeakReference<>(context);
        //this.mContext = context;
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

    private String makeURI(String method){
        return SERVER_URI + "/" + method;
    }


    public void loginUser(Map<String,String> params){

        Log.d(TAG, "loginUser()");
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

                        Settings.getInstance(mRef.get()).set(Settings.USER_IS_LOGGED_IN, true);

                        String userId = JSONresponse.getString(RESPONSE_USER_ID);

                        JSONObject userObj;

                        userObj = JSONresponse.getJSONObject(RESPONSE_USER);

                        String name = userObj.getString(RESPONSE_USER_NAME);
                        String email = userObj.getString(RESPONSE_USER_EMAIL);

                        User user = new User();
                        user.setRemoteId(Long.valueOf(userId));
                        user.setName(name);
                        user.setEmail(email);

                        if(DbManager.getInstance(mRef.get()).getUserService().insertUser(user)){
                            Intent intent = new Intent(mRef.get(), FlatLoginActivity.class);
                            mRef.get().startActivity(intent);
                            ((UserLoginActivity)mRef.get()).finish();
                            //todo finish activity


                        }




                    }else{

                        String errorMsg = JSONresponse.getString(RESPONSE_ERROR_MSG);
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
        });

        volleyService.makePOSTStringRequest("tag_login_user", makeURI(METHOD_LOGIN_USER), params);

    }


    public void registerUser(Map<String, String> params){

        Log.d(TAG, "registerUser()");
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


                        String userId = JSONresponse.getString(RESPONSE_USER_ID);

                        JSONObject userObj;

                        userObj = JSONresponse.getJSONObject(RESPONSE_USER);

                        String name = userObj.getString(RESPONSE_USER_NAME);
                        String email = userObj.getString(RESPONSE_USER_EMAIL);

                        User user = new User();
                        user.setRemoteId(Long.valueOf(userId));
                        user.setName(name);
                        user.setEmail(email);

                        Toast.makeText(mRef.get().getApplicationContext(), "User succesfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(mRef.get(), UserLoginActivity.class);
                        mRef.get().startActivity(intent);
                        ((UserRegisterActivity)mRef.get()).finish();






                    }else{

                        String errorMsg = JSONresponse.getString(RESPONSE_ERROR_MSG);
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
        });

        volleyService.makePOSTStringRequest("tag_register_user", makeURI(METHOD_REGISTER_USER), params);


    }

    public void registerFlat(Map<String, String> params){
        Log.d(TAG, "registerFlat()");
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


                        String flatId = JSONresponse.getString(RESPONSE_FLAT_ID);

                        JSONObject flatObj;

                        flatObj = JSONresponse.getJSONObject(RESPONSE_FLAT);

                        String address = flatObj.getString(RESPONSE_FLAT_ADDRESS);

                        Flat flat = new Flat();
                        flat.setRemoteId(Long.valueOf(flatId));
                        flat.setAddress(address);

                        Toast.makeText(mRef.get().getApplicationContext(), "Flat succesfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(mRef.get(), FlatLoginActivity.class);
                        mRef.get().startActivity(intent);
                        ((FlatRegisterActivity)mRef.get()).finish();






                    }else{

                        String errorMsg = JSONresponse.getString(RESPONSE_ERROR_MSG);
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
        });

        volleyService.makePOSTStringRequest("tag_register_user", makeURI(METHOD_REGISTER_USER), params);
    }

    public void loginFlat(Map<String, String> params){

        Log.d(TAG, "loginFlat()");
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

                        Settings.getInstance(mRef.get()).set(Settings.FLAT_IS_LOGGED_IN, true);

                        String flatId = JSONresponse.getString(RESPONSE_FLAT_ID);

                        JSONObject userObj;

                        userObj = JSONresponse.getJSONObject(RESPONSE_FLAT);

                        String address = userObj.getString(RESPONSE_FLAT_ADDRESS);

                        Flat flat = new Flat();
                        flat.setRemoteId(Long.valueOf(flatId));
                        flat.setAddress(address);

                        if(DbManager.getInstance(mRef.get()).getFlatService().insertFlat(flat)){
                            Intent intent = new Intent(mRef.get(), MainActivity.class);
                            mRef.get().startActivity(intent);
                            ((FlatLoginActivity)mRef.get()).finish();
                            //todo finish activity


                        }




                    }else{

                        String errorMsg = JSONresponse.getString(RESPONSE_ERROR_MSG);
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
        });

        volleyService.makePOSTStringRequest("tag_login_user", makeURI(METHOD_LOGIN_FLAT), params);

    }

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

        volleyService.makePOSTStringRequest("get_count_tag", makeURI(METHOD_GET_COUNTS), params);

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
                            task.setRemoteId(Long.valueOf(taskId));
                            task.setOwnerId(Long.valueOf(ownerId));
                            task.setTitle(title);
                            task.setDescription(description);
                            task.setDeadline(Long.valueOf(deadline));
                            task.setIsDone(Boolean.valueOf(isDone));

                            DbManager.getInstance(mRef.get()).getTaskService().insertTask(task);
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
                        task.setRemoteId(Long.valueOf(taskId));
                        task.setOwnerId(Long.valueOf(ownerId));
                        task.setTitle(title);
                        task.setDescription(description);
                        task.setDeadline(Long.valueOf(deadline));
                        task.setIsDone(Boolean.valueOf(isDone));

                        DbManager.getInstance(mRef.get()).getTaskService().insertTask(task);



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
        volleyService.makePOSTStringRequest("tag_create_task", makeURI(METHOD_CREATE_TASK), params);
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
                        task.setRemoteId(Long.valueOf(taskId));
                        task.setOwnerId(Long.valueOf(ownerId));
                        task.setTitle(title);
                        task.setDescription(description);
                        task.setDeadline(Long.valueOf(deadline));
                        task.setIsDone(Boolean.valueOf(isDone));

                        if(DbManager.getInstance(mRef.get()).getTaskService().updateTask(task)){
                            LogManager.logInfo("Task updated succesfully!");
                        }else{
                            LogManager.logInfo("Error occured during updating task..");
                        }



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
        volleyService.makePOSTStringRequest("tag_update_task", makeURI(METHOD_UPDATE_TASK), params);


    }



}
