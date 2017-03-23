package com.example.wassim.tp2.DataStructures;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

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
    public int getUserId(){return UserId;}

    public static User createUser(String name, Bitmap picture) {
        LocationManager locationManager;

// Get the LocationManager object from the System Service LOCATION_SERVICE
        locationManager = (LocationManager) ContextHolder.getMainContext().getSystemService(Context.LOCATION_SERVICE);

// Create a criteria object needed to retrieve the provider
        Criteria criteria = new Criteria();

// Get the name of the best available provider
        String provider = locationManager.getBestProvider(criteria, true);

// We can use the provider immediately to get the last known location
        if (ActivityCompat.checkSelfPermission(ContextHolder.getMainContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ContextHolder.getMainContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }else {
            Location location = locationManager.getLastKnownLocation(provider);

            // request that the provider send this activity GPS updates every 20 seconds
            locationManager.requestLocationUpdates(provider, 20000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    User.getUser().updateLocation(location);
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
    }
}
