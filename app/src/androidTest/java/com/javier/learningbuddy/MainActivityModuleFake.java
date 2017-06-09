package com.javier.learningbuddy;

import com.javier.learningbuddy.dagger.MainActivityModule;

import org.mockito.Mockito;

/**
 * Created by javie on 5/30/2017.
 */

public class MainActivityModuleFake extends MainActivityModule {

    @Override
    public MainActivityPresenter providePresenter() {

        return Mockito.mock(MainPresenter.class);
    }
}
