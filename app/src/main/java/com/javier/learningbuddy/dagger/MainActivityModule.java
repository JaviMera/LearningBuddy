package com.javier.learningbuddy.dagger;

import com.javier.learningbuddy.MainActivityPresenter;
import com.javier.learningbuddy.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javie on 5/30/2017.
 */

@Module
public class MainActivityModule {

    @Provides
    @Singleton
    public MainActivityPresenter providePresenter() {

        return new MainPresenter();
    }
}
