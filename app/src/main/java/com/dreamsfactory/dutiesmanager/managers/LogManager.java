package com.dreamsfactory.dutiesmanager.managers;

/**
 * Created by Kuba on 2017-02-27.
 */

public class LogManager {

    public static void logInfo(String infoMessage){
        System.out.println("INFO - "+infoMessage);
    }
    public static void logError(String errorMessage){
        System.out.println("ERROR - "+errorMessage);
    }
}
