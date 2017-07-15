package com.javier.learningbuddy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javier.learningbuddy.R;
import com.javier.learningbuddy.model.Item;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by javie on 6/3/2017.
 */

public class SearchAdapter extends Adapterbase<SearchViewHolder, Item>{

    private List<Item> items;

    public SearchAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.search_item, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {

        Item currentItem = this.items.get(position);
        holder.setTitle(currentItem.snippet.title);
        holder.setChannelTitle(currentItem.snippet.channelTitle);
        holder.setThumbnail(currentItem.snippet.thumbnails.high.url);
    }

    @Override
    public int getItemCount() {

        return this.items.size();
    }

    @Override
    public void accept(@NonNull Item item) throws Exception {
        this.items.add(item);
        this.notifyItemInserted(this.items.size() - 1);
    }

    @Override
    public void clear() {

        int size = this.items.size();
        this.items.clear();
        this.notifyItemRangeRemoved(0, size);
    }

    public Item get(int i) {

        return this.items.get(i);
    }
}
