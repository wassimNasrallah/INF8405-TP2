package com.example.wassim.tp2.DataStructures;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Louis-Philippe on 3/16/2017.
 */

public class Settings {
    private static Settings m_settings;
    private Activity m_activity;
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

        BatteryManager bm = (BatteryManager)m_activity.getSystemService(Context.BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if(batLevel<20 && askForBatterySaving){
            //Call UI for battery saving mode

            AlertDialog.Builder builder = new AlertDialog.Builder(ContextHolder.getMainContext());
            builder.setTitle("Battery usage").setMessage("Switch to power saving ")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes button clicked, do something
                            databasePullTick = 100;
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //No button clicked, do something
                            askForBatterySaving=false;
                        }
                    });

//            //if yes
//            databasePullTick = 30000;
//            //else
//            askForBatterySaving=false;
        }else if(batLevel>20){
            databasePullTick = 1000;
            askForBatterySaving = true;
        }
        return true;
    }

}
