package com.dreamsfactory.dutiesmanager.database;

import android.content.ContentValues;
import android.content.Context;

import com.dreamsfactory.dutiesmanager.database.services.DbServiceBase;
import com.dreamsfactory.dutiesmanager.database.services.FriendService;
import com.dreamsfactory.dutiesmanager.database.services.TaskService;

/**
 * Created by Kuba on 2017-03-01.
 */

public class DbManager {

    private static DbManager _instance;

    private DbHelper dbClient;
    private Context mContext;
    private boolean initialized = false;

    //all services entities
    private TaskService taskService;
    private FriendService friendService;

    //singleton
    private DbManager(Context context){
        this.mContext = context;
    }

    private synchronized DbManager get(Context context){
        if(_instance == null){
            _instance = new DbManager(context);
        }
        return _instance;
    }

    public DbManager getInstance(Context context){
        return get(context.getApplicationContext());
    }
    public TaskService getTaskService(){
        return taskService;
    }
    public FriendService getFriendService(){
        return friendService;
    }

    private void init(){
        if(initialized){
            dbClient = new DbHelper(mContext);
            DbServiceBase.init(dbClient);

            taskService = new TaskService();
            friendService = new FriendService();

            initialized = true;
        }
    }

    public void reset(){
        dbClient.delete(mContext);
    }
}
