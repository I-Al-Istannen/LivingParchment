package me.ialistannen.livingparchment.feature.mainscreen;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.ialistannen.livingparchment.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
// Not quite seeing the advantage yet. Which features to isolate and test?
public class AddViewExistsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addViewExistsTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.go_to_add_button), isDisplayed())
        );
        appCompatButton.perform(click());

        ViewInteraction button = onView(withId(R.id.lookup_button));
        button.check(matches(isDisplayed()));

        ViewInteraction editText = onView(withId(R.id.isbn_input_field));
        editText.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.scan_barcode), withContentDescription("Scan Barcode"))
        );
        textView.check(matches(isDisplayed()));

        ViewInteraction spinner = onView(withId(R.id.location_spinner));
        spinner.check(matches(isDisplayed()));

    }
}
