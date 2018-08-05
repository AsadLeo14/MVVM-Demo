package com.example.user.mvvmdemo.util;

import android.app.Application;
import android.support.annotation.UiThread;

import com.example.user.mvvmdemo.di.ApplicationComponent;
import com.example.user.mvvmdemo.di.DaggerApplicationComponent;

public class CustomApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @UiThread
    public ApplicationComponent getApplicationComponent()
    {
        if(mApplicationComponent == null)
        {
            mApplicationComponent = DaggerApplicationComponent.builder()  // Component Builder Build After Project Compile
                    .application(this)
                    .build();
        }
        return mApplicationComponent;
    }
}
