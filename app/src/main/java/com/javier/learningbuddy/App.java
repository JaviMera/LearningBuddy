package com.javier.learningbuddy;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.javier.learningbuddy.dagger.DaggerMainActivityComponent;
import com.javier.learningbuddy.dagger.MainActivityComponent;
import com.javier.learningbuddy.dagger.MainActivityModule;

/**
 * Created by javie on 5/30/2017.
 */

public class App extends Application {

    private MainActivityComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mainComponent = DaggerMainActivityComponent.builder()
            .mainActivityModule(new MainActivityModule())
            .build();
    }

    public MainActivityComponent getComponent() {

        return mainComponent;
    }

    @VisibleForTesting
    public void setComponent(MainActivityComponent component) {

        this.mainComponent = component;
    }
}
