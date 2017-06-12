package com.javier.learningbuddy.api;

import com.javier.learningbuddy.model.Page;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by javie on 6/11/2017.
 */

public interface YoutubeService {

    @GET("youtube/v3/search")
    Observable<Page> search(@QueryMap Map<String, Object> params);
}
