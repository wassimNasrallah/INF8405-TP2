package com.example.wassim.tp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Wassim on 28/02/2017.
 */

public class Group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        String str = intent.getStringExtra("text");

        setTitle(str);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:

                AlertDialog.Builder build = new AlertDialog.Builder(this);

                build.setTitle("Sign Out");
                build.setMessage("Are you sure?");
                build.setIcon(android.R.drawable.ic_dialog_alert);
                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getBaseContext(), ChooseGroup.class);
                        startActivity(i);
                    }
                });
                build.setNegativeButton("No", null);
                build.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
