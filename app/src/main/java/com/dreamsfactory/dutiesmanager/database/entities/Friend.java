package com.dreamsfactory.dutiesmanager.database.entities;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Kuba on 2017-02-24.
 */

public class Friend extends DBEntityBase{

    /**
     * Constant variables
     */
    public static final String TABLE_NAME = "Friend";
    public static final String COLUMN_NAME_FRIEND_NAME = "FriendName";
    public static final String COLUMN_NAME_EMAIL = "Email";
    public static final String COLUMN_NAME_USER_ID = "UserId";

    /**
     * Variables
     */
    private String friendName;
    private String friendEmail;
    private long userId;
    /**
     * Constructors
     */
    public Friend(){
        this.userId = 0;
        this.friendName = "";
        this.friendEmail = "";
    }

    /**
     * Static methods
     */
    public static String getCreateEntries(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA +
                COLUMN_NAME_USER_ID + INTEGER_TYPE + COMMA +
                COLUMN_NAME_FRIEND_NAME + TEXT_TYPE + COMMA +
                COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA + ");";
    }
    public static String getDeleteEntries(){ return "DROP TABLE IF EXISTS "+ TABLE_NAME; }

    public static String[] getFullProjection(){
        String[] projection = {
                _ID,
                COLUMN_NAME_USER_ID,
                COLUMN_NAME_FRIEND_NAME,
                COLUMN_NAME_EMAIL
        };
        return projection;
    }

    //Base class methods

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER_ID, userId);
        values.put(COLUMN_NAME_FRIEND_NAME, friendName);
        values.put(COLUMN_NAME_EMAIL, friendEmail);
        return values;
    }

    @Override
    public boolean readFromCursor(Cursor cursor) {
        try{
            this.id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            this.userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_USER_ID));
            this.friendName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_FRIEND_NAME));
            this.friendEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_EMAIL));
            return true;

        }catch(Exception ex){
            return false;
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    //public methods

    public long getUserId(){
        return userId;
    }
    public void setUserId(long userId){
        this.userId = userId;
    }
    public String getFriendName(){
        return friendName;
    }
    public void setFriendName(String friendName){
        this.friendName = friendName;
    }
    public String getFriendEmail(){
        return friendEmail;
    }
    public void setFriendEmail(String friendEmail){
        this.friendEmail = friendEmail;
    }
}
