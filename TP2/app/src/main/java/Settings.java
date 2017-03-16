import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;

/**
 * Created by Louis-Philippe on 3/16/2017.
 */

public class Settings {
    private static Settings m_settings;
    private Activity m_activity;
    private int databasePullTick = 1000;
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
        if(batLevel<20){
            //Call UI for battery saving mode
            databasePullTick = 30000;
        }
        return true;
    }

}
