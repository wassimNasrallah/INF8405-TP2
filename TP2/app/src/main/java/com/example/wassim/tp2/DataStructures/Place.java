package com.example.wassim.tp2.DataStructures;
import android.graphics.Picture;
import android.location.Location;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Louis-Philippe on 3/4/2017.
 */

public class Place {

    private Picture locationImage;
    private HashMap<String,Integer> userNotes;
    private Location location;

    //might need more stuff in here
    public Place(List<String>users){
        userNotes = new HashMap<>();
        for(String name : users){
            userNotes.put(name, -1);
        }
    }
    public String getLocationString(){
        //TODO: figure how to get good syntax
     return "LocationFormatWhatever";
    }
    public void addScore(String user, int note){
        userNotes.put(user,note);
    }
    public float getAverage(){
        float sum=0;
        int number=0;
        for(int j : userNotes.values()){
            if(j>0){
                number++;
                sum+=j;
            }
        }
        return (sum/number);
    }
}
