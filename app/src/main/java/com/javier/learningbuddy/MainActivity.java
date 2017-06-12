package com.javier.learningbuddy;

import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.view.RxMenuItem;
import com.javier.learningbuddy.R;
import com.javier.learningbuddy.adapters.SearchAdapter;
import com.javier.learningbuddy.model.Page;
import com.squareup.picasso.Picasso;

import org.mockito.internal.matchers.And;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by javie on 5/18/2017.
 */

public class MainActivity extends AppCompatActivity implements ActivityView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        RxMenuItem.clicks(menuItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(aVoid -> {

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
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
