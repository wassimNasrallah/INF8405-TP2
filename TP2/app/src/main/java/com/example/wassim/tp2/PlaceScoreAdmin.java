package com.example.wassim.tp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;

/**
 * Created by Wassim on 22/03/2017.
 */

public class PlaceScoreAdmin extends AppCompatActivity {

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_score_admin);
        
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Float ratingNumber = ratingBar.getRating();

        //TODO: backend insert place score, photo...

    }
}
