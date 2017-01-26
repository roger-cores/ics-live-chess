package com.frostox.livechess;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by roger on 11/4/2016.
 */
public class LiveChess extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
