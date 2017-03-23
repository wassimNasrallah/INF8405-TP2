package com.example.wassim.tp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;

import com.example.wassim.tp2.DataStructures.ContextHolder;

/**
 * Created by Wassim on 22/03/2017.
 */

public class PlaceScore extends AppCompatActivity {

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextHolder.setCurrentContext(this);
        setContentView(R.layout.place_score);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Float ratingNumber = ratingBar.getRating();

    }


    //TODO: backend insert place score, photo...

}
