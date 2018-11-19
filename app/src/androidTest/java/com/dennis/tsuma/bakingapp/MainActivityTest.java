package com.dennis.tsuma.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dennis.tsuma.bakingapp.ui.StepDetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Phew! i don't know what is going on with this test class. please assist with any material
 * that i can use to advance my knowledge on Espresso
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule2
            = new IntentsTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;

    @Test
    public void recyclerViewDisplaysDataOnCreateTest() {
        onView(withId(R.id.recipesRvItem));
    }

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        idlingResource = mActivityTestRule2.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(idlingResource);
    }


    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(
                new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void checkIfIntentStarts_RecipeDetailFragment() {
        //Scroll to the first item in the recycler view and perform click action
        onView(ViewMatchers.withId(R.id.recipesRvItem))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Check to see if the intent launches RecipeDetailFragment
        intended(hasComponent(StepDetailActivity.class.getName()));
    }


    @Test
    public void findRecipeName_AtPosition() {
        onView(withId(R.id.recipesRvItem)).perform(RecyclerViewActions
                .scrollToPosition(2));
        onView(withText("Yellow Cake")).check(matches(isDisplayed()));

        onView(withId(R.id.recipesRvItem)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));
    }


    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

}
