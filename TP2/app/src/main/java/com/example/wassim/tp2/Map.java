package com.example.wassim.tp2;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wassim.tp2.DataStructures.Group;
import com.example.wassim.tp2.DataStructures.Place;
import com.example.wassim.tp2.DataStructures.User;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Wassim on 22/03/2017.
 */

public class Map  extends AppCompatActivity {


public void update(){

    GoogleMap map = null;
    Group g = Group.getGroup();
    for(Place l : g.getLocations()){
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(-126.084, 37.422))
                .title("LocationMarker")
                .icon(BitmapDescriptorFactory.fromBitmap(User.getUser().getUserPicture())));//TODO use location if exist, else random?
    }
    for(Place l : g.getLocations()){
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(-126.084, 37.422))
                .title("LocationMarker")
                .icon(BitmapDescriptorFactory.fromBitmap(User.getUser().getUserPicture())));//TODO use location if exist, else random?
    }

}

    //TODO: backend insert map
}
