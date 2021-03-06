package com.javier.learningbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.javier.learningbuddy.adapters.SearchAdapter;
import com.javier.learningbuddy.model.Page;

import org.w3c.dom.Text;

import java.util.LinkedList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
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

    @BindView(R.id.searchResultsRecycler)
    RecyclerView searchResultsRecycler;

    @Inject
    SearchActivityPresenter presenter;

    private SearchAdapter searchAdapter;
    private Page currentPage;

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_delete_text:

                this.searchEditText
                    .getText()
                    .clear();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App)getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Display the home button when the activity launches
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.searchAdapter = new SearchAdapter(new LinkedList<>());
        this.searchResultsRecycler.setAdapter(searchAdapter);
        this.searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRecycler.setHasFixedSize(true);
        this.searchResultsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = layoutManager.getItemCount();

                // When the user deletes the text query, don't activate the scrolling logic
                // since we don't want any results
                if(itemCount == 0)
                    return;

                if(itemCount == layoutManager.findLastCompletelyVisibleItemPosition() + 1) {

                    // When the user scrolls to the bottom, send the current pageToken
                    // in order to request the next page from our current set of results
                    presenter
                        .getVideos(searchEditText.getText().toString(), currentPage.nextPageToken)
                        .subscribeOn(Schedulers.io())
                        .map(response -> currentPage = response)
                        .flatMap(response -> Observable.fromIterable(response.items))
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(
                                searchAdapter,
                                error -> Log.e(TAG, error.getMessage())
                        );
                }
            }
        });

        // Subscription to handle empty queries after valid queries have been sent
        // First item will be skipped since it will always be an empty query when activity launches
        RxTextView.textChanges(this.searchEditText)
            .observeOn(AndroidSchedulers.mainThread())
            .skip(1)
            .filter(TextUtils::isEmpty)
            .subscribe(text -> searchAdapter.clear());

        // Subscription to handle valid queries
        // First item will be skipped since it will always be an empty query when activity launches
        RxTextView.textChanges(this.searchEditText)
            .observeOn(AndroidSchedulers.mainThread())
            .skip(1)
            .filter(text -> !TextUtils.isEmpty(text))
            .subscribe(text -> {

                //Clear the adapter on every new query
                searchAdapter.clear();

                // When the user types in a new query, always send a blank page token
                // since this behavior will always return the first page of results
                presenter
                    .getVideos(text.toString(), "")
                    .subscribeOn(Schedulers.io())
                    .map(response -> this.currentPage = response)
                    .flatMap(response -> Observable.fromIterable(response.items))
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
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
