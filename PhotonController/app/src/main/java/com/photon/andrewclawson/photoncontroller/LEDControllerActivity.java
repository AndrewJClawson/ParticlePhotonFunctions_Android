package com.photon.andrewclawson.photoncontroller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import java.lang.Runnable;

import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleDevice;
import Photon.Manager;

import java.util.Arrays;

//import com.photon.PhotonManager;



public class LEDControllerActivity extends AppCompatActivity {
    Button GreenLEDButton;
    Button RedLEDButton;
    Button BlueLEDButton;
    Button ResetLEDButton;
    Button MainMenuButton;
    CheckBox MultipleLEDCheckbox;

    //Manager photonManager = new Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParticleCloudSDK.init(this);
        setContentView(R.layout.activity_ledcontroller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SetupUIComponents();
    }

    public void SetupUIComponents(){
        GreenLEDButton = (Button) findViewById(R.id.greenLEDButton);
        GreenLEDButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new LEDControllerTask("green").execute();
            }
        });
        RedLEDButton = (Button) findViewById(R.id.redLEDButton);
        RedLEDButton.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               new LEDControllerTask("red").execute();
           }
        });
        BlueLEDButton = (Button) findViewById(R.id.blueLEDButton);
        BlueLEDButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new LEDControllerTask("blue").execute();
            }
        });
        ResetLEDButton = (Button) findViewById(R.id.resetLEDButton);
        ResetLEDButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new LEDControllerTask("none").execute();
            }
        });
        MainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        MainMenuButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new LEDControllerTask("none").execute();
                returnToStartup(v);
            }
        });

        MultipleLEDCheckbox = (CheckBox) findViewById(R.id.multipleLEDCheckbox);

    }



    public void returnToStartup(View view){
        Intent startupIntent = new Intent(view.getContext(),StartupActivity.class);
        startActivity(startupIntent);
    }

    public class LEDControllerTask extends AsyncTask<Void, Void, Void> {
        public Manager photonManager = new Manager();
        public ParticleCloud cloud;
        public ParticleDevice loadDevice;
        public boolean IsLoggedIn;
        public String color;
        public LEDControllerTask(String lightColor){
            color = lightColor;
        }
        protected Void doInBackground(Void... voids){
            try{
                ParticleCloudSDK.getCloud().logIn("andrewjclawson18@gmail.com","clawson1");
                IsLoggedIn = ParticleCloudSDK.getCloud().isLoggedIn();
                loadDevice = ParticleCloudSDK.getCloud().getDevice("aclawson");
                //particleDevice = particleCloud.getDevice("aclawson");
                int resultCode = loadDevice.callFunction("ToggleLED", Arrays.asList(color));
            } catch (Exception e){
                e.printStackTrace();
                runOnUiThread(new Runnable(){
                    public void run(){
                        Toast.makeText(LEDControllerActivity.this,"Particle cloud exception caught.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }


    }
}
