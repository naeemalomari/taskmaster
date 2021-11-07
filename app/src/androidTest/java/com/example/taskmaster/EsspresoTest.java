package com.example.taskmaster;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import androidx.test.espresso.contrib.RecyclerViewActions;


import static androidx.test.espresso.action.ViewActions.click;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class EsspresoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void MainActivity(){
       onView(withId(R.id.button2)).check(matches(withText("ALL TASKS")));
    }

    @Test
    public void settingsActivity(){
        onView(withId(R.id.usernameBtn)).perform(click());
        onView(withId(R.id.editTextTextPersonName)).perform(typeText("Naeem"),closeSoftKeyboard());
    }
    @Test
    public void addTask(){
        onView(withId(R.id.addTask)).perform(click());
        onView(withId(R.id.titleInput)).perform(typeText("Add task"),closeSoftKeyboard());
        onView(withId(R.id.bodyInput)).perform(typeText("body task"),closeSoftKeyboard());
        onView(withId(R.id.stateInput)).perform(typeText("your state"), closeSoftKeyboard());
        onView(withId(R.id.submitTaskInfo)).perform(click());

    }
    @Test
    public void openTaskDetail() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.recylerViewId)).check(matches(isDisplayed()));
        Thread.sleep(5000);

        onView(withId(R.id.recylerViewId)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.textView4)).check(matches(withText("LinkedList")));
        onView(withId(R.id.textView5)).check(matches(withText("Head")));
        onView(withId(R.id.state2)).check(matches(withText("New")));
    }
}
