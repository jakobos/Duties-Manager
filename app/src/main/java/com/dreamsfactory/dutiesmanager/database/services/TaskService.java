package com.dreamsfactory.dutiesmanager.database.services;

import android.database.Cursor;
import android.util.Log;

import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.managers.LogManager;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.jar.Pack200;

/**
 * Created by Kuba on 2017-02-27.
 */

public class TaskService extends DbServiceBase {

    public boolean insertTask(Task task){
        long id = executeQueryInsert(task);
        if(id <= 0)
            return false;
        else
            return true;
    }

    public boolean updateTask(Task task){
        int count = executeQueryUpdate(task);
        if(count > 0)
            return true;
        else
            return false;
    }

    public ArrayList<Task> getAllTasks(){
        Cursor cursor = executeQueryGetAll(Task.TABLE_NAME, Task.getFullProjection());
        ArrayList<Task> tasks = new ArrayList<>();

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Task task = new Task();
            if(task.readFromCursor(cursor)){
                tasks.add(task);
            }
        }
        return tasks;
    }
    public ArrayList<Task> getTasksByUserId(long userId){
        Cursor cursor = executeQueryWhere(Task.TABLE_NAME, Task.getFullProjection(), Task.COLUMN_NAME_OWNER_ID, String.valueOf(userId));

        ArrayList<Task> tasks = new ArrayList<>();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Task task = new Task();
            if(task.readFromCursor(cursor))
                tasks.add(task);
        }
        return tasks;
    }
    public ArrayList<Task> getTasksByFree(){
        Cursor cursor = executeQueryWhere(Task.TABLE_NAME, Task.getFullProjection(), Task.COLUMN_NAME_OWNER_ID, String.valueOf(0));
        ArrayList<Task> tasks = new ArrayList<>();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Task task = new Task();
            if(task.readFromCursor(cursor))
                tasks.add(task);
        }
        return tasks;
    }
    public ArrayList<Task> getTasksByIsDone(boolean isDone){
        int value=0;
        if(isDone)
            value = 1;
        else
            value = 0;
        Cursor cursor = executeQueryWhere(Task.TABLE_NAME, Task.getFullProjection(), Task.COLUMN_NAME_IS_DONE, String.valueOf(value));

        ArrayList<Task> tasks = new ArrayList<>();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Task task = new Task();
            if(task.readFromCursor(cursor))
                tasks.add(task);
        }
        return tasks;
    }
    public Task getTask(long taskId){
        LogManager.logError("in getTask() method");
        Cursor cursor = executeQueryWhere(Task.TABLE_NAME, Task.getFullProjection(), Task._ID, String.valueOf(taskId));
        if(cursor == null){
            LogManager.logError("Cursor is null");
        }else{
            LogManager.logInfo("Cursor is not null");
        }
        if(cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        Task task = new Task();
        return task.readFromCursor(cursor) ? task : null;
    }
    public boolean deleteAllTasks(){
        int count = executeQueryDelete(Task.TABLE_NAME);
        if(count > 0)
            return true;
        else
            return false;
    }


}
