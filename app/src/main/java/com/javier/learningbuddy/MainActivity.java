package com.javier.learningbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.javier.learningbuddy.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 5/18/2017.
 */

public class MainActivity extends AppCompatActivity implements ActivityView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchResultsRecycler)
    RecyclerView searchResultsRecycler;

    @Inject
    MainActivityPresenter presenter;
    private SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App)getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.setSupportActionBar(toolbar);
    }
}
