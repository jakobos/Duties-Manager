package com.dreamsfactory.dutiesmanager.database.services;

import android.database.Cursor;

import com.dreamsfactory.dutiesmanager.database.entities.Friend;

import java.util.ArrayList;

/**
 * Created by Kuba on 2017-03-01.
 */

public class FriendService extends DbServiceBase {

    public boolean insertFriend(Friend friend){
        long id = executeQueryInsert(friend);
        if(id <= 0)
            return false;
        else
            return true;
    }
    public boolean updateFriend(Friend friend){
        int count = executeQueryUpdate(friend);
        if(count > 0)
            return true;
        else
            return false;

    }

    public ArrayList<Friend> getAllFriends(){
        Cursor cursor = executeQueryGetAll(Friend.TABLE_NAME, Friend.getFullProjection());

        if(cursor.getCount() == 0)
            return null;

        ArrayList<Friend> friends = new ArrayList<>();

        for(int i=0; i < cursor.getCount();i++) {
            cursor.moveToPosition(i);
            Friend friend = new Friend();
            if(friend.readFromCursor(cursor))
                friends.add(friend);
        }
        return friends;
    }
    public Friend getFriend(long userId){
        Cursor cursor = executeQueryWhere(Friend.TABLE_NAME, Friend.getFullProjection(), Friend.COLUMN_NAME_USER_ID, String.valueOf(userId));
        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();
        Friend friend = new Friend();
        return friend.readFromCursor(cursor) ? friend : null;
    }



}
