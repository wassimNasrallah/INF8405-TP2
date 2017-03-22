package com.example.wassim.tp2.DataStructures;

import android.content.Context;

/**
 * Created by gamyot on 2017-03-22.
 */

public class ContextHolder {
    private static Context mainContext = null;
    public static void setContext(Context context){
        mainContext = context;
    }

    public static Context getMainContext(){
        return mainContext;
    }
}
