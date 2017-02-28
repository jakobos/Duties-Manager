package com.dreamsfactory.dutiesmanager.database.services;

import android.database.Cursor;

import com.dreamsfactory.dutiesmanager.database.entities.Task;

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

        if(cursor.getCount() == 0)
            return null;

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
        if(cursor.getCount() == 0)
            return null;

        ArrayList<Task> tasks = new ArrayList<>();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Task task = new Task();
            if(task.readFromCursor(cursor))
                tasks.add(task);
        }
        return tasks;
    }
    public ArrayList<Task> getTasksByIsDone(){
        Cursor cursor = executeQueryWhere(Task.TABLE_NAME, Task.getFullProjection(), Task.COLUMN_NAME_IS_DONE, String.valueOf(1));
        if(cursor.getCount() == 0)
            return null;

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
        Cursor cursor = executeQueryWhere(Task.TABLE_NAME, Task.getFullProjection(), Task._ID, String.valueOf(taskId));

        if(cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        Task task = new Task();
        return task.readFromCursor(cursor) ? task : null;
    }


}
