package com.javier.learningbuddy.model;

/**
 * Created by javie on 5/18/2017.
 */

public class VideoSnippet {

    public String publishedAt;
    public String title;
    public String description;
    public Thumbnails thumbnails;
    public String channelTitle;

    public VideoSnippet(String publishedAt, String title, String description, Thumbnails thumbnails, String channelTitle) {

        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
        this.channelTitle = channelTitle;
    }
}
