package com.javier.learningbuddy;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.javier.learningbuddy.dagger.ActivityComponent;
import com.javier.learningbuddy.dagger.DaggerMainActivityComponent;
import com.javier.learningbuddy.dagger.MainActivityComponent;

/**
 * Created by javie on 5/30/2017.
 */

public class App extends Application {

    private ActivityComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mainComponent = DaggerMainActivityComponent.create();
    }

    public ActivityComponent getComponent() {

        return mainComponent;
    }

    @VisibleForTesting
    public void setComponent(ActivityComponent component) {

        this.mainComponent = component;
    }
}
