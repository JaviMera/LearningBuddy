package com.javier.learningbuddy;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(
        MainActivity.class,
        false,
        false
    );

    @Inject
    SearchActivityPresenter presenter;

    @Test
    public void searchIconLaunchesSearchActivity() throws Exception {

        // Arrange
        Intent intent = new Intent();

        // Act
        rule.launchActivity(intent);
        onView(withId(R.id.action_search)).perform(click());

        // Assert that the search activity will be launched showing / not showing the following views
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.action_delete_text)).check(matches(isDisplayed()));
        onView(withId(R.id.searchResultsRecycler)).check(matches(isDisplayed()));
    }
}
