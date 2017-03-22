package com.example.wassim.tp2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wassim.tp2.DataStructures.Group;
import com.example.wassim.tp2.DataStructures.Settings;
import com.example.wassim.tp2.database.DatabaseTest;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {


    Button buttonPhoto;
    ImageView img;
    private static final int CAM_REQUEST = 1111;
    Button buttonLogin;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTimer();
        setContentView(R.layout.login);
        //testDatabase();
        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
        ((Button) findViewById(R.id.buttonLogin)).setEnabled(false);
        img = (ImageView) findViewById(R.id.ImageView);

        buttonPhoto.setOnClickListener(new buttonPhotoListener());
        butListener();
    }

    //Test database
    private void testDatabase(){
        DatabaseTest.groupDaoTest1(this.getBaseContext());
    }

    public void butListener() {

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.editTextUserName);
                if(text!=null && text.getText()!=null && text.getText().length()>1) {
                    Intent i = new Intent(getBaseContext(), ChooseGroup.class);
                    startActivity(i);

                    //TODO: call backend username, password and image

                    User.createUser(text.getText().toString(), ((BitmapDrawable) img.getDrawable()).getBitmap());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
            if (data != null && data.getExtras() != null) {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bm);
                buttonLogin.setEnabled(true);
            }
        }
    }

    class buttonPhotoListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cam, CAM_REQUEST);
        }
    }

    public void startTimer(){
        final Handler handler = new Handler();
        Timer    timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            if(Group.getGroup()!=null)
                                Group.getGroup().updateGroup();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask,0, Settings.getInstance().getUpdateTick()*30);
    }
}
