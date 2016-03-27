package com.photon.andrewclawson.photoncontroller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    Manager photonManager = new Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        RedLEDButton = (Button) findViewById(R.id.redLEDButton);
        BlueLEDButton = (Button) findViewById(R.id.blueLEDButton);
        ResetLEDButton = (Button) findViewById(R.id.resetLEDButton);
        MainMenuButton = (Button) findViewById(R.id.mainMenuButton);

        MultipleLEDCheckbox = (CheckBox) findViewById(R.id.multipleLEDCheckbox);

    }

    public void greenButtonPressed(){
        new LEDControllerTask("green").execute();
    }

    public void redButtonPressed(){
        new LEDControllerTask("red").execute();
    }

    public void blueButtonPressed(){
        new LEDControllerTask("blue").execute();
    }

    public void resetLEDButtonPressed(){
        new LEDControllerTask("none").execute();
    }

    public class LEDControllerTask extends AsyncTask<Void, Void, Void> {
        public ParticleCloud cloud;
        public ParticleDevice loadDevice;
        public String color;
        public LEDControllerTask(String lightColor){
            color = lightColor;
        }
        protected Void doInBackground(Void... voids){
            try{
                ParticleCloudSDK.getCloud().logIn(photonManager.getEmail(),photonManager.getPassword());
                boolean IsLoggedIn = ParticleCloudSDK.getCloud().isLoggedIn();
                loadDevice = ParticleCloudSDK.getCloud().getDevice(photonManager.getDeviceName());
                //particleDevice = particleCloud.getDevice("aclawson");
                int resultCode = loadDevice.callFunction("ToggleLED", Arrays.asList(color));
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }


    }
}
