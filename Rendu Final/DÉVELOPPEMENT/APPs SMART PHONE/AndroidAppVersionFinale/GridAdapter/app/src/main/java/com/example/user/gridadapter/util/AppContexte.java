package com.example.user.gridadapter.util;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by KDTM0398 on 19/05/2016.
 */
public class AppContexte {

    // Unique instance of AppContexte
    private static AppContexte instance = new AppContexte();

    // l'activité actuelle
    private Activity activityRunning;
    // Pour tester la visibilite de l'activity (Application en contexte)
    private boolean activityVisible;

    // Retourner l'unique instance
    public static AppContexte getInstance(){
        return instance;
    }
    // Getters et Setters
    public Activity getActivityRunning() {
        return activityRunning;
    }

    public void setActivityRunning(Activity actRunning) {
        activityRunning = actRunning;

        activityVisible = true;
    }

    public boolean isActivityVisible(){
        return activityVisible;
    }

    public void setActivityVisible(AppCompatActivity activity, boolean bl){
        if (activityRunning.equals(activity))
            activityVisible = bl;
    }
}
