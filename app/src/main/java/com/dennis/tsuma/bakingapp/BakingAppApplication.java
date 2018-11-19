package com.dennis.tsuma.bakingapp;

import android.app.Application;
import android.content.Context;

public class BakingAppApplication extends Application {
    private static BakingAppApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static BakingAppApplication getApp() {
        return myApplication;
    }


}
