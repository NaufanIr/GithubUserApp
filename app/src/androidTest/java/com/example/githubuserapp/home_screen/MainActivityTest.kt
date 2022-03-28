package com.example.githubuserapp.home_screen

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.githubuserapp.R
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun darkModeToogleTest(){
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))

        onView(withId(R.id.item_theme)).perform(click())
        onView(withId(R.id.item_theme)).perform(click())

        onView(withId(R.id.item_search)).perform(click())

        pressBack()

        onView(withId(R.id.item_favorite)).perform(click())
        onView(withId(R.id.rv_fav_users)).check(matches(isDisplayed()))
    }
}
