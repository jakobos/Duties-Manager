package com.dreamsfactory.dutiesmanager.database.entities;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Kuba on 2017-02-24.
 */

public class Task extends DBEntityBase {

    //
    //Constant variables
    //
    public static final String TABLE_NAME = "Task";
    public static final String COLUMN_NAME_TASK_ID = "TaskId";
    public static final String COLUMN_NAME_TITLE = "Title";
    public static final String COLUMN_NAME_DESCRIPTION = "Description";
    public static final String COLUMN_NAME_DEADLINE = "Deadline";
    public static final String COLUMN_NAME_IS_DONE = "IsDone";
    public static final String COLUMN_NAME_OWNER_ID = "OwnerId";

    //
    //Variables
    //

    private String title;
    private String description;
    private long deadline;
    private boolean isDone;
    private long ownerId;
    private long taskId;

    //
    //constructors
    //

    //
    //Static methods
    //

    public static String getCreateEntries(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA +
                COLUMN_NAME_TASK_ID + INTEGER_TYPE + COMMA +
                COLUMN_NAME_TITLE + TEXT_TYPE + COMMA +
                COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA +
                COLUMN_NAME_DEADLINE + INTEGER_TYPE + COMMA +
                COLUMN_NAME_IS_DONE + INTEGER_TYPE + COMMA +
                COLUMN_NAME_OWNER_ID + INTEGER_TYPE + COMMA + ");";
    }
    public static String getDeleteEntries(){ return "DROP TABLE IF EXISTS "+ TABLE_NAME; }

    public static String[] getFullProjection(){
        String[] projection = {
                _ID,
                COLUMN_NAME_TASK_ID,
                COLUMN_NAME_TITLE,
                COLUMN_NAME_DESCRIPTION,
                COLUMN_NAME_DEADLINE,
                COLUMN_NAME_IS_DONE,
                COLUMN_NAME_OWNER_ID
        };
        return projection;
    }


    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TASK_ID, taskId);
        values.put(COLUMN_NAME_TITLE, title);
        values.put(COLUMN_NAME_DESCRIPTION, description);
        values.put(COLUMN_NAME_DEADLINE, deadline);
        values.put(COLUMN_NAME_IS_DONE, isDone);
        values.put(COLUMN_NAME_OWNER_ID, ownerId);
        return values;
    }

    @Override
    public boolean readFromCursor(Cursor cursor) {
        try{
            this.id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            this.taskId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_TASK_ID));
            this.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
            this.description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION));
            this.deadline = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_DEADLINE));
            if(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_IS_DONE)) == 1){
                this.isDone = true;
            }else{
                this.isDone = false;
            }

            this.ownerId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_OWNER_ID));
            return true;

        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    //
    //base class methods
    //

    public long getTaskId(){
        return taskId;
    }
    public void setTaskId(long taskId){
        this.taskId = taskId;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public long getDeadline(){
        return deadline;
    }
    public void setDeadline(long deadline){
        this.deadline = deadline;
    }
    public Boolean getIsDone(){
        return isDone;
    }
    public void setIsDone(Boolean isDone){
        this.isDone = isDone;
    }
}
