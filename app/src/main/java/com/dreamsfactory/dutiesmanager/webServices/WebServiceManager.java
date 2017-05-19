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
import com.dreamsfactory.dutiesmanager.activities.NewTaskActivity;
import com.dreamsfactory.dutiesmanager.activities.UserLoginActivity;
import com.dreamsfactory.dutiesmanager.activities.UserRegisterActivity;
import com.dreamsfactory.dutiesmanager.database.DbManager;
import com.dreamsfactory.dutiesmanager.database.entities.Flat;
import com.dreamsfactory.dutiesmanager.database.entities.Friend;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.database.entities.User;
import com.dreamsfactory.dutiesmanager.managers.LogManager;
import com.dreamsfactory.dutiesmanager.settings.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuba on 2017-03-07.
 */

public class WebServiceManager {

    private final static String SERVER_URI = "http://192.168.8.100/duties_manager_api";

    private final static String METHOD_GET_COUNTS = "get_counts.php";

    private final static String METHOD_GET_FRIENDS = "get_friends.php";
    private final static String METHOD_GET_TASKS = "get_tasks.php";

    private final static String METHOD_CREATE_TASK = "create_task.php";
    private final static String METHOD_UPDATE_TASK = "update_task.php";

    private final static String METHOD_REGISTER_USER = "register_user.php";
    private final static String METHOD_LOGIN_USER = "login_user.php";

    private final static String METHOD_REGISTER_FLAT = "register_flat.php";
    private final static String METHOD_LOGIN_FLAT = "login_flat.php";

    private final static String RESPONSE_ERROR = "error";
    private final static String RESPONSE_ERROR_MSG = "error_msg";

    private final static String RESPONSE_USER_ID = "uid";
    private final static String RESPONSE_USER_NAME = "name";
    private final static String RESPONSE_USER_EMAIL = "email";
    private final static String RESPONSE_USER = "user";

    private final static String RESPONSE_CREATED_AT = "created_at";
    private final static String RESPONSE_UPDATED_AT = "updated_at";

    private final static String RESPONSE_TASK = "task";
    private final static String RESPONSE_TASKS = "tasks";
    private final static String RESPONSE_TASK_ID = "tid";
    private final static String RESPONSE_OWNER_ID = "oid";
    private final static String RESPONSE_TASK_TITLE = "title";
    private final static String RESPONSE_TASK_DESCRIPTION = "description";
    private final static String RESPONSE_TASK_DEADLINE = "deadline";
    private final static String RESPONSE_TASK_IS_DONE = "is_done";

    private final static String RESPONSE_FRIENDS = "friends";
    private final static String RESPONSE_FRIEND_ID = "fid";
    private final static String RESPONSE_FRIEND_NAME = "name";
    private final static String RESPONSE_FRIEND_EMAIL = "email";


    private final static String RESPONSE_FLAT_ID = "fid";
    private final static String RESPONSE_FLAT_ADDRESS = "address";
    private final static String RESPONSE_FLAT = "flat";

    private final static String RESPONSE_LAST_SYNC_FRIEND = "last_sync_friend";
    private final static String RESPONSE_LAST_SYNC_TASK = "last_sync_task";

    private final static String RESPONSE_IS_NEW_TASK = "is_new_task";
    private final static String RESPONSE_IS_NEW_FRIEND = "is_new_friend";





    private static WebServiceManager _instance;


    private WeakReference<Context> mRef;
    private Context mContext;

    private static final String TAG = WebServiceManager.class.getSimpleName();


    private static boolean isInitialized = false;


