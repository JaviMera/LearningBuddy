package com.javier.learningbuddy.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.javier.learningbuddy.R;
import com.javier.learningbuddy.SearchActivity;
import com.javier.learningbuddy.SearchActivityPresenter;
import com.javier.learningbuddy.SearchPresenter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javie on 5/30/2017.
 */

@Module
public class SearchActivityModule {

    private final Context context;

    public SearchActivityModule(Context context) {

        this.context = context;
    }

    @Provides
    @Singleton
    Context providesContext() {

        return this.context;
    }

    @Provides
    @Singleton
    OkHttpClient providesClient() {

        return new OkHttpClient.Builder()
            .addInterceptor(chain -> {

                Request headers = chain.request()
                    .newBuilder()
                    .addHeader("X-Android-Package", "com.javier.learningbuddy")
                    .addHeader("X-Android-Cert", "3C5527DBF5897C0BD29D0CDB71C9E1CE36DC35A8")
                    .build();

                return chain.proceed(headers);
            })
            .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient client){

        return new Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.googleapis.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    @Singleton
    public SearchActivityPresenter providePresenter(Context context, Retrofit retrofit) {

        return new SearchPresenter(context, retrofit);
    }
}
