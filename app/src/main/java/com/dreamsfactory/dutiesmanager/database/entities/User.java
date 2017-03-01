package com.dreamsfactory.dutiesmanager.database.entities;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Kuba on 2017-02-24.
 */

public class User extends DBEntityBase {



    //
    //Constant variables
    //
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_NAME_USERNAME = "Username";
    public static final String COLUMN_NAME_EMAIL = "Email";
    public static final String COLUMN_NAME_USER_ID = "UserId";

    //
    //Variables
    //

    private String name;
    private String email;
    private long userId;

    //
    //constructors
    //
    public User(){
        this.userId = 0;
        this.name = "";
        this.email = "";
    }

    //
    //Static methods
    //

    public static String getCreateEntries(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA +
                COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA +
                COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA +
                COLUMN_NAME_USER_ID + INTEGER_TYPE + COMMA + ");";
    }
    public static String getDeleteEntries(){ return "DROP TABLE IF EXISTS "+ TABLE_NAME; }

    public static String[] getFullProjection(){
        String[] projection = {
                _ID,
                COLUMN_NAME_USERNAME,
                COLUMN_NAME_EMAIL,
                COLUMN_NAME_USER_ID
        };
        return projection;
    }


    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USERNAME, name);
        values.put(COLUMN_NAME_EMAIL, email);
        values.put(COLUMN_NAME_USER_ID, userId);
        return values;
    }

    @Override
    public boolean readFromCursor(Cursor cursor) {
        try{
            this.id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            this.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_USERNAME));
            this.email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_EMAIL));
            this.userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_USER_ID));

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
    //Base class methods
    //

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public long getUserId(){
        return userId;
    }
    public void setUserId(long userId){
        this.userId = userId;
    }
}
