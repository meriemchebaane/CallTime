package com.example.user.calltime2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by chebaane on 04/11/2017.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View decorView = getWindow().getDecorView();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // thread for splash screen running
        Thread logoTimer = new Thread() {
            public void run() {
                try{
                    // Temporisation affichage Splash screen
                    sleep(2000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    // Chargement de l'ecran principal
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }

                finish();
            }
        };

        logoTimer.start();
    }

}
