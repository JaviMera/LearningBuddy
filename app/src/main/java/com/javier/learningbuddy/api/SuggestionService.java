package com.javier.learningbuddy.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by javie on 6/17/2017.
 */

public interface SuggestionService {

    @GET("complete/search?client=firefox")
    Observable<String> getSuggestions(@Query("q") String query);
}
