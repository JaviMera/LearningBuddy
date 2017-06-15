package com.javier.learningbuddy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.javier.learningbuddy.adapters.SearchAdapter;
import com.javier.learningbuddy.adapters.SearchViewHolder;
import com.javier.learningbuddy.model.Item;
import com.javier.learningbuddy.model.Page;
import com.javier.learningbuddy.model.Thumbnail;
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
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

/**
 * Created by javie on 6/9/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    private Thumbnail lowRes = new Thumbnail("android.resource://com.javier.learningbuddy/" + R.mipmap.ic_launcher, 120, 90);
    private Thumbnail medRes = new Thumbnail("android.resource://com.javier.learningbuddy/" + R.mipmap.ic_launcher, 320, 180);
    private Thumbnail highRes = new Thumbnail("android.resource://com.javier.learningbuddy/" + R.mipmap.ic_launcher, 480, 360);

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
            .searchActivityModule(new SearchActivityModuleFake(InstrumentationRegistry.getContext()))
            .build();

        App app = (App) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

        app.setComponent(componentFake);
        componentFake.inject(this);
    }

    @Test
    public void deleteSearchIconClickClearsSearchEditText() throws Exception {

        // Arrange
        String searchText = "sup";

        // Act
        rule.launchActivity(new Intent());
        onView(withId(R.id.searchEditText)).perform(typeText(searchText));
        onView(withId(R.id.action_delete_text)).perform(click());

        // Assert
        onView(withId(R.id.searchEditText)).check(matches(withText("")));
    }

    @Test
    public void deleteQueryDisplaysNoResults() throws Exception {

        // Arrange
        String searchText = "I like turtles";
        when(this.presenter.getVideos(any(String.class))).thenReturn(Observable.just(new Page(new LinkedList<Item>() {
            {
                add(createItem("turtles", "1231231", "turtles", "I like turtles", "Zombie kid says I like turtles", "JaviMerca"));
            }
        })));

        // Act
        rule.launchActivity(new Intent());
        onView(withId(R.id.searchEditText)).perform(typeText(searchText));
        onView(withId(R.id.action_delete_text)).perform(click());

        // Assert
        int expectedItemCount = 0;
        onView(withId(R.id.searchResultsRecycler)).check(matches(new RecyclerItemCountMatcher(expectedItemCount)));
    }

    @Test
    public void validSearchReturnsResponseWithItems() throws Exception {

        // Arrange
        String searchText = "H";
        String title = "Harambe American Hero";
        String channelTitle = "Javi Merca";
        Thumbnail lowRes = new Thumbnail("android.resource://com.javier.learningbuddy/" + R.mipmap.ic_launcher, 120, 90);
        Thumbnail medRes = new Thumbnail("android.resource://com.javier.learningbuddy/" + R.mipmap.ic_launcher, 320, 180);
        Thumbnail highRes = new Thumbnail("android.resource://com.javier.learningbuddy/" + R.mipmap.ic_launcher, 480, 360);
        when(this.presenter.getVideos(any(String.class))).thenReturn(Observable.just(new Page(new LinkedList<Item>(){
            {
                add(new Item(
                    new VideoId("gorilla", "123456789"),
                    new VideoSnippet("publication", title, "amazing video about a gorilla being murdered", new Thumbnails(lowRes, medRes, highRes), channelTitle))
                );
                add(new Item(
                        new VideoId("gorilla", "123456789"),
                        new VideoSnippet("publication", title, "amazing video about a gorilla being murdered", new Thumbnails(lowRes, medRes, highRes), channelTitle))
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

    @Test
    public void NewResponseClearsRecyclerItems() throws Exception {

        // Arrange
        String firstQuery = "s"; // assume this will return 1 result
        String secondQuery = "dsajl"; // assume this will return 3 results
        when(this.presenter.getVideos(any(String.class))).thenReturn(Observable.just(new Page(new LinkedList<Item>(){
            {
                add(createItem("supkind", "supid", "suppublication", "suptitle", "supdescription", "supchannel"));
            }
        })));

        // Act
        rule.launchActivity(new Intent());
        onView(withId(R.id.searchEditText)).perform(typeText(firstQuery));

        when(this.presenter.getVideos(any(String.class))).thenReturn(Observable.just(new Page(new LinkedList<Item>(){
            {
                add(createItem("supkind", "supid", "suppublication", "suptitle", "supdescription", "supchannel"));
                add(createItem("supkind", "supid", "suppublication", "suptitle", "supdescription", "supchannel"));
                add(createItem("supkind", "supid", "suppublication", "suptitle", "supdescription", "supchannel"));
            }
        })));
        onView(withId(R.id.searchEditText)).perform(clearText());
        onView(withId(R.id.searchEditText)).perform(typeText(secondQuery));

        // Assert
        int expectedItemCount = 3;
        onView(withId(R.id.searchResultsRecycler)).check(matches(new RecyclerItemCountMatcher(expectedItemCount)));
    }

    private Item createItem(String videoKind, String videoId, String publication, String title, String description, String channel) {

        return new Item(
            new VideoId(videoKind, videoId),
            new VideoSnippet(publication, title, description, new Thumbnails(lowRes, medRes, highRes), channel));
    }

    private class RecyclerItemCountMatcher extends BaseMatcher {

        private int expectedCount;

        public RecyclerItemCountMatcher(int expectedCount) {

            this.expectedCount = expectedCount;
        }

        @Override
        public boolean matches(Object view) {

            RecyclerView recyclerView = (RecyclerView)view;
            SearchAdapter adapter = (SearchAdapter) recyclerView.getAdapter();

            return this.expectedCount == adapter.getItemCount();
        }

        @Override
        public void describeTo(Description description) {

            description.appendText("item count is " + this.expectedCount);
        }
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

    private class ThumbnailMatcher extends BaseMatcher{

        @Override
        public boolean matches(Object view) {

            RecyclerView recyclerView = (RecyclerView)view;
            SearchViewHolder viewHolder = (SearchViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
            Drawable drawable = viewHolder.getThumbnail().getDrawable();
            return drawable != null;
        }

        @Override
        public void describeTo(Description description) {

        }
    }
}