package com.javier.learningbuddy;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.javier.learningbuddy.adapters.SearchViewHolder;
import com.javier.learningbuddy.model.Item;
import com.javier.learningbuddy.model.Page;
import com.javier.learningbuddy.model.Thumbnails;
import com.javier.learningbuddy.model.VideoId;
import com.javier.learningbuddy.model.VideoSnippet;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import javax.inject.Inject;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.when;

/**
 * Created by javie on 6/9/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public ActivityTestRule<SearchActivity> rule = new ActivityTestRule<>(
        SearchActivity.class,
        false,
        false
    );

    @Inject
    SearchActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {

        SearchActivityComponentFake componentFake = DaggerSearchActivityComponentFake
            .builder()
            .searchActivityModule(new SearchActivityModuleFake())
            .build();

        App app = (App) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

        app.setComponent(componentFake);
        componentFake.inject(this);
    }

    @Test
    public void validSearchReturnsResponseWithItems() throws Exception {

        // Arrange
        String searchText = "Harambe";
        String title = "Harambe American Hero";
        String channelTitle = "Javi Merca";
        when(this.presenter.getVideos()).thenReturn(Observable.just(new Page(new LinkedList<Item>(){
            {
                add(new Item(
                    new VideoId("gorilla", "123456789"),
                    new VideoSnippet("publication", title, "amazing video about a gorilla being murdered", new Thumbnails(), channelTitle))
                );
            }
        })));

        // Act
        rule.launchActivity(new Intent());
        onView(withId(R.id.searchEditText)).perform(typeText(searchText));

        // Assert
        onView(withId(R.id.searchResultsRecycler)).check(matches(new TitleMatcher(title)));
        onView(withId(R.id.searchResultsRecycler)).check(matches(new ChannelTitleMatcher(channelTitle)));
    }

    private class TitleMatcher extends BaseMatcher {

        private String title;

        TitleMatcher(String title) {

            this.title = title;
        }

        @Override
        public boolean matches(Object view) {

            RecyclerView recyclerView = (RecyclerView)view;
            SearchViewHolder viewHolder = (SearchViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
            return viewHolder.getTitle().equals(this.title);
        }

        @Override
        public void describeTo(Description description) {

        }
    }

    private class ChannelTitleMatcher extends BaseMatcher {

        private final String channelTitle;

        ChannelTitleMatcher(String channelTitle) {

            this.channelTitle = channelTitle;
        }

        @Override
        public boolean matches(Object view) {

            RecyclerView recyclerView = (RecyclerView)view;
            SearchViewHolder viewHolder = (SearchViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
            return viewHolder.getChannelTitle().equals(this.channelTitle);
        }

        @Override
        public void describeTo(Description description) {

        }
    }
}