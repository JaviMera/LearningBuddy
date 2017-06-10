package com.javier.learningbuddy;

import android.app.SearchableInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.javier.learningbuddy.adapters.SearchAdapter;

import java.util.LinkedList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by javie on 6/9/2017.
 */

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.deleteSearchText)
    ImageView deleteTextImage;

    @BindView(R.id.searchResultsRecycler)
    RecyclerView searchResultsRecycler;

    @Inject
    SearchActivityPresenter presenter;

    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App)getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Display the home button when the activity launches
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Hide the delete icon when the activity launches since we only want to display it
        // when the user types text in the search view
        this.deleteTextImage.setVisibility(View.GONE);

        this.searchAdapter = new SearchAdapter(new LinkedList<>());
        this.searchResultsRecycler.setAdapter(searchAdapter);
        this.searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRecycler.setHasFixedSize(true);

        RxTextView.textChanges(this.searchEditText)
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
    }
}
