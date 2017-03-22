package com.example.wassim.tp2.DataStructures;

import android.graphics.Bitmap;
import android.location.Location;

/**
 * Created by Louis-Philippe on 3/22/2017.
 */

public class User {

    private static User m_user;
    public static User getUser(){return m_user;}
    private Location userLocation;
    public Location getLocation(){return userLocation;}
    private Bitmap userPicture;
    public Bitmap getUserPicture(){return userPicture;}
    private String userName;
    public String getUserName(){return userName;}
    private int UserId;

    public static User createUser(String name, Bitmap picture){
        m_user = new User(name, picture);
        //TODO update Database with new user
        //TODO assign userId from the database;
        return m_user;
    }

    private User(String name, Bitmap picture){
        this.userName = name;
        this.userPicture = picture;
        this.userLocation = null;
    }

    public User getUser(String userName){
        //TODO get location/image from database
        User u = new User(userName,null);
        u.updateLocation(null);
        return u;

    }

    public void updateLocation(Location location){
        this.userLocation = location;
    }
}
