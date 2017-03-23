package com.example.wassim.tp2.DataStructures;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.wassim.tp2.database.DatabaseAccesObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Created by Louis-Philippe on 3/9/2017.
 */

public class Events {

    private String name;
    private HashMap<Integer, AnswerToEventEnum> participationAnswerMap;
    private Place location;
    private Date start;
    private Date end;
    private String freeDescription;

    public Events(String name, Place location, Date start, Date end, List<User> userList, String freeDescription){
        participationAnswerMap = new HashMap<Integer, AnswerToEventEnum>();
        this.start = start;
        this.end = end;
        List<Integer> userIdList = new ArrayList<>();
        for(User userId : userList){
            userIdList.add(userId.getUserId());
        }
        for (Integer userId :userIdList ) {
            participationAnswerMap.put(userId, AnswerToEventEnum.NOTANSWERED);
        }
        this.freeDescription = freeDescription;
        //TODO update database with the new event
        //TODO fill database with empty event answer
    }

    public Events(String eventName, Place eventPlace, Date startTime, Date endTime, HashMap<Integer,AnswerToEventEnum> userNameAnswerMap, String eventDescription) {
        name = eventName;
        participationAnswerMap = userNameAnswerMap;
        location = eventPlace;
        start = startTime;
        end = startTime;
        freeDescription = eventDescription;

    }

    public Events getEvent(int eventId) {
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        return dao.gatherEvent(eventId);
    }

    public void update() {

    }

    private void addToDeviceCalendar(String startDate, String endDate, String title, String description, Place location) {

        String stDate = startDate;
        String enDate = endDate;

        GregorianCalendar calDate = new GregorianCalendar();
        //GregorianCalendar calEndDate = new GregorianCalendar();

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm");
        Date date, edate;
        try {
            date = originalFormat.parse(startDate);
            stDate = targetFormat.format(date);

        } catch (ParseException ex) {
        }

        long startMillis = 0;
        long endMillis = 0;
        String dates[] = stDate.split(",");

        String SD_YeaR = dates[0];
        String SD_MontH = dates[1];
        String SD_DaY = dates[2];
        String SD_HouR = dates[3];
        String SD_MinutE = dates[4];

        calDate.set(Integer.parseInt(SD_YeaR), Integer.parseInt(SD_MontH) - 1, Integer.parseInt(SD_DaY), Integer.parseInt(SD_HouR), Integer.parseInt(SD_MinutE));
        startMillis = calDate.getTimeInMillis();

        try {
            ContentResolver cr = ContextHolder.getMainContext().getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + 60 * 60 * 1000);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.EVENT_LOCATION, location.getLocationString());
            values.put(CalendarContract.Events.HAS_ALARM, 1);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance()
                    .getTimeZone().getID());
            System.out.println(Calendar.getInstance().getTimeZone().getID());
            if (ActivityCompat.checkSelfPermission(ContextHolder.getMainContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            long eventId = Long.parseLong(uri.getLastPathSegment());
            Log.d("Ketan_Event_Id", String.valueOf(eventId));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setUserAnswer(Integer userId, AnswerToEventEnum answer) {
        if (answer == AnswerToEventEnum.NOTANSWERED)
            return false;
        if (participationAnswerMap.containsKey(userId) && participationAnswerMap.get(userId) == AnswerToEventEnum.NOTANSWERED) {
            participationAnswerMap.put(userId, answer);
            //TODO update database with the answer
            return true;
        }
        return false;
    }

}
