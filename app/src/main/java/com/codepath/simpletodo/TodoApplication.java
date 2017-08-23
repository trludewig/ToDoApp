package com.codepath.simpletodo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by tludewig on 8/22/17.
 */

public class TodoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
