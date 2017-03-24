package com.example.wassim.tp2.DataStructures;
import android.graphics.Bitmap;
import android.location.Location;

import com.example.wassim.tp2.database.DatabaseAccesObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Louis-Philippe on 3/4/2017.
 */

public class Place {

    private Bitmap locationImage;
    public Bitmap getLocationImage(){return locationImage;}
    private HashMap<Integer,Integer> userScoreMap;
    private Location location;
    private long placeId;

    //might need more stuff in here
    public Place(List<Integer>users, Location location){
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        placeId = dao.insertPlace(this);
        userScoreMap = new HashMap<>();
        for(Integer userId : users){
            userScoreMap.put(userId, -1);
            dao.insertScore(userId,placeId,null);
        }
        this.location = location;
    }

    public Place (Bitmap image, Location newLocation, HashMap<Integer,Integer> scoreMap ){
        locationImage = image;
        userScoreMap = scoreMap;
        location = newLocation;
    }

    public void update(){

    }

    public Place getPlace(int placeId){
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        return dao.gatherPlace(placeId);
    }

    public String getLocationString(){
        //TODO: figure how to get good syntax
     return "LocationFormatWhatever";
    }

    public double getLocationLat(){
        return location.getLatitude();
    }

    public double getLocationLon(){
        return location.getLongitude();
    }

    public void addImage(Bitmap image){
        this.locationImage = image;
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        dao.updatePlaceImage(placeId,image);
    }

    public void addScore(int userId, int note){
        userScoreMap.put(userId,note);
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        dao.updatePlaceScore(userId,placeId,note);
    }

    public boolean isAllAnswered(){
        boolean isFilled = true;
        for(Integer i: userScoreMap.values()){
            if(i==-1){
                isFilled=false;
            }
        }
        return isFilled;
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
