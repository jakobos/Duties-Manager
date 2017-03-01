package com.dreamsfactory.dutiesmanager.database.services;

import android.database.Cursor;

import com.dreamsfactory.dutiesmanager.database.entities.User;

/**
 * Created by Kuba on 2017-03-01.
 */

public class UserService extends DbServiceBase {
    public boolean insertUser(User user){
        long id = executeQueryInsert(user);
        if(id <= 0)
            return false;
        else
            return true;
    }
    public boolean updateUser(User user){
        int count = executeQueryUpdate(user);
        if(count > 0)
            return true;
        else
            return false;
    }
    public User getUser(){
        Cursor cursor = executeQueryGetAll(User.TABLE_NAME, User.getFullProjection());
        if(cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();

        User user = new User();
        return user.readFromCursor(cursor) ? user : null;
    }
    public boolean deleteAllUsers(){
        int count = executeQueryDelete(User.TABLE_NAME);
        if(count > 0)
            return true;
        else
            return false;
    }
}
