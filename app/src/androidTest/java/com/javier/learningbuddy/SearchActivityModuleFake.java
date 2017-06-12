package com.javier.learningbuddy;

import android.content.Context;

import com.javier.learningbuddy.dagger.SearchActivityModule;

import org.mockito.Mockito;

import retrofit2.Retrofit;

/**
 * Created by javie on 5/30/2017.
 */

public class SearchActivityModuleFake extends SearchActivityModule {

    public SearchActivityModuleFake(Context context) {
        super(context);
    }

    @Override
    public SearchActivityPresenter providePresenter(Context context, Retrofit retrofit) {

        return Mockito.mock(SearchPresenter.class);
    }
}
