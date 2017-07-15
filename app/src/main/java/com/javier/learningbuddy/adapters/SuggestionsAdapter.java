package com.javier.learningbuddy.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.javier.learningbuddy.FragmentSuggestions;
import com.javier.learningbuddy.R;
import com.javier.learningbuddy.SearchActivity;
import com.javier.learningbuddy.model.Suggestion;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by javie on 6/17/2017.
 */

public class SuggestionsAdapter extends Adapterbase<SuggestionViewHolder, String> {

    private FragmentSuggestions parent;
    private List<Suggestion> items;

    public SuggestionsAdapter(FragmentSuggestions parent, List<Suggestion> items) {
        this.parent = parent;
        this.items = items;
    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
            .from(this.parent.getContext())
            .inflate(R.layout.suggestion_item, parent, false);

        return new SuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder holder, int position) {

        Suggestion suggestion = this.items.get(position);
        RxView.clicks(holder.itemView)
                .map(response -> suggestion)
                .subscribe(this.parent);

        holder.suggestionTextView.setText(suggestion.getText());
    }

    @Override
    public int getItemCount() {

        return this.items.size();
    }

    @Override
    public void clear() {

        int size = this.items.size();
        this.items.clear();
        this.notifyItemRangeRemoved(0, size);
    }

    @Override
    public void accept(@NonNull String s) throws Exception {

        this.items.add(new Suggestion(s));
        this.notifyItemInserted(this.items.size() - 1);
    }
}