    private WebServiceManager(Context context){
        mRef = new WeakReference<>(context);
        this.mContext = context.getApplicationContext();
    }
    public static synchronized WebServiceManager getInstance(Context context){
        //init();
        if(_instance == null){

            _instance = new WebServiceManager(context);
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



                        String userId = JSONresponse.getString(RESPONSE_USER_ID);

                        JSONObject userObj;

                        userObj = JSONresponse.getJSONObject(RESPONSE_USER);

                        String name = userObj.getString(RESPONSE_USER_NAME);
                        String email = userObj.getString(RESPONSE_USER_EMAIL);

                        User user = new User();
                        user.setRemoteId(Long.valueOf(userId));
                        user.setName(name);
                        user.setEmail(email);

                        if(DbManager.getInstance(mContext).getUserService().insertUser(user)){

                            Settings.getInstance(mContext).set(Settings.USER_ID, Long.valueOf(userId));
                            Settings.getInstance(mContext).set(Settings.USER_IS_LOGGED_IN, true);

                            UserLoginActivity activity = (UserLoginActivity) mRef.get();
                            activity.nextToFlatActivity();

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

                        Toast.makeText(mContext, "User succesfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        UserRegisterActivity activity = (UserRegisterActivity)mRef.get();
                        activity.backToLoginActivity();






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

                        Toast.makeText(mContext, "Flat succesfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        FlatRegisterActivity activity = (FlatRegisterActivity)mRef.get();
                        activity.backToLoginActivity();






                    }else{

                        String errorMsg = JSONresponse.getString(RESPONSE_ERROR_MSG);
                        LogManager.logError(errorMsg);

                    }

                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
        });

        volleyService.makePOSTStringRequest("tag_register_user", makeURI(METHOD_REGISTER_FLAT), params);
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



                        String flatId = JSONresponse.getString(RESPONSE_FLAT_ID);

                        JSONObject userObj;

                        userObj = JSONresponse.getJSONObject(RESPONSE_FLAT);

                        String address = userObj.getString(RESPONSE_FLAT_ADDRESS);

                        Flat flat = new Flat();
                        flat.setRemoteId(Long.valueOf(flatId));
                        flat.setAddress(address);

                        if(DbManager.getInstance(mContext).getFlatService().insertFlat(flat)){
                            Settings.getInstance(mContext).set(Settings.FLAT_ID, Long.valueOf(flatId));
                            Settings.getInstance(mContext).set(Settings.FLAT_IS_LOGGED_IN, true);
                            FlatLoginActivity activity = (FlatLoginActivity) mRef.get();
                            activity.nextToMainActivity();



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

    public void syncWithRemote(String userId, String flatId, String lastSyncTask, String lastSyncFriend){
        getCount(userId, flatId, lastSyncTask, lastSyncFriend);
    }

    private void getCount(final String userId, final String flatId, final String lastSyncTask, final String lastSyncFriend){

        Map<String, String> params = new HashMap<>();
        params.put("flat_id", flatId);
        params.put("last_sync_task", lastSyncTask);
        params.put("last_sync_friend", lastSyncFriend);

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
                    boolean error = JSONresponse.getBoolean(RESPONSE_ERROR);

                    if(!error){

                        boolean isNewTask = JSONresponse.getBoolean(RESPONSE_IS_NEW_TASK);
                        boolean isNewFriend = JSONresponse.getBoolean(RESPONSE_IS_NEW_FRIEND);

                        if(isNewTask){
                            LogManager.logInfo("There is new task");
                            Map<String, String> taskParams = new HashMap<String, String>();
                            taskParams.put("flat_id", flatId);
                            taskParams.put("last_sync", lastSyncTask);

                            getTasks(taskParams);
                        }else{
                            LogManager.logInfo("No new tasks");
                        }
                        if(isNewFriend){
                            LogManager.logInfo("There is new friend");
                            Map<String, String> friendParams = new HashMap<String, String>();
                            friendParams.put("flat_id", flatId);
                            friendParams.put("last_sync", lastSyncFriend);
                            friendParams.put("user_id", userId);

                            getFriends(friendParams);
                        }else{
                            LogManager.logInfo("No new friends");
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

        volleyService.makePOSTStringRequest("get_count_tag", makeURI(METHOD_GET_COUNTS), params);

    }

    private void getTasks(Map<String, String> params){
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
                try{
                    JSONObject JSONresponse = new JSONObject(response);
                    //boolean error = JSONresponse.getBoolean(RESPONSE_ERROR);
                    boolean error = JSONresponse.getBoolean(RESPONSE_ERROR);

                    if(!error){
                        Settings.getInstance(mContext).set(Settings.LAST_SYNC_TASK, Calendar.getInstance().getTimeInMillis());
                        JSONArray objects;
                        // images found
                        // Getting Array of images
                        objects = JSONresponse.getJSONArray(RESPONSE_TASKS);

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
                            task.setIsDone(Integer.valueOf(isDone).equals(1));

                            if(!DbManager.getInstance(mContext).getTaskService().updateTask(task))
                                DbManager.getInstance(mContext).getTaskService().insertTask(task);

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
        volleyService.makePOSTStringRequest("tag_get_tasks", makeURI(METHOD_GET_TASKS), params);



    }
    private void getFriends(Map<String, String> params){
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
                try{
                    JSONObject JSONresponse = new JSONObject(response);
                    //boolean error = JSONresponse.getBoolean(RESPONSE_ERROR);
                    boolean error = JSONresponse.getBoolean(RESPONSE_ERROR);

                    if(!error){
                        Settings.getInstance(mContext).set(Settings.LAST_SYNC_FRIEND, Calendar.getInstance().getTimeInMillis());
                        JSONArray objects;
                        // images found
                        // Getting Array of images
                        objects = JSONresponse.getJSONArray(RESPONSE_FRIENDS);

                        //ArrayList<Task> tasksList = new ArrayList<>();

                        // looping through All Products
                        for (int i = 0; i < objects.length(); i++) {
                            JSONObject friendObj = objects.getJSONObject(i);

                            String friendId = friendObj.getString(RESPONSE_FRIEND_ID);
                            String name = friendObj.getString(RESPONSE_FRIEND_NAME);
                            String email = friendObj.getString(RESPONSE_FRIEND_EMAIL);


                            Friend friend = new Friend();
                            friend.setRemoteId(Long.valueOf(friendId));
                            friend.setFriendName(name);
                            friend.setFriendEmail(email);

                            if(!DbManager.getInstance(mContext).getFriendService().updateFriend(friend))
                                DbManager.getInstance(mContext).getFriendService().insertFriend(friend);

                            //tasksList.add(task);
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
        volleyService.makePOSTStringRequest("tag_get_friends", makeURI(METHOD_GET_FRIENDS), params);



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

                        DbManager.getInstance(mContext).getTaskService().insertTask(task);
                        NewTaskActivity activity = (NewTaskActivity) mRef.get();
                        activity.finish();


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
                        LogManager.logInfo("is done = "+isDone);
                        if(Integer.valueOf(isDone) > 0)
                            task.setIsDone(true);
                        else
                            task.setIsDone(false);
                        LogManager.logInfo("Task is done: "+task.getIsDone().toString());

                        if(DbManager.getInstance(mContext).getTaskService().updateTask(task)){
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
