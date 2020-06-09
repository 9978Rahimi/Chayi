package ir.coleo.alexa;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ir.coleo.chayi.pipline.PipLine;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(JUnit4.class)
public class MainActivityTest {


    private String phoneToBeTyped;
    private String codeToBeTyped;
    private IdlingResource mIdlingResource;

//    @Rule
//    public ActivityScenarioRule<MainActivity> activityScenarioRule
//            = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void initValidString() {
        phoneToBeTyped = "09384142925";
        codeToBeTyped = "1234";
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity((ActivityScenario.ActivityAction<MainActivity>) activity -> {
            mIdlingResource = PipLine.getIdlingResource();
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource);
        });
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.phone_textView)).perform(typeText(phoneToBeTyped), closeSoftKeyboard());
        onView(withId(R.id.phone_button))
                .perform(click());

        onView(withId(R.id.code_edit_text)).perform(typeText(codeToBeTyped), closeSoftKeyboard());
        onView(withId(R.id.code_button))
                .perform(click());

//        onView(withId(R.id.code_edit_text)).check(matches(withText(stringToBeTyped)));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}