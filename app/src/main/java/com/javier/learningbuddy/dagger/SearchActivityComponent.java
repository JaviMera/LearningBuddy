package com.javier.learningbuddy.dagger;

import com.javier.learningbuddy.MainActivity;
import com.javier.learningbuddy.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javie on 5/30/2017.
 */

@Singleton
@Component(modules = SearchActivityModule.class)
public interface SearchActivityComponent {

    void inject(SearchActivity view);
}
