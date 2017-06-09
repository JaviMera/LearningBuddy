package com.javier.learningbuddy.dagger;

import com.javier.learningbuddy.ActivityView;
import com.javier.learningbuddy.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javie on 5/30/2017.
 */

@Singleton
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity view);
}
