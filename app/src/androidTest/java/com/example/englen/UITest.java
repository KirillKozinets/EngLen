package com.example.englen;


import android.content.Intent;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.example.englen.view.Activitis.MainActivity;
import com.example.englen.view.Fragments.LearnNewWords;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class UITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mActivityRule.launchActivity(new Intent());
    }

    @Test
    public void coolTextDisplayed() {
        LearnNewWords fragment = new LearnNewWords();

        Bundle bundle = new Bundle();
        bundle.putBoolean("isNew",true);
        fragment.setArguments(bundle);

        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.Fr, fragment).commit();

        for(int i = 0 ; i < 250 ; i++) {
            onView(withId(R.id.b1)).perform(click());
            onView(withId(R.id.b6)).perform(click());
            onView(withId(R.id.b6)).perform(click());
        }
    }
}