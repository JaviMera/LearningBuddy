package com.javier.learningbuddy;

import com.javier.learningbuddy.model.Page;

import io.reactivex.Observable;

/**
 * Created by javie on 5/30/2017.
 */

public class SearchPresenter implements SearchActivityPresenter {

    private ActivityView view;

    @Override
    public void setView(ActivityView view) {

        this.view = view;
    }

    @Override
    public Observable<Page> getVideos() {
        return null;
    }
}
