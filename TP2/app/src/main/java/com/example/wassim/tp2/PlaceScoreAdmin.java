package com.example.wassim.tp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.wassim.tp2.DataStructures.ContextHolder;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by Wassim on 22/03/2017.
 */

public class PlaceScoreAdmin extends AppCompatActivity{

    Button buttonAddPlaces;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_score_admin);
        ContextHolder.setCurrentContext(this);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        //Float ratingNumber = ratingBar.getRating();
        final Activity a = this;

        buttonAddPlaces = (Button) findViewById(R.id.buttonAddPlaces);

        butListener();

        //TODO: backend insert place score, photo...
    }

    private void butListener() {
        buttonAddPlaces.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(i);

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {


            }

        });


    }


}
