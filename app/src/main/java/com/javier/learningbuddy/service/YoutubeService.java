package com.javier.learningbuddy.service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.javier.learningbuddy.model.Page;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by javie on 5/18/2017.
 */

public interface YoutubeService {

    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(chain -> {

            Request request = chain.request()
                .newBuilder()
                .addHeader("X-Android-Package", "com.javier.learningbuddy")
                .addHeader("X-Android-Cert", "3C5527DBF5897C0BD29D0CDB71C9E1CE36DC35A8")
                .build();

            return chain.proceed(request);

        }).build();

    @GET("youtube/v3/search")
    Observable<Page> search(@QueryMap Map<String, Object> params);

    public static final Retrofit youtube = new Retrofit.Builder()
        .client(client)
        .baseUrl("https://www.googleapis.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
