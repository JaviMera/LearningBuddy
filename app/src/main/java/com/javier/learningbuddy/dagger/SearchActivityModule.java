package com.javier.learningbuddy.dagger;

import com.javier.learningbuddy.SearchActivityPresenter;
import com.javier.learningbuddy.SearchPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javie on 5/30/2017.
 */

@Module
public class SearchActivityModule {

    @Provides
    @Singleton
    public SearchActivityPresenter providePresenter() {

        return new SearchPresenter();
    }
}
