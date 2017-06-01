package com.javier.learningbuddy;

import com.javier.learningbuddy.dagger.ActivityComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javie on 5/30/2017.
 */

@Singleton
@Component(modules = MainActivityModuleFake.class)
public interface MainActivityComponentFake extends ActivityComponent<MainActivityUITest>{

    void inject(MainActivityUITest test);
}
