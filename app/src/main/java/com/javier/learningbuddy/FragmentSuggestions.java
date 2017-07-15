package com.javier.learningbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javier.learningbuddy.adapters.SuggestionsAdapter;
import com.javier.learningbuddy.model.Suggestion;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by javie on 6/20/2017.
 */

public class FragmentSuggestions extends FragmentBase implements Action1<Suggestion> {

    public static FragmentSuggestions newInstance() {

        return new FragmentSuggestions();
    }

    @BindView(R.id.suggestionRecycler)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater
            .from(getContext())
            .inflate(R.layout.fragment_suggestion, container, false);

        ButterKnife.bind(this, view);

        this.adapter = new SuggestionsAdapter(this, new LinkedList<>());
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void call(Suggestion suggestion) {

        this.parent.getVideos(suggestion);
    }
}
