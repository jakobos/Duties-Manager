package com.dreamsfactory.dutiesmanager.database.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dreamsfactory.dutiesmanager.managers.LogManager;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kuba on 2017-02-24.
 */

public class Task extends DBEntityBase implements Parcelable {

    //
    //Constant variables
    //
    public static final String TABLE_NAME = "Task";
    //public static final String COLUMN_NAME_TASK_ID = "TaskId";
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
   // private String taskId;

    //
    //constructors
    //
    public Task(){
        this.title = "";
        this.description = "";
        this.deadline = 0;
        this.isDone = false;
        this.ownerId = 0;
        setUUID("");
        setRemoteId(0);
//        this.taskId = "";
    }

    public Task(String title){
        this.title = title;
        this.description = "";
        this.deadline = 0;
        this.isDone = false;
        this.ownerId = 0;
        setUUID("");
        setRemoteId(0);
//        this.taskId = "";
    }
    public Task(String title, long deadline, long ownerId){
        this.title = title;
        this.description = "Przykładowy opis zadania..";
        this.deadline = deadline;
        this.isDone = false;
        this.ownerId = ownerId;
        setUUID("");
        setRemoteId(0);
 //       this.taskId = "";
    }

    //
    //Static methods
    //

    public static String getCreateEntries(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA +
                REMOTE_ID + INTEGER_TYPE + COMMA +
                UNIQUE_ID + TEXT_TYPE + COMMA +
                COLUMN_NAME_TITLE + TEXT_TYPE + COMMA +
                COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA +
                COLUMN_NAME_DEADLINE + INTEGER_TYPE + COMMA +
                COLUMN_NAME_IS_DONE + INTEGER_TYPE + COMMA +
                COLUMN_NAME_OWNER_ID + INTEGER_TYPE + ");";
    }
    public static String getDeleteEntries(){ return "DROP TABLE IF EXISTS "+ TABLE_NAME; }

    public static String[] getFullProjection(){
        String[] projection = {
                _ID,
                REMOTE_ID,
                UNIQUE_ID,
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
        values.put(REMOTE_ID, getRemoteId());
        values.put(UNIQUE_ID, getUUID());
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
            //this.taskId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TASK_ID));
            setRemoteId(cursor.getLong(cursor.getColumnIndexOrThrow(REMOTE_ID)));
            setUUID(cursor.getString(cursor.getColumnIndexOrThrow(UNIQUE_ID)));
            this.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
            this.description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION));
            this.deadline = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_DEADLINE));
            LogManager.logInfo("Task: "+title+" , is done: "+cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_IS_DONE)));
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

//    public String getTaskId(){
//        return taskId;
//    }
//    public void setTaskId(String taskId){
//        this.taskId = taskId;
//    }
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
    public long getOwnerId(){
        return ownerId;
    }
    public void setOwnerId(long ownerId){
        this.ownerId = ownerId;
    }

    //
    //special methods

    /**
     * Check if task is free
     * @return
     */
    public boolean isFree(){
        if(ownerId > 0)
            return false;
        else
            return true;
    }

    /**
     * Shows text how much time is to deadline
     * @return
     */
    public String getCountdown(){
        long currentMs = (Calendar.getInstance().getTime()).getTime();
        long time = deadline - currentMs;
        //in seconds
        long seconds = time/1000;
        long minutes = (seconds/60);
        int hours = (int)(minutes / 60);
        int days = hours/24;

        String text;
        if(days>0){
            if(days==1){
                text=""+days+" dzień";
            }else{
                text=""+days+" dni";
            }
        }else if(hours>0){
            text = ""+hours+" godz.";
        }else if(minutes>0){
            text = ""+minutes+"min.";
        }else{
            text = "0 min.";
        }
        return text;
    }
    public long getMilis(){
        return deadline - Calendar.getInstance().getTimeInMillis();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    /** save object in parcel */
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(description);
        out.writeLong(deadline);
        out.writeByte((byte) (isDone ? 1 : 0));
        out.writeLong(ownerId);
        out.writeString(getUUID());
        out.writeLong(getRemoteId());
    }

    public static final Parcelable.Creator<Task> CREATOR
            = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    /** recreate object from parcel */
    private Task(Parcel in) {
        title = in.readString();
        description = in.readString();
        deadline = in.readLong();
        isDone = in.readByte() != 0;
        ownerId = in.readLong();
        setUUID(in.readString());// = in.readString();
        setRemoteId(in.readLong());
    }


}
