package com.javier.learningbuddy;

import android.support.v7.app.AppCompatActivity;

import com.javier.learningbuddy.model.Page;

import io.reactivex.Observable;

/**
 * Created by javie on 5/30/2017.
 */

public class MainPresenter implements MainActivityPresenter {

    private ActivityView view;

    @Override
    public void setView(ActivityView view) {

        this.view = view;
    }

    @Override
    public Observable<Page> getVideos() {
        return null;
    }

    @Override
    public String getName() {

        return "Real presenter";
    }
}
