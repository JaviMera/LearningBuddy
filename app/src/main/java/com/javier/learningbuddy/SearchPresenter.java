package com.javier.learningbuddy;

import android.content.Context;

import com.javier.learningbuddy.api.YoutubeService;
import com.javier.learningbuddy.model.Page;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by javie on 5/30/2017.
 */

public class SearchPresenter implements SearchActivityPresenter {

    private ActivityView view;
    private YoutubeService youtubeService;
    private Map<String, Object> youtubeParams;

    @Inject
    public SearchPresenter(Context context, Retrofit retrofit) {

        this.youtubeService = retrofit.create(YoutubeService.class);
        this.youtubeParams = new HashMap<>();
        this.youtubeParams.put("key", context.getString(R.string.youtube_key));
        this.youtubeParams.put("part", "snippet");
    }

    @Override
    public void setView(ActivityView view) {

        this.view = view;
    }

    @Override
    public Observable<Page> getVideos(String query) {

        this.youtubeParams.put("q", query);

        return this
            .youtubeService
            .search(this.youtubeParams);
    }
}
