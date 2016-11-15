package com.artisans.code.movimento1euro;

import android.content.ComponentName;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.artisans.code.movimento1euro.menus.LoginActivity;
import com.artisans.code.movimento1euro.menus.MainMenu;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.not;


/**
 * Created by Antonio on 13-11-2016.
 */

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    @Rule
    public IntentsTestRule<LoginActivity> mLoginActivityTestRule =
            new IntentsTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void clickSignInButton_SuccessfulLogin() throws Exception {
        enterCredentials("diogo@cenas.pt", "123");

        Intents.intended(hasComponent(new ComponentName(getTargetContext(), MainMenu.class)));
    }
    @Test
    public void travisIntegrationTest() throws Exception {
        Assert.assertEquals(0,0);
    }

    @Test
    public void clickSignInButton_FailedLogin() throws Exception {
        enterCredentials("ThisIsAFailedEmailAddress@yahoo.pt", "password");

        onView(withText(startsWith("Failed Login"))).
                inRoot(withDecorView(
                        not(is(mLoginActivityTestRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    private void enterCredentials(String email, String passowrd) {
        onView(withId(R.id.input_email))
                .perform(clearText(), typeText(email));
        onView((withId(R.id.input_password)))
                .perform(clearText(), typeText(passowrd), closeSoftKeyboard());
        onView(withId(R.id.btn_sign_in))
                .perform(click());
    }
}
