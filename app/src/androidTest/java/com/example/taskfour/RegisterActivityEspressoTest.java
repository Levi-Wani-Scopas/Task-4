package com.example.taskfour;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityEspressoTest {

    @Before
    public void setUp() {
        // Launch the RegisterActivity
        ActivityScenario.launch(RegisterActivity.class);
    }

    @Test
    public void testRegisterUser_Success() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "password123";

        // Type text into the registration form fields
        onView(withId(R.id.nameRegister)).perform(typeText(name));
        onView(withId(R.id.emailRegister)).perform(typeText(email));
        onView(withId(R.id.passwordRegister)).perform(typeText(password));

        // Click the Register button
        onView(withId(R.id.btnRegister)).perform(click());

        // Verify that the success toast message is displayed
        onView(withText("Registration Successful! Now Login")).check(matches(isDisplayed()));

        // Verify that the user is redirected to the LoginActivity
        onView(withText("Login")).check(matches(isDisplayed()));
    }

    @Test
    public void testGoToLogin() {
        // Click on the "Go to Login" link
        onView(withId(R.id.goToLogin)).perform(click());

        // Verify that the LoginActivity is displayed
        onView(withText("Login")).check(matches(isDisplayed()));
    }
}
