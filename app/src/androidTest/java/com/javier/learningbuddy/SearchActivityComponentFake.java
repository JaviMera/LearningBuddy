package com.javier.learningbuddy;

import com.javier.learningbuddy.dagger.SearchActivityComponent;
import com.javier.learningbuddy.dagger.SearchActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javie on 5/30/2017.
 */

@Singleton
@Component(modules = SearchActivityModule.class)
public interface SearchActivityComponentFake extends SearchActivityComponent {

    void inject(SearchActivityTest test);
}
