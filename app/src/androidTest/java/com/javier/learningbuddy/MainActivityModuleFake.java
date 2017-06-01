package com.javier.learningbuddy;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javie on 5/30/2017.
 */

@Module
public class MainActivityModuleFake {

    @Provides
    @Singleton
    public MainActivityPresenter providesPresenter() {

        return Mockito.mock(MainActivityPresenter.class);
    }
}
