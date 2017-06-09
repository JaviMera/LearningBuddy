package com.javier.learningbuddy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.javier.learningbuddy.adapters.SearchAdapter;
import com.javier.learningbuddy.adapters.SearchViewHolder;
import com.javier.learningbuddy.model.Item;
import com.javier.learningbuddy.model.Page;
import com.javier.learningbuddy.model.Thumbnails;
import com.javier.learningbuddy.model.VideoId;
import com.javier.learningbuddy.model.VideoSnippet;

import junit.framework.Assert;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CustomMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import javax.inject.Inject;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Inject
    MainActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {

        MainActivityComponentFake componentFake = DaggerMainActivityComponentFake
            .builder()
            .mainActivityModule(new MainActivityModuleFake())
            .build();

        App app = (App) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

        app.setComponent(componentFake);
        componentFake.inject(this);
    }

    @Test
    public void ValidSearchReturnsResults() throws Exception {

        // Arrange
        String searchText = "Harambe";
        when(this.presenter.getVideos()).thenReturn(Observable.just(new Page(new LinkedList<Item>(){
            {
                add(new Item(
                    new VideoId("gorilla", "123456789"),
                    new VideoSnippet("publication", "Harambe American Hero", "amazing video about a gorilla being murdered", new Thumbnails()))
                );
            }
        })));

        // Act
        rule.launchActivity(new Intent());
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText(""));
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText(searchText));

        // Assert
        onView(withId(R.id.searchResultsRecycler)).check(matches(new ItemMatcher("Harambe American Hero")));
    }

    private class ItemMatcher extends BaseMatcher  {

        private String titleMatch;

        public ItemMatcher(String titleMatch) {

            this.titleMatch = titleMatch;
        }

        @Override
        public boolean matches(Object view) {

            RecyclerView recyclerView = (RecyclerView)view;
            SearchAdapter adapter = (SearchAdapter) recyclerView.getAdapter();
            Item firstItem = adapter.get(0);

            return firstItem.snippet.title.equals(this.titleMatch);
        }

        @Override
        public void describeTo(Description description) {

        }
    }
}
