package com.example.wassim.tp2.DataStructures;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;

/**
 * Created by Louis-Philippe on 3/16/2017.
 */

public class Settings {
    private static Settings m_settings;
    private int databasePullTick = 1000;
    private boolean askForBatterySaving = true;

    public int getUpdateTick(){return databasePullTick;}
    public static Settings getInstance(){
        if(m_settings==null){
            m_settings = new Settings();
        }
        return m_settings;
    }


    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean update(){

        BatteryManager bm = (BatteryManager)ContextHolder.getMainContext().getSystemService(Context.BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if(batLevel<20 && askForBatterySaving){
            //Call UI for battery saving mode


            //if yes
            databasePullTick = 30000;
            //else
            askForBatterySaving=false;
        }else if(batLevel>20){
            databasePullTick = 1000;
            askForBatterySaving = true;
        }
        return true;
    }

}
