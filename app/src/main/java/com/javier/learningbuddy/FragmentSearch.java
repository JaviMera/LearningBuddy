package com.javier.learningbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javier.learningbuddy.adapters.SearchAdapter;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 6/20/2017.
 */

public class FragmentSearch extends FragmentBase {

    public static FragmentSearch newInstance() {

        return new FragmentSearch();
    }

    @BindView(R.id.searchRecycler)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater
            .from(this.parent)
            .inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        this.adapter = new SearchAdapter(new LinkedList<>());
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setHasFixedSize(true);

        return view;
    }
}
