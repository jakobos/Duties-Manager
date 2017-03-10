package com.dreamsfactory.dutiesmanager.settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kuba on 2017-03-10.
 */

public abstract class SettingsBase {

    private Context mContext;

    protected SettingsBase(Context context){
        mContext = context;
    }

    protected void clear(){
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        mContext = null;
    }

    protected abstract SharedPreferences getSharedPreferences0(Context context);

    protected SharedPreferences getSharedPreferences(){
        return getSharedPreferences0(mContext);
    }

    protected String getStringKey(int key){
        return mContext.getString(key);
    }
    protected String getString(String key){
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(key, null);
    }

    protected void setString(String key, String value){
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
