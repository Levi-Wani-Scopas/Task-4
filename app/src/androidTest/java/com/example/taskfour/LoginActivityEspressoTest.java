package com.example.taskfour;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.assertion.ViewAssertions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {

    @Before
    public void setUp() {
        // This will launch the LoginActivity
        ActivityScenario.launch(LoginActivity.class);
    }

    @Test
    public void testLoginWithValidCredentials() {
        String email = "test@example.com";
        String password = "password123";

        // Type text in the email and password fields
        onView(withId(R.id.emailLogin)).perform(typeText(email));
        onView(withId(R.id.passwordLogin)).perform(typeText(password));

        // Simulate login button click
        onView(withId(R.id.btnLogin)).perform(click());

        // Verify that the login succeeds (UI-related behavior)
        // You can check for a specific message or behavior after login.
        onView(withText("Login Successful!")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginWithEmptyFields() {
        // Try to login with empty fields
        onView(withId(R.id.emailLogin)).perform(typeText(""));
        onView(withId(R.id.passwordLogin)).perform(typeText(""));

        // Simulate login button click
        onView(withId(R.id.btnLogin)).perform(click());

        // Verify that an error message appears for empty fields
        onView(withText("Please fill in all fields")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRedirectToRegisterActivity() {
        // Check that clicking on "Go to Register" navigates to RegisterActivity
        onView(withId(R.id.goToRegister)).perform(click());

        // Verify that RegisterActivity is launched
        onView(withText("Register")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
