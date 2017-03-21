package com.dreamsfactory.dutiesmanager.database.services;

import android.database.Cursor;

import com.dreamsfactory.dutiesmanager.database.entities.Flat;

/**
 * Created by Kuba on 2017-03-01.
 */

public class FlatService extends DbServiceBase{
    public boolean insertFlat(Flat flat){
        deleteAllFlats();
        long id = executeQueryInsert(flat);
        if(id <= 0)
            return false;
        else
            return true;
    }
    public boolean updateFlat(Flat flat){
        int count = executeQueryUpdate(flat);
        if(count > 0)
            return true;
        else
            return false;
    }
    public Flat getFlat(){
        Cursor cursor = executeQueryGetAll(Flat.TABLE_NAME, Flat.getFullProjection());
        if(cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();

        Flat flat = new Flat();
        return flat.readFromCursor(cursor) ? flat : null;
    }
    public boolean deleteAllFlats(){
        int count = executeQueryDelete(Flat.TABLE_NAME);
        if(count > 0)
            return true;
        else
            return false;
    }
}
