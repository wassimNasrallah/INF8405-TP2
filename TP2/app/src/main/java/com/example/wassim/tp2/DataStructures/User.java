package com.example.wassim.tp2.DataStructures;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.*;

import com.example.wassim.tp2.database.DatabaseAccesObject;

/**
 * Created by Louis-Philippe on 3/22/2017.
 */

public class User {

    private static User m_user;

    public static User getUser() {
        return m_user;
    }

    private Location userLocation;

    public Location getLocation() {
        return userLocation;
    }

    private Bitmap userPicture;

    public Bitmap getUserPicture() {
        return userPicture;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    private int UserId;

    public int getUserId() {
        return UserId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static User createUser(String name, Bitmap picture) {
        int answerRequest=0;
        LocationManager locationManager;
        if (ContextHolder.getMainContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextHolder.getMainContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)ContextHolder.getMainContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions((Activity)ContextHolder.getMainContext(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        answerRequest);
            }
        }else{
            // Get the LocationManager object from the System Service LOCATION_SERVICE
            locationManager = (LocationManager) ContextHolder.getMainContext().getSystemService(Context.LOCATION_SERVICE);

            // Create a criteria object needed to retrieve the provider
            Criteria criteria = new Criteria();

            // Get the name of the best available provider
            String provider = locationManager.getBestProvider(criteria, true);

            // We can use the provider immediately to get the last known location

            Location location = locationManager.getLastKnownLocation(provider);

            locationManager.requestLocationUpdates(provider, 20000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(User.getUser()!=null) {
                        User.getUser().updateLocation(location);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }

        m_user = new User(name, picture);
        //TODO update Database with new user
        //TODO assign userId from the database;
        return m_user;
    }

    public User(String name, Bitmap picture) {
        this.userName = name;
        this.userPicture = picture;
        this.userLocation = null;
    }

    public User getUser(Integer userId) {
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        return dao.gatherUser(userId);

    }

    public void updateLocation(Location location) {

        this.userLocation = location;
        //TODO update local database
    }
}
