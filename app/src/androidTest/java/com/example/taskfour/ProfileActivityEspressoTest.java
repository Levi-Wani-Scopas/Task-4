package com.example.taskfour;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;

@RunWith(AndroidJUnit4.class)
public class ProfileActivityEspressoTest {

    @Before
    public void setUp() {
        // This will launch the ProfileActivity
        ActivityScenario.launch(ProfileActivity.class);
    }

    @Test
    public void testUpdateProfileWithValidName() {
        String name = "New Name";

        // Type text in the profile name
        onView(withId(R.id.profileName)).perform(typeText(name));

        // Click the update profile button
        onView(withId(R.id.btnUpdateProfile)).perform(click());

        // Verify that the Toast message for "Profile Updated" is shown
        onView(withText("Profile Updated")).check(matches(isDisplayed()));
    }

    @Test
    public void testUpdateProfileWithoutImage() {
        String name = "New Name Without Image";

        // Type text in the profile name
        onView(withId(R.id.profileName)).perform(typeText(name));

        // Click the update profile button
        onView(withId(R.id.btnUpdateProfile)).perform(click());

        // Verify that the profile was updated without a new image
        onView(withText("Profile Updated")).check(matches(isDisplayed()));
    }

    @Test
    public void testSelectImage() {
        // Simulate selecting a profile image
        onView(withId(R.id.profileImage)).perform(click());

        // Verify that an image selection intent is triggered
        onView(withText("Choose Image")).check(matches(isDisplayed()));
    }

    @Test
    public void testLogout() {
        // Click on the logout button
        onView(withId(R.id.btnLogout)).perform(click());

        // Verify that the user is redirected to the LoginActivity
        onView(withText("Login")).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButton() {
        // Click on the back button
        onView(withId(R.id.btnBack)).perform(click());

        // Verify that the MainActivity is launched
        onView(withText("Main Activity")).check(matches(isDisplayed()));
    }
}

