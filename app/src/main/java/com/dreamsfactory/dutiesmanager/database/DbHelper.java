package com.dreamsfactory.dutiesmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dreamsfactory.dutiesmanager.database.entities.Flat;
import com.dreamsfactory.dutiesmanager.database.entities.Friend;
import com.dreamsfactory.dutiesmanager.database.entities.Task;
import com.dreamsfactory.dutiesmanager.database.entities.User;

/**
 * Created by Kuba on 2017-02-24.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_FILE_NAME = "DutiesManager.db";

    public DbHelper(Context context){
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.getCreateEntries());
        db.execSQL(Flat.getCreateEntries());
        db.execSQL(Friend.getCreateEntries());
        db.execSQL(Task.getCreateEntries());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(User.getDeleteEntries());
        db.execSQL(Flat.getDeleteEntries());
        db.execSQL(Friend.getDeleteEntries());
        db.execSQL(Task.getDeleteEntries());

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void delete(Context context){
        context.deleteDatabase(DB_FILE_NAME);
    }
}
