package com.javier.learningbuddy;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.javier.learningbuddy.dagger.DaggerSearchActivityComponent;
import com.javier.learningbuddy.dagger.SearchActivityComponent;
import com.javier.learningbuddy.dagger.SearchActivityModule;

/**
 * Created by javie on 5/30/2017.
 */

public class App extends Application {

    private SearchActivityComponent searchComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.searchComponent = DaggerSearchActivityComponent.builder()
            .searchActivityModule(new SearchActivityModule())
            .build();
    }

    public SearchActivityComponent getComponent() {

        return searchComponent;
    }

    @VisibleForTesting
    public void setComponent(SearchActivityComponent component) {

        this.searchComponent = component;
    }
}
