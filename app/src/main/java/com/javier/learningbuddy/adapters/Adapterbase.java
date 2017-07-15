package com.javier.learningbuddy.adapters;

import android.support.v7.widget.RecyclerView;

import com.javier.learningbuddy.model.Item;

import io.reactivex.functions.Consumer;

/**
 * Created by javie on 6/26/2017.
 */

public abstract class Adapterbase<TViewHolder extends RecyclerView.ViewHolder, TConsumer>
        extends RecyclerView.Adapter<TViewHolder>
        implements Consumer<TConsumer> {

    public abstract void clear();
}
