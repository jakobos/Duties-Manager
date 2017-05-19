package com.dreamsfactory.dutiesmanager.database.entities;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Kuba on 2017-02-24.
 */

public class Flat extends DBEntityBase {


    //
    //Constant variables
    //
    public static final String TABLE_NAME = "Flat";
    public static final String COLUMN_NAME_ADDRESS = "Address";

    //
    //Variables
    //

    private String address;

    //
    //constructors
    //
    public Flat(){
        setRemoteId(0);
        setUUID("");
        this.address = "";
    }

    //
    //Static methods
    //

    public static String getCreateEntries(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA +
                REMOTE_ID + INTEGER_TYPE + COMMA +
                COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA +
                UNIQUE_ID + TEXT_TYPE + ");";
    }
    public static String getDeleteEntries(){ return "DROP TABLE IF EXISTS "+ TABLE_NAME; }

    public static String[] getFullProjection(){
        String[] projection = {
                _ID,
                REMOTE_ID,
                COLUMN_NAME_ADDRESS,
                UNIQUE_ID
        };
        return projection;
    }


    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(REMOTE_ID, getRemoteId());
        values.put(COLUMN_NAME_ADDRESS, address);
        values.put(UNIQUE_ID, getUUID());
        return values;
    }

    @Override
    public boolean readFromCursor(Cursor cursor) {
        try{
            this.id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            setRemoteId(cursor.getLong(cursor.getColumnIndexOrThrow(REMOTE_ID)));
            this.address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ADDRESS));
            setUUID(cursor.getString(cursor.getColumnIndexOrThrow(UNIQUE_ID)));

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

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

}
