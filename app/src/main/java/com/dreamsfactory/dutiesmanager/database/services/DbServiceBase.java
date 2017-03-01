package com.dreamsfactory.dutiesmanager.database.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dreamsfactory.dutiesmanager.database.DbHelper;
import com.dreamsfactory.dutiesmanager.database.entities.DBEntityBase;

import java.util.ArrayList;

/**
 * Created by Kuba on 2017-02-27.
 */

public class DbServiceBase {

    private static DbHelper dbClient;

    public static void init(DbHelper dbClient){
        DbServiceBase.dbClient = dbClient;
    }

    //
    //protected methods

    protected long executeQueryInsert(DBEntityBase entity){
        ContentValues values = entity.getContentValues();
        return dbWrite().insert(entity.getTableName(), null, values);
    }
    protected int executeQueryUpdate(DBEntityBase entity){
        if(entity.getId() <= 0)
            return 0;
        ContentValues values = entity.getContentValues();
        String whereClause = DBEntityBase._ID+" = ?";
        return dbWrite().update(entity.getTableName(), values, whereClause, new String[] {String.valueOf(entity.getId())});
    }

    protected int executeQueryUpdate(DBEntityBase entity, String comparisonColumn, String value){
        if(entity.getId() <= 0)
            return 0;
        ContentValues values = entity.getContentValues();
        String whereClause = comparisonColumn + " = ?";
        return dbWrite().update(entity.getTableName(), values, whereClause, new String[]{String.valueOf(value)});
    }
    protected int executeQueryUpdate(DBEntityBase entity, ArrayList<String> whereColumns, String[] whereArgs){
        if(entity.getId() <= 0)
            return 0;
        ContentValues values = entity.getContentValues();
        String whereStatement = "";
        for(int i = 0; i < whereColumns.size(); i++){
            String column = whereColumns.get(i);
            whereStatement+= column + " = ?";
            if(i+1 < whereColumns.size())
                column+=" AND ";
        }
        return dbWrite().update(entity.getTableName(), values, whereStatement, whereArgs);
    }


    protected Cursor executeQueryGetAll(String tableName, String[] columns){
        return dbRead().query(tableName, columns, null, null, null, null, null);
    }

    protected Cursor executeQueryWhere(String tableName, String[] columns, ArrayList<String> whereColumns, String[] whereArgs){
        String whereStatement = "";
        for(int i = 0; i < whereColumns.size(); i++){
            String column = whereColumns.get(i);
            whereStatement += column + " = ? ";
            if(i+1 < whereColumns.size())
                column += "AND ";
        }
        return dbRead().query(tableName, columns, whereStatement, whereArgs, null, null, null);
    }
    protected Cursor executeQueryWhere(String tableName, String[] columns, String interestColumn, String interestValue){
        ArrayList<String> whereColumns = new ArrayList<>();
        whereColumns.add(interestColumn);
        String[] whereValues = {interestValue};
        return executeQueryWhere(tableName, columns, whereColumns, whereValues);
    }
    protected int executeQueryDelete(String tableName, ArrayList<String> whereColumns, String[] whereArgs){
        String whereStatement = "";
        for(int i =0; i < whereColumns.size(); i++){
            String column = whereColumns.get(i);
            whereStatement += column + " = ? ";
            if(i+1 < whereColumns.size())
                column += "AND ";
        }
        return dbRead().delete(tableName, whereStatement, whereArgs);
    }
    protected int executeQueryDelete(String tableName, String interestColumn, String interestValue){
        ArrayList<String> whereColumns = new ArrayList<>();
        whereColumns.add(interestColumn);
        String[] whereValues = {interestValue};
        return executeQueryDelete(tableName, whereColumns, whereValues);
    }
    protected int executeQueryDelete(String tableName){
        return dbRead().delete(tableName, null, null);
    }

    private SQLiteDatabase dbRead(){
        return dbClient.getReadableDatabase();
    }
    private SQLiteDatabase dbWrite(){
        return dbClient.getWritableDatabase();
    }
}
