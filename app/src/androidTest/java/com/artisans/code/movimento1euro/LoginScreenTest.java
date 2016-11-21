package com.artisans.code.movimento1euro;

import android.content.ComponentName;
import android.provider.Settings;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.*;
import android.support.test.espresso.core.deps.guava.base.Strings;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.artisans.code.movimento1euro.menus.LoginActivity;
import com.artisans.code.movimento1euro.menus.MainMenu;
import com.facebook.AccessToken;
import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import junit.framework.Assert;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
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

    private static final String TAG = LoginScreenTest.class.getSimpleName();
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
    public void FacebookLoginAndLogout() throws Exception{
        LoginManager.getInstance().logOut();
        closeSoftKeyboard();
        onView(withId(R.id.facebook_login_button))
                .perform(click());
        String fbToken = AccessToken.getCurrentAccessToken().getToken();
        String fbId = AccessToken.getCurrentAccessToken().getUserId();
        Log.e(TAG,"FACEBOOK CLIENT TOKEN: " + fbToken);
        Assert.assertNotNull(fbToken);
        Assert.assertNotNull(fbId);
        Intents.intended(hasComponent(new ComponentName(getTargetContext(), MainMenu.class)));

        onView(withContentDescription(getTargetContext().getString(R.string.navigation_drawer_open))).perform(click());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logout));
        if(AccessToken.getCurrentAccessToken() == null){
            fbToken = null;
            fbId = null;
        }else{
            fbToken = AccessToken.getCurrentAccessToken().getToken();
            fbId = AccessToken.getCurrentAccessToken().getUserId();
        }

        Log.e(TAG,"FACEBOOK CLIENT TOKEN: " + fbToken);
        Assert.assertNull(fbToken);
        Assert.assertNull(fbId);
    }

    @Test
    public void clickSignInButton_FailedLogin() throws Exception {
        enterCredentials("ThisIsAFailedEmailAddress@yahoo.pt", "password");

        onView(withText(startsWith(getTargetContext().getString(R.string.failed_login)))).
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
