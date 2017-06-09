package com.javier.learningbuddy.model;

import java.util.List;

/**
 * Created by javie on 5/18/2017.
 */

public class Page {

    public String kind;
    public String etag;
    public String nextPageToken;
    public String regionCode;
    public List<Item> items;
    public PageInfo pageInfo;

    public Page(List<Item> items) {

        this.items = items;
    }
}
