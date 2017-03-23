package com.example.wassim.tp2.DataStructures;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.BatteryManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.wassim.tp2.GroupActivity;

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

            AlertDialog.Builder builder = new AlertDialog.Builder(ContextHolder.getMainContext());
            builder.setTitle("Battery usage").setMessage("Switch to power saving ?");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Yes button clicked, do something
                    databasePullTick = 100;
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //No button clicked, do something
                    askForBatterySaving=false;
                }
            });
            builder.show();

        }else if(batLevel>20){
            databasePullTick = 1000;
            askForBatterySaving = true;
        }
        return true;
    }

}
