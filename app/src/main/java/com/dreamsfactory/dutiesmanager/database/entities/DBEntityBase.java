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

    public static String UNIQUE_ID = "UniqueId";
    public static String REMOTE_ID = "RemoteId";

    //variables
    protected long id;

    //actually UNIQUE_ID is not used
    protected String uniqueId;
    protected long remoteId;

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

    public String getUUID(){
        return uniqueId;
    }
    public void setUUID(String uniqueId){
        this.uniqueId = uniqueId;
    }

    public long getRemoteId(){return this.remoteId;}
    public void setRemoteId(long remoteId){this.remoteId = remoteId;}

}
