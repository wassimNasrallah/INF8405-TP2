package com.example.wassim.tp2.DataStructures;

import android.app.Activity;

import java.util.Date;
import java.util.List;

/**
 * Created by Louis-Philippe on 3/16/2017.
 */

public class Group {
    private Activity m_activity;
    private String name;
    private String organisaterName;
    private List<String>users;
    private Place[] locations;
    private Events event;
    public void createEvent(int locationNumber, String name, String description, Date start, Date end){
        event = new Events(m_activity,name,locations[locationNumber],start,end, users,description);
        //Send event to users
    }
    public void recordUserAnswerForLocation(String user, List<Integer> notes){
        if(users.contains(user)){
            for(int i =0;i<notes.size();i++){
                locations[i].addScore(user,notes.get(i));
            }
        }
    }
    public void recordUserAnswerForEvent(String user, AnswerToEventEnum answer){
        event.setUserAnswer(user,answer);
    }
}
