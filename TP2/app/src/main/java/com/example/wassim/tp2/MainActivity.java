package com.example.wassim.tp2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    Button buttonPhoto;
    ImageView img;
    private static final int CAM_REQUEST = 1111;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
        img = (ImageView) findViewById(R.id.ImageView);

        buttonPhoto.setOnClickListener(new buttonPhotoListener());
        butListener();
    }

    public void butListener() {

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), ChooseGroup.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST)
        {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bm);
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
}
