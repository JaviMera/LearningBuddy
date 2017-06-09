package com.javier.learningbuddy.model;

/**
 * Created by javie on 5/18/2017.
 */

public class Item {

    public VideoId id;
    public VideoSnippet snippet;

    public Item(VideoId id, VideoSnippet snippet) {

        this.id = id;
        this.snippet = snippet;
    }
}
