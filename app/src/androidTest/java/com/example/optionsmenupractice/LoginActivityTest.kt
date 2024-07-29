import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.optionsmenupractice.R
import com.example.optionsmenupractice.main.activities.HomeActivity
import com.example.optionsmenupractice.main.activities.LoginActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testLoginUser_emptyUsernameAndPassword() {
        onView(withId(R.id.login_button)).perform(click())
        // Verify that Snackbar is displayed with the expected text
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Please fill in login details"))))
    }

    @Test
    fun testFailedLogin() {

        onView(withId(R.id.usernameTXT)).perform(replaceText("testuser"))
        onView(withId(R.id.passwordtxt)).perform(replaceText("wrongpassword"))

        // Click on the login button
        onView(withId(R.id.login_button)).perform(click())

        // Verify that "Login failed" AlertDialog is displayed
        onView(withText("Login failed")).check(matches(isDisplayed()))
    }
}
