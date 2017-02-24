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
    public static final String COLUMN_NAME_FLAT_ID = "FlatId";

    //
    //Variables
    //

    private String address;
    private long flatId;

    //
    //constructors
    //

    //
    //Static methods
    //

    public static String getCreateEntries(){
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + PRIMARY_KEY + COMMA +
                COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA +
                COLUMN_NAME_FLAT_ID + INTEGER_TYPE + COMMA + ");";
    }
    public static String getDeleteEntries(){ return "DROP TABLE IF EXISTS "+ TABLE_NAME; }

    public static String[] getFullProjection(){
        String[] projection = {
                _ID,
                COLUMN_NAME_ADDRESS,
                COLUMN_NAME_FLAT_ID
        };
        return projection;
    }


    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ADDRESS, address);
        values.put(COLUMN_NAME_FLAT_ID, flatId);
        return values;
    }

    @Override
    public boolean readFromCursor(Cursor cursor) {
        try{
            this.id = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            this.address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ADDRESS));
            this.flatId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_FLAT_ID));

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
    public long getFlatId(){
        return flatId;
    }
    public void setFlatId(long flatId){
        this.flatId = flatId;
    }

}
