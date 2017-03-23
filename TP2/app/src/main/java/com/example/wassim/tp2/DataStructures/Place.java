package com.example.wassim.tp2.DataStructures;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Louis-Philippe on 3/4/2017.
 */

public class Place {

    private Bitmap locationImage;
    private HashMap<String,Integer> userScoreMap;
    private Location location;

    //might need more stuff in here
    public Place(List<String>users){
        userScoreMap = new HashMap<>();
        for(String name : users){
            userScoreMap.put(name, -1);
            //TODO update database with the empty answer
        }
    }

    public Place (Bitmap image, Location newLocation, HashMap<String,Integer> scoreMap ){
        locationImage = image;
        userScoreMap = scoreMap;
        location = newLocation;
    }

    public void update(){

    }

    public Place getPlace(int placeId){
        //TODO get info from db
        return null;
    }

    public String getLocationString(){
        //TODO: figure how to get good syntax
     return "LocationFormatWhatever";
    }
    public void addScore(String user, int note){
        userScoreMap.put(user,note);
        //TODO update database with the new answer
    }
    public float getAverage(){
        float sum=0;
        int number=0;
        for(int j : userScoreMap.values()){
            if(j>0){
                number++;
                sum+=j;
            }
        }
        return (sum/number);
    }
}
