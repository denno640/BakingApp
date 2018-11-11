package com.dennis.tsuma.bakingapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class BakingAppApplication extends Application {
    private static BakingAppApplication myApplication;
    private Picasso picassoWithCache;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;


        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 15 * 1024 * 1024);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().cache(cache);
        picassoWithCache = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(okHttpClientBuilder.build()))
                .build();


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static BakingAppApplication getApp() {
        return myApplication;
    }

    public Picasso getPicassoWithCache() {
        return picassoWithCache;
    }

}
