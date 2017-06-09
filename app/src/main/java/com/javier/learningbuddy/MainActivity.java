package com.javier.learningbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.javier.learningbuddy.R;
import com.javier.learningbuddy.adapters.SearchAdapter;
import com.javier.learningbuddy.model.Page;

import org.mockito.internal.matchers.And;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by javie on 5/18/2017.
 */

public class MainActivity extends AppCompatActivity implements ActivityView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchResultsRecycler)
    RecyclerView searchResultsRecycler;

    @Inject
    MainActivityPresenter presenter;

    private SearchView searchView;
    private SearchAdapter searchAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        RxSearchView.queryTextChanges(searchView)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(text -> {

                presenter
                    .getVideos()
                    .flatMap(response -> Observable.fromIterable(response.items))
                    .subscribe(
                        searchAdapter,
                        error -> Log.e(TAG, error.getMessage())
                    );
            },
            error -> {
                Log.e(TAG, error.getMessage());
            });
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App)getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.setSupportActionBar(toolbar);

        this.searchAdapter = new SearchAdapter(new LinkedList<>());
        this.searchResultsRecycler.setAdapter(searchAdapter);
        this.searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRecycler.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
