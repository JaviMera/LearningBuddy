package com.javier.learningbuddy;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.javier.learningbuddy.api.SuggestionService;
import com.javier.learningbuddy.api.YoutubeService;
import com.javier.learningbuddy.model.Page;
import com.javier.learningbuddy.model.Suggestion;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by javie on 5/30/2017.
 */

public class SearchPresenter implements SearchActivityPresenter {

    private ActivityView view;
    private YoutubeService youtubeService;
    private SuggestionService suggestionService;
    private Map<String, Object> youtubeParams;

    @Inject
    public SearchPresenter(Context context, Retrofit retrofit) {

        this.youtubeService = retrofit.create(YoutubeService.class);
        this.suggestionService = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://suggestqueries.google.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(SuggestionService.class);

        this.youtubeParams = new HashMap<>();
        this.youtubeParams.put("key", context.getString(R.string.youtube_key));
        this.youtubeParams.put("part", "snippet");
    }

    @Override
    public void setView(ActivityView view) {

        this.view = view;
    }

    @Override
    public Observable<Page> getVideos(String query, String pageToken) {

        this.youtubeParams.put("q", query);
        this.youtubeParams.put("pageToken", pageToken);

        return this
            .youtubeService
            .search(this.youtubeParams);
    }

    @Override
    public Observable<String> getSuggestions(String query) {

        return this.suggestionService.getSuggestions(query);
    }
}
