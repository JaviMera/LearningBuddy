package com.javier.learningbuddy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by javie on 5/18/2017.
 */

class Thumbnails {

    @SerializedName("default")
    public Thumbnail low;

    public Thumbnail medium;
    public Thumbnail high;
}