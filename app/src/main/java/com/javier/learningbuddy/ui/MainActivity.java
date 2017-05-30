package com.javier.learningbuddy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;

import com.javier.learningbuddy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 5/18/2017.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.setSupportActionBar(toolbar);
    }
}
