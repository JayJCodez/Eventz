package com.example.optionsmenupractice

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.optionsmenupractice.main.activities.HomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testOpenDrawer() {
        // Open the drawer by clicking the menu icon
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())

        // Verify that the navigation view is displayed
        onView(withId(R.id.navigation)).check(matches(isDisplayed()))

        // Verify that specific items in the navigation view are displayed
        onView(withId(R.id.home)).check(matches(isDisplayed()))
        onView(withId(R.id.newevent)).check(matches(isDisplayed()))

    }

}
