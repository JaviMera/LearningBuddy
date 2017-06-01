package com.javier.learningbuddy.dagger;

/**
 * Created by javie on 5/30/2017.
 */

public interface ActivityComponent<T> {

    void inject(T view);
}
