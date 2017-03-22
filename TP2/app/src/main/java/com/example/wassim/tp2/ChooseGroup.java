package com.example.wassim.tp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.wassim.tp2.DataStructures.Group;

/**
 * Created by Wassim on 28/02/2017.
 */

public class ChooseGroup extends AppCompatActivity {

    Button buttonCreateJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_choice);

        EditText text = (EditText)findViewById(R.id.textGroup);
        String str = text.getText().toString();
//
//        setTitle(str);



        butListener();
    }


    public void butListener() {

        buttonCreateJoin = (Button) findViewById(R.id.buttonJoinCreate);

        buttonCreateJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                EditText text = (EditText)findViewById(R.id.textGroup);
                Group.getOrCreateGroup(text.getText().toString());
                Intent i = new Intent(getBaseContext(), Group.class);
                startActivity(i);

                Intent i2 = new Intent(ChooseGroup.this, Group.class);
                i2.putExtra("text", text.getText().toString());
                startActivity(i2);

                //TODO: backend group name

            }
        });
    }

}
