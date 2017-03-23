package com.example.wassim.tp2;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wassim.tp2.DataStructures.ContextHolder;
import com.example.wassim.tp2.DataStructures.Group;
import com.example.wassim.tp2.DataStructures.Place;
import com.example.wassim.tp2.DataStructures.Settings;
import com.example.wassim.tp2.DataStructures.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wassim on 22/03/2017.
 */

public class Map  extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private MapView mapView;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextHolder.setCurrentContext(this);
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        timer.cancel();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng camLoc= new LatLng(User.getUser().getLocation().getLatitude(), User.getUser().getLocation().getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(camLoc));
    }

    public void startTimer(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            update();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask,0, 1000);
    }

public void update(){

    Group g = Group.getGroup();
    if(g!=null) {
        if (g.getEvent() == null) {
            if (g.getLocations() != null) {
                for (Place l : g.getLocations()) {
                    MarkerOptions markerOp = new MarkerOptions()
                            .position(new LatLng(l.getLocationLat(), l.getLocationLon()))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    if (l.getLocationImage() != null) {
                        markerOp.icon(BitmapDescriptorFactory.fromBitmap(l.getLocationImage()));
                    }
                    map.addMarker(markerOp);
                }
            }
        } else {
            MarkerOptions markerOp = new MarkerOptions()
                    .position(new LatLng(g.getEvent().getLocation().getLocationLat(), g.getEvent().getLocation().getLocationLon()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            if (g.getEvent().getLocation().getLocationImage() != null) {
                markerOp.icon(BitmapDescriptorFactory.fromBitmap(g.getEvent().getLocation().getLocationImage()));
            }
            map.addMarker(markerOp);
        }
        if (g.getUsers() != null) {
            for (User u : g.getUsers()) {
                MarkerOptions markerOp = new MarkerOptions()
                        .position(new LatLng(u.getLocation().getLatitude(), u.getLocation().getLongitude()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                if (u.getUserPicture() != null) {
                    markerOp.icon(BitmapDescriptorFactory.fromBitmap(u.getUserPicture()));
                }
                map.addMarker(markerOp);
            }
        }
        MarkerOptions markerOp = new MarkerOptions()
                .position(new LatLng(User.getUser().getLocation().getLatitude(), User.getUser().getLocation().getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        if (User.getUser().getUserPicture() != null) {
            markerOp.icon(BitmapDescriptorFactory.fromBitmap(User.getUser().getUserPicture()));
        }
        map.addMarker(markerOp);
    }

}
    //TODO: backend insert map
}
