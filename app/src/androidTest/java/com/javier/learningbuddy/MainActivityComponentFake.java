package com.javier.learningbuddy;

import com.javier.learningbuddy.dagger.MainActivityComponent;
import com.javier.learningbuddy.dagger.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javie on 5/30/2017.
 */

@Singleton
@Component(modules = MainActivityModule.class)
public interface MainActivityComponentFake extends MainActivityComponent{

    void inject(MainActivityUITest test);
}
