package com.dreamsfactory.dutiesmanager.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dreamsfactory.dutiesmanager.R;

/**
 * Created by Kuba on 2017-03-10.
 */

public class Settings extends SettingsBase{
    //constants
    public final static int USER_ID = R.string.pref_user_id;
    public final static int FLAT_ID = R.string.pref_flat_id;



    //singleton

    private static Settings _instance = null;

    protected Settings(Context context) {
        super(context);
    }



    public static synchronized Settings getInstance(Context context){
        return get0(context.getApplicationContext());
    }
    private static synchronized Settings get0(Context applicationContext){
        if(_instance == null){
            _instance = new Settings(applicationContext);

        }
        return _instance;
    }

    @Override
    protected SharedPreferences getSharedPreferences0(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    //getters

    public String get(int resId){
        return getString(getStringKey(resId));
    }
    public boolean getBoolean(int resId, boolean defaultValue){
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean(getStringKey(resId), defaultValue);
    }
    public boolean getBoolean(int resId){
        return getBoolean(resId, false);
    }


    //setters

    public <T> void set(int resId, T value){
        setString(getStringKey(resId), String.valueOf(value));
    }
    public void set(int resId, boolean value){
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(getStringKey(resId), value);
        editor.apply();
    }

}
