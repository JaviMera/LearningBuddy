package com.javier.learningbuddy;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

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

        MainActivityComponentFake componentFake = DaggerMainActivityComponentFake.create();

        App app = (App) InstrumentationRegistry
            .getInstrumentation()
            .getTargetContext()
            .getApplicationContext();

        app.setComponent(componentFake);
        componentFake.inject(this);
    }

    @Test
    public void dummyTest() throws Exception {

        when(this.presenter.getName()).thenReturn("Fake presenter");

        rule.launchActivity(new Intent());
    }
}
