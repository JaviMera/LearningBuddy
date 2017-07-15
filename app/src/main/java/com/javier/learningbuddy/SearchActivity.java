package com.javier.learningbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.javier.learningbuddy.adapters.Adapterbase;
import com.javier.learningbuddy.adapters.SearchAdapter;
import com.javier.learningbuddy.adapters.SuggestionsAdapter;
import com.javier.learningbuddy.model.Item;
import com.javier.learningbuddy.model.Page;
import com.javier.learningbuddy.model.Suggestion;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by javie on 6/9/2017.
 */

public class SearchActivity extends AppCompatActivity{

    public static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.container)
    FrameLayout suggestionsContainer;

    @BindView(R.id.searchContainer)
    FrameLayout searchContainer;

    @Inject
    SearchActivityPresenter presenter;

    private Page currentPage;
    private FragmentBase suggestionsFragment;
    private FragmentBase searchFragment;

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

        this.suggestionsFragment = FragmentSuggestions.newInstance();
        this.searchFragment = FragmentSearch.newInstance();

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
            .replace(R.id.container, this.suggestionsFragment)
            .replace(R.id.searchContainer, this.searchFragment)
            .commit();

//        this.searchResultsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int itemCount = layoutManager.getItemCount();
//
//                // When the user deletes the text query, don't activate the scrolling logic
//                // since we don't want any results
//                if(itemCount == 0)
//                    return;
//
//                if(itemCount == layoutManager.findLastCompletelyVisibleItemPosition() + 1) {
//
//                    // When the user scrolls to the bottom, send the current pageToken
//                    // in order to request the next page from our current set of results
//                    presenter
//                        .getVideos(searchEditText.getText().toString(), currentPage.nextPageToken)
//                        .subscribeOn(Schedulers.io())
//                        .map(response -> currentPage = response)
//                        .flatMap(response -> Observable.fromIterable(response.items))
//                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
//                        .subscribe(
//                                searchAdapter,
//                                error -> Log.e(TAG, error.getMessage())
//                        );
//                }
//            }
//        });

        RxTextView.textChanges(this.searchEditText)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(text -> {

                this.searchFragment
                    .getAdapter()
                    .clear();

                this.searchContainer.setVisibility(View.GONE);

                this.suggestionsFragment
                    .getAdapter()
                    .clear();

                this.suggestionsContainer.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(text))
                    return;

                presenter.getSuggestions(text.toString())
                    .subscribeOn(Schedulers.io())
                    .map(response -> response.replace("[",""))
                    .map(response -> response.replace("]",""))
                    .map(response -> response.replaceAll("\"",""))
                    .map(response -> response.split(","))
                    .flatMap(Observable::fromArray)
                    .skip(1) // Skip first item since first and second items are repeated
                    .subscribe(this.suggestionsFragment.getAdapter());

            });
    }

    public void getVideos(Suggestion suggestion) {

        this.suggestionsFragment
            .getAdapter()
            .clear();
        this.suggestionsContainer.setVisibility(View.GONE);

        this.searchFragment
            .getAdapter()
            .clear();

        this.searchContainer.setVisibility(View.VISIBLE);

        presenter
            .getVideos(suggestion.getText(), "")
            .subscribeOn(Schedulers.io())
            .map(response -> this.currentPage = response)
            .flatMap(response -> Observable.fromIterable(response.items))
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe(
                this.searchFragment.getAdapter(),
                error -> Log.e(TAG, error.getMessage())
            );
    }
}
