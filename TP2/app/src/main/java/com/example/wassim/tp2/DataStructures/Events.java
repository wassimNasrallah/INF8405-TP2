package com.example.wassim.tp2.DataStructures;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Louis-Philippe on 3/9/2017.
 */

public class Events {

    private Activity m_activity;
    private String name;
    private HashMap<String,AnswerToEventEnum> answerMap;
    private Place location;
    private Date start;
    private Date end;
    private String freeDescription;

    public Events(Activity activity, String name, Place location, Date start, Date end, List<String> names, String freeDescription){
        m_activity = activity;
        answerMap = new HashMap<String,AnswerToEventEnum>();
        this.start =start;
        this.end = end;
        for(String user : names){
            answerMap.put(user, AnswerToEventEnum.NOTANSWERED);
        }
        this.freeDescription = freeDescription;
    }
    private void addToDeviceCalendar(String startDate,String endDate, String title ,String description, Place location) {

        String stDate = startDate;
        String enDate = endDate;

        GregorianCalendar calDate = new GregorianCalendar();
        //GregorianCalendar calEndDate = new GregorianCalendar();

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm");
        Date date,edate;
        try {
            date = originalFormat.parse(startDate);
            stDate=targetFormat.format(date);

        } catch (ParseException ex) {}

        long startMillis = 0;
        long endMillis = 0;
        String dates[] = stDate.split(",");

        String SD_YeaR = dates[0];
        String SD_MontH = dates[1];
        String SD_DaY = dates[2];
        String SD_HouR = dates[3];
        String SD_MinutE = dates[4];

        calDate.set(Integer.parseInt(SD_YeaR), Integer.parseInt(SD_MontH)-1, Integer.parseInt(SD_DaY), Integer.parseInt(SD_HouR), Integer.parseInt(SD_MinutE));
        startMillis = calDate.getTimeInMillis();

        try {
            ContentResolver cr = m_activity.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + 60 * 60 * 1000);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.EVENT_LOCATION,location.getLocationString());
            values.put(CalendarContract.Events.HAS_ALARM,1);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance()
                    .getTimeZone().getID());
            System.out.println(Calendar.getInstance().getTimeZone().getID());
            if (ActivityCompat.checkSelfPermission(m_activity, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            long eventId = Long.parseLong(uri.getLastPathSegment());
            Log.d("Ketan_Event_Id", String.valueOf(eventId));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setUserAnswer(String name, AnswerToEventEnum answer){
        if(answer == AnswerToEventEnum.NOTANSWERED)
            return false;
        if(answerMap.containsKey(name) && answerMap.get(name)==AnswerToEventEnum.NOTANSWERED){
            answerMap.put(name, answer);
            return  true;
        }
        return false;
    }

}
