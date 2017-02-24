package com.dreamsfactory.dutiesmanager.database.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Kuba on 2017-02-22.
 */

public abstract class DBEntityBase implements BaseColumns {

    //constants
    protected static String TEXT_TYPE = " TEXT";
    protected static String INTEGER_TYPE = " INTEGER";
    protected static String FLOAT_TYPE = " REAL";
    protected static String PRIMARY_KEY = " PRIMARY KEY";
    protected static String COMMA = ", ";


    //variables
    protected long id;

    //methods
    public abstract ContentValues getContentValues();
    public abstract boolean readFromCursor(Cursor cursor);
    public abstract String getTableName();

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }


}
