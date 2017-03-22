package com.dreamsfactory.dutiesmanager.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dreamsfactory.dutiesmanager.managers.LogManager;
import com.dreamsfactory.dutiesmanager.settings.Settings;
import com.dreamsfactory.dutiesmanager.webServices.WebServiceManager;

import java.util.HashMap;
import java.util.Map;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 */
public class SyncService extends IntentService {


    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(Settings.getInstance(this).getBoolean(Settings.USER_IS_LOGGED_IN) && Settings.getInstance(this).getBoolean(Settings.FLAT_IS_LOGGED_IN)){

            String flatId = Settings.getInstance(this).get(Settings.FLAT_ID);
            String last_sync_task = Settings.getInstance(this).get(Settings.LAST_SYNC_TASK);
            String last_sync_friend = Settings.getInstance(this).get(Settings.LAST_SYNC_FRIEND);
            String userId = Settings.getInstance(this).get(Settings.USER_ID);

            LogManager.logInfo("Start sync data..");
            WebServiceManager.getInstance(this).syncWithRemote(userId, flatId, last_sync_task, last_sync_friend);

        }
    }


}
