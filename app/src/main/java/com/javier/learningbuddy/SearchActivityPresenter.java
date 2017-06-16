package com.javier.learningbuddy;

import android.support.v7.app.AppCompatActivity;

import com.javier.learningbuddy.model.Page;

import io.reactivex.Observable;

/**
 * Created by javie on 5/30/2017.
 */

public interface SearchActivityPresenter {

    void setView(ActivityView view);
    Observable<Page> getVideos(String query, String pageToken);
}
