package com.javier.learningbuddy;

import com.javier.learningbuddy.dagger.SearchActivityModule;

import org.mockito.Mockito;

/**
 * Created by javie on 5/30/2017.
 */

public class SearchActivityModuleFake extends SearchActivityModule {

    @Override
    public SearchActivityPresenter providePresenter() {

        return Mockito.mock(SearchPresenter.class);
    }
}
